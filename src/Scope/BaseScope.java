package Scope;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import utility.ErrorReminder;
import utility.Location;
import AST.ClassDefNode;
import AST.FunctDefNode;
import AST.VarDefListNode;
import AST.VarDefNode;
import AST.TypeNode;
import AST.ArrayTypeNode;

abstract public class BaseScope implements Scope {
	protected Scope parent;
	protected LinkedHashMap<String, Symbol> methodList;
	
	public BaseScope(Scope parent) {
		this.parent = parent;
		this.methodList = new LinkedHashMap<String, Symbol>();
	}
	
	public Scope getEnclosingScope() {
		return this.parent;
	}
	
	public abstract Type resolveType(String identifier); 
	
	public abstract Symbol resovleVar(Location loc, String identifier, int dimension, ErrorReminder errorReminder);
	
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

	public abstract Scope defineClass(ClassDefNode node, ErrorReminder errorReminder);
	
	@Override
	public void defineParaList(ArrayList<VarDefNode> paraList, ErrorReminder errorReminder) {

	}
	
	public abstract boolean inLoopScope();
	
	public abstract boolean inIfScope();
	
	public abstract boolean inFunctScope();
	
	public abstract boolean inClassScope();
}
