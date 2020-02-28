package Scope;

import java.util.ArrayList;
import java.util.LinkedHashMap;

import AST.ArrayExprNode;
import AST.ArrayTypeNode;
import AST.ClassDefNode;
import AST.ExprNode;
import AST.FunctDefNode;
import AST.FunctExprNode;
import AST.TypeNode;
import AST.VarDefListNode;
import AST.VarDefNode;
import AST.VarExprNode;
import utility.ErrorReminder;
import utility.Location;

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
	public void defineVarList(VarDefListNode node, ErrorReminder errorReminder) {
		ArrayList<VarDefNode> varList = node.getVarList();
		TypeNode typeNode = varList.get(0).getType();
		String typeIdentifier = typeNode.toString();
		Type type = resolveType(typeIdentifier);
		if (type == null) { 
			//check variable type
			errorReminder.error(node.getLoc(), 
				"the type \"" + typeIdentifier + "\" is not declared in this scope."
			);
			return;
		}
		if (type.toString().equals("void")) {
			errorReminder.error(node.getLoc(), "the variable declared void.");
			return;
		}
		if (typeNode instanceof ArrayTypeNode) {
			int dimension = ((ArrayTypeNode)typeNode).getDimension(); 
			for(VarDefNode var : varList) {
				String identifier = var.getIdentifier();
				//check variable name
				if (this.varList.containsKey(identifier)) {
					errorReminder.error(node.getLoc(), 
						"the variable \"" + identifier + "\" has the same name with the previous variable."
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
						"the variable \"" + identifier + "\" has the same name with the previous variable."
					);
				}
				else {
					this.varList.put(identifier, new VarSymbol(identifier, type));
				}
			}	
		}
	}

	public abstract Scope defineFunct(FunctDefNode node, ErrorReminder errorReminder);
	
	public abstract void defineParaList(ArrayList<VarDefNode> paraList, ErrorReminder errorReminder);
	
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
			if (!(item.getType() instanceof IntType)) {
				errorReminder.error(item.getLoc(), "the index of the array shoule be an integer.");
				return null;
			}
		}
		
		VarSymbol var = varList.get(identifier);
		Type type = var.getType();
		int dimension = node.getDimension();
		if (type instanceof ArrayType) {
			int tmp = ((ArrayType)type).getDimension();
			if (dimension > tmp) {
				errorReminder.error(node.getLoc(), "the dimension of the array \"" + identifier + "\" is invalid.");
				return null;
			}
			if (dimension >= tmp){
				String typeIdentifier = type.toString();
				return new VarSymbol(identifier, resolveType(typeIdentifier));
			}
			else {
				return new VarSymbol(identifier, new ArrayType(identifier, tmp - dimension));
			}
		} 
		else {
			errorReminder.error(node.getLoc(), "the dimension of the array \"" + identifier + "\" is invalid.");
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
	
	public abstract boolean inFunctScope();
	
	public abstract boolean inClassScope();
	
	public abstract ClassSymbol getClassSymbol();
	
	@Override
	public boolean isVar() {
		return false;
	}
	
	public abstract boolean isFunct();
}
