package yapl.interfaces;

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
public interface BackendAsmRM
{
    /*--- implementation constants ---*/
    
    /**
     * Return the machine word size in bytes.
     */
    public int wordSize();

    /**
     * Return numerical representation of boolean value.     
     */
    public int boolValue(boolean value);

    /*--- register management ---*/
    
    /** 
     * Allocate a register.
     * @return the register number or -1 if all registers are in use.
     */
    public byte allocReg();
    
    /** 
     * Deallocate a register.
     * @param reg       the register number.
     */
    public void freeReg(byte reg);
    
    /** Return the number of a fixed register containing zero. 
     * @return the register number. 
     */
    public byte zeroReg();
    
    /*--- emitting comments and labels ---*/
	
	/** 
	 * Emit a one-line comment into assembler code. 
	 */
	public void comment(String comment);

	/** 
	 * Emit an address label with comment. 
	 * @param label		the label.
	 * @param comment	the comment, may be <code>null</code>. 
	 */
	public void emitLabel(String label, String comment);
	
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
    public int allocStaticData(int bytes, String comment);

    /**
     * Allocate string constant in static data area.
     * The string constant will be terminated by a zero byte.
     * @param string   string constant (not quoted).
     * @return         start offset of allocated string in static data area. 
     */
    public int allocStringConstant(String string);

    /** 
     * Allocate space on the stack.
     * The allocated space will be word-aligned.
     * @param bytes     number of bytes to be allocated.
     * @param comment   an inline comment to be emitted when
     *                  generating assembler code (may be <code>null</code>).
     * @return the offset of the lowest address of the 
     *         allocated stack area. 
     */
    public int allocStack(int bytes, String comment);
    
    /*--- run-time memory allocation ---*/
    
	/**
	 * Store array dimension length.
	 * Needs to be called with increasing values of <code>dim</code>, before
	 * calling {@link #allocArray(byte, int)}.
	 * @param dim      array dimension; starts at 0.
	 * @param lenReg   register containing the length of the given array dimension.
	 */
	public void storeArrayDim(int dim, byte lenReg);
	
	/**
	 * Allocate (one- or multi-dimensional) array at run time.
	 * Only word-sized element types are supported.
	 * {@link #storeArrayDim(int, byte)} must have been called before.
	 * @param destReg   register where to store the array start address.
	 */
	public void allocArray(byte destReg);
	
    /*--- load/store operations ---*/
    
	/** 
	 * Issue a <em>load constant</em> (aka <em>load immediate</em>)
	 * instruction.
	 * @param reg		the destination register.
	 * @param value		the constant value (word) to be loaded.
	 */
	public void loadConst(byte reg, int value);
	
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
	public void loadAddress(byte reg, int addr, boolean isStatic);
	
	/** Issue a <em>load word</em> instruction using an offset address.
	 * @param reg		the destination register.
	 * @param addr		the source memory offset address.
	 * @param isStatic  if <code>true</code>, <code>addr</code> is
	 *                  relative to the static data area; otherwise, 
	 *                  it is relative to the current stack frame.
	 */
	public void loadWord(byte reg, int addr, boolean isStatic);
	
	/** Issue a <em>store word</em> instruction using an offset address.
	 * @param reg		the source register.
	 * @param addr		the destination offset address.
     * @param isStatic  if <code>true</code>, <code>addr</code> is
     *                  relative to the static data area; otherwise, 
     *                  it is relative to the current stack frame.
	 */
	public void storeWord(byte reg, int addr, boolean isStatic);
	
    /** Issue a <em>load word</em> instruction using an address register.
     * @param reg       the destination register.
     * @param addrReg   the register containing the memory address.
     */
    public void loadWordReg(byte reg, byte addrReg);
    
    /** Issue a <em>store word</em> instruction using an address register.
     * @param reg       the source register.
     * @param addrReg   the register containing the memory address.
     */
    public void storeWordReg(byte reg, int addrReg);
    
    /**
     * Load an array element (word).
     * @param dest      the destination register.
     * @param baseAddr  register holding the array base address.
     * @param index     register holding the element index.
     */
    public void loadArrayElement(byte dest, byte baseAddr, byte index);
    
	/**
	 * Store an array element (word).
	 * @param src       the source register.
	 * @param baseAddr  register holding the array base address.
	 * @param index     register holding the element index.
	 */
	public void storeArrayElement(byte src, byte baseAddr, byte index);
	
	/**
	 * Determine length of 1-dimensional array at run time.
	 * @param dest      the destination register.
	 * @param baseAddr  register holding the array base address.
	 */
	public void arrayLength(byte dest, byte baseAddr);
	
    /*--- run-time I/O operations ---*/
    
    /**
     * Emit code for printing a string constant.
     * @param addr    address offset of string constant in static data area. 
     */
    public void writeString(int addr);

    /*--- arithmetic operations ---*/
    
	/** 
	 * Negate integer register.<br>
	 * <code>regDest = -regX</code>.
	 * @param regDest		destination register number.
	 * @param regX			register number of source operand.
	 */
	public void neg(byte regDest, byte regX);
	
	/** 
	 * Add integer registers.<br>
	 * <code>regDest = regX + regY</code><br>
	 * The destination register may be equal to one of the source registers.
	 * @param regDest		destination register number.
	 * @param regX			register number of first source operand.
	 * @param regY			register number of second source operand.
	 */
	public void add(byte regDest, byte regX, byte regY);

	/** 
	 * Subtract integer registers.<br>
	 * <code>regDest = regX - regY</code><br>
	 * The destination register may be equal to one of the source registers.
	 * @param regDest		destination register number.
	 * @param regX			register number of first source operand.
	 * @param regY			register number of second source operand.
	 */
	public void sub(byte regDest, byte regX, byte regY);

	/** 
	 * Multiply integer registers.<br>
	 * <code>regDest = regX * regY</code><br>
	 * The destination register may be equal to one of the source registers.
	 * @param regDest		destination register number.
	 * @param regX			register number of first source operand.
	 * @param regY			register number of second source operand.
	 */
	public void mul(byte regDest, byte regX, byte regY);

	/** 
	 * Divide integer registers.<br>
	 * <code>regDest = regX / regY</code><br>
	 * The destination register may be equal to one of the source registers.
	 * @param regDest		destination register number.
	 * @param regX			register number of first source operand.
	 * @param regY			register number of second source operand.
	 */
	public void div(byte regDest, byte regX, byte regY);
	
	/** 
	 * Remainder of integer division.<br>
	 * <code>regDest = regX % regY</code><br>
	 * The destination register may be equal to one of the source registers.
	 * @param regDest		destination register number.
	 * @param regX			register number of first source operand.
	 * @param regY			register number of second source operand.
	 */
	public void mod(byte regDest, byte regX, byte regY);
	
    /*--- comparison operations ---*/
    
	/** Set boolean register value by <code>less than</code> comparison.<br>
	 * <code>regDest = (regX &lt; regY)</code><br>
	 * The destination register may be equal to one of the source registers.
	 * @param regDest		destination register number.
	 * @param regX			register number of first source operand.
	 * @param regY			register number of second source operand.
	 */
	public void isLess(byte regDest, byte regX, byte regY);
	
	/** 
	 * Set boolean register value by <code>less than or equal</code> comparison.<br>
	 * <code>regDest = (regX &lt;= regY)</code><br>
	 * The destination register may be equal to one of the source registers.
	 * @param regDest		destination register number.
	 * @param regX			register number of first source operand.
	 * @param regY			register number of second source operand.
	 */
	public void isLessOrEqual(byte regDest, byte regX, byte regY);
	
	/** 
	 * Set boolean register value by <code>is equal</code> comparison.<br>
	 * <code>regDest = (regX == regY)</code><br>
	 * The destination register may be equal to one of the source registers.
	 * @param regDest		destination register number.
	 * @param regX			register number of first source operand.
	 * @param regY			register number of second source operand.
	 */
	public void isEqual(byte regDest, byte regX, byte regY);
	
    /*--- logical operations ---*/
    
	/** 
	 * Logical NOT of a boolean register value.<br>
	 * <code>regDest = NOT regSrc</code><br>
	 * The destination register may be equal to the source register.
	 * @param regDest		destination register number.
	 * @param regSrc		source register number.
	 */
	public void not(byte regDest, byte regSrc);

	/** 
	 * Logical AND of boolean register values.<br>
	 * <code>regDest = regX AND regY</code><br>
	 * The destination register may be equal to one of the source registers.
	 * @param regDest		destination register number.
	 * @param regX			register number of first source operand.
	 * @param regY			register number of second source operand.
	 */
	public void and(byte regDest, byte regX, byte regY);
	
	/** 
	 * Logical OR of boolean register values.<br>
	 * <code>regDest = regX OR regY</code><br>
	 * The destination register may be equal to one of the source registers.
	 * @param regDest		destination register number.
	 * @param regX			register number of first source operand.
	 * @param regY			register number of second source operand.
	 */
	public void or(byte regDest, byte regX, byte regY);
	
    /*--- jump instructions ---*/
    
	/** 
	 * Emit branch instruction jumping to <code>label</code>
	 * if a register represents a given boolean value.
	 * @param reg		the register representing a boolean value.
	 * @param value		the boolean value to compare with <code>reg</code>.
	 * @param label		the label to jump to. 
	 */
	public void branchIf(byte reg, boolean value, String label);
	
	/** Emit unconditional jump to <code>label</code>. */
	public void jump(String label);
	
    /* --- procedure calls --- */
    
    /** 
     * Generate the main program's prolog. An assembler label suitable as
     * the main program entry point will be chosen by the implemenation. 
     */
    public void enterMain();
    
    /** 
     * Generate the main program's epilog, causing termination of the program.
     * This could be an 'exit' system call, for example.
     * @param label         the assembler label to use at epilog entry.
     */
    public void exitMain(String label);
    
    
    /** 
     * Generate a procedure's prolog (setup the stack frame).
     * @param label         the assembler label to use for the procedure.
     * @param nParams       the number of formal parameters.
     */
    public void enterProc(String label, int nParams);
    
    /** 
     * Generate a procedure's epilog and return to the caller.
     * Releases the procedure's stack frame.
     * @param label         the assembler label to use at epilog entry.
     */
    public void exitProc(String label);
    
    /** 
     * Return from procedure by jumping to the procedure epilog.
     * @param label     the procedure epilog label; must match the label given
     *                  to {@link #exitProc(String)}.
     * @param reg       the register holding the return value. Set this to -1
     *                  if the procedure does not return a value.
     */
    public void returnFromProc(String label, byte reg);
    
    /** 
     * Prepare stack frame for procedure call.
     * DO NOT allocate space on the stack between calling
     * this method and calling {@link #callProc(byte, String)}.
     * @param numArgs       the number of procedure arguments.
     */
    public void prepareProcCall(int numArgs);
    
    /** 
     * Pass an argument residing in a register to a procedure.
     * Needs to be called after {@link #prepareProcCall(int)}.
     * @param arg       the argument number, starting at 0.
     * @param reg       the register number where the argument
     *                  value resides.
     */
    public void passArg(int arg, byte reg);
    
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
    public void callProc(byte reg, String name);
    
    /** 
     * Return the stack address offset for a given procedure parameter index,
     * relative to the procedure's stack frame.
     * @param index     the parameter index, starts at 0.
     */
    public int paramOffset(int index);
    
}