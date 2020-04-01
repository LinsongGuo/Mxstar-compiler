package IR;

import java.util.ArrayList;
import java.util.LinkedHashMap;

import IR.Symbol.IRConstString;
import IR.Symbol.IRGlobalString;
import IR.Symbol.IRGlobalVariable;
import IR.Symbol.IRRegister;
import IR.Type.IRArrayType;
import IR.Type.IRClassType;
import IR.Type.IRInt1Type;
import IR.Type.IRInt32Type;
import IR.Type.IRInt8Type;
import IR.Type.IRPtrType;
import IR.Type.IRVoidType;
import Scope.FunctSymbol;
import Scope.GlobalScope;

public class IRModule {
	private LinkedHashMap<String, IRGlobalVariable> globalVarList;
	private LinkedHashMap<String, IRGlobalString> stringList;
	private LinkedHashMap<String, IRFunction> functList, builtInFunctList;
	private LinkedHashMap<String, IRClassType> classList;
	
	public IRModule(GlobalScope globalScope, Scope.StringType stringTemplate) {
		LinkedHashMap<String, FunctSymbol> functSymbols = globalScope.getFunctList();
		
		globalVarList = new LinkedHashMap<String, IRGlobalVariable>();
		stringList = new LinkedHashMap<String, IRGlobalString>();
		functList = new LinkedHashMap<String, IRFunction>();
		builtInFunctList = new LinkedHashMap<String, IRFunction>();
		classList = new LinkedHashMap<String, IRClassType>();
		
		ArrayList<IRRegister> parameters1 = new ArrayList<IRRegister>();
		parameters1.add(new IRRegister(new IRPtrType(new IRInt8Type()), "str"));
		IRFunction funct1 = new IRFunction(new IRVoidType(), "__print", parameters1);
		builtInFunctList.put("__print", funct1);
		functSymbols.get("print").setIRFunction(funct1);
		
		ArrayList<IRRegister> parameters2 = new ArrayList<IRRegister>();
		parameters2.add(new IRRegister(new IRPtrType(new IRInt8Type()), "str"));
		IRFunction funct2 = new IRFunction(new IRVoidType(), "__println", parameters2);
		builtInFunctList.put("__println", funct2);
		functSymbols.get("println").setIRFunction(funct2);
		
		ArrayList<IRRegister> parameters3 = new ArrayList<IRRegister>();
		parameters3.add(new IRRegister(new IRInt32Type(), "n"));
		IRFunction funct3 = new IRFunction(new IRVoidType(), "__printInt", parameters3);
		builtInFunctList.put("__printInt", funct3);
		functSymbols.get("printInt").setIRFunction(funct3);		
		
		ArrayList<IRRegister> parameters4 = new ArrayList<IRRegister>();
		parameters4.add(new IRRegister(new IRInt32Type(), "n"));
		IRFunction funct4 = new IRFunction(new IRVoidType(), "__printlnInt", parameters4);
		builtInFunctList.put("__printlnInt", funct4);
		functSymbols.get("printlnInt").setIRFunction(funct4);
		
		ArrayList<IRRegister> parameters5 = new ArrayList<IRRegister>();
		IRFunction funct5 = new IRFunction(new IRPtrType(new IRInt8Type()), "__getString", parameters5);
		builtInFunctList.put("__getString", funct5);
		functSymbols.get("getString").setIRFunction(funct5);
		
		ArrayList<IRRegister> parameters6 = new ArrayList<IRRegister>();
		IRFunction funct6 = new IRFunction(new IRInt32Type(), "__getInt", parameters6);
		builtInFunctList.put("__getInt", funct6);
		functSymbols.get("getInt").setIRFunction(funct6);	
		
		ArrayList<IRRegister> parameters7 = new ArrayList<IRRegister>();
		parameters7.add(new IRRegister(new IRInt32Type(), "i"));
		IRFunction funct7 = new IRFunction(new IRPtrType(new IRInt8Type()), "__toString", parameters7);
		builtInFunctList.put("__toString", funct7);
		functSymbols.get("toString").setIRFunction(funct7);
		/*
		ArrayList<IRRegister> parameters8 = new ArrayList<IRRegister>();
		builtInFunctList.put("__arraySize", funct8);
		*/
		
		LinkedHashMap<String, FunctSymbol> stringFuncts = stringTemplate.getFunctList();
		
		//string.length()
		ArrayList<IRRegister> parameters9 = new ArrayList<IRRegister>();
		parameters9.add(new IRRegister(new IRPtrType(new IRInt8Type()), "str"));
		IRFunction funct9 = new IRFunction(new IRInt32Type(), "__stringLength", parameters9);
		builtInFunctList.put("__stringLength", funct9);
		stringFuncts.get("length").setIRFunction(funct9);
		
		//string.substring(l, r)
		ArrayList<IRRegister> parameters10 = new ArrayList<IRRegister>();
		parameters10.add(new IRRegister(new IRPtrType(new IRInt8Type()), "str"));
		parameters10.add(new IRRegister(new IRInt32Type(), "l"));
		parameters10.add(new IRRegister(new IRInt32Type(), "r"));
		IRFunction funct10 = new IRFunction(new IRPtrType(new IRInt8Type()), "__stringSubstring", parameters10);
		builtInFunctList.put("__stringSubstring", funct10);
		stringFuncts.get("substring").setIRFunction(funct10);
		
		//string.parseInt()
		ArrayList<IRRegister> parameters11 = new ArrayList<IRRegister>();
		parameters11.add(new IRRegister(new IRPtrType(new IRInt8Type()), "str"));
		IRFunction funct11 = new IRFunction(new IRInt32Type(), "__stringParseInt", parameters11);
		builtInFunctList.put("__stringParseInt", funct11);
		stringFuncts.get("parseInt").setIRFunction(funct11);
		
		//int ord(str, pos)
		ArrayList<IRRegister> parameters12 = new ArrayList<IRRegister>();
		parameters12.add(new IRRegister(new IRPtrType(new IRInt8Type()), "str"));
		parameters12.add(new IRRegister(new IRInt32Type(), "pos"));
		IRFunction funct12 = new IRFunction(new IRInt32Type(), "__stringOrd", parameters12);
		builtInFunctList.put("__StringOrd", funct12);
		stringFuncts.get("ord").setIRFunction(funct12);
		
		//string.add(str1, str2)
		ArrayList<IRRegister> parameters13 = new ArrayList<IRRegister>();
		parameters13.add(new IRRegister(new IRPtrType(new IRInt8Type()), "str1"));
		parameters13.add(new IRRegister(new IRPtrType(new IRInt8Type()), "str2"));
		IRFunction funct13 = new IRFunction(new IRPtrType(new IRInt8Type()), "__stringAdd", parameters13);
		builtInFunctList.put("__stringAdd", funct13);
		
		//string.equal(str1, str2)
		ArrayList<IRRegister> parameters14 = new ArrayList<IRRegister>();
		parameters14.add(new IRRegister(new IRPtrType(new IRInt8Type()), "str1"));
		parameters14.add(new IRRegister(new IRPtrType(new IRInt8Type()), "str2"));
		IRFunction funct14 = new IRFunction(new IRInt1Type(), "__stringEqual", parameters14);
		builtInFunctList.put("__stringEqual", funct14);
		
		//string.notEqual(str1, str2)
		ArrayList<IRRegister> parameters15 = new ArrayList<IRRegister>();
		parameters15.add(new IRRegister(new IRPtrType(new IRInt8Type()), "str1"));
		parameters15.add(new IRRegister(new IRPtrType(new IRInt8Type()), "str2"));
		IRFunction funct15 = new IRFunction(new IRInt1Type(), "__stringNotEqual", parameters15);
		builtInFunctList.put("__stringNotEqual", funct15);
		
		//string.less(str1, str2)
		ArrayList<IRRegister> parameters16 = new ArrayList<IRRegister>();
		parameters16.add(new IRRegister(new IRPtrType(new IRInt8Type()), "str1"));
		parameters16.add(new IRRegister(new IRPtrType(new IRInt8Type()), "str2"));
		IRFunction funct16 = new IRFunction(new IRInt1Type(), "__stringLess", parameters16);
		builtInFunctList.put("__stringLess", funct16);
		
		//string.lessEqual(str1, str2)
		ArrayList<IRRegister> parameters17 = new ArrayList<IRRegister>();
		parameters17.add(new IRRegister(new IRPtrType(new IRInt8Type()), "str1"));
		parameters17.add(new IRRegister(new IRPtrType(new IRInt8Type()), "str2"));
		IRFunction funct17 = new IRFunction(new IRInt1Type(), "__stringLessEqual", parameters17);
		builtInFunctList.put("__stringLessEqual", funct17);
		
		//string.greater(str1, str2)
		ArrayList<IRRegister> parameters18 = new ArrayList<IRRegister>();
		parameters18.add(new IRRegister(new IRPtrType(new IRInt8Type()), "str1"));
		parameters18.add(new IRRegister(new IRPtrType(new IRInt8Type()), "str2"));
		IRFunction funct18 = new IRFunction(new IRInt1Type(), "__stringGreater", parameters18);
		builtInFunctList.put("__stringGreater", funct18);
		
		//string.greaterEqual(str1, str2)
		ArrayList<IRRegister> parameters19 = new ArrayList<IRRegister>();
		parameters19.add(new IRRegister(new IRPtrType(new IRInt8Type()), "str1"));
		parameters19.add(new IRRegister(new IRPtrType(new IRInt8Type()), "str2"));
		IRFunction funct19 = new IRFunction(new IRInt1Type(), "__stringGreaterEqual", parameters19);
		builtInFunctList.put("__stringGreaterEqual", funct19);
	}

	public IRFunction getBuiltInFunct(String name) {
		return builtInFunctList.get(name);
	}
	
	public LinkedHashMap<String, IRFunction> getBuiltInFunctList() {
		return builtInFunctList;
	}
	
	public void addGlobalVariable(IRGlobalVariable globalVar) {
		globalVarList.put(globalVar.getName(), globalVar);
	} 
	
	public IRGlobalVariable getGlobalVar(String name) {
		if(!globalVarList.containsKey(name)) {
			System.err.println("error in getGlobalVar!");
		}
		return globalVarList.get(name);
 	}
	
	public LinkedHashMap<String, IRGlobalVariable> getGlobalVarList() {
		return globalVarList;
	}
	
	public IRGlobalString addGlobalString(String str) {
		str = str.replace("\\\\", "\\");
        str = str.replace("\\n", "\n");
        str = str.replace("\\\"", "\"");
        str = str + "\0";
        if (stringList.containsKey(str)) 
        	return stringList.get(str);
        int label = stringList.size();
        IRGlobalString globalString = new IRGlobalString(new IRPtrType(new IRArrayType(new IRInt8Type(), str.length())), ".str." + String.valueOf(label), new IRConstString(str));
        stringList.put(str, globalString);
        return globalString;
	} 
	
	public LinkedHashMap<String, IRGlobalString> getStringList() {
		return stringList;
	}
	
	public void addFunct(String name, IRFunction funct) {
		functList.put(name, funct);
	}
	
	public IRFunction getFunct(String name) {
		return functList.get(name);
	}
	
	public LinkedHashMap<String, IRFunction> getFunctList() {
		return functList;
	}
	
	public void addClass(String name, IRClassType classType) {
		classList.put(name, classType);
	}
	
	public IRClassType getClass(String name) {
		return classList.get(name);
	}
	
	public LinkedHashMap<String, IRClassType> getClassList() {
		return classList;
	}
	
	public void accept(IRVisitor visitor) {
		visitor.visit(this);
	}
}
