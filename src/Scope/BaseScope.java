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
	private LinkedHashMap<String, Type> varList;
	
	public BaseScope(Scope parent) {
		this.parent = parent;
		this.varList = new LinkedHashMap<String, Type>();
	}
	
	public Scope getEnclosingScope() {
		return this.parent;
	}
	
	public abstract Type resolveType(String identifier); 

	public void defineVarList(VarDefListNode node, ErrorReminder errorReminder) {
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
	
	public abstract Scope defineFunct(FunctDefNode node, ErrorReminder errorReminder);

	public abstract Scope defineClass(ClassDefNode node, ErrorReminder errorReminder);
	
	@Override
	public void defineParaList(ArrayList<VarDefNode> paraList, ErrorReminder errorReminder) {

	}
}
