package Scope;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import utility.ErrorReminder;
import AST.ClassDefNode;
import AST.FunctDefNode;
import AST.VarDefListNode;
import AST.VarDefNode;

abstract public class BaseScope implements Scope {
	protected Scope parent;
	private ArrayList<LocalScope> children;
	private LinkedHashMap<String, Type> varList;
	
	public BaseScope(Scope parent) {
		this.parent = parent;
		this.children = new ArrayList<LocalScope>();
		this.varList = new LinkedHashMap<String, Type>();
	}
	
	public Scope getEnclosingScope() {
		return this.parent;
	}
	
	public abstract Type resolveType(String identifier); 

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
	
	@Override
	public void defineFunct(FunctDefNode node, ErrorReminder errorReminder) {
		errorReminder.error(node.getLoc(), "404 NOT FOUND!");
	}
	
	@Override
	public void defineClass(ClassDefNode node, ErrorReminder errorReminder) {
		errorReminder.error(node.getLoc(), "404 NOT FOUND!");
	}
	
}
