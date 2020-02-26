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
	
	public ClassSymbol(Scope parent, String identifier) {
		super(parent, identifier);
	}
	
	@Override
	public Symbol resolveFunct(FunctDefNode node, ErrorReminder errorReminder) {
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
			if (typeNode instanceof ArrayTypeNode) 
				dimension = ((ArrayTypeNode) typeNode).getDimension();	
		}
		
		//check identifier
		String identifier = node.getIdentifier();
		FunctSymbol functSymbol = new FunctSymbol(this, identifier, type, dimension);
		if (methodList.containsKey(identifier)) {
			errorReminder.error(node.getLoc(), 
				"The function \"" + identifier + "()\" has the same name with previous function."
			);
		}
		methodList.put(identifier, functSymbol);
		return functSymbol;
	}
	
	
	@Override
	public void defineParaList(ArrayList<VarDefNode> paraList, ErrorReminder errorReminder) {
		
	}
	
	@Override
	public boolean inFunctScope() {
		return false;
	}
	
	@Override
	public boolean inClassScope() {
		return true;
	}
}
