package yapl.impl;

import yapl.interfaces.Token;

public class Type {
	
	/** define consts **/
	public static int INT = 0;
	public static int BOOL = 1;
	public static int CONST = 2;
	
	/** define data types **/
	private boolean readOnly;
	private int type;
	private Token token;

	
	/** Constructor(s) of Type */
	public Type(boolean readOnly, int type, Token token){		
		this.readOnly = readOnly;
		this.type = type;
		this.token = token;		
	}
	
	/** Helper Method to get Type of an image**/
	public static int getTypeOfImage(String image){			
		if(image.equals("int"))
			return Type.INT;
		else if(image.equals("bool"))
			return Type.BOOL;
		else 
			return Type.CONST;
	}
	
	/** Auto generated GETTER and SETTER **/
	public boolean isReadOnly() {
		return readOnly;
	}
	
	public void setReadOnly(boolean readOnly) {
		this.readOnly = readOnly;
	}
	
	public int getType() {
		return type;
	}
	
	public void setType(int type) {
		this.type = type;
	}
	
	public Token getToken() {
		return token;
	}
	
	public void setToken(Token token) {
		this.token = token;
	}

}
