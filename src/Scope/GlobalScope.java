package Scope;

import java.util.ArrayList;
import java.util.LinkedHashMap;

import AST.ClassDefNode;
import AST.FunctDefNode;
import AST.VarDefNode;
import utility.ErrorReminder;

public class GlobalScope extends BaseScope {
	private LinkedHashMap<String, Type> typeList;
	private LinkedHashMap<String, FunctSymbol> functList;
	
	public GlobalScope(Scope parent) {
		super(parent);
		this.typeList = new LinkedHashMap<String, Type>();
		this.functList = new LinkedHashMap<String, FunctSymbol>();
	}
	
	@Override
	public Type resolveType(String identifier) {
		if(typeList.containsKey(identifier))
			return typeList.get(identifier);
		Scope parent = getEnclosingScope();
		if (parent != null) 
			return parent.resolveType(identifier);
		return null;
	}
	
	public void setBuiltInType() {
		typeList.put("bool", new BuiltInTypeSymbol("bool"));
		typeList.put("int", new BuiltInTypeSymbol("int"));
		typeList.put("string", new BuiltInTypeSymbol("string"));
		typeList.put("void", new BuiltInTypeSymbol("void"));
	}
	
	public void setBuiltInFunction(Scope gobalScope) {
		LinkedHashMap<String, Type> paraList1 = new LinkedHashMap<String, Type>();
		paraList1.put("str", new BuiltInTypeSymbol("string"));
		functList.put("print", new FunctSymbol(gobalScope, "print", new BuiltInTypeSymbol("void"), 0, paraList1));
		functList.put("println", new FunctSymbol(gobalScope, "println", new BuiltInTypeSymbol("void"), 0, paraList1));	
		
		LinkedHashMap<String, Type> paraList2 = new LinkedHashMap<String, Type>();
		paraList2.put("n", new BuiltInTypeSymbol("int"));
		functList.put("printInt", new FunctSymbol(gobalScope, "printInt", new BuiltInTypeSymbol("void"), 0, paraList2));
		functList.put("printlnInt", new FunctSymbol(gobalScope, "printlnInt", new BuiltInTypeSymbol("void"), 0, paraList2));	
		
		functList.put("getString", new FunctSymbol(gobalScope, "getString", new BuiltInTypeSymbol("string"), 0, new LinkedHashMap<String, Type>()));	
		
		functList.put("getInt", new FunctSymbol(gobalScope, "getInt", new BuiltInTypeSymbol("int"), 0, new LinkedHashMap<String, Type>()));	
		
		LinkedHashMap<String, Type> paraList3 = new LinkedHashMap<String, Type>();
		paraList3.put("i", new BuiltInTypeSymbol("int"));
		functList.put("toString", new FunctSymbol(gobalScope, "toString", new BuiltInTypeSymbol("string"), 0, paraList3));	
	}
}
