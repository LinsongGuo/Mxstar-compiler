package Scope;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Map.Entry;

import AST.ArrayExprNode;
import AST.ArrayTypeNode;
import AST.ClassDefNode;
import AST.ExprNode;
import AST.FunctDefNode;
import AST.FunctExprNode;
import AST.TypeNode;
import AST.VarDefNode;
import AST.VarExprNode;
import utility.ErrorReminder;
import utility.Location;

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
	public Scope defineFunct(FunctDefNode node, ErrorReminder errorReminder) {
		//check identifier
		String identifier = node.getIdentifier();
		if (functList.containsKey(identifier)) {
			errorReminder.error(node.getLoc(), 
				"redeclaration of function \'" + identifier + "\'."
			);
		}		
		//check return type
		TypeNode typeNode = node.getType();
		FunctSymbol functSymbol;
		if (typeNode != null) {
			String typeIdentifier = typeNode.typeString();
			Type type = resolveType(typeIdentifier);
			if (type == null) {
				errorReminder.error(node.getLoc(), 
					"the class \'" + typeIdentifier + "\' was not declared in this scope."
				);
				functSymbol = new FunctSymbol(this, identifier, null);
			}
			else if (typeNode instanceof ArrayTypeNode) {
				int dimension = ((ArrayTypeNode) typeNode).getDimension();
				functSymbol = new FunctSymbol(this, identifier, new ArrayType(typeIdentifier, dimension));
			}
			else {
				functSymbol = new FunctSymbol(this, identifier, type);
			}
		}
		else {
			errorReminder.error(node.getLoc(), "the function should have return value.");
			functSymbol = new FunctSymbol(this, identifier, null);
		}
		functList.put(identifier, functSymbol);
		return functSymbol;
	}
	
	@Override
	public Scope defineClass(ClassDefNode node, ErrorReminder errorReminder) {
		String identifier = node.getIdentifier();
		if (typeList.containsKey(identifier)) {
			errorReminder.error(node.getLoc(), 
				"redeclaration of the class \'" + identifier + "\'." 
			);
		}
		ClassSymbol classSymbol = new ClassSymbol(this, identifier);
		typeList.put(identifier, classSymbol);
		return classSymbol;	
	}
	
	@Override
	public Type resolveType(String identifier) {
		if(typeList.containsKey(identifier))
			return typeList.get(identifier);
		else 
			return null;
	}
	
	@Override
	public VarSymbol resovleVar(VarExprNode node, ErrorReminder errorReminder) {
		String identifier = node.getIdentifier();
		if(!varList.containsKey(identifier)) {
			errorReminder.error(node.getLoc(), "the variable \'" + identifier + "\' was not declared in this scope.");
			return null;
		}
		else
			return varList.get(identifier);
	}
	
	@Override
	public VarSymbol resovleArray(ArrayExprNode node, ErrorReminder errorReminder) {
		String identifier = node.getIdentifier();
		if(!varList.containsKey(identifier)) {
			errorReminder.error(node.getLoc(), "the variable \'" + identifier + "\' was not declared in this scope.");
			return null;
		}
		
		ArrayList<ExprNode> indexExpr = node.getIndexExpr();
		for(ExprNode item : indexExpr) {
			if(item != null) {
				Type tmp = item.getType();
				if (!(tmp instanceof IntType)) {
					errorReminder.error(item.getLoc(), "cannot convert \'" + tmp.toString() + "\' to \'int\'.");
				}
			}
			else {
				errorReminder.error(item.getLoc(), "empty index of array.");
			}	
		}
		
		VarSymbol var = varList.get(identifier);
		Type type = var.getType();
		int dimension = node.getDimension();
		if (type instanceof ArrayType) {
			int tmp = ((ArrayType)type).getDimension();
			if (dimension > tmp) {
				errorReminder.error(node.getLoc(), "invalid dimension of \'" + identifier + "\'.");
				return null;
			}
			else {
				String typeIdentifier = type.typeString();
				if (dimension == tmp)
					return new VarSymbol(identifier, resolveType(typeIdentifier));
				else 
					return new VarSymbol(identifier, new ArrayType(typeIdentifier, tmp - dimension));
			} 
		} 
		else {
			errorReminder.error(node.getLoc(), "invalid dimension of \'" + identifier + "\'.");
			return null;
		}
	}
	
	@Override
	public FunctSymbol resolveFunct(FunctExprNode node, ErrorReminder errorReminder) {
		String identifier = node.getIdentifier();
		if (!functList.containsKey(identifier)) {
			errorReminder.error(node.getLoc(), "the function \'" + identifier + "\' was not declared in this scope.");
			return null;
		}
		FunctSymbol functSymbol = functList.get(identifier);
		LinkedHashMap<String, Type> argueList = functSymbol.getParaList();
		ArrayList<ExprNode> paraList = node.getParaList();
		if (paraList.size() < argueList.size()) {
			errorReminder.error(node.getLoc(), "too few parameters to function \'" + identifier + "\'.");
			//return null;
		}
		if (paraList.size() > argueList.size()) {
			errorReminder.error(node.getLoc(), "too many parameters to function \'" + identifier + "\'.");
			//return null;
		}
		int i = 0;
		for (Map.Entry<String, Type> entry : argueList.entrySet()) {
			if (i >= paraList.size()) 
				break;
			Type tmp1 = entry.getValue(), tmp2 = paraList.get(i).getType();
			if (tmp2 != null) {
				//int d1 = (tmp1 instanceof ArrayType) ? ((ArrayType)tmp1).getDimension() : 0;
				//int d2 = (tmp2 instanceof ArrayType) ? ((ArrayType)tmp2).getDimension() : 0;
				if (!tmp1.toString().equals(tmp2.toString())) {
					errorReminder.error(paraList.get(i).getLoc(), 
						"cannot convert \'" + tmp2.toString() + "\' to \'" + tmp1.toString() + "\' in initialization."
					);
					//return null;
				}
			}
			i++;
		}
		return functSymbol;
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
	public FunctSymbol getFunctSymbol() {
		return null;
	}
	
	@Override
	public ClassSymbol getClassSymbol() {
		return null;
	}
	
	public void setBuiltInType(Scope gobalScope) {
	}
	
	public void setBuiltInMember(Scope gobalScope, StringType stringTemplate) {
		typeList.put("bool", new BoolType());
		typeList.put("int", new IntType());
		typeList.put("void", new VoidType());
		typeList.put("string", stringTemplate);
	
		LinkedHashMap<String, Type> paraList1 = new LinkedHashMap<String, Type>();
		paraList1.put("str", stringTemplate);
		functList.put("print", new FunctSymbol(gobalScope, "print", new VoidType(), paraList1));
		
		LinkedHashMap<String, Type> paraList2 = new LinkedHashMap<String, Type>();
		paraList2.put("str", stringTemplate);
		functList.put("println", new FunctSymbol(gobalScope, "println", new VoidType(), paraList2));	
		
		LinkedHashMap<String, Type> paraList3 = new LinkedHashMap<String, Type>();
		paraList3.put("n", new IntType());
		functList.put("printInt", new FunctSymbol(gobalScope, "printInt", new VoidType(), paraList3));
		
		LinkedHashMap<String, Type> paraList4 = new LinkedHashMap<String, Type>();
		paraList4.put("n", new IntType());
		functList.put("printlnInt", new FunctSymbol(gobalScope, "printlnInt", stringTemplate, paraList4));	
		
		functList.put("getString", new FunctSymbol(gobalScope, "getString", stringTemplate, new LinkedHashMap<String, Type>()));	
		
		functList.put("getInt", new FunctSymbol(gobalScope, "getInt", new IntType(), new LinkedHashMap<String, Type>()));	
		
		LinkedHashMap<String, Type> paraList7 = new LinkedHashMap<String, Type>();
		paraList7.put("i", new IntType());
		functList.put("toString", new FunctSymbol(gobalScope, "toString", stringTemplate, paraList7));	
	}
}
