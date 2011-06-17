package yapl.impl;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.PrintStream;
import java.util.HashMap;


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
 * 
 * 
 * Notes: 
 * Bool True => 11111111 
 * Bool False => 00000000 
 * 
 */
public class BackendMIPS implements yapl.interfaces.BackendAsmRM {
	
	public PrintStream printStream;

	/*--- Pointers --*/
	public int sPC;           	 	// Address of the Start Entry
    public int pc,fp,sp;            // Program, Frame, Stackpointer
    public int gp = 0;				// Global Pointer for static data
       
    /*--- Register Mapping ---*/        
    private boolean[] registers;  		// Saves if the register is used
    HashMap<Byte,String> registerName;	// Mapping of the registers
        
    /*--- Constants ---*/
    private final int WORDSIZE = 4;
    private final int TRUE = 1;
    private final int FALSE = 0;
    
    /*--- Static data ---*/
    private boolean[] staticData;				// stores on word ares
    private boolean staticDataLabelPrinted = false;	// stores if the .data - staticData Lable was printed
    private boolean textLabelPrinted = false;	// stores if the .close Lable was printed
    
    /*---- Restore Counter ---*/
    public int counter = 0;
    
    /**
     * Constructor
     * @param printStream - for printing the generated code
     */
	public BackendMIPS(PrintStream printStream) {
		
		registers = new boolean[32];
		registerName = new HashMap<Byte,String>();
		staticData = new boolean[16000];	
		initRegisterNames();

		this.printStream = printStream;
		this.initHeaderAndStaticData();
	}

    /*--- implementation constants ---*/    
	
    /**
     * Return the machine word size in bytes.
     */
	public int wordSize() { 
		return WORDSIZE;
	}
	
    /**
     * Return numerical representation of boolean value.     
     */
	public int boolValue(boolean value) {
		if(value == true)
			return TRUE;
		return FALSE;
	}

   /*--- register management ---*/
    
    /** 
     * Allocate a register.
     * @return the register number or -1 if all registers are in use.
     */
	public byte allocReg() {
		
		// at first temporary registers
		for(int i = 8; i <= 15; i++){
			if(registers[i] == false){
				registers[i] = true;
				return (byte) i;
			}
		}
		for(int i = 24; i <= 25; i++){
			if(registers[i] == false){
				registers[i] = true;
				return (byte) i;
			}			
		}
		// long living variables
		for(int i = 16; i <= 23; i++){
			if(registers[i] == false){
				registers[i] = true;
				return (byte) i;
			}			
		}
		
		return (byte) -1;
			
	}

	/** 
     * Deallocate a register.
     * @param reg       the register number.
     */
	public void freeReg(byte reg) {		
		if(reg < 32)
			registers[reg] = false;
	}

	 /** Return the number of a fixed register containing zero. 
     * @return the register number. 
     */
	public byte zeroReg() {
		return (byte) 0;
	}

	
  /*--- emitting comments and labels ---*/
	
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
		if(comment == "")
			this.printStream.print(label + ":\n");
		else
			this.printStream.print(label + ": # " + comment + "\n");
	}

 /*--- compile-time memory allocation ---*/
    
    /**
     * Allocate space in static data area.
     * The allocated space will be word-aligned.
     * @param bytes     number of bytes to allocate space for.
     * @param comment   an inline comment to be emitted when
     *                  generating assembler code (may be <code>null</code>).
     * @return the offset of the lowest address of the 
     *         allocated static data area. 
     */
	public int allocStaticData(int bytes, String comment) {
		// returning Address for the starting point of the data area
		int returnAddress = this.gp;
		
		if(comment == null)
			comment = "";
				
		// calculations for word alignment
		double div = ((double)bytes) / ((double) this.wordSize());
		int cWords = (int) Math.ceil(div);
			
		
		// mark which 'block' are used
		for(int i = 0; i < cWords; i++ ){			
			this.staticData[this.gp/this.WORDSIZE+i] = true;
			this.gp += this.wordSize();
		}
		
		comment += " (offset = " + returnAddress + ")";
		
		// Prints the staticData
		if(!this.staticDataLabelPrinted){
			this.printStream.println(".data");
			this.emitLabel("staticData", "");						
			this.printStream.println("	.align 2");
			this.staticDataLabelPrinted = true;
		}
		 		
		this.printStream.println("	.space " + cWords*this.WORDSIZE + " # " + comment);
		
		return returnAddress;
	}

    /**
     * Allocate string constant in static data area.
     * The string constant will be terminated by a zero byte.
     * @param string   string constant (not quoted).
     * @return         start offset of allocated string in static data area. 
     */
	public int allocStringConstant(String string){				
		// returning Address for the starting point of the data area
		int returnAddress = this.gp;
		
		byte[] byteString = string.getBytes();		
		double div = ((double) (byteString.length+1))/((double)this.WORDSIZE);
		int cWords = (int)Math.ceil(div);
		// mark which 'block' are used
		for(int i = 0; i < cWords; i++ ){			
			this.staticData[this.gp/this.WORDSIZE+i] = true;
			this.gp += this.wordSize();
		}			
		
		// Prints the staticData
		if(!this.staticDataLabelPrinted){
			this.emitLabel("staticData", "");						
			this.printStream.println("	.align 2");
			this.staticDataLabelPrinted = true;
		}
		
		// calculate dummy bytes for word alignment
		int dummyBytes = cWords*this.WORDSIZE - (string.getBytes().length+1);
		
		// Exception for predefindend functions
		if(string.equals("\\n"))
			dummyBytes++;
		
		// Not static data
		if(this.textLabelPrinted){
			this.printStream.println(".data");
			this.printStream.println("	.asciiz \"" + string + "\" # offset = " + returnAddress);
			if(dummyBytes != 0 )
				this.printStream.println("	.space " + (dummyBytes) + " # dummyBytes");
			this.printStream.println(".text");
		}else{		
			this.printStream.println("	.asciiz \"" + string + "\" # offset = " + returnAddress);
			if(dummyBytes != 0 )
				this.printStream.println("	.space " + (dummyBytes) + " # dummyBytes");
		}
		
		
		return returnAddress;
	}

	/**
	 * Allocates a integer in static data
	 * @param string
	 * @return
	 */
	public int allocIntConstant(int integer){				
		// returning Address for the starting point of the data area
		int returnAddress = this.gp;
		this.gp += this.WORDSIZE;
		
		// Prints the staticData
		if(!this.staticDataLabelPrinted){
			this.emitLabel("staticData", "");						
			this.printStream.println("	.align 2");
			this.staticDataLabelPrinted = true;
		}
		
		// Not static data
		if(this.textLabelPrinted){
			this.printStream.println(".data");
			this.printStream.println("	.word " + integer + " # offset = " + returnAddress);
			this.printStream.println(".text");
		}else{		
			this.printStream.println("	.word " + integer + " # offset = " + returnAddress);
		}		
		
		return returnAddress;
	}
	
	
	
    /** 
     * Allocate space on the stack.
     * The allocated space will be word-aligned.
     * @param bytes     number of bytes to be allocated.
     * @param comment   an inline comment to be emitted when
     *                  generating assembler code (may be <code>null</code>).
     * @return the offset of the lowest address of the 
     *         allocated stack area. 
     */
	public int allocStack(int bytes, String comment) {
		// TODO Auto-generated method stub
		return 0;
	}

    /*--- run-time memory allocation ---*/
    
	/**
	 * Store array dimension length.
	 * Needs to be called with increasing values of <code>dim</code>, before
	 * calling {@link #allocArray(byte, int)}.
	 * @param dim      array dimension; starts at 0.
	 * @param lenReg   register containing the length of the given array dimension.
	 */
	public void storeArrayDim(int dim, byte lenReg) {
		// TODO Auto-generated method stub

	}

	/**
	 * Allocate (one- or multi-dimensional) array at run time.
	 * Only word-sized element types are supported.
	 * {@link #storeArrayDim(int, byte)} must have been called before.
	 * @param destReg   register where to store the array start address.
	 */
	public void allocArray(byte destReg) {
		// TODO Auto-generated method stub

	}

    /*--- load/store operations ---*/
    
	/** 
	 * Issue a <em>load constant</em> (aka <em>load immediate</em>)
	 * instruction.
	 * @param reg		the destination register.
	 * @param value		the constant value (word) to be loaded.
	 */
	public void loadConst(byte reg, int value) {
		String register = this.registerName.get(reg);
		if(register == "")
			register = "$" + reg;
		
		this.printStream.println("\tli " + register + "," + value);		
	}

	/**
	 * Issue a <em>load address</em> instruction using an offset address.
	 * The memory address corresponding to the location given by <code>addr</code>
	 * will be loaded into the destination register.
	 * @param reg       the destination register.
	 * @param addr      the memory offset address.
	 * @param isStatic  if <code>true</code>, <code>addr</code> is
     *                  relative to the static data area; otherwise, 
     *                  it is relative to the current stack frame.
	 */
	public void loadAddress(byte reg, int addr, boolean isStatic) {
		String register = this.registerName.get(reg);
		if(register == "")
			register = "$" + reg;
		
		if(isStatic){
			this.printStream.println("\tla " + register + ", " + addr + "($23)" );
		}else{
			this.printStream.println("\tla " + register + ", " + addr + "($sp)" );
		}
	}
	
	/** Issue a <em>load word</em> instruction using an offset address.
	 * @param reg		the destination register.
	 * @param addr		the source memory offset address.
	 * @param isStatic  if <code>true</code>, <code>addr</code> is
	 *                  relative to the static data area; otherwise, 
	 *                  it is relative to the current stack frame.
	 */
	public void loadWord(byte reg, int addr, boolean isStatic) {
		String register = this.registerName.get(reg);
		if(register == "")
			register = "$" + reg;
		
		if(isStatic){
			this.printStream.println("\tlw " + register + ", " + addr + "($23)" );
		}else{
			this.printStream.println("\tlw " + register + ", " + addr + "($sp)" );
		}
	}

	/** Issue a <em>store word</em> instruction using an offset address.
	 * @param reg		the source register.
	 * @param addr		the destination offset address.
     * @param isStatic  if <code>true</code>, <code>addr</code> is
     *                  relative to the static data area; otherwise, 
     *                  it is relative to the current stack frame.
	 */
	public void storeWord(byte reg, int addr, boolean isStatic) {		
		String register = this.registerName.get(reg);
		if(register == "")
			register = "$" + reg;
		
		if(isStatic){
			this.printStream.println("\tsw " + register + ", " + addr + "($23)" );
		}else{
			this.printStream.println("\tsw " + register + ", " + addr + "($sp)" );
		}
	}

    /** Issue a <em>load word</em> instruction using an address register.
     * @param reg       the destination register.
     * @param addrReg   the register containing the memory address.
     */
	public void loadWordReg(byte reg, byte addrReg) {
		String destReg = this.registerName.get(reg);
		String sourceReg = this.registerName.get(addrReg);
		if(destReg == "")
			destReg = "$" + reg;
		if(sourceReg == "")
			sourceReg = "$" + addrReg;		
		this.printStream.println("\tlw " + destReg + ", " + sourceReg);
	}

    /** Issue a <em>store word</em> instruction using an address register.
     * @param reg       the source register.
     * @param addrReg   the register containing the memory address.
     */
	public void storeWordReg(byte reg, int addrReg) {
		String destReg = this.registerName.get(reg);
		String sourceReg = this.registerName.get(addrReg);
		if(destReg == "")
			destReg = "$" + reg;
		if(sourceReg == "")
			sourceReg = "$" + addrReg;		
		this.printStream.println("\tsw " + destReg + ", " + sourceReg);
	}

    /**
     * Load an array element (word).
     * @param dest      the destination register.
     * @param baseAddr  register holding the array base address.
     * @param index     register holding the element index.
     */
	public void loadArrayElement(byte dest, byte baseAddr, byte index) {
		this.printStream.println("\tlw $" + dest + ", " + this.WORDSIZE*index + "($" + baseAddr + ")");
	}

	/**
	 * Store an array element (word).
	 * @param src       the source register.
	 * @param baseAddr  register holding the array base address.
	 * @param index     register holding the element index.
	 */
	public void storeArrayElement(byte src, byte baseAddr, byte index) {
		this.printStream.println("\tsw $" + src + ", " + this.WORDSIZE*index + "($" + baseAddr + ")");
	}

	/**
	 * Determine length of 1-dimensional array at run time.
	 * @param dest      the destination register.
	 * @param baseAddr  register holding the array base address.
	 */
	public void arrayLength(byte dest, byte baseAddr) {
		// TODO Auto-generated method stub
	}

    /*--- run-time I/O operations ---*/
    
    /**
     * Emit code for printing a string constant.
     * @param addr    address offset of string constant in static data area. 
     */
	public void writeString(int addr) {		
	    //addi	$sp, $sp, -4	# saveRegs
		//la  	$4, 4($23)
		//sw  	$4, 4($sp)	# arg 0
		//jal 	write
		//addi	$sp, $sp, 4	# restoreRegs complete
		
		this.printStream.println("\taddi	$sp, $sp, -4	# saveRegs");
		this.loadAddress((byte)4, addr, true);		
		this.printStream.println("\tsw  	$4, " + addr + "($sp)	# arg 0");
		this.callProc((byte) -1, "write");
		this.printStream.println("\taddi	$sp, $sp, 4	# saveRegs");
		
	}

    /*--- arithmetic operations ---*/
    
	/** 
	 * Negate integer register.<br>
	 * <code>regDest = -regX</code>.
	 * @param regDest		destination register number.
	 * @param regX			register number of source operand.
	 */
	public void neg(byte regDest, byte regX) {
		this.printStream.println("\tneg $" + regDest + ", $" + regX);
	}

	/** 
	 * Add integer registers.<br>
	 * <code>regDest = regX + regY</code><br>
	 * The destination register may be equal to one of the source registers.
	 * @param regDest		destination register number.
	 * @param regX			register number of first source operand.
	 * @param regY			register number of second source operand.
	 */
	public void add(byte regDest, byte regX, byte regY) {
		this.printStream.println("\tadd $" + regDest + ", $" + regX + ", $" + regY);
	}

	/** 
	 * Subtract integer registers.<br>
	 * <code>regDest = regX - regY</code><br>
	 * The destination register may be equal to one of the source registers.
	 * @param regDest		destination register number.
	 * @param regX			register number of first source operand.
	 * @param regY			register number of second source operand.
	 */
	public void sub(byte regDest, byte regX, byte regY) {
		this.printStream.println("\tsub $" + regDest + ", $" + regX + ", $" + regY);	
	}

	/** 
	 * Multiply integer registers.<br>
	 * <code>regDest = regX * regY</code><br>
	 * The destination register may be equal to one of the source registers.
	 * @param regDest		destination register number.
	 * @param regX			register number of first source operand.
	 * @param regY			register number of second source operand.
	 */
	public void mul(byte regDest, byte regX, byte regY) {
		this.printStream.println("\tmul $" + regDest + ", $" + regX + ", $" + regY);
	}
	
	/** 
	 * Divide integer registers.<br>
	 * <code>regDest = regX / regY</code><br>
	 * The destination register may be equal to one of the source registers.
	 * @param regDest		destination register number.
	 * @param regX			register number of first source operand.
	 * @param regY			register number of second source operand.
	 */
	public void div(byte regDest, byte regX, byte regY) {		
		// divide
		this.printStream.println("\tdiv $" + regX + ", $" + regY);
		// load HI to destination
		this.printStream.println("\tmfhi $" + regDest);
	}

	/** 
	 * Remainder of integer division.<br>
	 * <code>regDest = regX % regY</code><br>
	 * The destination register may be equal to one of the source registers.
	 * @param regDest		destination register number.
	 * @param regX			register number of first source operand.
	 * @param regY			register number of second source operand.
	 */
	public void mod(byte regDest, byte regX, byte regY) {		
		this.printStream.println("\tdiv\t$" + regX + ", $" + regY);
		this.printStream.println("\tmflo $" + regDest);
	}
	
    /*--- comparison operations ---*/
    
	/** Set boolean register value by <code>less than</code> comparison.<br>
	 * <code>regDest = (regX &lt; regY)</code><br>
	 * The destination register may be equal to one of the source registers.
	 * @param regDest		destination register number.
	 * @param regX			register number of first source operand.
	 * @param regY			register number of second source operand.
	 */
	public void isLess(byte regDest, byte regX, byte regY) {
		this.printStream.println ("\tslt $" + regDest + ", $" + regX + ", $" + regY);
	}

	/** 
	 * Set boolean register value by <code>less than or equal</code> comparison.<br>
	 * <code>regDest = (regX &lt;= regY)</code><br>
	 * The destination register may be equal to one of the source registers.
	 * @param regDest		destination register number.
	 * @param regX			register number of first source operand.
	 * @param regY			register number of second source operand.
	 */
	public void isLessOrEqual(byte regDest, byte regX, byte regY) {
		this.printStream.println ("\tsle\t$" + regDest + ", $" + regX + ", $" + regY);
	}

	/** 
	 * Set boolean register value by <code>is equal</code> comparison.<br>
	 * <code>regDest = (regX == regY)</code><br>
	 * The destination register may be equal to one of the source registers.
	 * @param regDest		destination register number.
	 * @param regX			register number of first source operand.
	 * @param regY			register number of second source operand.
	 */
	public void isEqual(byte regDest, byte regX, byte regY) {
		this.printStream.println ("\tseq $" + regDest + ", $" + regX + ", $" + regY);
	}

    /*--- logical operations ---*/
    
	/** 
	 * Logical NOT of a boolean register value.<br>
	 * <code>regDest = NOT regSrc</code><br>
	 * The destination register may be equal to the source register.
	 * @param regDest		destination register number.
	 * @param regSrc		source register number.
	 */
	public void not(byte regDest, byte regSrc) {
		this.printStream.println ("\tnot $" + regDest + ", $" + regSrc);
	}

	/** 
	 * Logical AND of boolean register values.<br>
	 * <code>regDest = regX AND regY</code><br>
	 * The destination register may be equal to one of the source registers.
	 * @param regDest		destination register number.
	 * @param regX			register number of first source operand.
	 * @param regY			register number of second source operand.
	 */
	public void and(byte regDest, byte regX, byte regY) {
		this.printStream.println ("\tseq $" + regDest + ", $" + regX + ", $" + regY);
	}

	/** 
	 * Logical OR of boolean register values.<br>
	 * <code>regDest = regX OR regY</code><br>
	 * The destination register may be equal to one of the source registers.
	 * @param regDest		destination register number.
	 * @param regX			register number of first source operand.
	 * @param regY			register number of second source operand.
	 */
	public void or(byte regDest, byte regX, byte regY) {
		this.printStream.println ("\tseq $" + regDest + ", $" + regX + ", $" + regY);
	}

   /*--- jump instructions ---*/
    
	/** 
	 * Emit branch instruction jumping to <code>label</code>
	 * if a register represents a given boolean value.
	 * @param reg		the register representing a boolean value.
	 * @param value		the boolean value to compare with <code>reg</code>.
	 * @param label		the label to jump to. 
	 */
	public void branchIf(byte reg, boolean value, String label) {
		if(value)
			this.printStream.println ("\tbeq $" + reg + ", 11111111, " + label);
		else
			this.printStream.println ("\tbeq $" + reg + ", 00000000, " + label);	
	}

	/** Emit unconditional jump to <code>label</code>. */
	public void jump(String label) {
		this.printStream.println("\tjal " + label);		
	}

	/** 
     * Generate the main program's prolog. An assembler label suitable as
     * the main program entry point will be chosen by the implemenation. 
     */
	public void enterMain() {		
		// closes the static data part if it's not done until now
		this.closeStaticData();
		// insert predefined functions
		this.insertPredefinedFunctions();		
		// print ".globl main"
		this.printStream.println(".globl main");
		// print "main:"
		this.emitLabel("main", "");
		// standard init main code		
		this.printStream.println("\tmove	$fp, $sp");		
		this.printStream.println("\tla  	$23, staticData	# pointer to static data");
		
	}

    /** 
     * Generate the main program's epilog, causing termination of the program.
     * This could be an 'exit' system call, for example.
     * @param label         the assembler label to use at epilog entry.
     */
	public void exitMain(String label) {
				
		this.printStream.println(label + ":");
		this.printStream.println("\tli  	$v0, 10	# exit system call");
		this.printStream.println("\tsyscall");

	}

    
    /** 
     * Generate a procedure's prolog (setup the stack frame).
     * @param label         the assembler label to use for the procedure.
     * @param nParams       the number of formal parameters.
     */
	public void enterProc(String label, int nParams) {
		
		// closes the static data part if it's not done until now
		this.closeStaticData();
		
		this.printStream.println(".globl " + label);		
		this.emitLabel(label, "");
		
		//addi	$sp, $sp, -8
	    //sw  	$fp, 8($sp)
	    //addi	$fp, $sp, 8
	    //sw  	$ra, -4($fp)
		this.printStream.println("\taddi	$sp, $sp, -8");		
		this.storeWord((byte) 30, (byte)8, false);
		this.printStream.println("\taddi	$fp, $sp, 8");		
		this.storeWord((byte)31,(byte)-4,false);		
		
	}
	
    /** 
     * Generate a procedure's epilog and return to the caller.
     * Releases the procedure's stack frame.
     * @param label         the assembler label to use at epilog entry.
     */
	public void exitProc(String label) {
		
		this.emitLabel(label, "");
		
		//lw  	$ra, -4($fp)
		//move	$sp, $fp
		//lw  	$fp, 0($fp)
		//jr  	$ra
		this.loadWord((byte)31, (byte)-4, false);		    
		this.printStream.println("\tmove	$sp, $fp");
		this.loadWord((byte)30, (byte)0, false);
		this.printStream.println("\tjr  	$ra");
		
	}

    /** 
     * Return from procedure by jumping to the procedure epilog.
     * @param label     the procedure epilog label; must match the label given
     *                  to {@link #exitProc(String)}.
     * @param reg       the register holding the return value. Set this to -1
     *                  if the procedure does not return a value.
     */
	public void returnFromProc(String label, byte reg) {		
		this.printStream.println("\tmove $v0, $" + reg);
		this.jump(label);
	}

    /** 
     * Prepare stack frame for procedure call.
     * DO NOT allocate space on the stack between calling
     * this method and calling {@link #callProc(byte, String)}.
     * @param numArgs       the number of procedure arguments.
     */
	public void prepareProcCall(int numArgs) {
		
	    //addi	$sp, $sp, -12	# saveRegs
		this.printStream.println("\taddi	$sp, $sp, " + (-this.WORDSIZE*numArgs));		
		this.comment("saveRegisters");
				
		// save all registers in current skope
		// at first temporary registers
		for(int i = 8; i <= 15; i++){
			this.storeWord((byte) i, (counter*(-this.WORDSIZE))-this.sp, false);
			counter++;
		}
		for(int i = 24; i <= 25; i++){
			this.storeWord((byte) i, (counter*(-this.WORDSIZE))-this.sp, false);
			counter++;			
		}
		// long living varialbes
		for(int i = 16; i <= 23; i++){
			this.storeWord((byte) i, (counter*(-this.WORDSIZE))-this.sp, false);
			counter++;
		}

	}

    /** 
     * Pass an argument residing in a register to a procedure.
     * Needs to be called after {@link #prepareProcCall(int)}.
     * @param arg       the argument number, starting at 0.
     * @param reg       the register number where the argument
     *                  value resides.
     */
	public void passArg(int arg, byte reg) {
		this.storeWord((byte) reg, (byte)((arg+1)*this.WORDSIZE), false);
		this.comment("pass Arg");
	}

    /** 
     * Generate a procedure call.
     * {@link #prepareProcCall(int)} MUST be called before.
     * Procedure arguments (if any) need to be passed using
     * {@link #passArg(int, byte)} before.
     * @param reg           register number for storing the
     *                      return value. Set this parameter to
     *                      -1, if the procedure does not return
     *                      a value.
     * @param name          the procedure name (label); must match
     *                      the label given to {@link #enterProc(String, boolean)}.
     */
	public void callProc(byte reg, String name) {
	    //jal 	func
	    //add 	$2, $v0, $zero
		
		this.jump(name);
		if(reg > 0 )
			this.add((byte) 2, (byte)2, (byte)0);		
	}
	
    /** 
     * Return the stack address offset for a given procedure parameter index,
     * relative to the procedure's stack frame.
     * @param index     the parameter index, starts at 0.
     */	public int paramOffset(int index) {
		return (index + 1) * this.WORDSIZE;		
	}
	
	/**
	 * Prints out initial code
	 */
	public void initHeaderAndStaticData(){
		this.comment(" MIPS assembler code generated by the YAPL compiler");
		this.comment(" (C) 2011 Compilerbau -> Stefan Mak)");				
		this.allocStaticData(4, "dimAddr1");
	    //.asciiz	"True"	# offset = 4
		this.allocStringConstant("True");		
	    //.asciiz	"False"	# offset = 9
		this.allocStringConstant("False");
	}
	
	/**
	 * Prints out the closing of the initial code and static data definition
	 */
	public void closeStaticData(){
		
		if(!this.textLabelPrinted){
			this.textLabelPrinted = true;
			this.printStream.println(".text");			
		}
	}
	
	/**
	 * Initializes the Mapping of the registernames to a byte value
	 */
	public void initRegisterNames(){
		//stores the value 0
		registerName.put((byte)0, "$zero");
		//reserved for assembler
		registerName.put((byte)1, "$at");
		//stores the function results, more than 2 results -> stack
		registerName.put((byte)2, "$v0");
		registerName.put((byte)3, "$v1");		
		//fucntion arguments, if more than 4 arguments are needed -> stack
		registerName.put((byte)4, "$a0");
		registerName.put((byte)5, "$a1");
		registerName.put((byte)6, "$a2");
		registerName.put((byte)7, "$a3");		
		// temporary variables
		registerName.put((byte)8, "$t0");
		registerName.put((byte)9, "$t1");
		registerName.put((byte)10, "$t2");
		registerName.put((byte)11, "$t3");
		registerName.put((byte)12, "$t4");
		registerName.put((byte)13, "$t5");
		registerName.put((byte)14, "$t6");
		registerName.put((byte)15, "$t7");
		registerName.put((byte)24, "$t8");
		registerName.put((byte)25, "$t9");		
		// long living variables
		for(int i = 16; i <= 23; i++){
			registerName.put((byte)i, "$s" + (i - 16));
		}		
		// reserved for kernel
		registerName.put((byte)26, "$k0");
		registerName.put((byte)27, "$k1");		
		// points to middle of a 64K block in the data segment
		registerName.put((byte)28, "$gp");		
		// stack pointer (top of stack)
		registerName.put((byte)29, "$sp");		
		// frame pointer (beginning of current frame)
		registerName.put((byte)30, "$fp");		
		// return address
		registerName.put((byte)31, "$ra");
	}
	
	/**
	 * Inserts the write, writeint and writebool function
	 */
	private void insertPredefinedFunctions(){
		File writeFile = new File("src/yapl/impl/PredefinedSources/write.code");				
		File writeintFile = new File("src/yapl/impl/PredefinedSources/writeint.code");
		File writeboolFile = new File("src/yapl/impl/PredefinedSources/writebool.code");
		
		FileReader fis = null;	    
	    BufferedReader br = null;
		
		try {
			  
			  // print WRITE
			  fis = new FileReader(writeFile); 		      
		      br = new BufferedReader(fis);
		      
		      while (br.ready()) {
		        this.printStream.println(br.readLine());
		      }		     		      
 
		      fis.close();
		      br.close();
		      
			  // print WRITEINT
			  fis = new FileReader(writeintFile); 		      
		      br = new BufferedReader(fis);
		      
		      while (br.ready()) {
		        this.printStream.println(br.readLine());
		      }
 
		      fis.close();
		      br.close();
		      
			  // print WRITEBOOL
			  fis = new FileReader(writeboolFile); 		      
		      br = new BufferedReader(fis);
		      
		      while (br.ready()) {
		        this.printStream.println(br.readLine());
		      }
 
		      fis.close();
		      br.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	
	/**
	 * Restores all Registers from stack
	 */
	public void restoreRegisters(){

		//Restore Registers
		
		// long living varialbes
		for(int i = 23; i >= 16; i--){
			this.loadWord((byte) i, (--counter*(-this.WORDSIZE))-this.sp, false);			
		}
		
		for(int i = 25; i >= 24; i--){
			this.loadWord((byte) i, (--counter*(-this.WORDSIZE))-this.sp, false);			
		}
					
		for(int i = 15; i >= 8; i--){
			this.loadWord((byte) i, (--counter*(-this.WORDSIZE))-this.sp, false);			
		}


		
	}
	
}
