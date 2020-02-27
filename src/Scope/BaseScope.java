package Scope;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import utility.ErrorReminder;
import utility.Location;
import AST.ClassDefNode;
import AST.FunctDefNode;
import AST.FunctExprNode;
import AST.VarDefListNode;
import AST.VarDefNode;
import AST.TypeNode;
import AST.ArrayExprNode;
import AST.ArrayTypeNode;

abstract public class BaseScope implements Scope {
	protected Scope parent;
	protected LinkedHashMap<String, VarSymbol> varList;
	
	public BaseScope(Scope parent) {
		this.parent = parent;
		this.varList = new LinkedHashMap<String, VarSymbol>();
	}
	
	/*
	public Type stringToType(String s) {
		if (s.equals("bool")) 
			return new BoolType();
		else if (s.equals("int"))
			return new IntType();
		else if (s.equals("string"))
			return new StringType();
		else 
			return new ClassSymbol(s);
	}*/
	
	public abstract Scope getGlobalScope();
	
	@Override
	public Scope getEnclosingScope() {
		return this.parent;
	}
	
	public abstract Type resolveType(String identifier); 
	
	public abstract VarSymbol resovleVar(ArrayExprNode node, ErrorReminder errorReminder);
	
	public abstract FunctSymbol resolveFunct(FunctExprNode node, ArrayList<Type> typeList, ErrorReminder errorReminder);

	@Override	
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
				if (this.varList.containsKey(identifier)) {
					errorReminder.error(node.getLoc(), 
						"The variable \"" + identifier + "\" has the same name with the previous variable."
					);
				}
				else {
					this.varList.put(identifier, new VarSymbol(identifier, new ArrayType(typeIdentifier, dimension)));
				}
			}	
		}
		else {
			for(VarDefNode var : varList) {
				String identifier = var.getIdentifier();
				//check variable name
				if (this.varList.containsKey(identifier)) {
					errorReminder.error(node.getLoc(), 
						"The variable \"" + identifier + "\" has the same name with the previous variable."
					);
				}
				else {
					this.varList.put(identifier, new VarSymbol(identifier, type));
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
	
	public abstract ClassSymbol getClassSymbol();
}
