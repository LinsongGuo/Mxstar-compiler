package Scope;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import utility.ErrorReminder;
import AST.ClassDefNode;
import AST.FunctDefNode;
import AST.FunctExprNode;
import AST.VarDefNode;
import AST.VarExprNode;
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

	public abstract Scope getGlobalScope();
	
	@Override
	public Scope getEnclosingScope() {
		return this.parent;
	}

	public abstract ClassSymbol declareClass(ClassDefNode node, ErrorReminder errorReminder);
	
	public abstract FunctSymbol declareFunct(FunctDefNode node, ErrorReminder errorReminder);
	
	@Override
	public void declareParaList(ArrayList<VarDefNode> paraList, ErrorReminder errorReminder) {

	}
	
	@Override	
	public VarSymbol declareVar(VarDefNode node, ErrorReminder errorReminder) {
		TypeNode typeNode = node.getType();
		String typeIdentifier = typeNode.typeString();
		Type type = resolveType(typeIdentifier);
		if (type == null) { 
			//check variable type
			errorReminder.error(node.getLoc(), 
				"\'" + typeIdentifier + "\' does not name a type."
			);
			return null;
		}
		if (typeIdentifier.equals("void")) {
			errorReminder.error(node.getLoc(), "the variable declared void.");
			return null;
		}
		if (typeNode instanceof ArrayTypeNode) {
			type = new ArrayType(getGlobalScope(), typeIdentifier, ((ArrayTypeNode)typeNode).getDimension());
		}
		String identifier = node.getIdentifier();
		//check variable name
		if (this.varList.containsKey(identifier)) {
			errorReminder.error(node.getLoc(), 
				"redeclaration of variable \'" + identifier + "\'."
			);
			return null;
		}
		else {
			VarSymbol varSymbol = new VarSymbol(identifier, type, this);
			this.varList.put(identifier, varSymbol);
			return varSymbol;
		}	
	}

	public abstract Type resolveType(String identifier); 
	
	public abstract VarSymbol resolveVar(VarExprNode node, ErrorReminder errorReminder);
	
	public abstract VarSymbol resolveArray(ArrayExprNode node, ErrorReminder errorReminder);
	
	public abstract FunctSymbol resolveFunct(FunctExprNode node, ErrorReminder errorReminder);
	
	public abstract boolean inLoopScope();
	
	public abstract boolean inIfScope();

	public abstract FunctSymbol InFunctSymbol();

	public abstract ClassSymbol InClassSymbol();
	
	public VarSymbol getVarSymbol(String identifier) {
		return varList.get(identifier);
	}

	public abstract FunctSymbol getFunctScope(String identifier);
	
	public abstract ClassSymbol getClassScope(String identifier);
	
	public abstract boolean duplicateClass(String identifier);
	
	/*
	//for IR
	protected LinkedHashMap<String, IRRegister> registerList;
	
	@Override
	public void addRegister(String name, IRRegister reg) {
		if (registerList.containsKey(name)) {
			System.err.println("error in BaseScope.addRegister");
		}	
		registerList.put(name, reg);
	}
	
	@Override
	public IRRegister resolveRegister(String name) {
		if (registerList.containsKey(name)) 
			return registerList.get(name);
		if (parent != null)
			return parent.resolveRegister(name);
		return null;
	}*/
}
