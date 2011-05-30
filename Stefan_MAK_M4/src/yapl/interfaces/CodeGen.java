package yapl.interfaces;

import yapl.impl.ArrayType;
import yapl.impl.Type;
import yapl.lib.YAPLException;

/**
 * Interface to code generator methods called by the parser. Some of these
 * methods should also implement type checking.
 * <p>
 * An implementation of this interface should not emit assembler or machine code
 * directly, but is expected to virtually "generate" generic 3-address-code by calling
 * methods of an appropriate backend interface only.
 * </p><p>
 * The term <em>register</em> is used for both register and stack machines.
 * In the latter case, a register value refers to an element
 * on the expression stack; register numbers are not needed then, because
 * the order of operands is implied by the stack.
 * </p>
 * 
 * @author Mario Taschwer
 * @version $Id: CodeGen.java 171 2011-05-16 13:31:35Z mt $
 */
public interface CodeGen {

	/**
	 * Generate a new address label. Labels must be unique.
	 */
	public String newLabel();

	/** Assign an address label to the current code address. */
	public void assignLabel(String label);

	/**
	 * Load the value represented by <code>attr</code> into a register
	 * if <code>attr</code> does not already represent a register value. The
	 * {@link Attrib#getRegister() register property} of <code>attr</code>
	 * will be set to the register number.
	 * 
	 * @return the register number.
	 * @throws YAPLException
	 *             (NoMoreRegs) if there are no free registers available;
	 *             cannot occur with stack machine backends.
	 * @throws YAPLException
	 *             (Internal) if the {@link Attrib#getKind() kind} or
	 *             {@link Attrib#getType() type} properties of <code>attr</code>
	 *             have an unexpected value.
	 */
	public byte loadReg(Attrib attr) 
	throws YAPLException;

	/**
	 * Release the register used by the register operand <code>attr</code>. 
	 * The operand's {@link Attrib#getKind() kind} will be set to
	 * {@link Attrib#None}.
	 * Has no effect with stack machine backends or
	 * if <code>attr</code> does not represent a register operand.
	 */
	public void freeReg(Attrib attr);

	/**
	 * Allocate space for a memory object (i.e. a variable) at compile time. 
	 * If the symbol belongs to a {@link Symbol#isGlobal() global scope}, space will be
	 * allocated in the global data area; otherwise, space will be allocated 
	 * in the current stack frame.
	 * 
	 * @param sym
	 *            the symbol to allocate space for. The symbol's
	 *            {@link Symbol#getOffset() address offset} will be updated.
	 * @throws YAPLException (Internal)
	 *            if <code>sym</code> does not provide sufficient information
	 *            (data type, scope), etc.
	 */
	public void allocVariable(Symbol sym) 
	throws YAPLException;

	/**
	 * Store length of given array dimension at run time.
	 * The stored array dimensions are needed for run-time allocation of
	 * the array, see {@link #allocArray(Type)}.
	 * @param dim       dimension number; starts at 0.
	 * @param length    operand representing the dimension length.
	 *                  Its register is released.
	 * @throws YAPLException (TooManyDims)
	 *                  if <code>dim</code> exceeds an implementation-defined maximum.
	 */
	public void storeArrayDim(int dim, Attrib length)
	throws YAPLException;
	
	/**
	 * Allocate array at run time.
	 * @param arrayType  array type.
	 * @return           Attrib object representing a register operand
	 *                   holding the array base address.
	 * @throws YAPLException
	 */
	public Attrib allocArray(ArrayType arrayType)
	throws YAPLException;
	
	/**
	 * Update a formal parameter's {@link Symbol#getOffset() address offset}.
	 * Must not generate code.
	 * 
	 * @param sym
	 *            the formal parameter symbol.
	 * @param pos
	 *            the parameter position within the list of formal parameters
	 *            (starts at 0).
	 */
	public void setParamOffset(Symbol sym, int pos);

	/**
	 * Generate code for address offset computation of an array element.
	 * With stack machines supporting array access, this method may simply
	 * push the array base address and the index operand to the expression
	 * stack.
	 * 
	 * @param arr
	 *            the operand representing the array base address; its
	 *            {@link Attrib#getKind() kind},
	 *            {@link Attrib#getRegister() register}, and
	 *            {@link Attrib#getType() type} attributes will be updated in
	 *            place to represent the <em>array element</em>,
	 *            see {@link Attrib#ArrayElement}.
	 * @param index
	 *            the operand (expression) representing the index of the array
	 *            element (starts at 0).
	 * @throws YAPLException
	 *            (Internal) if <code>arr</code> does not represent an array type.
	 */
	public void arrayOffset(Attrib arr, Attrib index) 
	throws YAPLException;

	/**
	 * Generate code for array length computation at run time.
	 * @param arr   operand representing the array.
	 * @return      the object referenced by <code>arr</code>, updated to represent
	 *              the number of elements of the first dimension of <code>arr</code>.
	 * @throws YAPLException
	 */
	public Attrib arrayLength(Attrib arr)
	throws YAPLException;
	
	/**
	 * Generate code for variable assignment.
	 * Releases any registers occupied by LHS and RHS expressions.
	 * 
	 * @param lvalue
	 *            left-hand side value of assignment (target).
	 * @param expr
	 *            right-hand side value of assignment (source).
	 * @throws YAPLException
	 *             (Internal) if <code>lvalue</code> has an illegal
	 *             {@link Attrib#getKind() kind property}.
	 */
	public void assign(Attrib lvalue, Attrib expr) 
	throws YAPLException;

	/**
	 * Check types and generate code for unary operation <code>x = op x</code>.
	 * <code>x</code> will be updated in place to represent the result.
	 * 
	 * @param op
	 *            the operator symbol.
	 * @param x
	 *            the operand.
	 * @return the object referenced by <code>x</code>.
	 * @throws YAPLException
	 *             (Internal) if the operator symbol is not a valid unary
	 *             operator.
	 * @throws YAPLException
	 *             (IllegalOp1Type) if the operator cannot be applied to the
	 *             given operand type.
	 */
	public Attrib op1(Token op, Attrib x) 
	throws YAPLException;

	/**
	 * Check types and generate code for binary operation
	 * <code>x = x op y</code>. <code>x</code> will be updated in place to
	 * represent the result. If y occupies a register, it will be released.
	 * 
	 * @param x
	 *            the left operand.
	 * @param op
	 *            the operator symbol.
	 * @param y
	 *            the right operand.
	 * @return the object referenced by <code>x</code>.
	 * @throws YAPLException
	 *             (Internal) if the operator symbol is not a valid binary
	 *             operator.
	 * @throws YAPLException
	 *             (IllegalOp2Type) if the operator cannot be applied to
	 *             the given operand types.
	 */
	public Attrib op2(Attrib x, Token op, Attrib y) 
	throws YAPLException;

	/**
	 * Check types and generate code for relational operation
	 * <code>x op y</code>. The result is represented by <code>x</code>,
	 * which is updated in place. Its type is set to Boolean.
	 * If y occupies a register, it will be released.
	 * 
	 * @param x
	 *            the left operand.
	 * @param op
	 *            the operator symbol.
	 * @param y
	 *            the right operand.
	 * @return the object referenced by <code>x</code>.
	 * @throws YAPLException
	 *             (Internal) if the operator symbol is not a valid relational
	 *             operator.
	 * @throws YAPLException
	 *             (IllegalRelOpType) if the operator cannot be applied to
	 *             the given operand types.
	 */
	public Attrib relOp(Attrib x, Token op, Attrib y) 
	throws YAPLException;

	/**
	 * Check types and generate code for equality operation <code>x op y</code>.
	 * The result is represented by <code>x</code>, which is updated in
	 * place. Its type is set to Boolean.
	 * If y occupies a register, it will be released.
	 * 
	 * @param x
	 *            the left operand.
	 * @param op
	 *            the operator symbol.
	 * @param y
	 *            the right operand.
	 * @return the object referenced by <code>x</code>.
	 * @throws YAPLException
	 *             (Internal) if the operator symbol is not a valid relational
	 *             operator.
	 * @throws YAPLException
	 *             (IllegalEqualOpType) if the operator cannot be applied to
	 *             the given operand types.
	 */
	public Attrib equalOp(Attrib x, Token op, Attrib y) 
	throws YAPLException;

	/**
	 * Enter procedure. Generate the procedure's prolog (setup stack frame).
	 * 
	 * @param proc
	 *            the procedure symbol; the formal parameters must already be
	 *            attached as a linked list (see {@link Symbol#getNextSymbol()}).
	 *            If <code>proc == null</code>, code for the main program
	 *            (entry point) will be generated.
	 */
	public void enterProc(Symbol proc) 
	throws YAPLException;

	/**
	 * Exit procedure. Generate the procedure's epilog (release stack frame).
	 * 
	 * @param proc
	 *            the procedure symbol; if <code>proc == null</code>,
	 *            exit from the main program.
	 */
	public void exitProc(Symbol proc) 
	throws YAPLException;

	/**
	 * Return from procedure. The return value type is <em>not</em> checked -
	 * this should happen before calling this method. The generated code
	 * will typically jump to the procedure's epilog (see {@link #exitProc(Symbol)}).
	 * 
	 * @param proc
	 *            the symbol representing the procedure containing the RETURN
	 *            statement; if <code>proc == null</code>, return from the
	 *            main program.
	 * @param returnVal
	 *            the operand representing the value to be returned by the
	 *            procedure. May be <code>null</code> if the procedure does
	 *            not return a value.
	 */
	public void returnFromProc(Symbol proc, Attrib returnVal)
	throws YAPLException;

	/**
	 * Procedure call.
	 * Releases any registers occupied by procedure arguments.
	 * 
	 * @param proc
	 *            the procedure symbol.
	 * @param args
	 *            the Attrib objects representing the argument values; may be
	 *            <code>null</code>.
	 * @return a new Attrib object representing the procedure's return value;
	 *         <code>null</code> if the procedure does not return a value.
	 */
	public Attrib callProc(Symbol proc, Attrib[] args) 
	throws YAPLException;

	/**
	 * Generate code for writing a string constant to standard output.
	 * 
	 * @param string
	 *            string to be written, enclosed in double quotes.
	 */
	public void writeString(String string) 
	throws YAPLException;

	/**
	 * Generate code jumping to <code>label</code> if
	 * <code>condition</code> is <code>false</code>.
	 */
	public void branchIfFalse(Attrib condition, String label)
	throws YAPLException;

	/** Generate code unconditionally jumping to <code>label</code>. */
	public void jump(String label);

}
