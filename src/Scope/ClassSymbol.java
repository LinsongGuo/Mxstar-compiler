package Scope;

import java.util.LinkedHashMap;

import AST.ClassDefNode;
import AST.FunctDefNode;
import utility.ErrorReminder;

public class ClassSymbol extends ScopedSymbol {
	private LinkedHashMap<String, FunctSymbol> functList;
	
	public ClassSymbol(Scope parent, String identifier) {
		super(parent, identifier);
	}

	@Override
	public void defineFunct(FunctDefNode node, ErrorReminder errorReminder) {
		errorReminder.error(node.getLoc(), "404 NOT FOUND!");
	}

	@Override
	public void defineClass(ClassDefNode node, ErrorReminder errorReminder) {
		errorReminder.error(node.getLoc(), "404 NOT FOUND!");
	}
	
}
