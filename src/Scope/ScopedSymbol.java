package Scope;

import java.util.ArrayList;
import java.util.LinkedHashMap;

import AST.ArrayExprNode;
import AST.ArrayTypeNode;
import AST.ClassDefNode;
import AST.FunctDefNode;
import AST.FunctExprNode;
import AST.TypeNode;
import AST.VarDefListNode;
import AST.VarDefNode;
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
				"the type \"" + typeIdentifier + "\" is not declared in this scope."
			);
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
	
	@Override
	public Scope defineClass(ClassDefNode node, ErrorReminder errorReminder) {
		errorReminder.error(node.getLoc(), "invalid class definition.");
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
	
	public abstract ClassSymbol getClassSymbol();
	
	@Override
	public boolean isVar() {
		return false;
	}
	
	public abstract boolean isFunct();
}
