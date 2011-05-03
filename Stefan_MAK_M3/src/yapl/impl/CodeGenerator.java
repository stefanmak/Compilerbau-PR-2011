package yapl.impl;

import yapl.interfaces.Attrib;
import yapl.interfaces.CodeGen;
import yapl.interfaces.Symbol;
import yapl.interfaces.Token;
import yapl.lib.YAPLException;

public class CodeGenerator implements CodeGen{

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
	public void loadReg(Attrib attr) throws YAPLException {
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
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void writeString(String string) throws YAPLException {
		// TODO Auto-generated method stub
		
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
