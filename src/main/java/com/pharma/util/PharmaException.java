package com.pharma.util;

public class PharmaException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int status = 500;

	public PharmaException(String string) {
		super(string);
	}

	public PharmaException(String message, Throwable e) {
		super(message, e);
	}
}
