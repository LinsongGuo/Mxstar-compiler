package Scope;

import java.util.ArrayList;
import AST.VarDefListNode;
import AST.FunctDefNode;
import AST.ClassDefNode;
import utility.ErrorReminder;

public interface Scope {
	public abstract Scope getEnclosingScope();

	public abstract Type resolveType(String identifier); 
	
	public abstract void defineVar(VarDefListNode node, ErrorReminder errorReminder);

	public abstract void defineFunct(FunctDefNode node, ErrorReminder errorReminder);

	public abstract void defineClass(ClassDefNode node, ErrorReminder errorReminder);

}
