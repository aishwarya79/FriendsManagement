package com.spgroup.friendsmanagement.service;

import java.util.Set;

import com.spgroup.friendsmanagement.exception.UnSupportedOperationException;
import com.spgroup.friendsmanagement.exception.UserNotFoundException;

public interface FriendsMgmtService {

	/**
	 * Adds two users as friends
	 * 
	 * @param emailAddr1
	 * @param emailAddr2
	 * @throws UnSupportedOperationException
	 * @throws UserNotFoundException
	 */
	void connectFriends(String emailAddr1, String emailAddr2) throws UnSupportedOperationException, UserNotFoundException;

	/**
	 * Retrieves all friends of a user
	 * 
	 * @param emailAddr
	 * @return
	 * @throws UserNotFoundException
	 */
	Set<String> getAllFriends(String emailAddr) throws UserNotFoundException;

	/**
	 * Retrieves the mutual friends of two users
	 * 
	 * @param emailAddr1
	 * @param emailAddr2
	 * @return
	 * @throws UserNotFoundException
	 */
	Set<String> getMutualFriends(String emailAddr1, String emailAddr2) throws UserNotFoundException;

	/**
	 * Subscribes requestor to target's updates
	 * 
	 * @param requestor
	 * @param target
	 * @throws UserNotFoundException
	 */
	void subscribeToUpdates(String requestorEmail, String targetEmail) throws UserNotFoundException;

	/**
	 * Enables requestor to block target
	 * 
	 * @param requestor
	 * @param target
	 * @throws UserNotFoundException
	 */
	void blockUser(String requestorEmail, String targetEmail) throws UserNotFoundException;

	/**
	 * Gets all recipients of a given user updates
	 * 
	 * @param sender
	 * @param messageText
	 * @return
	 * @throws UserNotFoundException
	 */
	Set<String> getRecipientsList(String sender, String messageText) throws UserNotFoundException;

}
