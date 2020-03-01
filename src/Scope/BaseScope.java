package Scope;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import utility.ErrorReminder;
import utility.Location;
import AST.ClassDefNode;
import AST.ExprNode;
import AST.FunctDefNode;
import AST.FunctExprNode;
import AST.VarDefListNode;
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
	public void declareVar(VarDefNode node, ErrorReminder errorReminder) {
		TypeNode typeNode = node.getType();
		String typeIdentifier = typeNode.typeString();
		Type type = resolveType(typeIdentifier);
		if (type == null) { 
			//check variable type
			errorReminder.error(node.getLoc(), 
				"class \'" + typeIdentifier + "\' was not decalred in this scope."
			);
			//return;
		}
		if (typeIdentifier.equals("void")) {
			errorReminder.error(node.getLoc(), "the variable declared void.");
			//return;
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
		}
		else 
			this.varList.put(identifier, new VarSymbol(identifier, type));
	}

	public abstract Type resolveType(String identifier); 
	
	public abstract VarSymbol resovleVar(VarExprNode node, ErrorReminder errorReminder);
	
	public abstract VarSymbol resovleArray(ArrayExprNode node, ErrorReminder errorReminder);
	
	public abstract FunctSymbol resolveFunct(FunctExprNode node, ErrorReminder errorReminder);
	
	public abstract boolean inLoopScope();
	
	public abstract boolean inIfScope();

	public abstract FunctSymbol getFunctSymbol();

	public abstract ClassSymbol getClassSymbol();
	
	public abstract FunctSymbol getFunctScope(String identifier);
	
	public abstract ClassSymbol getClassScope(String identifier);
}
