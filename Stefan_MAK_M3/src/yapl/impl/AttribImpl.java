package yapl.impl;

import yapl.interfaces.Attrib;

/**
 * Attributes of production symbols for type checking and code generation.
 * An object implementing this interface stores attributes of production
 * symbols of the YAPL grammar. Conceptually, an Attrib object represents an
 * operand of a generated instruction, or a branch condition.
 * <p>
 * The interface constants specify the various kinds of operands
 * represented by an object implementing this interface. Operand kinds comprise
 * registers, constants, and memory operands.
 * </p><p>
 * The term <em>register</em> is used for both register and stack machines.
 * In the latter case, a register value refers to an element
 * on the expression stack; register numbers are not needed then, because
 * the order of operands is implied by the stack.
 * </p><p>
 * An Attrib object may also represent a memory operand which is to be computed
 * at run time using a base address and an offset, e.g. an array element.
 * In this case the base address is represented by an Attrib object b which
 * is linked to another Attrib object representing the offset using the
 * <code>b.setOffsetAttrib(Attrib)</code> method.
 * </p>
 * 
 * @author Mario Taschwer
 * @version $Id: Attrib.java 150 2010-04-15 15:55:58Z mt $
 */


public class AttribImpl implements Attrib{

	/** Return the kind of operand represented by this object.
	 * 
	 * @return one of the constants defined by this interface.
	 */
	public byte getKind() {
		// TODO Auto-generated method stub
		return 0;
	}

	/**
	 * Set the kind of operand represented by this object.
	 * 
	 * @param kind     one of the constants defined by this interface.
	 */
	public void setKind(byte kind) {
		// TODO Auto-generated method stub
		
	}

	/** Get the data type of this operand (or its target object). */
	public Type getType() {
		// TODO Auto-generated method stub
		return null;
	}

	/** Set the data type of this operand (or its target object). */
	public void setType(Type type) {
		// TODO Auto-generated method stub
		
	}

	/** Return <code>true</code> iff this operand can be evaluated
	 * at compile time.
	 */
	public boolean isConstant() {
		// TODO Auto-generated method stub
		return false;
	}

	/** Specify whether this operand can be evaluated at compile time. */
	public void setConstant(boolean isConstant) {
		// TODO Auto-generated method stub
		
	}

	/** Return <code>true</code> iff this operand represents
	 * a global memory object (i.e. it is stored in heap memory).
	 */
	public boolean isGlobal() {
		// TODO Auto-generated method stub
		return false;
	}

	/** Specify whether this operand represents a global memory object.
	 * @see #isGlobal()
	 */
	public void setGlobal(boolean isGlobal) {
		// TODO Auto-generated method stub
		
	}

	/** Return the address offset (for memory operands).
	 *  If this operand represents a global memory object
	 *  (see {@link #isGlobal()}), the offset is relative to
	 *  the global variable storage area. Otherwise,
	 *  the offset is relative to the current stack frame.
	 */
	public int getOffset() {
		// TODO Auto-generated method stub
		return 0;
	}

	/** Set the address offset (for memory operands). 
	 * @see #getOffset()
	 */
	public void setOffset(int offset) {
		// TODO Auto-generated method stub
		
	}

	/** Get the register number (for register operands). */
	public byte getRegister() {
		// TODO Auto-generated method stub
		return 0;
	}

	/** Set the register number (for register operands). */
	public void setRegister(byte register) {
		// TODO Auto-generated method stub
		
	}

}
