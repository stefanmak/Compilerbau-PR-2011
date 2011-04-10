package yapl.interfaces;

import yapl.impl.*;

/** Interface to YAPL symbols contained in the symbol table.
 * A symbol's data type is represented by an abstract class <code>Type</code>,
 * which needs to be defined before using this interface.
 * 
 * @author Mario Taschwer
 * @version $Id: Symbol.java 99 2009-03-23 14:30:37Z mt $
 * @see Symboltable
 */
public interface Symbol
{
	/* symbol kinds */
	public static final int Program       = 0;
	public static final int Procedure     = 1;
	public static final int Variable      = 2;
	public static final int Constant      = 3;
	public static final int Parameter     = 4; // formal parameter

	/** Return the symbol's kind. */
	public int getKind();
	
	/** Return the text version of the symbol's kind. 
	 * @return One of the literal strings: <code>program, procedure, variable, constant,
	 *         parameter</code>.
	 */
	public String getKindString();

	/** Set the symbol's kind. */
	public void setKind(int kind);
	
	/** Return the symbol's name (identifier). */
	public String getName();
	
	/** Return the symbol's data type. */
	public Type getType();

	/** Set the symbol's data type. */
	public void setType(Type type);
	
	/** Return <code>true</code> iff this symbol represents a formal parameter 
	 * passed by reference.
	 * @see #setReference()
	 */
	public boolean isReference();
	
	/** Specify whether this symbol represents a formal parameter passed by reference. */
	public void setReference(boolean isReference);
	
	/** Return <code>true</code> iff this symbol belongs to a global scope.
	 * That is, its value is stored in heap memory if applicable. 
	 * @see #getOffset()
	 */
	public boolean isGlobal();
	
	/** Specify whether this symbol belongs to a global scope. 
	 * @see #isGlobal() 
	 */
	public void setGlobal(boolean isGlobal);
	
	/** Return the symbol's address offset on the heap or on the stack.
	 * For global symbols, the offset is relative to the heap storage area
	 * for global variables.
	 * For local symbols, the offset is relative to the current stack frame.
	 * @see #isGlobal()
	 */
	public int getOffset();
	
	/** Set the symbol's address offset. 
	 * @see #getOffset()
	 */
	public void setOffset(int offset);
	
	/** Return the next symbol linked to this one.
	 * Parameter symbols are represented as a linked list attached
	 * to the procedure symbol.
	 * @return <code>null</code> if there is no symbol linked to this one.
	 */
	public Symbol getNextSymbol();
	
	/** Link another symbol to this one. 
	 * @see #getNextSymbol()
	 */
	public void setNextSymbol(Symbol symbol);
	
	/** Procedure symbol: return <code>true</code> iff the parser has encountered
	 * a Return statement within the procedure's body.
	 */
	public boolean getReturnSeen();
	
	/** Procedure symbol: indicate whether the parser has encountered a Return
	 * statement within the procedure's body.
	 */
	public void setReturnSeen(boolean seen);
	
	/** Return a text representation of this symbol for debugging purposes. */
	public String toString();
}
