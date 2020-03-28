package Scope;

import IR.Type.IRType;

public interface Type {
	public abstract String toString();
	
	public abstract String typeString();
	
	public abstract boolean isBuiltInType();
	
//	public abstract IRType toIRType();
}
