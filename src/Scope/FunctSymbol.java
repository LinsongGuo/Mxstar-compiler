package Scope;

import java.util.ArrayList;
import java.util.LinkedHashMap;

import AST.FunctDefNode;
import AST.VarDefNode;
import AST.TypeNode;
import AST.ArrayTypeNode;
import AST.ExprNode;
import AST.FunctExprNode;
import utility.ErrorReminder;

public class FunctSymbol extends ScopedSymbol {
	private Type type;
	private boolean constructor;
	private LinkedHashMap<String, VarSymbol> paraList;
	
	public FunctSymbol(Scope parent, String identifier, Type type) {
		super(parent, identifier);
		this.type = type;
		this.paraList = new LinkedHashMap<String, VarSymbol>();
		this.constructor = false;
	}

	public FunctSymbol(Scope parent, String identifier, Type type, LinkedHashMap<String, VarSymbol> paraList, LinkedHashMap<String, VarSymbol> varList) {
		super(parent, identifier);
		this.type = type;
		this.paraList = paraList;
		this.varList = varList;
		this.constructor = false;
	}
	
	public void setConstructor() {
		constructor = true;
	}
	
	public boolean isConstructor() {
		return constructor;
	}
	
	@Override
	public FunctSymbol declareFunct(FunctDefNode node, ErrorReminder errorReminder) {
		errorReminder.error(node.getLoc(), "invalid function definion.");
		return null;
	}
	
	@Override
	public void declareParaList(ArrayList<VarDefNode> paraList, ErrorReminder errorReminder) {
		for(VarDefNode item : paraList) {
			if (item == null) continue;
			TypeNode typeNode = item.getType();
			String typeIdentifier = typeNode.typeString();
			Type paraType = resolveType(typeIdentifier);
			if (paraType == null) {
				errorReminder.error(item.getLoc(), 
					"\'" + typeIdentifier + "\' does not name a type."
				);
			}
			else if (paraType.equals("void")) {
				errorReminder.error(item.getLoc(), "the variable declared void.");
				return;
			}
			else {
				String paraIdentifier = item.getIdentifier();
				if(this.paraList.containsKey(paraIdentifier)) {
					errorReminder.error(item.getLoc(),
						"redeclaration of \'" + paraIdentifier + "\'."
					);
				}
				else {
					if (typeNode instanceof ArrayTypeNode) {
						ArrayType tmp = new ArrayType(getGlobalScope(), typeIdentifier, ((ArrayTypeNode) typeNode).getDimension());
						this.paraList.put(paraIdentifier, new VarSymbol(paraIdentifier, tmp));
						this.varList.put(paraIdentifier, new VarSymbol(paraIdentifier, tmp));
					}
					else {
						this.paraList.put(paraIdentifier, new VarSymbol(paraIdentifier, paraType));
						this.varList.put(paraIdentifier, new VarSymbol(paraIdentifier, paraType));
					}
				}
			}
		}
	}
	
	@Override
	public FunctSymbol resolveFunct(FunctExprNode node, ErrorReminder errorReminder) {
		return parent.resolveFunct(node, errorReminder);
	}
	
	@Override
	public FunctSymbol InFunctSymbol() {
		return this;
	}
	
	@Override
	public ClassSymbol InClassSymbol() {
		return parent.InClassSymbol();
	}
	
	@Override
	public FunctSymbol getFunctScope(String identifier) {
		return null;
	}
	
	@Override
	public boolean isVar() {
		return false;
	}

	@Override
	public boolean isFunct() {
		return true;
	}
	
	public LinkedHashMap<String, VarSymbol> getParaList() {
		return paraList;
	}
	
	public Type getType() {
		return type;
	}
	
	public boolean matchParameters(FunctExprNode node) {
		ArrayList<ExprNode> paraList = node.getParaList();
		return paraList.size() == this.paraList.size();
	}

}
