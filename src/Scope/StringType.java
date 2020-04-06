
package Scope;

import java.util.LinkedHashMap;

public class StringType extends ClassSymbol implements Type {
	public StringType(Scope globalScope) {
		super(globalScope, "string");
		
		functList.put("length", new FunctSymbol(this, "length", new IntType()));
		
		LinkedHashMap<String, VarSymbol> paraList1 = new LinkedHashMap<String, VarSymbol>();
		paraList1.put("left", new VarSymbol("left", new IntType(), null));
		paraList1.put("right", new VarSymbol("right", new IntType(), null));
		LinkedHashMap<String, VarSymbol> varList1 = new LinkedHashMap<String, VarSymbol>();
		varList1.put("left", new VarSymbol("left", new IntType(), null));
		varList1.put("right", new VarSymbol("right", new IntType(), null));
		functList.put("substring", new FunctSymbol(this, "substring", this, paraList1, varList1));
		
		functList.put("parseInt", new FunctSymbol(this, "parseInt", new IntType()));
		
		LinkedHashMap<String, VarSymbol> paraList3 = new LinkedHashMap<String, VarSymbol>();
		paraList3.put("pos", new VarSymbol("pos", new IntType(), null));
		LinkedHashMap<String, VarSymbol> varList3 = new LinkedHashMap<String, VarSymbol>();
		varList3.put("pos", new VarSymbol("pos", new IntType(), null));
		functList.put("ord", new FunctSymbol(this, "ord", new IntType(), paraList3, varList3));
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
	
	public LinkedHashMap<String, FunctSymbol> getFunctList() {
		return functList;
	}
}
