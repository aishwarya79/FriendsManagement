package com.spgroup.friendsmanagement.advice;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.spgroup.friendsmanagement.dto.response.FriendsMgmtResponse;
import com.spgroup.friendsmanagement.exception.UnSupportedOperationException;
import com.spgroup.friendsmanagement.exception.UserNotFoundException;

@RestControllerAdvice
public class FriendsMgmtControllerAdvice {

	private static final Logger logger = LoggerFactory.getLogger(FriendsMgmtControllerAdvice.class);

	@ExceptionHandler(UserNotFoundException.class)
	public ResponseEntity<FriendsMgmtResponse> handleUserNotFoundException(UserNotFoundException ex) {
		logger.error("UserNotFoundException: ", ex);
		FriendsMgmtResponse errorResponse = new FriendsMgmtResponse(false);
		errorResponse.setErrorMsg(ex.getMessage());
		return new ResponseEntity<FriendsMgmtResponse>(errorResponse, HttpStatus.BAD_REQUEST);
	}

	@ExceptionHandler(UnSupportedOperationException.class)
	public ResponseEntity<FriendsMgmtResponse> handleUnsupportedOperationException(UnSupportedOperationException ex) {
		logger.error("UnSupportedOperationException: ", ex);
		FriendsMgmtResponse errorResponse = new FriendsMgmtResponse(false);
		errorResponse.setErrorMsg(ex.getMessage());
		return new ResponseEntity<FriendsMgmtResponse>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
	}

	@ExceptionHandler(IllegalArgumentException.class)
	public ResponseEntity<FriendsMgmtResponse> handleInvalidInput(IllegalArgumentException ex) {
		logger.error("IllegalArgumentException: ", ex);
		FriendsMgmtResponse errorResponse = new FriendsMgmtResponse(false);
		errorResponse.setErrorMsg(ex.getMessage());
		return new ResponseEntity<FriendsMgmtResponse>(errorResponse, HttpStatus.BAD_REQUEST);
	}

	@ExceptionHandler(Exception.class)
	public ResponseEntity<FriendsMgmtResponse> handleGenericException(Exception ex) {
		logger.error("Exception: ", ex);
		FriendsMgmtResponse errorResponse = new FriendsMgmtResponse(false);
		errorResponse.setErrorMsg(ex.getMessage());
		return new ResponseEntity<FriendsMgmtResponse>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
	}

}
