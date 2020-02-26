package Scope;

import java.util.ArrayList;
import AST.VarDefNode;
import AST.VarDefListNode;
import AST.FunctDefNode;
import AST.ClassDefNode;
import utility.ErrorReminder;

public interface Scope {
	public abstract Scope getEnclosingScope();

	public abstract Type resolveType(String identifier); 
	
	public abstract void defineVarList(VarDefListNode node, ErrorReminder errorReminder);

	public abstract Scope defineFunct(FunctDefNode node, ErrorReminder errorReminder);

	public abstract Scope defineClass(ClassDefNode node, ErrorReminder errorReminder);
	
	public abstract void defineParaList(ArrayList<VarDefNode> paraList, ErrorReminder errorReminder);

}
