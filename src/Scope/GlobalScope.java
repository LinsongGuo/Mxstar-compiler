package Scope;

import java.util.ArrayList;
import java.util.LinkedHashMap;

import AST.ArrayTypeNode;
import AST.ClassDefNode;
import AST.FunctDefNode;
import AST.TypeNode;
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
	
	@Override
	public Scope defineFunct(FunctDefNode node, ErrorReminder errorReminder) {
		TypeNode typeNode = node.getType();
		Type type = null;
		int dimension = 0;
		if (typeNode != null) {
			String typeIdentifier = typeNode.getIdentifier();
			type = resolveType(typeIdentifier);
			if (type == null) {
				errorReminder.error(node.getLoc(), 
					"The return type \"" + typeIdentifier + "\" is not defined."
				);
			}
			if (type instanceof ArrayTypeNode) 
				dimension = ((ArrayTypeNode) type).getDimension();	
		}
		
		//check identifier
		String identifier = node.getIdentifier();
		FunctSymbol functSymbol = new FunctSymbol(this, identifier, type, dimension);
		if (functList.containsKey(identifier)) {
			errorReminder.error(node.getLoc(), 
				"The function \"" + identifier + "()\" has the same name with previous function."
			);
		}
		functList.put(identifier, functSymbol);
		return functSymbol;
	}
	
	@Override
	public Scope defineClass(ClassDefNode node, ErrorReminder errorReminder) {
		String identifier = node.getIdentifier();
		if (typeList.containsKey(identifier)) {
			errorReminder.error(node.getLoc(), 
				"The class \"" + identifier + "\" has the same name with previous class." 
			);
		}
		ClassSymbol classSymbol = new ClassSymbol(this, identifier);
		return classSymbol;
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
		
		LinkedHashMap<String, Type> paraList2 = new LinkedHashMap<String, Type>();
		paraList2.put("str", new BuiltInTypeSymbol("string"));
		functList.put("println", new FunctSymbol(gobalScope, "println", new BuiltInTypeSymbol("void"), 0, paraList2));	
		
		LinkedHashMap<String, Type> paraList3 = new LinkedHashMap<String, Type>();
		paraList3.put("n", new BuiltInTypeSymbol("int"));
		functList.put("printInt", new FunctSymbol(gobalScope, "printInt", new BuiltInTypeSymbol("void"), 0, paraList3));
		
		LinkedHashMap<String, Type> paraList4 = new LinkedHashMap<String, Type>();
		paraList4.put("n", new BuiltInTypeSymbol("int"));
		functList.put("printlnInt", new FunctSymbol(gobalScope, "printlnInt", new BuiltInTypeSymbol("void"), 0, paraList4));	
		
		functList.put("getString", new FunctSymbol(gobalScope, "getString", new BuiltInTypeSymbol("string"), 0, new LinkedHashMap<String, Type>()));	
		
		functList.put("getInt", new FunctSymbol(gobalScope, "getInt", new BuiltInTypeSymbol("int"), 0, new LinkedHashMap<String, Type>()));	
		
		LinkedHashMap<String, Type> paraList7 = new LinkedHashMap<String, Type>();
		paraList7.put("i", new BuiltInTypeSymbol("int"));
		functList.put("toString", new FunctSymbol(gobalScope, "toString", new BuiltInTypeSymbol("string"), 0, paraList7));	
	}
}
