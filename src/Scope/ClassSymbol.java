package Scope;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map;

import AST.ArrayExprNode;
import AST.ArrayTypeNode;
import AST.ExprNode;
import AST.FunctDefNode;
import AST.FunctExprNode;
import AST.TypeNode;
import AST.VarDefNode;
import AST.VarExprNode;
import IR.Type.IRClassType;
import IR.Type.IRType;
import utility.ErrorReminder;

public class ClassSymbol extends ScopedSymbol implements Type {
	protected LinkedHashMap<String, FunctSymbol> functList;
	private FunctSymbol constructor;
	
	public ClassSymbol(Scope parent, String identifier) {
		super(parent, identifier);
		functList = new LinkedHashMap<String, FunctSymbol>();
	}
	
	@Override
	public String toString() {
		return identifier;
	}
	
	@Override
	public String typeString() {
		return identifier;
	}
	
	@Override
	public FunctSymbol declareFunct(FunctDefNode node, ErrorReminder errorReminder) {
		//check return type
		String identifier = node.getIdentifier();
		TypeNode typeNode = node.getType();
		Type retType;
		if (typeNode == null) {
			errorReminder.error(node.getLoc(), "the function should have return value.");
			return null;		
		}
		else {
			String typeIdentifier = typeNode.typeString();
			retType = resolveType(typeIdentifier);
			if (retType == null) {
				errorReminder.error(node.getLoc(), 
					"\'" + typeIdentifier + "\' does not name a type."
				);
				return null;
			}
			else if (typeNode instanceof ArrayTypeNode) {
				int dimension = ((ArrayTypeNode) typeNode).getDimension();
				retType = new ArrayType(getGlobalScope(), typeIdentifier, dimension);
			}
		}
		//check identifier
		if (functList.containsKey(identifier)) {
			errorReminder.error(node.getLoc(), 
				"redeclaration of function \'" + identifier + "\'."
			);
			return null;
		}	
		FunctSymbol functSymbol = new FunctSymbol(this, identifier, retType);
		functList.put(identifier, functSymbol);
		return functSymbol;
	}
	
	@Override
	public void declareParaList(ArrayList<VarDefNode> paraList, ErrorReminder errorReminder) {
		
	}
	
	@Override
	public FunctSymbol resolveFunct(FunctExprNode node, ErrorReminder errorReminder) {
		String identifier = node.getIdentifier();
		if (!functList.containsKey(identifier)) {
			return parent.resolveFunct(node, errorReminder);
		}
		FunctSymbol functSymbol = functList.get(identifier);
		LinkedHashMap<String, VarSymbol> argueList = functSymbol.getParaList();
		ArrayList<ExprNode> paraList = node.getParaList();
		if (paraList.size() < argueList.size()) {
			errorReminder.error(node.getLoc(), "too few parameters to function \'" + identifier + "\'.");
		}
		if (paraList.size() > argueList.size()) {
			errorReminder.error(node.getLoc(), "too many parameters to function \'" + identifier + "\'.");
		}
		int i = 0;
		for (Map.Entry<String, VarSymbol> entry : argueList.entrySet()) {
			if (i >= paraList.size()) 
				break;
			Type tmp1 = entry.getValue().getType(), tmp2 = paraList.get(i).getType();
			if (tmp2 != null) {
				if (!tmp1.toString().equals(tmp2.toString())) {
					errorReminder.error(paraList.get(i).getLoc(), 
						"cannot convert \'" + tmp2.toString() + "\' to \'" + tmp1.toString() + "\' in initialization."
					);
				}
			}
			i++;
		}
		return functSymbol;
	}
		
	@Override
	public FunctSymbol InFunctSymbol() {
		return null;
	}
	
	@Override
	public ClassSymbol InClassSymbol() {
		return this;
	}
	
	@Override
	public FunctSymbol getFunctScope(String identifier) {
		return functList.get(identifier);
	}
	
	@Override
	public boolean isFunct() {
		return false;
	}
	
	@Override
	public boolean isBuiltInType() {
		return false;
	}
	
	public FunctSymbol declareConstructor() {
		String identifier = toString();
		constructor = new FunctSymbol(this, identifier, this);
		constructor.setConstructor();
		functList.put(identifier, constructor);
		return constructor;
	}
	
	public FunctSymbol getConstructor() {
		return constructor;
	}
	
	public VarSymbol findVar(VarExprNode node, ErrorReminder errorReminder) {
		String identifier = node.getIdentifier();
		if(!varList.containsKey(identifier)) {
			errorReminder.error(node.getLoc(),
				"class \'" + toString() + "\' has no member named " + node.getIdentifier()
			);
			return null;
		}
		else 
			return varList.get(identifier);
	}
	
	public VarSymbol findArray(String name) {
		return varList.get(name);
	}
	
	public VarSymbol findArray(ArrayExprNode node, ErrorReminder errorReminder) {
		String identifier = node.getIdentifier();
		if(!varList.containsKey(identifier)) {
			errorReminder.error(node.getLoc(),
				"class \'" + toString() + "\' has no member named " + identifier + "."
			);
			return null;
		}
		//check index
		ExprNode indexExpr = node.getIndexExpr();
		if (indexExpr != null) {
			Type indexType = indexExpr.getType();
			if (!(indexType instanceof IntType)) {
				errorReminder.error(indexExpr.getLoc(), "cannot convert \'" + indexType.toString() + "\' to \'int\'.");
			}
		}
		else {
			errorReminder.error(node.getLoc(), "empty index of array.");
		}	
		//get type
		VarSymbol var = varList.get(identifier);
		Type type = var.getType();
		if (type instanceof ArrayType) {
			int tmp = ((ArrayType)type).getDimension();
			String typeIdentifier = type.typeString();
			if (tmp == 1)
				return new VarSymbol(identifier, resolveType(typeIdentifier), this);
			else 
				return new VarSymbol(identifier, new ArrayType(getGlobalScope(), typeIdentifier, tmp - 1), this);
		} 
		else {
			errorReminder.error(node.getLoc(), "\'" + identifier + "\' is a variable not an array.");
			return null;
		}
	}
	
	public FunctSymbol findFunct(FunctExprNode node, ErrorReminder errorReminder) {
		String identifier = node.getIdentifier();
		if (!functList.containsKey(identifier)) {
			errorReminder.error(node.getLoc(),
				"class \'" + toString() + "\' has no member named \'" + node.getIdentifier() + "\'."
			);
			return null;
		}
		FunctSymbol functSymbol = functList.get(identifier);
		LinkedHashMap<String, VarSymbol> argueList = functSymbol.getParaList();
		ArrayList<ExprNode> paraList = node.getParaList();
		if (paraList.size() < argueList.size()) {
			errorReminder.error(node.getLoc(), "too few parameters to function \'" + identifier + "\'.");
		}
		if (paraList.size() > argueList.size()) {
			errorReminder.error(node.getLoc(), "too many parameters to function \'" + identifier + "\'.");
		}
		int i = 0;
		for (Map.Entry<String, VarSymbol> entry : argueList.entrySet()) {
			if (i >= paraList.size()) 
				break;
			Type tmp1 = entry.getValue().getType(), tmp2 = paraList.get(i).getType();
			if (tmp2 != null) {
				if (!tmp1.toString().equals(tmp2.toString())) {
					errorReminder.error(paraList.get(i).getLoc(), 
						"cannot convert \'" + tmp2.toString() + "\' to \'" + tmp1.toString() + "\' in initialization."
					);
				}
			}
			i++;
		}
		return functSymbol;
	}

	//for IR
	private IRClassType classType;
	
	public void setIRClass(IRClassType classType) {
		this.classType = classType;
	}
	
	public IRClassType toIRClass() {
		return classType;
	}
	
	public int order(String name) {
		int i = 0;
		for (Map.Entry<String, VarSymbol> entry : varList.entrySet()) {
			if (entry.getKey().equals(name))
				return i;
			++i;
		}
		return 0;
	}
}
