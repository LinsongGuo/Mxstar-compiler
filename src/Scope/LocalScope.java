package Scope;

import java.util.ArrayList;

import AST.ClassDefNode;
import AST.FunctDefNode;
import utility.ErrorReminder;
import utility.Location;

public class LocalScope extends BaseScope {
	private ArrayList<LocalScope> children;
	private ScopeType scopeType;
	
	public LocalScope(Scope parent, ScopeType scopeType) {
		super(parent);
		this.scopeType = scopeType;
		this.children = new ArrayList<LocalScope>();
	}

	@Override
	public Type resolveType(String identifier) {
		Scope parent = getEnclosingScope();
		if (parent != null) 
			return parent.resolveType(identifier);
		return null;
	}
	
	@Override
	public Symbol resovleVar(Location loc, String identifier, int dimension, ErrorReminder errorReminder) {
		if(!methodList.containsKey(identifier) || (methodList.get(identifier) instanceof FunctSymbol)) {
			return getEnclosingScope().resovleVar(loc, identifier, dimension, errorReminder);
		}
		VarSymbol var = (VarSymbol)methodList.get(identifier);
		int tmp = (var instanceof ArraySymbol) ? ((ArraySymbol)var).getDimension() : 0;
		if (dimension > tmp) {
			errorReminder.error(loc, "The dimension of the variable \"" + identifier + "\" is invalid.");
		}
		return var;
	}
	
	@Override
	public Symbol resolveFunct(FunctDefNode node, ErrorReminder errorReminder) {
		return getEnclosingScope().resolveFunct(node, errorReminder);
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
	
	@Override
	public boolean inLoopScope() {
		if (scopeType == ScopeType.LoopScope) return true;
		return getEnclosingScope().inLoopScope();
	}
	
	@Override
	public boolean inIfScope() {
		if (scopeType == ScopeType.IfScope) return true;
		return getEnclosingScope().inIfScope();
	}
	
	@Override
	public boolean inFunctScope() {
		return getEnclosingScope().inFunctScope();
	}
	
	@Override
	public boolean inClassScope() {
		return getEnclosingScope().inClassScope();
	}
}
