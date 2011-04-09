package yapl.impl;

import yapl.interfaces.Symbol;
import yapl.interfaces.Symboltable;
import yapl.lib.Type;

/** 
 * Represents the current symbol 
 * 
 * @author Mario Taschwer
 * @version $Id: Symbol.java 99 2009-03-23 14:30:37Z mt $
 * @see Symboltable
 */

public class SymbolImpl implements Symbol {

	/** Kind of the Symbol */
	private int kind = -1;
	/** Identifier of the Symbol */
	private String identifier = "";
	/** Type of the Symbol */
	private Type type; 
	/** Offset of the Symbol */
	private int offset;
	/** Next Symbol of this Symbol */
	private Symbol nextSymbol;

	
	/** Extended Constructor */
	public SymbolImpl(int kind, String identifier){
		this.identifier = identifier;
		this.kind = kind;		
	} 
	
	/** Return the symbol's kind. */
	public int getKind() {
		return this.kind;
	}

	/**
	 * Return the text version of the symbol's kind.
	 * 
	 * @return One of the literal strings:
	 *         <code>program, procedure, variable, constant,
	 *         parameter</code>.
	 */
	public String getKindString() {

		switch (kind) {
		case 0:
			return "Program";			
		case 1:
			return "Procedure";
		case 2:
			return "Variable";
		case 3:
			return "Constant";
		case 4:
			return "Parameter";
		}
		
		return "";
	}

	/** Set the symbol's kind. */
	public void setKind(int kind) {
		this.kind = kind;
	}

	/** Return the symbol's name (identifier). */
	public String getName() {		
		return this.identifier;
	}

	/** Return the symbol's data type. */
	public Type getType() {
		// TODO Auto-generated method stub
		return null;
	}

	/** Set the symbol's data type. */
	public void setType(Type type) {
		this.type = type;

	}

	/**
	 * Return <code>true</code> iff this symbol represents a formal parameter
	 * passed by reference.
	 * 
	 * @see #setReference()
	 */
	public boolean isReference() {
		// TODO Auto-generated method stub
		return false;
	}

	/**
	 * Specify whether this symbol represents a formal parameter passed by
	 * reference.
	 */
	public void setReference(boolean isReference) {
		// TODO Auto-generated method stub

	}

	/**
	 * Return <code>true</code> iff this symbol belongs to a global scope. That
	 * is, its value is stored in heap memory if applicable.
	 * 
	 * @see #getOffset()
	 */
	public boolean isGlobal() {
		// TODO Auto-generated method stub
		return false;
	}

	/**
	 * Specify whether this symbol belongs to a global scope.
	 * 
	 * @see #isGlobal()
	 */
	public void setGlobal(boolean isGlobal) {
		// TODO Auto-generated method stub

	}

	/**
	 * Return the symbol's address offset on the heap or on the stack. For
	 * global symbols, the offset is relative to the heap storage area for
	 * global variables. For local symbols, the offset is relative to the
	 * current stack frame.
	 * 
	 * @see #isGlobal()
	 */
	public int getOffset() {
		return this.offset;
	}

	/**
	 * Set the symbol's address offset.
	 * 
	 * @see #getOffset()
	 */
	public void setOffset(int offset) {
		this.offset = offset;
	}

	/**
	 * Return the next symbol linked to this one. Parameter symbols are
	 * represented as a linked list attached to the procedure symbol.
	 * 
	 * @return <code>null</code> if there is no symbol linked to this one.
	 */
	public Symbol getNextSymbol() {
		return nextSymbol;
	}

	/**
	 * Link another symbol to this one.
	 * 
	 * @see #getNextSymbol()
	 */
	public void setNextSymbol(Symbol symbol) {
		this.nextSymbol = symbol;
	}

	/**
	 * Procedure symbol: return <code>true</code> iff the parser has encountered
	 * a Return statement within the procedure's body.
	 */
	public boolean getReturnSeen() {
		// TODO Auto-generated method stub
		return false;
	}

	/**
	 * Procedure symbol: indicate whether the parser has encountered a Return
	 * statement within the procedure's body.
	 */
	public void setReturnSeen(boolean seen) {
		// TODO Auto-generated method stub

	}

}
