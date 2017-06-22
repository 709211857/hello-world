package com.example.austintest.exception;

/**
 * 
 * @author austin
 *
 */
public class FriendResourcesException extends RestApiServiceException{

	private static final long serialVersionUID = -4254058515317387547L;
	
	public FriendResourcesException(String errorCode, String message) {
		super(message,errorCode);
	}

	public FriendResourcesException(String errorCode, Throwable cause) {
		super(cause, errorCode);
	}

	public FriendResourcesException( String errorCode, String message, Throwable cause) {
		super(message, cause, errorCode);
	}

	public FriendResourcesException(String errorCode, String message, Throwable cause,
			boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace, errorCode);
	}

}
