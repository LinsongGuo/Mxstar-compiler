package Scope;

import AST.ExprNode;
import utility.ErrorReminder;

public class VarSymbol extends Symbol{
	protected Type type;
	
	public VarSymbol(String identifier, Type type) {
		super(identifier);
		this.type = type;
	}
	
	public Type getType() {
		return type;
	}
	
	@Override
	public boolean isVar() {
		return true;
	}
	
	@Override
	public boolean isFunct() {
		return false;
	}
	
	public void checkInitValue(ExprNode initValue, ErrorReminder errorReminder) {
		Type initType = initValue.getType();
		if (initType != null) {
			if (!initType.toString().equals(type.toString())) {
				errorReminder.error(initValue.getLoc(), 
					"cannot convert \'" + initType.toString() + "\' to \'" + type.toString() + "\' in initialization."
				);
			}
		}
	}	
}
