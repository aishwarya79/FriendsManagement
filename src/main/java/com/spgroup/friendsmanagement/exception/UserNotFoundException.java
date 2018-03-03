package com.spgroup.friendsmanagement.exception;

public class UserNotFoundException extends RuntimeException{
	
	private static final long serialVersionUID = 18844823736559239L;

	public UserNotFoundException(String message) {
		super(message);
	}

}
