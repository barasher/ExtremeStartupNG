package com.barasher.esng.exception;

public class RefereeException extends Exception {

	private static final long serialVersionUID = 5542616637558827718L;

	public RefereeException(String aMsg) {
		super(aMsg);
	}

	public RefereeException(String aMsg, Throwable aCause) {
		super(aMsg, aCause);
	}

}
