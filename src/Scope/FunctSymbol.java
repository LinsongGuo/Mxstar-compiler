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
	private int dimension;
	private LinkedHashMap<String, Type> paraList;
	private ArrayList<LocalScope> children;
	
	public FunctSymbol(Scope parent, String identifier) {
		super(parent, identifier);
		this.type = null;
		this.dimension = 0;
		this.paraList = new LinkedHashMap<String, Type>();
		this.children = new ArrayList<LocalScope>();
	}
	
	public FunctSymbol(Scope parent, String identifier, Type type, int dimension) {
		super(parent, identifier);
		this.type = type;
		this.dimension = dimension;
		this.paraList = new LinkedHashMap<String, Type>();
		this.children = new ArrayList<LocalScope>();
	}
	
	public FunctSymbol(Scope parent, String identifier, Type type, int dimension, LinkedHashMap<String, Type> paraList) {
		super(parent, identifier);
		this.type = type;
		this.dimension = dimension;
		this.paraList = paraList;
		this.children = new ArrayList<LocalScope>();
	}
	
	public LinkedHashMap<String, Type> getParaList() {
		return paraList;
	}
	
	@Override
	public FunctSymbol resolveFunct(FunctExprNode node, ArrayList<Type> typeList, ErrorReminder errorReminder) {
		return getEnclosingScope().resolveFunct(node, typeList, errorReminder);
	}

	
	@Override
	public Scope defineFunct(FunctDefNode node, ErrorReminder errorReminder) {
		errorReminder.error(node.getLoc(), "invalid function definion.");
		return null;
	}
	
	@Override
	public void defineParaList(ArrayList<VarDefNode> paraList, ErrorReminder errorReminder) {
		for(VarDefNode item : paraList) {
			String paraTypeIdentifier = item.getTypeIdentifier();
			Type paraType = resolveType(paraTypeIdentifier);
			if (paraType == null) {
				errorReminder.error(item.getLoc(), 
					"the parameter type \"" + paraTypeIdentifier + "\" is not declared in this scope.");
				return;
			}
			String paraIdentifier = item.getIdentifier();
			if(this.paraList.containsKey(paraIdentifier)) {
				errorReminder.error(item.getLoc(),
					"the parameter \"" + paraIdentifier + "\" has the same name with previous parameter."
				);
			}
			else {
				this.paraList.put(paraIdentifier, paraType);
			}
		}
	}

	@Override
	public boolean inFunctScope() {
		return true;
	}
	
	@Override
	public boolean inClassScope() {
		return getEnclosingScope().inClassScope();
	}
	
	@Override
	public ClassSymbol getClassSymbol() {
		return getEnclosingScope().getClassSymbol();
	}
	
	@Override
	public boolean isFunct() {
		return true;
	}
	
	public boolean matchParameters(FunctExprNode node) {
		ArrayList<ExprNode> paraList = node.getParaList();
		return paraList.size() == this.paraList.size();
	}
}
