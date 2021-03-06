package yapl.impl;

import java.io.PrintStream;
import java.util.HashMap;
import java.util.LinkedList;
import yapl.interfaces.Attrib;
import yapl.interfaces.CodeGen;
import yapl.interfaces.Symbol;
import yapl.interfaces.Token;
import yapl.lib.YAPLException;

public class CodeGenerator implements CodeGen {

	public BackendMIPS back;
	public HashMap<String, Attrib> variables;
	
	public CodeGenerator(PrintStream printStream) {

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
		//Constants
		if(sym.getType().isReadOnly()){
			if (sym.getType().getType() == 0) {
				int value = Integer.parseInt(sym.getType().getToken()
						.getImage());
				sym.setOffset(this.back.allocIntConstant(value));
			}else if(sym.getType().getType() == 1){
				boolean value = Boolean.parseBoolean(sym.getType().getToken().getImage());
				if(value)
					sym.setOffset(this.back.allocIntConstant(1));
				else
					sym.setOffset(this.back.allocIntConstant(0));
			}
		}else{			
			sym.setOffset(this.back.allocStaticData(4, sym.getName()));	
		}
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
		//System.out.println("->" + lvalue.getOffset() + " " + expr.getType().getToken().getImage());
		try{
		int value = 0;
		
		// assignement x := True
		if(expr.getType().getToken().getImage().equals("True")){
			value = 1;
			byte reg = this.back.allocReg();
			this.back.printStream.println("\tli 	$" + reg + ", " + value);
			this.back.storeWord(reg, lvalue.getOffset(), true);
		}
		// assignement x := False
		else if(expr.getType().getToken().getImage().equals("False")){
			value = 0;
			byte reg = this.back.allocReg();
			this.back.printStream.println("\tli 	$" + reg + ", " + value);
			this.back.storeWord(reg, lvalue.getOffset(), true);
		}
		// assignment x := VARIABLE
		else if(this.variables.containsKey(expr.getType().getToken().getImage())){		
			Attrib attrib = this.variables.get(expr.getType().getToken().getImage());
			byte reg = this.back.allocReg();
			this.back.printStream.println("\tlw 	$" + reg + ", " + attrib.getOffset() + "($23)");
			this.back.storeWord(reg, lvalue.getOffset(), true);
		}
		// assignment x:= 3
		else{
			try{
				value = Integer.parseInt(expr.getType().getToken().getImage());		
				byte reg = this.back.allocReg();
				this.back.printStream.println("\tli 	$" + reg + ", " + value);
				this.back.storeWord(reg, lvalue.getOffset(), true);
			}catch(Exception e){				
				// Assignment is an expression or array
			}			
		}
			
		}catch(Exception ex){
			// General Code Gen Error			
		}
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
	// not really used
	public Attrib callProc(Symbol proc, Attrib[] args) throws YAPLException {

		// check if writeln -> predefine procedure
		if (proc.getName().equals("writeln")) {
			this.writeString("\"\\n\"");
		} else {
			this.back.callProc((byte) 0, proc.getName());
		}
		return null;
	}

	// used instead of callProc()
	public void callProcedure(Symbol proc, LinkedList<Type> arguments,
			HashMap<String, Attrib> variables) throws YAPLException {
		
		try{
		
		this.variables = variables;
		
		// check if writeln -> predefined procedure
		if (proc.getName().equals("writeln")) {
			this.writeString("\"\\n\"");
		}
		// check if writeint -> predefined procedure
		else if (proc.getName().equals("writeint")) {
			this.back.prepareProcCall(arguments.size());
			int argCounter = 4;

			try {
				// Argument is number
				int value = Integer.parseInt(arguments.getFirst().getToken()
						.getImage());
				this.back.printStream.println("li	$" + argCounter + " , "
						+ value);

			} catch (Exception e) {
				// Argument is a Variable
				Attrib offset = variables.get(arguments.getFirst().getToken()
						.getImage());
				this.back.loadWord((byte) 4, offset.getOffset(), true);
			}

			this.back.callProc((byte) 0, proc.getName());
			this.back.restoreRegisters();
		} else {
			this.back.prepareProcCall(arguments.size());
			int argCounter = 4;
			for (Type t : arguments) {
				try {
					// Argument is number
					int value = Integer.parseInt(t.getToken().getImage());
					this.back.printStream.println("li	$" + argCounter + " , "
							+ value);

				} catch (Exception e1) {
					// Argument is a Bool - Constant
					if (t.getToken().getImage().equals("True"))
						this.back.printStream.println("li $" + argCounter
								+ ", 1");
					else if (t.getToken().getImage().equals("False"))
						this.back.printStream.println("li $" + argCounter
								+ ", 0");
					else {
						Attrib offset = variables.get(arguments.getFirst()
								.getToken().getImage());
						this.back.loadWord((byte) argCounter,
								offset.getOffset(), true);
					}
				}
			}
			this.back.callProc((byte) 0, proc.getName());
			this.back.restoreRegisters();
		}
		}catch(Exception ex){
			// General Code Generation Error occured			
		}
	}

	/**
	 * Writes a String
	 */
	public void writeString(String string) throws YAPLException {
		string = string.substring(1, string.length() - 1);
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
