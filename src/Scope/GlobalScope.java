package Scope;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map;
import AST.ArrayExprNode;
import AST.ArrayTypeNode;
import AST.ClassDefNode;
import AST.ExprNode;
import AST.FunctDefNode;
import AST.FunctExprNode;
import AST.TypeNode;
import AST.VarExprNode;
import utility.ErrorReminder;

public class GlobalScope extends BaseScope {
	private LinkedHashMap<String, Type> typeList;
	private LinkedHashMap<String, FunctSymbol> functList;
	
	public GlobalScope(Scope parent) {
		super(parent);
		this.typeList = new LinkedHashMap<String, Type>();
		this.functList = new LinkedHashMap<String, FunctSymbol>();
	}
	
	@Override
	public Scope getGlobalScope() {
		return this;
	}
	
	@Override
	public ClassSymbol declareClass(ClassDefNode node, ErrorReminder errorReminder) {
		String identifier = node.getIdentifier();
		if (typeList.containsKey(identifier)) {
			errorReminder.error(node.getLoc(), 
				"redeclaration of the class \'" + identifier + "\'." 
			);
			return null;
		}
		ClassSymbol classSymbol = new ClassSymbol(this, identifier);
		typeList.put(identifier, classSymbol);
		return classSymbol;	
	}
	
	@Override
	public FunctSymbol declareFunct(FunctDefNode node, ErrorReminder errorReminder) {
		//check return type
		String identifier = node.getIdentifier();
		TypeNode typeNode = node.getType();
		Type retType;
		if (typeNode == null) {
			errorReminder.error(node.getLoc(), "function should have return value.");
			return null;		
		}
		else {
			String typeIdentifier = typeNode.typeString();
			retType = resolveType(typeIdentifier);
			if (retType == null) {
				errorReminder.error(node.getLoc(), 
					"\'" + typeIdentifier + "\' does not name a type."
				);
				return null;
			}
			else if (typeNode instanceof ArrayTypeNode) {
				int dimension = ((ArrayTypeNode) typeNode).getDimension();
				retType = new ArrayType(getGlobalScope(), typeIdentifier, dimension);
			}
		}
		//check identifier
		if (functList.containsKey(identifier)) {
			errorReminder.error(node.getLoc(), 
				"redeclaration of function \'" + identifier + "\'."
			);
			return null;
		}		
		FunctSymbol functSymbol = new FunctSymbol(this, identifier, retType);
		functList.put(identifier, functSymbol);
		return functSymbol;
	}
	
	@Override
	public Type resolveType(String identifier) {
		if(typeList.containsKey(identifier))
			return typeList.get(identifier);
		else 
			return null;
	}
	
	@Override
	public VarSymbol resolveVar(VarExprNode node, ErrorReminder errorReminder) {
		String identifier = node.getIdentifier();
		if(!varList.containsKey(identifier)) {
			errorReminder.error(node.getLoc(), "variable \'" + identifier + "\' was not declared in this scope.");
			return null;
		}
		else
			return varList.get(identifier);
	}
	
	@Override
	public VarSymbol resolveArray(ArrayExprNode node, ErrorReminder errorReminder) {
		String identifier = node.getIdentifier();
		//check identifier
		if(!varList.containsKey(identifier)) {
			errorReminder.error(node.getLoc(), "variable \'" + identifier + "\' was not declared in this scope.");
			return null;
		}
		//get type
		VarSymbol var = varList.get(identifier);
		Type type = var.getType();
		if (type instanceof ArrayType) {
			int tmp = ((ArrayType)type).getDimension();
			String typeIdentifier = type.typeString();
			if (tmp == 1)
				return new VarSymbol(identifier, resolveType(typeIdentifier), null);
			else 
				return new VarSymbol(identifier, new ArrayType(getGlobalScope(), typeIdentifier, tmp - 1), null);
		} 
		else {
			errorReminder.error(node.getLoc(), "\'" + identifier + "\' is a variable not an array.");
			return null;
		}
	}
	
	@Override
	public FunctSymbol resolveFunct(FunctExprNode node, ErrorReminder errorReminder) {
		String identifier = node.getIdentifier();
		if (typeList.containsKey(identifier)) {
			Type type = typeList.get(identifier);
			if ((type instanceof BoolType) || (type instanceof IntType) || (type instanceof StringType)) {
				errorReminder.error(node.getLoc(), "\'" + identifier + "\' cannot be a function name.");
				return null;
			}
			else {
				return ((ClassSymbol)type).findFunct(node, errorReminder);
			}
		}
		else {
			if (!functList.containsKey(identifier)) {
				errorReminder.error(node.getLoc(), "function \'" + identifier + "\' was not declared in this scope.");
				return null;
			}
			FunctSymbol functSymbol = functList.get(identifier);
			LinkedHashMap<String, VarSymbol> argueList = functSymbol.getParaList();
			ArrayList<ExprNode> paraList = node.getParaList();
			if (paraList.size() < argueList.size()) {
				errorReminder.error(node.getLoc(), "too few parameters to function \'" + identifier + "\'.");
			}
			if (paraList.size() > argueList.size()) {
				errorReminder.error(node.getLoc(), "too many parameters to function \'" + identifier + "\'.");
			}
			int i = 0;
			for (Map.Entry<String, VarSymbol> entry : argueList.entrySet()) {
				if (i >= paraList.size()) 
					break;
				Type tmp1 = entry.getValue().getType(), tmp2 = paraList.get(i).getType();
				if (tmp2 != null) {
					if (tmp2 instanceof NullType) {
						if (!(tmp1 instanceof ClassSymbol)) {
							errorReminder.error(paraList.get(i).getLoc(),
								"null expression cannot be apply to type \'" + tmp1.toString() + "\'."
							);
						}
					}
					else if (!tmp1.toString().equals(tmp2.toString())) {
						errorReminder.error(paraList.get(i).getLoc(), 
							"cannot convert \'" + tmp2.toString() + "\' to \'" + tmp1.toString() + "\' in initialization."
						);
					}
				}
				i++;
			}
			return functSymbol;
		}
	}
	
	@Override
	public boolean inLoopScope() {
		return false;
	}
	
	@Override
	public boolean inIfScope() {
		return false;
	}
	
	@Override
	public FunctSymbol InFunctSymbol() {
		return null;
	}
	
	@Override
	public ClassSymbol InClassSymbol() {
		return null;
	}
	
	@Override
	public FunctSymbol getFunctScope(String identifier) {
		return functList.get(identifier);
	}
	
	@Override
	public ClassSymbol getClassScope(String identifier) {
		return (ClassSymbol)typeList.get(identifier);
	}
	
	@Override
	public boolean duplicateClass(String identifier) {
		return typeList.containsKey(identifier);
	}

	public void setBuiltInMember(Scope gobalScope, StringType stringTemplate) {
		typeList.put("bool", new BoolType());
		typeList.put("int", new IntType());
		typeList.put("void", new VoidType());
		typeList.put("string", stringTemplate);
	
		LinkedHashMap<String, VarSymbol> paraList = new LinkedHashMap<String, VarSymbol>();
		LinkedHashMap<String, VarSymbol> varList = new LinkedHashMap<String, VarSymbol>();
		paraList.put("str", new VarSymbol("str", stringTemplate, null));
		varList.put("str", new VarSymbol("str", stringTemplate, null));
		functList.put("print", new FunctSymbol(gobalScope, "print", new VoidType(), paraList, varList));
		
		LinkedHashMap<String, VarSymbol> paraList2 = new LinkedHashMap<String, VarSymbol>();
		LinkedHashMap<String, VarSymbol> varList2 = new LinkedHashMap<String, VarSymbol>();
		paraList2.put("str", new VarSymbol("str", stringTemplate, null));
		varList2.put("str", new VarSymbol("str", stringTemplate, null));
		functList.put("println", new FunctSymbol(gobalScope, "println", new VoidType(), paraList2, varList2));	
		
		LinkedHashMap<String, VarSymbol> paraList3 = new LinkedHashMap<String, VarSymbol>();
		LinkedHashMap<String, VarSymbol> varList3 = new LinkedHashMap<String, VarSymbol>();
		paraList3.put("n", new VarSymbol("n", new IntType(), null));
		varList3.put("n", new VarSymbol("n", new IntType(), null));
		functList.put("printInt", new FunctSymbol(gobalScope, "printInt", new VoidType(), paraList3, varList3));
		
		LinkedHashMap<String, VarSymbol> paraList4 = new LinkedHashMap<String, VarSymbol>();
		LinkedHashMap<String, VarSymbol> varList4 = new LinkedHashMap<String, VarSymbol>();
		paraList4.put("n", new VarSymbol("n", new IntType(), null));
		varList4.put("n", new VarSymbol("n", new IntType(), null));
		functList.put("printlnInt", new FunctSymbol(gobalScope, "printlnInt", new VoidType(), paraList4, varList4));	
		
		functList.put("getString", new FunctSymbol(gobalScope, "getString", stringTemplate));	
		
		functList.put("getInt", new FunctSymbol(gobalScope, "getInt", new IntType()));	
		
		LinkedHashMap<String, VarSymbol> paraList7 = new LinkedHashMap<String, VarSymbol>();
		LinkedHashMap<String, VarSymbol> varList7 = new LinkedHashMap<String, VarSymbol>();
		paraList7.put("i", new VarSymbol("i", new IntType(), null));
		varList7.put("i", new VarSymbol("i", new IntType(), null));
		functList.put("toString", new FunctSymbol(gobalScope, "toString", stringTemplate, paraList7, varList7));	
	}
	
	public LinkedHashMap<String, FunctSymbol> getFunctList() {
		return functList;
	}
	
	public Type findClassSymbol(String name) {
		return typeList.get(name);
	}
}
