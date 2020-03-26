package IR;

import java.util.ArrayList;
import java.util.HashMap;

import IR.Symbol.IRRegister;

public class SymbolHash {
	 private HashMap<String, ArrayList<Object>> symbolHash;
	 
	 public SymbolHash() {
		 symbolHash = new HashMap<String, ArrayList<Object>>();
	 }
	 
	 public void put(String name, Object object) { 
		 if (!symbolHash.containsKey(name)) {
			 symbolHash.put(name, new ArrayList<Object>());
		 }
		 ArrayList<Object> tmp = symbolHash.get(name);
		 
		 if (object instanceof IRRegister) {
			 ((IRRegister) object).setName(name + "." + tmp.size()); 
		 }
		 else if (object instanceof IRBasicBlock) {
			((IRBasicBlock) object).setName(name + "." + tmp.size()); 
		 }
		 else {
			 System.err.println("error in SymbolHash!");
		 }
		 tmp.add(object);
	 }
}
