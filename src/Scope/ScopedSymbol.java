package Scope;

import java.util.ArrayList;
import java.util.LinkedHashMap;

import AST.ClassDefNode;
import AST.FunctDefNode;
import AST.VarDefListNode;
import AST.VarDefNode;
import utility.ErrorReminder;

abstract public class ScopedSymbol extends Symbol implements Scope {
	protected Scope parent;
	private ArrayList<LocalScope> children;
	private LinkedHashMap<String, Type> varList;
	
	public ScopedSymbol(Scope parent, String identifier) {
		super(identifier);
		this.parent = parent;
		this.children = new ArrayList<LocalScope>();
		this.varList = new LinkedHashMap<String, Type>();
	}
	
	public Scope getEnclosingScope() {
		return parent;
	}
	
	@Override
	public Type resolveType(String identifier) {
		Scope parent = getEnclosingScope();
		if (parent != null) 
			return parent.resolveType(identifier);
		return null;
	}
	
	@Override 
	public void defineVar(VarDefListNode node, ErrorReminder errorReminder) {
		ArrayList<VarDefNode> varList = node.getVarList();
		String typeIdentifier = varList.get(0).getTypeIdentifier();
		Type type = resolveType(typeIdentifier);
		if (type == null) {
			errorReminder.error(node.getLoc(), 
				"The type \"" + typeIdentifier + "\" is not defined.");
			return;
		}
		for(VarDefNode var : varList) {
			String identifier = var.getIdentifier();
			if (this.varList.containsKey(identifier)) {
				errorReminder.error(node.getLoc(), 
					"The variable \"" + identifier + "\" has the same name with the previous variable.");
			}
			else {
				this.varList.put(identifier, type);
			}
		}
	}

	public abstract void defineClass(ClassDefNode node, ErrorReminder errorReminder);
	
	public abstract void defineFunct(FunctDefNode node, ErrorReminder errorReminder);
}
