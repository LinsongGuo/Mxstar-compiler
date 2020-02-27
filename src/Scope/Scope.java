
package Scope;

import java.util.ArrayList;

import AST.VarDefNode;
import AST.VarDefListNode;
import AST.FunctDefNode;
import AST.FunctExprNode;
import AST.ClassDefNode;
import AST.ArrayExprNode;
import utility.ErrorReminder;
import utility.Location;

public interface Scope {
	//public abstract Type stringToType(String s);
	public abstract Scope getGlobalScope();
	
	public abstract Scope getEnclosingScope();
	
	public abstract Type resolveType(String identifier); 
	
	public abstract VarSymbol resovleVar(ArrayExprNode node, ErrorReminder errorReminder);
	
	public abstract FunctSymbol resolveFunct(FunctExprNode node, ArrayList<Type> typeList, ErrorReminder errorReminder);
		
	public abstract void defineVarList(VarDefListNode node, ErrorReminder errorReminder);

	public abstract Scope defineFunct(FunctDefNode node, ErrorReminder errorReminder);

	public abstract Scope defineClass(ClassDefNode node, ErrorReminder errorReminder);
	
	public abstract void defineParaList(ArrayList<VarDefNode> paraList, ErrorReminder errorReminder);

	public abstract boolean inLoopScope();
	
	public abstract boolean inIfScope();
	
	public abstract boolean inFunctScope();
	
	public abstract boolean inClassScope();
	
	public abstract ClassSymbol getClassSymbol();
	
}
