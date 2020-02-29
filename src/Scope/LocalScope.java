package Scope;

import java.util.ArrayList;

import AST.ArrayExprNode;
import AST.ClassDefNode;
import AST.ExprNode;
import AST.FunctDefNode;
import AST.FunctExprNode;
import AST.VarExprNode;
import utility.ErrorReminder;
import utility.Location;

public class LocalScope extends BaseScope {
	private ArrayList<LocalScope> children;
	private ScopeType scopeType;
	
	public LocalScope(Scope parent, ScopeType scopeType) {
		super(parent);
		this.scopeType = scopeType;
		this.children = new ArrayList<LocalScope>();
	}

	@Override
	public Scope getGlobalScope() {
		return parent.getGlobalScope();
	}
	
	@Override
	public Scope defineFunct(FunctDefNode node, ErrorReminder errorReminder) {
		errorReminder.error(node.getLoc(), "invalid function definion.");
		return null;
	}
	
	@Override
	public Scope defineClass(ClassDefNode node, ErrorReminder errorReminder) {
		errorReminder.error(node.getLoc(), "invalid class definition.");
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
		
		ArrayList<ExprNode> indexExpr = node.getIndexExpr();
		for(ExprNode item : indexExpr) {
			if(item != null) {
				Type tmp = item.getType();
				if (!(tmp instanceof IntType)) {
					errorReminder.error(item.getLoc(), "cannot convert \'" + tmp.toString() + "\' to \'int\'.");
				}
			}
			else {
				errorReminder.error(item.getLoc(), "empty index of array.");
			}	
		}
		
		VarSymbol var = varList.get(identifier);
		Type type = var.getType();
		int dimension = node.getDimension();
		if (type instanceof ArrayType) {
			int tmp = ((ArrayType)type).getDimension();
			if (dimension > tmp) {
				errorReminder.error(node.getLoc(), "invalid dimension of \'" + identifier + "\'.");
				return null;
			}
			else {
				String typeIdentifier = type.typeString();
				if (dimension == tmp)
					return new VarSymbol(identifier, resolveType(typeIdentifier));
				else 
					return new VarSymbol(identifier, new ArrayType(typeIdentifier, tmp - dimension));
			} 
		} 
		else {
			errorReminder.error(node.getLoc(), "invalid dimension of \'" + identifier + "\'.");
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
	public FunctSymbol getFunctSymbol() {
		return parent.getFunctSymbol();
	}
	
	@Override
	public ClassSymbol getClassSymbol() {
		return parent.getClassSymbol();
	}
}
