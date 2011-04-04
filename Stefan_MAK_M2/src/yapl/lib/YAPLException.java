package yapl.lib;

import yapl.interfaces.CompilerError;

public class YAPLException extends Throwable implements CompilerError {

	@Override
	public int errorNumber() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int line() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int column() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public String getMessage() {
		// TODO Auto-generated method stub
		return null;
	}

}
