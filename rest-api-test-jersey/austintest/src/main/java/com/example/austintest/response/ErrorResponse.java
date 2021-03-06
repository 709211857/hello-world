package com.example.austintest.response;

import java.io.Serializable;

public class ErrorResponse implements Serializable {

	/**
	 * @author austin
	 */
	private static final long serialVersionUID = 1L;
	private String errorCode;
	private String errorMsg;

	public String getErrorCode() {
		return errorCode;
	}

	public void setErrorCode(String errorCode) {
		this.errorCode = errorCode;
	}

	public String getErrorMsg() {
		return errorMsg;
	}

	public void setErrorMsg(String errorMsg) {
		this.errorMsg = errorMsg;
	}

}
