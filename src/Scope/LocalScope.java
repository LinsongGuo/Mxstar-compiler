package Scope;

import AST.ArrayExprNode;
import AST.ClassDefNode;
import AST.FunctDefNode;
import AST.FunctExprNode;
import AST.VarExprNode;
import utility.ErrorReminder;

public class LocalScope extends BaseScope {
	//private ArrayList<LocalScope> children;
	private ScopeType scopeType;
	
	public LocalScope(Scope parent, ScopeType scopeType) {
		super(parent);
		this.scopeType = scopeType;
	}

	@Override
	public Scope getGlobalScope() {
		return parent.getGlobalScope();
	}
	
	@Override
	public ClassSymbol declareClass(ClassDefNode node, ErrorReminder errorReminder) {
		errorReminder.error(node.getLoc(), "invalid class definition.");
		return null;
	}
	
	@Override
	public FunctSymbol declareFunct(FunctDefNode node, ErrorReminder errorReminder) {
		errorReminder.error(node.getLoc(), "invalid function definion.");
		return null;
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
	
	@Override
	public FunctSymbol resolveFunct(FunctExprNode node, ErrorReminder errorReminder) {
		return parent.resolveFunct(node, errorReminder);
	}

	@Override
	public boolean inLoopScope() {
		if (scopeType == ScopeType.LoopScope) return true;
		return parent.inLoopScope();
	}
	
	@Override
	public boolean inIfScope() {
		if (scopeType == ScopeType.IfScope) return true;
		return parent.inIfScope();
	}
	
	@Override
	public FunctSymbol InFunctSymbol() {
		return parent.InFunctSymbol();
	}
	
	@Override
	public ClassSymbol InClassSymbol() {
		return parent.InClassSymbol();
	}

	@Override
	public FunctSymbol getFunctScope(String identifier) {
		return null;
	}
	
	@Override
	public ClassSymbol getClassScope(String identifier) {
		return null;
	}
	
	@Override
	public boolean duplicateClass(String identifier) {
		return false;
	} 
}
