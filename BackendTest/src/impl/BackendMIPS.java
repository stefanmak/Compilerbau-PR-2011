package impl;

import java.io.PrintStream;

import interfaces.BackendAsmRM;


/**
 * Compiler backend interface for generating assembler code on a register
 * machine.
 * <p>
 * Address offsets are relative to a certain base address (in the current stack
 * frame or in the static data area) which is determined and hided by the
 * implementation of this interface. Memory locations are always byte-addressed.
 * </p>
 * <p>
 * The machine word size is implementation-dependent and can be determined using
 * the {@link #wordSize()} method.
 * </p>
 * <p>
 * Boolean register values are to be interpreted according to
 * {@link #boolValue(boolean)}, which hides the actual numeric value within the
 * implementation of this interface.
 * </p>
 * <p>
 * Arrays may be allocated at run-time using the
 * {@link #storeArrayDim(int,byte)} and {@link #allocArray(byte,int)} methods.
 * Array access methods ({@link #loadArrayElement(byte,byte,byte,int)} and
 * {@link #storeArrayElement(byte,byte,byte,int)} support one-dimensional arrays
 * only. Multi-dimensional arrays need to be implemented by hierarchies of
 * one-dimensional arrays: an n-dimensional array with first dimension length k
 * is represented as a one-dimensional array of k pointers to (n-1)-dimensional
 * arrays. Hence, run-time allocation of multi-dimensional arrays needs support
 * by a recursive procedure in the run-time environment.
 * </p>
 * 
 * @author Mario Taschwer
 * @version $Id: BackendAsmRM.java 167 2011-05-10 10:25:55Z mt $
 */

public class BackendMIPS implements BackendAsmRM {

	// Variables
	private PrintStream printStream;
	
	static 	int sPC;            	// Address of the Start Entry
    static 	int pc,fp,sp;           // Program, Frame, Stackpointer
    

	// Constructor
	public BackendMIPS(PrintStream printStream) {
		this.printStream = printStream;
		initializing();
	}

	@Override
	public int wordSize() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int boolValue(boolean value) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public byte allocReg() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void freeReg(byte reg) {
		// TODO Auto-generated method stub

	}

	@Override
	public byte zeroReg() {
		// TODO Auto-generated method stub
		return 0;
	}

	/** 
	 * Emit a one-line comment into assembler code. 
	 */
	public void comment(String comment) {
		this.printStream.print("# " + comment + "\n");
	}

	/** 
	 * Emit an address label with comment. 
	 * @param label		the label.
	 * @param comment	the comment, may be <code>null</code>. 
	 */
	public void emitLabel(String label, String comment) {
		this.printStream.print(label + ": #" + comment);
	}

	@Override
	public int allocStaticData(int bytes, String comment) {
		// TODO Auto-generated method stub
		return 0;
	}

    /**
     * Allocate string constant in static data area.
     * The string constant will be terminated by a zero byte.
     * @param string   string constant (not quoted).
     * @return         start offset of allocated string in static data area. 
     */
	public int allocStringConstant(String string) {
	    this.printStream.println(".data");
	    this.printStream.println(".asciiz	" + string);
	    this.comment("offset: ");
		return 0;
	}

	@Override
	public int allocStack(int bytes, String comment) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void storeArrayDim(int dim, byte lenReg) {
		// TODO Auto-generated method stub

	}

	@Override
	public void allocArray(byte destReg) {
		// TODO Auto-generated method stub

	}

	@Override
	public void loadConst(byte reg, int value) {
		// TODO Auto-generated method stub

	}

	@Override
	public void loadAddress(byte reg, int addr, boolean isStatic) {
		// TODO Auto-generated method stub

	}

	@Override
	public void loadWord(byte reg, int addr, boolean isStatic) {
		// TODO Auto-generated method stub

	}

	@Override
	public void storeWord(byte reg, int addr, boolean isStatic) {
		// TODO Auto-generated method stub

	}

	@Override
	public void loadWordReg(byte reg, byte addrReg) {
		// TODO Auto-generated method stub

	}

	@Override
	public void storeWordReg(byte reg, int addrReg) {
		// TODO Auto-generated method stub

	}

	@Override
	public void loadArrayElement(byte dest, byte baseAddr, byte index) {
		// TODO Auto-generated method stub

	}

	@Override
	public void storeArrayElement(byte src, byte baseAddr, byte index) {
		// TODO Auto-generated method stub

	}

	@Override
	public void arrayLength(byte dest, byte baseAddr) {
		// TODO Auto-generated method stub

	}

	@Override
	public void writeString(int addr) {
		// TODO Auto-generated method stub

	}

	@Override
	public void neg(byte regDest, byte regX) {
		// TODO Auto-generated method stub

	}

	@Override
	public void add(byte regDest, byte regX, byte regY) {
		// TODO Auto-generated method stub

	}

	@Override
	public void sub(byte regDest, byte regX, byte regY) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mul(byte regDest, byte regX, byte regY) {
		// TODO Auto-generated method stub

	}

	@Override
	public void div(byte regDest, byte regX, byte regY) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mod(byte regDest, byte regX, byte regY) {
		// TODO Auto-generated method stub

	}

	@Override
	public void isLess(byte regDest, byte regX, byte regY) {
		// TODO Auto-generated method stub

	}

	@Override
	public void isLessOrEqual(byte regDest, byte regX, byte regY) {
		// TODO Auto-generated method stub

	}

	@Override
	public void isEqual(byte regDest, byte regX, byte regY) {
		// TODO Auto-generated method stub

	}

	@Override
	public void not(byte regDest, byte regSrc) {
		// TODO Auto-generated method stub

	}

	@Override
	public void and(byte regDest, byte regX, byte regY) {
		// TODO Auto-generated method stub

	}

	@Override
	public void or(byte regDest, byte regX, byte regY) {
		// TODO Auto-generated method stub

	}

	@Override
	public void branchIf(byte reg, boolean value, String label) {
		// TODO Auto-generated method stub

	}

	@Override
	public void jump(String label) {
		// TODO Auto-generated method stub

	}

	/** 
     * Generate the main program's prolog. An assembler label suitable as
     * the main program entry point will be chosen by the implemenation. 
     */
	public void enterMain() {
		this.emitLabel("main", "");
		this.printStream.println("move	$fp, $sp");
		//this.loadAddress(reg, addr, isStatic);
		this.printStream.println("la  	$23, staticData	# pointer to static data");
		
	}

    /** 
     * Generate the main program's epilog, causing termination of the program.
     * This could be an 'exit' system call, for example.
     * @param label         the assembler label to use at epilog entry.
     */
	public void exitMain(String label) {
				
		this.printStream.println(label + ":");
		this.printStream.println("li  	$v0, 10	# exit system call");
		this.printStream.println("syscall");

	}

	@Override
	public void enterProc(String label, int nParams) {
		// TODO Auto-generated method stub

	}

	@Override
	public void exitProc(String label) {
		// TODO Auto-generated method stub

	}

	@Override
	public void returnFromProc(String label, byte reg) {
		// TODO Auto-generated method stub

	}

	@Override
	public void prepareProcCall(int numArgs) {
		// TODO Auto-generated method stub

	}

	@Override
	public void passArg(int arg, byte reg) {
		// TODO Auto-generated method stub

	}

	@Override
	public void callProc(byte reg, String name) {
		// TODO Auto-generated method stub

	}

	@Override
	public int paramOffset(int index) {
		// TODO Auto-generated method stub
		return 0;
	}
	
	/**
	 * Prints out initial code
	 */
	public void initializing(){
		this.comment(" MIPS assembler code generated by the YAPL compiler");
		this.comment(" (C) 2011 ITEC, Klagenfurt University (mt@itec.aau.at)");
		
		this.printStream.println(".data");
		this.printStream.println("staticData:");
		this.printStream.println(".align 2");
		this.printStream.println(".space 4	# dimAddr1 (offset = 0)");
		
	}

}
