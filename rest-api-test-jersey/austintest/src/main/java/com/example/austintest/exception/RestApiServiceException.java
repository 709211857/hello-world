package com.example.austintest.exception;

public abstract class RestApiServiceException extends Exception {

	private static final long serialVersionUID = 7237958923875913740L;

	private String errorCode;
	
	public RestApiServiceException(String message) {
		super(message);
	}

	public RestApiServiceException(Throwable cause) {
		super(cause);
	}

	public RestApiServiceException(String message, Throwable cause) {
		super(message, cause);
	}

	public RestApiServiceException(String message, Throwable cause,
			boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}
	
	public RestApiServiceException(String message, String errorCode) {
		super(message);
		this.setErrorCode(errorCode);
	}

	public RestApiServiceException(Throwable cause, String errorCode) {
		super(cause);
		this.setErrorCode(errorCode);
	}

	public RestApiServiceException(String message, Throwable cause, String errorCode) {
		super(message, cause);
		this.setErrorCode(errorCode);
	}

	public RestApiServiceException(String message, Throwable cause,
			boolean enableSuppression, boolean writableStackTrace, String errorCode) {
		super(message, cause, enableSuppression, writableStackTrace);
		this.setErrorCode(errorCode);
	}

	public String getErrorCode() {
		return errorCode;
	}

	public void setErrorCode(String errorCode) {
		this.errorCode = errorCode;
	}
	
	public String getFormattedMessage(){
		return this.errorCode + "::" + this.getMessage();
	}
	
}
