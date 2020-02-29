package Scope;

import java.util.LinkedHashMap;

public class StringType extends ClassSymbol implements Type {
	
	
	public StringType(Scope globalScope) {
		super(globalScope, "string");
		
		functList.put("length", new FunctSymbol(this, "length", new IntType(), new LinkedHashMap<String, Type>()));
		
		LinkedHashMap<String, Type> paraList1 = new LinkedHashMap<String, Type>();
		paraList1.put("left", new IntType());
		paraList1.put("right", new IntType());
		functList.put("substring", new FunctSymbol(this, "substring", this, paraList1));
		
		functList.put("parseInt", new FunctSymbol(this, "parseInt", new IntType(), new LinkedHashMap<String, Type>()));
		
		LinkedHashMap<String, Type> paraList2 = new LinkedHashMap<String, Type>();
		paraList2.put("pos", new IntType());
		functList.put("ord", new FunctSymbol(this, "ord", new IntType(), paraList2));
	}
	
	@Override
	public String toString() {
		return new String("string");
	}
	
	@Override
	public String typeString() {
		return new String("string");
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
}
