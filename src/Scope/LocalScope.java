package Scope;

import java.util.ArrayList;

import AST.ArrayExprNode;
import AST.ClassDefNode;
import AST.FunctDefNode;
import AST.FunctExprNode;
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
	public VarSymbol resovleVar(ArrayExprNode node, ErrorReminder errorReminder) {
		String identifier = node.getIdentifier();
		if(!varList.containsKey(identifier)) {
			return getEnclosingScope().resovleVar(node, errorReminder);
		}
		VarSymbol var = (VarSymbol)varList.get(identifier);
		Type type = var.getType();
		int dimension = node.getDimension();
		if (type instanceof ArrayType) {
			int tmp = ((ArrayType)type).getDimension();
			if (dimension > tmp) {
				errorReminder.error(node.getLoc(), "the dimension of the array \"" + identifier + "\" is invalid.");
			}
			if (dimension >= tmp){
				String typeIdentifier = type.toString();
				if (typeIdentifier.equals("bool"))
					return new VarSymbol(identifier, new BoolType());
				else if (typeIdentifier.equals("int"))
					return new VarSymbol(identifier, new IntType());
				else if (typeIdentifier.equals("string"))
					return new VarSymbol(identifier, new StringType());
				else 
					return new VarSymbol(identifier, new ClassSymbol(getGlobalScope(), typeIdentifier));
			}
			else {
				return new VarSymbol(identifier, new ArrayType(identifier, tmp - dimension));
			}
		} 
		else {
			if (dimension > 0) {
				errorReminder.error(node.getLoc(), "the dimension of the array \"" + identifier + "\" is invalid.");
			}
			return var;
		}
	}
	
	@Override
	public FunctSymbol resolveFunct(FunctExprNode node, ArrayList<Type> typeList, ErrorReminder errorReminder) {
		return getEnclosingScope().resolveFunct(node, typeList, errorReminder);
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
	public boolean inLoopScope() {
		if (scopeType == ScopeType.LoopScope) return true;
		return getEnclosingScope().inLoopScope();
	}
	
	@Override
	public boolean inIfScope() {
		if (scopeType == ScopeType.IfScope) return true;
		return getEnclosingScope().inIfScope();
	}
	
	@Override
	public boolean inFunctScope() {
		return getEnclosingScope().inFunctScope();
	}
	
	@Override
	public boolean inClassScope() {
		return getEnclosingScope().inClassScope();
	}
	
	@Override
	public ClassSymbol getClassSymbol() {
		return getEnclosingScope().getClassSymbol();
	}
}
