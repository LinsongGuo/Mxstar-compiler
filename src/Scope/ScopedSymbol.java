package Scope;

import java.util.ArrayList;
import java.util.LinkedHashMap;

import AST.ArrayExprNode;
import AST.ArrayTypeNode;
import AST.ClassDefNode;
import AST.FunctDefNode;
import AST.FunctExprNode;
import AST.TypeNode;
import AST.VarDefNode;
import AST.VarExprNode;
import utility.ErrorReminder;

abstract public class ScopedSymbol extends Symbol implements Scope {
	protected Scope parent;
	protected LinkedHashMap<String, VarSymbol> varList;
	
	public ScopedSymbol(Scope parent, String identifier) {
		super(identifier);
		this.parent = parent;
		this.varList = new LinkedHashMap<String, VarSymbol>();
	}
	
	@Override
	public Scope getGlobalScope() {
		return parent;
	}

	@Override
	public Scope getEnclosingScope() {
		return parent;
	}
	
	@Override
	public ClassSymbol declareClass(ClassDefNode node, ErrorReminder errorReminder) {
		errorReminder.error(node.getLoc(), "invalid class definition.");
		return null;
	}
	
	public abstract FunctSymbol declareFunct(FunctDefNode node, ErrorReminder errorReminder);

	public abstract void declareParaList(ArrayList<VarDefNode> paraList, ErrorReminder errorReminder);

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
			VarSymbol varSymbol = new VarSymbol(identifier, type);
			this.varList.put(identifier, varSymbol);
			return varSymbol;
		}	
	}
	
	@Override
	public Type resolveType(String identifier) {
		if (parent != null) 
			return parent.resolveType(identifier);
		else 
			return null;
	}
	
	@Override
	public VarSymbol resovleVar(VarExprNode node, ErrorReminder errorReminder) {
		String identifier = node.getIdentifier();
		if(!varList.containsKey(identifier)) {
			return parent.resovleVar(node, errorReminder);
		}
		else 
			return varList.get(identifier);
	}
	
	@Override
	public VarSymbol resovleArray(ArrayExprNode node, ErrorReminder errorReminder) {
		String identifier = node.getIdentifier();
		if(!varList.containsKey(identifier)) {
			return parent.resovleArray(node, errorReminder);
		}
		/*
		//check index
		ExprNode indexExpr = node.getIndexExpr();
		if (indexExpr != null) {
			Type indexType = indexExpr.getType();
			if (!(indexType instanceof IntType)) {
				errorReminder.error(indexExpr.getLoc(), "cannot convert \'" + indexType.toString() + "\' to \'int\'.");
			}
		}
		else {
			errorReminder.error(node.getLoc(), "empty index of array.");
		}
		*/	
		//get type
		VarSymbol var = varList.get(identifier);
		Type type = var.getType();
		if (type instanceof ArrayType) {
			int tmp = ((ArrayType)type).getDimension();
			String typeIdentifier = type.typeString();
			if (tmp == 1)
				return new VarSymbol(identifier, resolveType(typeIdentifier));
			else 
				return new VarSymbol(identifier, new ArrayType(getGlobalScope(), typeIdentifier, tmp - 1));
		} 
		else {
			errorReminder.error(node.getLoc(), "\'" + identifier + "\' is a variable not an array.");
			return null;
		}
	}
		
	public abstract FunctSymbol resolveFunct(FunctExprNode node, ErrorReminder errorReminder);

	@Override
	public boolean inLoopScope() {
		return false;
	}
	
	@Override
	public boolean inIfScope() {
		return false;
	}
	
	public abstract FunctSymbol InFunctSymbol();
	
	public abstract ClassSymbol InClassSymbol();
	
	public VarSymbol getVarSymbol(String identifier) {
		return varList.get(identifier);
	}
	
	public abstract FunctSymbol getFunctScope(String identifier);
	
	@Override
	public ClassSymbol getClassScope(String identifier) {
		return null;
	}
	
	@Override
	public boolean duplicateClass(String identifier) {
		return false;
	} 

	@Override
	public boolean isVar() {
		return false;
	}
	
	public abstract boolean isFunct();
}
