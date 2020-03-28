package Scope;

import IR.Type.IRType;
import IR.Type.IRVoidType;

public class VoidType extends Symbol implements Type {
	
	public VoidType() {
		super("void");
	}
	
	@Override
	public String toString() {
		return new String("void");
	}
	
	@Override
	public String typeString() {
		return new String("void");
	}
	
	@Override
	public boolean isVar() {
		return false;
	}
	
	@Override
	public boolean isFunct() {
		return false;
	}
	
	@Override
	public boolean isBuiltInType() {
		return true;
	}
	
	/*
	@Override
	public IRType toIRType() {
		return new IRVoidType();
	}*/
}
