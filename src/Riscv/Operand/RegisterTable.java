package Riscv.Operand;

import java.util.HashMap;
import java.util.Set;

public class RegisterTable  {
	public static RvPhysicalRegister zero = new RvPhysicalRegister("zero");
	public static RvPhysicalRegister ra = new RvPhysicalRegister("ra");
	public static RvPhysicalRegister sp = new RvPhysicalRegister("sp");
	public static RvPhysicalRegister gp = new RvPhysicalRegister("gp");
	public static RvPhysicalRegister tp = new RvPhysicalRegister("tp");
	public static RvPhysicalRegister t0 = new RvPhysicalRegister("t0");
	public static RvPhysicalRegister t1 = new RvPhysicalRegister("t1");
	public static RvPhysicalRegister t2 = new RvPhysicalRegister("t2");
	public static RvPhysicalRegister s0 = new RvPhysicalRegister("s0");
	public static RvPhysicalRegister s1 = new RvPhysicalRegister("s1");
	public static RvPhysicalRegister a0 = new RvPhysicalRegister("a0");
	public static RvPhysicalRegister a1 = new RvPhysicalRegister("a1");
	public static RvPhysicalRegister a2 = new RvPhysicalRegister("a2");
	public static RvPhysicalRegister a3 = new RvPhysicalRegister("a3");
	public static RvPhysicalRegister a4 = new RvPhysicalRegister("a4");
	public static RvPhysicalRegister a5 = new RvPhysicalRegister("a5");
	public static RvPhysicalRegister a6 = new RvPhysicalRegister("a6");
	public static RvPhysicalRegister a7 = new RvPhysicalRegister("a7");
	public static RvPhysicalRegister s2 = new RvPhysicalRegister("s2");
	public static RvPhysicalRegister s3 = new RvPhysicalRegister("s3");
	public static RvPhysicalRegister s4 = new RvPhysicalRegister("s4");
	public static RvPhysicalRegister s5 = new RvPhysicalRegister("s5");
	public static RvPhysicalRegister s6 = new RvPhysicalRegister("s6");
	public static RvPhysicalRegister s7 = new RvPhysicalRegister("s7");
	public static RvPhysicalRegister s8 = new RvPhysicalRegister("s8");
	public static RvPhysicalRegister s9 = new RvPhysicalRegister("s9");
	public static RvPhysicalRegister s10 = new RvPhysicalRegister("s10");
	public static RvPhysicalRegister s11 = new RvPhysicalRegister("s11");
	public static RvPhysicalRegister t3 = new RvPhysicalRegister("t3");
	public static RvPhysicalRegister t4 = new RvPhysicalRegister("t4");
	public static RvPhysicalRegister t5 = new RvPhysicalRegister("t5");
	public static RvPhysicalRegister t6 = new RvPhysicalRegister("t6");
	public static RvPhysicalRegister hi = new RvPhysicalRegister("%hi");
	public static RvPhysicalRegister lo = new RvPhysicalRegister("%lo");
	
	
	public static String[] names = {"zero", "ra", "sp", "gp", "tp", "t0", "t1", "t2", "s0", "s1", "a0", "a1", "a2", "a3", "a4", "a5", "a6", "a7", "s2", "s3", "s4", "s5", "s6", "s7", "s8", "s9", "s10", "s11", "t3", "t4", "t5", "t6" };
    public static String[] calleeSavedNames = {"s0", "s1", "s2", "s3", "s4", "s5", "s6", "s7", "s8", "s9", "s10", "s11"};
    public static String[] callerSavedNames = {"ra", "t0", "t1", "t2", "a0", "a1", "a2", "a3", "a4", "a5", "a6", "a7", "t3", "t4", "t5", "t6"};
    public static String[] argumentNames = {"a0", "a1", "a2", "a3", "a4", "a5", "a6", "a7"};
    public static String[] allocableNames = {"ra", "t0", "t1", "t2", "s0", "s1", "a0", "a1", "a2", "a3", "a4", "a5", "a6", "a7", "s2", "s3", "s4", "s5", "s6", "s7", "s8", "s9", "s10", "s11", "t3", "t4", "t5", "t6" };
   
    public static RvPhysicalRegister[] registers = {zero, ra, sp, gp, tp, t0, t1, t2, s0, s1, a0, a1, a2, a3, a4, a5, a6, a7, s2, s3, s4, s5, s6, s7, s8, s9, s10, s11, t3, t4, t5, t6};  
    public static RvPhysicalRegister[] argumentRegisters = {a0, a1, a2, a3, a4, a5, a6, a7};
    public static RvPhysicalRegister[] calleeSavedRegisters = {s0, s1, s2, s3, s4, s5, s6, s7, s8, s9, s10, s11}; //12
    public static RvPhysicalRegister[] callerSavedRegisters = {ra, t0, t1, t2, a0, a1, a2, a3, a4, a5, a6, a7, t3, t4, t5, t6}; //16
    public static RvPhysicalRegister[] allocableRegisters = {ra, t0, t1, t2, s0, s1, a0, a1, a2, a3, a4, a5, a6, a7, s2, s3, s4, s5, s6, s7, s8, s9, s10, s11, t3, t4, t5, t6};
    public static int allocableNumber = 28;
    //public static RvPhysicalRegister[] callerSavedRegisters = {ra, a0, a1, a2, a3, a4, a5, a6, a7}; //9
    //public static RvPhysicalRegister[] allocableRegisters = {ra, s0, s1, a0, a1, a2, a3, a4, a5, a6, a7, s2, s3, s4, s5, s6, s7, s8, s9, s10, s11};
    //public static int allocableNumber = 21;
    //public static Set<RvPhysicalRegister> allocableSet = Set.of(ra, s0, s1, a0, a1, a2, a3, a4, a5, a6, a7, s2, s3, s4, s5, s6, s7, s8, s9, s10, s11);
    
    public static Set<RvPhysicalRegister> allocableSet = Set.of(ra, t0, t1, t2, s0, s1, a0, a1, a2, a3, a4, a5, a6, a7, s2, s3, s4, s5, s6, s7, s8, s9, s10, s11, t3, t4, t5, t6);
    public static Set<RvPhysicalRegister> calleeSavedSet = Set.of(s0, s1, s2, s3, s4, s5, s6, s7, s8, s9, s10, s11); //12
    
    public static HashMap<String, RvPhysicalRegister> map;
    
    public RegisterTable() {
    	map = new HashMap<String, RvPhysicalRegister>();
    	map.put("zero", zero);
    	map.put("ra", ra);
    	map.put("sp", sp);
    	map.put("gp", gp);
    	map.put("tp", tp);
    	map.put("t0", t0);
    	map.put("t1", t1);
    	map.put("t2", t2);
    	map.put("s0", s0);
    	map.put("s1", s1);
    	map.put("a0", a0);
    	map.put("a1", a1);
    	map.put("a2", a2);
    	map.put("a3", a3);
    	map.put("a4", a4);
    	map.put("a5", a5);
    	map.put("a6", a6);
    	map.put("a7", a7);
    	map.put("s2", s2);
    	map.put("s3", s3);
    	map.put("s4", s4);
    	map.put("s5", s5);
    	map.put("s6", s6);
    	map.put("s7", s7);
    	map.put("s8", s8);
    	map.put("s9", s9);
    	map.put("s10", s10);
    	map.put("s11", s11);
    	map.put("t3", t3);
    	map.put("t4", t4);
    	map.put("t5", t5);
    	map.put("t6", t6);
    }

    public RvPhysicalRegister getRegister(String name) {
    	return map.get(name);
    }

}
