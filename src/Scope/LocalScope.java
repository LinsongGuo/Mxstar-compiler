package Scope;

import java.util.ArrayList;

import AST.ClassDefNode;
import AST.FunctDefNode;
import utility.ErrorReminder;

public class LocalScope extends BaseScope {
	private ArrayList<LocalScope> children;
	
	public LocalScope(Scope parent) {
		super(parent);
		children = new ArrayList<LocalScope>();
	}

	@Override
	public Type resolveType(String identifier) {
		Scope parent = getEnclosingScope();
		if (parent != null) 
			return parent.resolveType(identifier);
		return null;
	}
	
	@Override
	public Scope defineFunct(FunctDefNode node, ErrorReminder errorReminder) {
		errorReminder.error(node.getLoc(), "Invalid function definion.");
		return null;
	}
	
	@Override
	public Scope defineClass(ClassDefNode node, ErrorReminder errorReminder) {
		errorReminder.error(node.getLoc(), "Invalid class definition.");
		return null;
	}
}
