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
	public Type resolveType(String identifier) {
		if(typeList.containsKey(identifier))
			return typeList.get(identifier);
		Scope parent = getEnclosingScope();
		if (parent != null) 
			return parent.resolveType(identifier);
		return null;
	}
	
	@Override
	public VarSymbol resovleVar(ArrayExprNode node, ErrorReminder errorReminder) {
		String identifier = node.getIdentifier();
		if(!varList.containsKey(identifier)) {
			errorReminder.error(node.getLoc(), "the variable \"" + identifier + "\" is not declared in this scope.");
			return null;
		}
		VarSymbol var = (VarSymbol)varList.get(identifier);
		Type type = var.getType();
		int dimension = node.getDimension();
		if (type instanceof ArrayType) {
			int tmp = ((ArrayType)type).getDimension();
			if (dimension > tmp) {
				errorReminder.error(node.getLoc(), "the dimension of the array \"" + identifier + "\" is invalid.");
			}
			if (dimension >= tmp){
				String typeIdentifier = type.toString();
				if (typeIdentifier.equals("bool"))
					return new VarSymbol(identifier, new BoolType());
				else if (typeIdentifier.equals("int"))
					return new VarSymbol(identifier, new IntType());
				else if (typeIdentifier.equals("string"))
					return new VarSymbol(identifier, new StringType());
				else 
					return new VarSymbol(identifier, new ClassSymbol(getGlobalScope(), typeIdentifier));
			}
			else {
				return new VarSymbol(identifier, new ArrayType(identifier, tmp - dimension));
			}
		} 
		else {
			if (dimension > 0) {
				errorReminder.error(node.getLoc(), "the dimension of the array \"" + identifier + "\" is invalid.");
			}
			return var;
		}
	}
	
	@Override
	public FunctSymbol resolveFunct(FunctExprNode node, ArrayList<Type> typeList, ErrorReminder errorReminder) {
		String identifier = node.getIdentifier();
		if (!functList.containsKey(identifier)) {
			errorReminder.error(node.getLoc(), "the function \"" + identifier + "\" is not declared in this scope.");
		}
		FunctSymbol functSymbol = functList.get(identifier);
		LinkedHashMap<String, Type> paraList = functSymbol.getParaList();
		if (typeList.size() < paraList.size()) {
			errorReminder.error(node.getLoc(), "too few parameters to function " + identifier);
		}
		if (typeList.size() > paraList.size()) {
			errorReminder.error(node.getLoc(), "too many parameters to function " + identifier);
		}
		int i = 0;
		ArrayList<ExprNode> exprList = node.getParaList();
		for (Map.Entry<String, Type> entry : paraList.entrySet()) {
			if (i >= exprList.size()) 
				break;
			Type tmp1 = entry.getValue(), tmp2 = typeList.get(i);
			int d1 = (tmp1 instanceof ArrayType) ? ((ArrayType)tmp1).getDimension() : 0;
			int d2 = (tmp2 instanceof ArrayType) ? ((ArrayType)tmp2).getDimension() : 0;
			if (!tmp1.toString().equals(tmp2.toString()) || d1 != d2) {
				errorReminder.error(exprList.get(i).getLoc(), "the parameter's type is not matched.");
			}
			i++;
		}
		return functSymbol;
	}
	
	
	@Override
	public Scope defineFunct(FunctDefNode node, ErrorReminder errorReminder) {
		TypeNode typeNode = node.getType();
		Type type = null;
		int dimension = 0;
		if (typeNode != null) {
			String typeIdentifier = typeNode.getIdentifier();
			type = resolveType(typeIdentifier);
			if (type == null) {
				errorReminder.error(node.getLoc(), 
					"the return type \"" + typeIdentifier + "\" is not declared in this scope."
				);
			}
			if (typeNode instanceof ArrayTypeNode) 
				dimension = ((ArrayTypeNode) typeNode).getDimension();	
		}
		
		//check identifier
		String identifier = node.getIdentifier();
		FunctSymbol functSymbol = new FunctSymbol(this, identifier, type, dimension);
		if (functList.containsKey(identifier)) {
			errorReminder.error(node.getLoc(), 
				"the function \"" + identifier + "()\" has the same name with previous function."
			);
		}
		functList.put(identifier, functSymbol);
		return functSymbol;
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
		return classSymbol;
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
		functList.put("print", new FunctSymbol(gobalScope, "print", new VoidType(), 0, paraList1));
		
		LinkedHashMap<String, Type> paraList2 = new LinkedHashMap<String, Type>();
		paraList2.put("str", new StringType());
		functList.put("println", new FunctSymbol(gobalScope, "println", new VoidType(), 0, paraList2));	
		
		LinkedHashMap<String, Type> paraList3 = new LinkedHashMap<String, Type>();
		paraList3.put("n", new IntType());
		functList.put("printInt", new FunctSymbol(gobalScope, "printInt", new VoidType(), 0, paraList3));
		
		LinkedHashMap<String, Type> paraList4 = new LinkedHashMap<String, Type>();
		paraList4.put("n", new IntType());
		functList.put("printlnInt", new FunctSymbol(gobalScope, "printlnInt", new VoidType(), 0, paraList4));	
		
		functList.put("getString", new FunctSymbol(gobalScope, "getString", new StringType(), 0, new LinkedHashMap<String, Type>()));	
		
		functList.put("getInt", new FunctSymbol(gobalScope, "getInt", new IntType(), 0, new LinkedHashMap<String, Type>()));	
		
		LinkedHashMap<String, Type> paraList7 = new LinkedHashMap<String, Type>();
		paraList7.put("i", new IntType());
		functList.put("toString", new FunctSymbol(gobalScope, "toString", new StringType(), 0, paraList7));	
	}
}
