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
	private ArrayList<LocalScope> children;
	
	public FunctSymbol(Scope parent, String identifier) {
		super(parent, identifier);
		this.type = null;
		this.dimension = 0;
		this.paraList = new LinkedHashMap<String, Type>();
		this.children = new ArrayList<LocalScope>();
	}
	
	public FunctSymbol(Scope parent, String identifier, Type type, int dimension) {
		super(parent, identifier);
		this.type = type;
		this.dimension = dimension;
		this.paraList = new LinkedHashMap<String, Type>();
		this.children = new ArrayList<LocalScope>();
	}
	
	public FunctSymbol(Scope parent, String identifier, Type type, int dimension, LinkedHashMap<String, Type> paraList) {
		super(parent, identifier);
		this.type = type;
		this.dimension = dimension;
		this.paraList = paraList;
		this.children = new ArrayList<LocalScope>();
	}
	
	@Override
	public Scope defineFunct(FunctDefNode node, ErrorReminder errorReminder) {
		errorReminder.error(node.getLoc(), "Invalid function definion.");
		return null;
	}
	
	@Override
	public void defineParaList(ArrayList<VarDefNode> paraList, ErrorReminder errorReminder) {
		for(VarDefNode item : paraList) {
			String paraTypeIdentifier = item.getTypeIdentifier();
			Type paraType = resolveType(paraTypeIdentifier);
			if (paraType == null) {
				errorReminder.error(item.getLoc(), 
					"The parameter type \"" + paraTypeIdentifier + "\" is not defined.");
				return;
			}
			String paraIdentifier = item.getIdentifier();
			if(this.paraList.containsKey(paraIdentifier)) {
				errorReminder.error(item.getLoc(),
					"The parameter \"" + paraIdentifier + "\" has the same name with previous parameter."
				);
			}
			else {
				this.paraList.put(paraIdentifier, paraType);
			}
		}
	}

		
}
