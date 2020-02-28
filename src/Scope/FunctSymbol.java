package Scope;

import java.util.ArrayList;
import java.util.LinkedHashMap;

import AST.ClassDefNode;
import AST.FunctDefNode;
import AST.VarDefNode;
import AST.TypeNode;
import AST.ArrayTypeNode;
import AST.ExprNode;
import AST.FunctExprNode;
import utility.ErrorReminder;

public class FunctSymbol extends ScopedSymbol {
	private Type type;
	private LinkedHashMap<String, Type> paraList;
	private ArrayList<LocalScope> children;
	
	public FunctSymbol(Scope parent, String identifier) {
		super(parent, identifier);
		this.type = null;
		this.paraList = new LinkedHashMap<String, Type>();
		this.children = new ArrayList<LocalScope>();
	}
	
	public FunctSymbol(Scope parent, String identifier, Type type) {
		super(parent, identifier);
		this.type = type;
		this.paraList = new LinkedHashMap<String, Type>();
		this.children = new ArrayList<LocalScope>();
	}
	
	public FunctSymbol(Scope parent, String identifier, Type type, LinkedHashMap<String, Type> paraList) {
		super(parent, identifier);
		this.type = type;
		this.paraList = paraList;
		this.children = new ArrayList<LocalScope>();
	}
	
	@Override
	public Scope defineFunct(FunctDefNode node, ErrorReminder errorReminder) {
		errorReminder.error(node.getLoc(), "invalid function definion.");
		return null;
	}
	
	@Override
	public void defineParaList(ArrayList<VarDefNode> paraList, ErrorReminder errorReminder) {
		for(VarDefNode item : paraList) {
			TypeNode typeNode = item.getType();
			String typeIdentifier = typeNode.toString();
			Type paraType = resolveType(typeIdentifier);
			if (paraType == null) {
				errorReminder.error(item.getLoc(), 
					"the parameter type \"" + typeIdentifier + "\" is not declared in this scope."
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
						"the parameter \"" + paraIdentifier + "\" has the same name with previous parameter."
					);
				}
				else {
					if (typeNode instanceof ArrayTypeNode) {
						this.paraList.put(paraIdentifier, new ArrayType(typeIdentifier, ((ArrayTypeNode) typeNode).getDimension()));
					}
					else {
						this.paraList.put(paraIdentifier, paraType);
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
	public boolean inFunctScope() {
		return true;
	}
	
	@Override
	public boolean inClassScope() {
		return parent.inClassScope();
	}
	
	@Override
	public ClassSymbol getClassSymbol() {
		return parent.getClassSymbol();
	}
	
	@Override
	public boolean isFunct() {
		return true;
	}
	
	public LinkedHashMap<String, Type> getParaList() {
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
