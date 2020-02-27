package Scope;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map;

import AST.ArrayTypeNode;
import AST.ClassDefNode;
import AST.ExprNode;
import AST.FunctDefNode;
import AST.FunctExprNode;
import AST.TypeNode;
import AST.VarDefNode;
import utility.ErrorReminder;

public class ClassSymbol extends ScopedSymbol implements Type {
	private LinkedHashMap<String, FunctSymbol> functList;
	
	public ClassSymbol(Scope parent, String identifier) {
		super(parent, identifier);
		functList = new LinkedHashMap<String, FunctSymbol>();
	}
	
	@Override
	public String toString() {
		return identifier;
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
	public void defineParaList(ArrayList<VarDefNode> paraList, ErrorReminder errorReminder) {
		
	}
	
	@Override
	public boolean inFunctScope() {
		return false;
	}
	
	@Override
	public boolean inClassScope() {
		return true;
	}
	
	@Override
	public boolean isFunct() {
		return false;
	}
	
	@Override
	public ClassSymbol getClassSymbol() {
		return this;
	}
	
	
	public VarSymbol getVarSymbol(String identifier) {
		if (!varList.containsKey(identifier)) {
			return null;
		}
		return varList.get(identifier);
	}
	
	public FunctSymbol getFunctSymbol(String identifier) {
		if (!functList.containsKey(identifier)) {
			return null; 
		}
		return functList.get(identifier);
	}
}
