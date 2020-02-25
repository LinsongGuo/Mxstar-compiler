package Scope;

import java.util.ArrayList;
import java.util.LinkedHashMap;

import AST.ClassDefNode;
import AST.FunctDefNode;
import AST.VarDefNode;
import AST.TypeNode;
import AST.ArrayTypeNode;
import utility.ErrorReminder;

public class FunctSymbol extends ScopedSymbol {
	private Type type;
	private int dimension;
	private LinkedHashMap<String, Type> paraList;
	
	public FunctSymbol(Scope parent, String identifier) {
		super(parent, identifier);
	}
	
	public FunctSymbol(Scope parent, String identifier, Type type, int dimension, LinkedHashMap<String, Type> paraList) {
		super(parent, identifier);
		this.type = type;
		this.dimension = dimension;
		this.paraList = paraList;
	}
	
	@Override
	public void defineFunct(FunctDefNode node, ErrorReminder errorReminder) {
		//check return type
		TypeNode typeNode = node.getType();
		String typeIdentifier = typeNode.getIdentifier();
		Type type = resolveType(typeIdentifier);
		if (type == null) {
			errorReminder.error(node.getLoc(), 
				"The return type \"" + typeIdentifier + "\" is not defined.");
			return;
		}
		this.type = type;
		if (type instanceof ArrayTypeNode) 
			this.dimension = ((ArrayTypeNode) type).getDimension();
		else 
			this.dimension = 0;
		
		//check parameter type
		ArrayList<VarDefNode> paraList = node.getParaList();
		for(VarDefNode var : paraList) {
			String paraTypeIdentifier = var.getTypeIdentifier();
			Type paraType = resolveType(paraTypeIdentifier);
			if (paraType == null) {
				errorReminder.error(node.getLoc(), 
					"The parameter type \"" + paraTypeIdentifier + "\" is not defined.");
				return;
			}
			String paraIdentifier = var.getIdentifier();
			if(this.paraList.containsKey(paraIdentifier)) {
				errorReminder.error(var.getLoc(),
					"The parameter \"" + paraIdentifier + "\" has the same name with previous parameter."
				);
			}
			else {
				this.paraList.put(paraIdentifier, paraType);
			}
		}
	}
	

	@Override
	public void defineClass(ClassDefNode node, ErrorReminder errorReminder) {
		errorReminder.error(node.getLoc(), "404 NOT FOUND!");
	}
	
}
