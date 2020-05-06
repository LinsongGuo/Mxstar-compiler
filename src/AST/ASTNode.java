package AST;

import Scope.Scope;
import utility.Location;

abstract public class ASTNode {
	protected Location loc;
	protected Scope scope;
	
	public ASTNode(Location loc) {
		this.loc = loc;
		this.scope = null;
	}
	
	public Location getLoc() {
		return loc;
	}
	
	public void setScope(Scope scope) {
		this.scope = scope;
	}
	
	public Scope getScope() {
		return scope;
	}
	
	public abstract void accept(ASTVisitor visitor);
}
