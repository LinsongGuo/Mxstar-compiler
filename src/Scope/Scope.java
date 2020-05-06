
package Scope;

import java.util.ArrayList;

import AST.VarDefNode;
import AST.FunctDefNode;
import AST.FunctExprNode;
import AST.ClassDefNode;
import AST.VarExprNode;
import AST.ArrayExprNode;
import utility.ErrorReminder;

public interface Scope {
	//public abstract Type stringToType(String s);
	public abstract Scope getGlobalScope();
	
	public abstract Scope getEnclosingScope();
	
	public abstract ClassSymbol declareClass(ClassDefNode node, ErrorReminder errorReminder);
	
	public abstract FunctSymbol declareFunct(FunctDefNode node, ErrorReminder errorReminder);

	public abstract void declareParaList(ArrayList<VarDefNode> paraList, ErrorReminder errorReminder);

	public abstract VarSymbol declareVar(VarDefNode node, ErrorReminder errorReminder);
	
	public abstract Type resolveType(String identifier); 
	
	public abstract VarSymbol resovleVar(VarExprNode node, ErrorReminder errorReminder);
	
	public abstract VarSymbol resovleArray(ArrayExprNode node, ErrorReminder errorReminder);
	
	public abstract FunctSymbol resolveFunct(FunctExprNode node, ErrorReminder errorReminder);
		
	public abstract boolean inLoopScope();
	
	public abstract boolean inIfScope();
	
	public abstract FunctSymbol InFunctSymbol();
	
	public abstract ClassSymbol InClassSymbol();
	
	public abstract VarSymbol getVarSymbol(String identifier);
	
	public abstract FunctSymbol getFunctScope(String identifier);
	
	public abstract ClassSymbol getClassScope(String identifier);	
	
	public abstract boolean duplicateClass(String identifier);

}
