package yapl.impl;

import yapl.interfaces.Token;

public class ArrayType extends Type{

	/** Array specific Variables */
	private int dimension;
	
	
	/** Constructor */
	public ArrayType(boolean readOnly, int type, Token token, int dimension) {
		super(readOnly, type, token);		
		this.dimension = dimension;
	}


	/** Auto generated Getter & Setter */
	
	public int getDimension() {
		return dimension;
	}


	public void setDimension(int dimension) {
		this.dimension = dimension;
	}

	
	
}
