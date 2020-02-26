package Scope;

import java.util.ArrayList;
import java.util.LinkedHashMap;

import AST.ArrayTypeNode;
import AST.ClassDefNode;
import AST.FunctDefNode;
import AST.TypeNode;
import AST.VarDefListNode;
import AST.VarDefNode;
import utility.ErrorReminder;
import utility.Location;

abstract public class ScopedSymbol extends Symbol implements Scope {
	protected Scope parent;
	protected LinkedHashMap<String, Symbol> methodList;
	
	public ScopedSymbol(Scope parent, String identifier) {
		super(identifier);
		this.parent = parent;
		this.methodList = new LinkedHashMap<String, Symbol>();
	}
	
	public Scope getEnclosingScope() {
		return parent;
	}
	
	@Override
	public Type resolveType(String identifier) {
		Scope parent = getEnclosingScope();
		
		if (parent != null) 
			return parent.resolveType(identifier);
		return null;
	}
	
	@Override
	public Symbol resovleVar(Location loc, String identifier, int dimension, ErrorReminder errorReminder) {
		if(!methodList.containsKey(identifier) || (methodList.get(identifier) instanceof FunctSymbol)) {
			return getEnclosingScope().resovleVar(loc, identifier, dimension, errorReminder);
		}
		VarSymbol var = (VarSymbol)methodList.get(identifier);
		int tmp = (var instanceof ArraySymbol) ? ((ArraySymbol)var).getDimension() : 0;
		if (dimension > tmp) {
			errorReminder.error(loc, "The dimension of the variable \"" + identifier + "\" is invalid.");
		}
		return var;
	}
		
	public abstract Symbol resolveFunct(FunctDefNode node, ErrorReminder errorReminder);

	public void defineVarList(VarDefListNode node, ErrorReminder errorReminder) {
		ArrayList<VarDefNode> varList = node.getVarList();
		TypeNode typeNode = varList.get(0).getType();
		String typeIdentifier = typeNode.getIdentifier();
		Type type = resolveType(typeIdentifier);
		if (type == null) { 
			//check variable type
			errorReminder.error(node.getLoc(), 
				"The type \"" + typeIdentifier + "\" is not defined."
			);
		}
		
		if (typeNode instanceof ArrayTypeNode) {
			int dimension = ((ArrayTypeNode)typeNode).getDimension(); 
			for(VarDefNode var : varList) {
				String identifier = var.getIdentifier();
				//check variable name
				if (methodList.containsKey(identifier)) {
					errorReminder.error(node.getLoc(), 
						"The variable \"" + identifier + "\" has the same name with the previous variable."
					);
				}
				else {
					methodList.put(identifier, new ArraySymbol(identifier, type, dimension));
				}
			}	
		}
		else {
			for(VarDefNode var : varList) {
				String identifier = var.getIdentifier();
				//check variable name
				if (methodList.containsKey(identifier)) {
					errorReminder.error(node.getLoc(), 
						"The variable \"" + identifier + "\" has the same name with the previous variable."
					);
				}
				else {
					methodList.put(identifier, new VarSymbol(identifier, type));
				}
			}	
		}
	}	

	public abstract Scope defineFunct(FunctDefNode node, ErrorReminder errorReminder);
	
	@Override
	public Scope defineClass(ClassDefNode node, ErrorReminder errorReminder) {
		errorReminder.error(node.getLoc(), "Invalid class definition.");
		return null;
	}
	
	public abstract void defineParaList(ArrayList<VarDefNode> paraList, ErrorReminder errorReminder);
	
	@Override
	public boolean inLoopScope() {
		return false;
	}
	
	@Override
	public boolean inIfScope() {
		return false;
	}
	
	public abstract boolean inFunctScope();
	
	public abstract boolean inClassScope();
}
