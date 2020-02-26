package Scope;

import java.util.ArrayList;
import java.util.LinkedHashMap;

import AST.ArrayTypeNode;
import AST.ClassDefNode;
import AST.FunctDefNode;
import AST.TypeNode;
import AST.VarDefNode;
import utility.ErrorReminder;

public class ClassSymbol extends ScopedSymbol {
	private LinkedHashMap<String, FunctSymbol> functList;
	
	public ClassSymbol(Scope parent, String identifier) {
		super(parent, identifier);
		this.functList = new LinkedHashMap<String, FunctSymbol>();
	}
	
	public Scope defineFunct(FunctDefNode node, ErrorReminder errorReminder) {
		//check return type
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
	public void defineParaList(ArrayList<VarDefNode> paraList, ErrorReminder errorReminder) {
		
	}
}
