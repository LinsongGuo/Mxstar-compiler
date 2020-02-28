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
				"the function \"" + identifier + "()\" has the same name with previous function."
			);
		}		
		//check return type
		TypeNode typeNode = node.getType();
		if (typeNode != null) {
			String typeIdentifier = typeNode.toString();
			Type type = resolveType(typeIdentifier);
			if (type == null) {
				errorReminder.error(node.getLoc(), 
					"the return type \"" + typeIdentifier + "\" is not declared in this scope."
				);
				return null;
			}
			if (typeNode instanceof ArrayTypeNode) {
				int dimension = ((ArrayTypeNode) typeNode).getDimension();
				FunctSymbol functSymbol = new FunctSymbol(this, identifier, new ArrayType(typeIdentifier, dimension));
				functList.put(identifier, functSymbol);
				return functSymbol;
			}
			else {
				FunctSymbol functSymbol = new FunctSymbol(this, identifier, type);
				functList.put(identifier, functSymbol);
				return functSymbol;
			}
		}
		else {
			errorReminder.error(node.getLoc(), "the function should have return value.");
			return null;
		}
	}
	
	@Override
	public Scope defineClass(ClassDefNode node, ErrorReminder errorReminder) {
		String identifier = node.getIdentifier();
		if (typeList.containsKey(identifier)) {
			errorReminder.error(node.getLoc(), 
				"the class \"" + identifier + "\" has the same name with previous class." 
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
			errorReminder.error(node.getLoc(), "the variable \"" + identifier + "\" is not declared in this scope.");
			return null;
		}
		else
			return varList.get(identifier);
	}
	
	@Override
	public VarSymbol resovleArray(ArrayExprNode node, ErrorReminder errorReminder) {
		String identifier = node.getIdentifier();
		if(!varList.containsKey(identifier)) {
			errorReminder.error(node.getLoc(), "the variable \"" + identifier + "\" is not declared in this scope.");
			return null;
		}
		
		ArrayList<ExprNode> indexExpr = node.getIndexExpr();
		for(ExprNode item : indexExpr) {
			if (!(item.getType() instanceof IntType)) {
				errorReminder.error(item.getLoc(), "the index of the array shoule be an integer.");
				return null;
			}
		}
		
		VarSymbol var = varList.get(identifier);
		Type type = var.getType();
		int dimension = node.getDimension();
		if (type instanceof ArrayType) {
			int tmp = ((ArrayType)type).getDimension();
			if (dimension > tmp) {
				errorReminder.error(node.getLoc(), "the dimension of the array \"" + identifier + "\" is invalid.");
				return null;
			}
			if (dimension >= tmp){
				String typeIdentifier = type.toString();
				return new VarSymbol(identifier, resolveType(typeIdentifier));
			}
			else {
				return new VarSymbol(identifier, new ArrayType(identifier, tmp - dimension));
			}
		} 
		else {
			errorReminder.error(node.getLoc(), "the dimension of the array \"" + identifier + "\" is invalid.");
			return null;
		}
	}
	
	@Override
	public FunctSymbol resolveFunct(FunctExprNode node, ErrorReminder errorReminder) {
		String identifier = node.getIdentifier();
		if (!functList.containsKey(identifier)) {
			errorReminder.error(node.getLoc(), "the function \"" + identifier + "\" is not declared in this scope.");
			return null;
		}
		FunctSymbol functSymbol = functList.get(identifier);
		LinkedHashMap<String, Type> argueList = functSymbol.getParaList();
		ArrayList<ExprNode> paraList = node.getParaList();
		if (paraList.size() < argueList.size()) {
			errorReminder.error(node.getLoc(), "too few parameters to function " + identifier + ".");
			return null;
		}
		if (paraList.size() > argueList.size()) {
			errorReminder.error(node.getLoc(), "too many parameters to function " + identifier + ".");
			return null;
		}
		int i = 0;
		for (Map.Entry<String, Type> entry : argueList.entrySet()) {
			if (i >= paraList.size()) 
				break;
			Type tmp1 = entry.getValue(), tmp2 = paraList.get(i).getType();
			int d1 = (tmp1 instanceof ArrayType) ? ((ArrayType)tmp1).getDimension() : 0;
			int d2 = (tmp2 instanceof ArrayType) ? ((ArrayType)tmp2).getDimension() : 0;
			if (!tmp1.toString().equals(tmp2.toString()) || d1 != d2) {
				errorReminder.error(paraList.get(i).getLoc(), "the parameter's type is not matched.");
				return null;
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
	public boolean inFunctScope() {
		return false;
	}
	
	@Override
	public boolean inClassScope() {
		return false;
	}
	
	@Override
	public ClassSymbol getClassSymbol() {
		return null;
	}
	
	public void setBuiltInType() {
		typeList.put("bool", new BoolType());
		typeList.put("int", new IntType());
		typeList.put("string", new StringType());
		typeList.put("void", new VoidType());
	}
	
	public void setBuiltInFunction(Scope gobalScope) {
		LinkedHashMap<String, Type> paraList1 = new LinkedHashMap<String, Type>();
		paraList1.put("str", new StringType());
		functList.put("print", new FunctSymbol(gobalScope, "print", new VoidType(), paraList1));
		
		LinkedHashMap<String, Type> paraList2 = new LinkedHashMap<String, Type>();
		paraList2.put("str", new StringType());
		functList.put("println", new FunctSymbol(gobalScope, "println", new VoidType(), paraList2));	
		
		LinkedHashMap<String, Type> paraList3 = new LinkedHashMap<String, Type>();
		paraList3.put("n", new IntType());
		functList.put("printInt", new FunctSymbol(gobalScope, "printInt", new VoidType(), paraList3));
		
		LinkedHashMap<String, Type> paraList4 = new LinkedHashMap<String, Type>();
		paraList4.put("n", new IntType());
		functList.put("printlnInt", new FunctSymbol(gobalScope, "printlnInt", new VoidType(), paraList4));	
		
		functList.put("getString", new FunctSymbol(gobalScope, "getString", new StringType(), new LinkedHashMap<String, Type>()));	
		
		functList.put("getInt", new FunctSymbol(gobalScope, "getInt", new IntType(), new LinkedHashMap<String, Type>()));	
		
		LinkedHashMap<String, Type> paraList7 = new LinkedHashMap<String, Type>();
		paraList7.put("i", new IntType());
		functList.put("toString", new FunctSymbol(gobalScope, "toString", new StringType(), paraList7));	
	}
}
