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
	protected LinkedHashMap<String, FunctSymbol> functList;
	
	public ClassSymbol(Scope parent, String identifier) {
		super(parent, identifier);
		functList = new LinkedHashMap<String, FunctSymbol>();
	}
	
	@Override
	public String toString() {
		return identifier;
	}
	
	@Override
	public String typeString() {
		return identifier;
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
	public FunctSymbol getFunctSymbol() {
		return null;
	}
	
	@Override
	public ClassSymbol getClassSymbol() {
		return this;
	}
	
	@Override
	public boolean isFunct() {
		return false;
	}
	
	@Override
	public boolean isBuiltInType() {
		return false;
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
				"the class \'" + toString() + "\" has no member named " + identifier + "."
			);
			return null;
		}

		ArrayList<ExprNode> indexExpr = node.getIndexExpr();
		for(ExprNode item : indexExpr) {
			if(item != null) {
				Type tmp = item.getType();
				if (!(tmp instanceof IntType)) {
					errorReminder.error(item.getLoc(), "cannot convert \'" + tmp.toString() + "\' to \'int\' in initilization.");
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
	
	public FunctSymbol findFunct(FunctExprNode node, ErrorReminder errorReminder) {
		String identifier = node.getIdentifier();
		if (!functList.containsKey(identifier)) {
			errorReminder.error(node.getLoc(),
				"the type \'" + toString() + "\" has no member named \'" + node.getIdentifier() + "\'."
			);
			return null;
		}
		FunctSymbol functSymbol = functList.get(identifier);
		LinkedHashMap<String, Type> argueList = functSymbol.getParaList();
		ArrayList<ExprNode> paraList = node.getParaList();
		if (paraList.size() < argueList.size()) {
			errorReminder.error(node.getLoc(), "too few parameters to function \'" + identifier + "\'.");
		}
		if (paraList.size() > argueList.size()) {
			errorReminder.error(node.getLoc(), "too many parameters to function \'" + identifier + "\'.");
		}
		int i = 0;
		for (Map.Entry<String, Type> entry : argueList.entrySet()) {
			if (i >= paraList.size()) 
				break;
			Type tmp1 = entry.getValue(), tmp2 = paraList.get(i).getType();
			if (tmp2 != null) {
				if (!tmp1.toString().equals(tmp2.toString())) {
					errorReminder.error(paraList.get(i).getLoc(), 
						"cannot convert \'" + tmp2.toString() + "\' to \'" + tmp1.toString() + "\' in initialization."
					);
				}
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
