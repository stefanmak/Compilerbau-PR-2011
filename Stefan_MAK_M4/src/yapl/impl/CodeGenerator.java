package yapl.impl;

import java.io.File;
import java.io.PrintStream;

import yapl.interfaces.Attrib;
import yapl.interfaces.CodeGen;
import yapl.interfaces.Symbol;
import yapl.interfaces.Token;
import yapl.lib.YAPLException;

public class CodeGenerator implements CodeGen{

	public BackendMIPS back;
	
	public CodeGenerator(PrintStream printStream){
				
		back = new BackendMIPS(printStream);		
		
	}
	
	@Override
	public String newLabel() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void assignLabel(String label) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public byte loadReg(Attrib attr) throws YAPLException {
		return 0;
		// TODO Auto-generated method stub
		
	}

	@Override
	public void freeReg(Attrib attr) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void allocVariable(Symbol sym) throws YAPLException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void storeArrayDim(int dim, Attrib length) throws YAPLException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Attrib allocArray(ArrayType arrayType) throws YAPLException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setParamOffset(Symbol sym, int pos) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void arrayOffset(Attrib arr, Attrib index) throws YAPLException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Attrib arrayLength(Attrib arr) throws YAPLException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void assign(Attrib lvalue, Attrib expr) throws YAPLException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Attrib op1(Token op, Attrib x) throws YAPLException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Attrib op2(Attrib x, Token op, Attrib y) throws YAPLException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Attrib relOp(Attrib x, Token op, Attrib y) throws YAPLException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Attrib equalOp(Attrib x, Token op, Attrib y) throws YAPLException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void enterProc(Symbol proc) throws YAPLException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void exitProc(Symbol proc) throws YAPLException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void returnFromProc(Symbol proc, Attrib returnVal)
			throws YAPLException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Attrib callProc(Symbol proc, Attrib[] args) throws YAPLException {
		this.back.callProc((byte)0, proc.getName());
		return null;
	}

	/**
	 * Writes a String 
	 */	
	public void writeString(String string) throws YAPLException {
		string = string.substring(1, string.length()-1);		
		int reg = this.back.allocStringConstant(string);		
		this.back.writeString(reg);
	}

	@Override
	public void branchIfFalse(Attrib condition, String label)
			throws YAPLException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void jump(String label) {
		// TODO Auto-generated method stub
		
	}

}
