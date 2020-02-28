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
import AST.VarDefNode;
import AST.VarExprNode;
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
	public Scope defineFunct(FunctDefNode node, ErrorReminder errorReminder) {
		//check identifier
		String identifier = node.getIdentifier();
		if (functList.containsKey(identifier)) {
			errorReminder.error(node.getLoc(), 
				"the function \"" + identifier + "()\" has the same name with previous function."
			);
			return null;
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
	public void defineParaList(ArrayList<VarDefNode> paraList, ErrorReminder errorReminder) {
		
	}
	
	@Override
	public FunctSymbol resolveFunct(FunctExprNode node, ErrorReminder errorReminder) {
		String identifier = node.getIdentifier();
		if (!functList.containsKey(identifier)) {
			return parent.resolveFunct(node, errorReminder);
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
	public boolean isBuiltInType() {
		return false;
	}
	
	@Override
	public ClassSymbol getClassSymbol() {
		return this;
	}
	
	public VarSymbol findVar(VarExprNode node, ErrorReminder errorReminder) {
		String identifier = node.getIdentifier();
		if(!varList.containsKey(identifier)) {
			errorReminder.error(node.getLoc(),
				"the class \'" + toString() + "\" has no member named " + node.getIdentifier()
			);
			return null;
		}
		else 
			return varList.get(identifier);
	}
	
	public VarSymbol findArray(ArrayExprNode node, ErrorReminder errorReminder) {
		String identifier = node.getIdentifier();
		if(!varList.containsKey(identifier)) {
			errorReminder.error(node.getLoc(),
				"the class \'" + toString() + "\" has no member named " + node.getIdentifier()
			);
			return null;
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
	
	public FunctSymbol findFunct(FunctExprNode node, ErrorReminder errorReminder) {
		String identifier = node.getIdentifier();
		if (!functList.containsKey(identifier)) {
			errorReminder.error(node.getLoc(),
				"the class \'" + toString() + "\" has no member named " + node.getIdentifier()
			);
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
	
	public Scope defineConstructor() {
		String identifier = toString();
		FunctSymbol functSymbol = new FunctSymbol(this, identifier);
		functList.put(identifier, functSymbol);
		return functSymbol;
	}
}
