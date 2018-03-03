package com.spgroup.friendsmanagement.service.impl;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.spgroup.friendsmanagement.exception.UnSupportedOperationException;
import com.spgroup.friendsmanagement.exception.UserNotFoundException;
import com.spgroup.friendsmanagement.model.User;
import com.spgroup.friendsmanagement.repository.FriendsMgmtRepository;
import com.spgroup.friendsmanagement.service.FriendsMgmtService;
import com.spgroup.friendsmanagement.utils.FriendsMgmtUtils;

@Service
public class FriendsMgmtServiceImpl implements FriendsMgmtService {

	private static final Logger logger = LoggerFactory.getLogger(FriendsMgmtServiceImpl.class);

	@Autowired
	FriendsMgmtRepository friendsMgmtRepository;

	@Override
	public void connectFriends(String emailAddr1, String emailAddr2)
			throws UnSupportedOperationException, UserNotFoundException {

		logger.debug("Going to Connect " + emailAddr1 + " with " + emailAddr2);
		User user1 = friendsMgmtRepository.findByEmail(emailAddr1);
		User user2 = friendsMgmtRepository.findByEmail(emailAddr2);

		if (user1 == null || user2 == null) {
			throw new UserNotFoundException("One or more User(s) do not exist");
		}

		// If either user has blocked other user, friend connection not possible
		if (user1.getBlockedList().contains(user2) || user2.getBlockedList().contains(user1)) {
			throw new UnSupportedOperationException("Cannot add blocked user as Friend");
		}

		user1.getFriendsList().add(user2);
		friendsMgmtRepository.save(user1);

		user2.getFriendsList().add(user1);
		friendsMgmtRepository.save(user2);
		logger.info("Users connected Successfully");
	}

	@Override
	public Set<String> getAllFriends(String emailAddr) throws UserNotFoundException {
		logger.info("Getting friend list for " + emailAddr);
		User user = friendsMgmtRepository.findByEmail(emailAddr);
		if (user == null) {
			throw new UserNotFoundException("User do not exist");
		}
		return extractEmailsFromUsers(user.getFriendsList());
	}

	@Override
	public Set<String> getMutualFriends(String emailAddr1, String emailAddr2) throws UserNotFoundException {

		logger.info("Going to find common friends between " + emailAddr1 + " and " + emailAddr2);

		User user1 = friendsMgmtRepository.findByEmail(emailAddr1);
		User user2 = friendsMgmtRepository.findByEmail(emailAddr2);

		if (user1 == null || user2 == null) {
			throw new UserNotFoundException("One or more User(s) do not exist");
		}

		Set<User> user1Friends = user1.getFriendsList();
		Set<User> user2Friends = user2.getFriendsList();
		Set<User> commonFriends = new HashSet<>();

		Set<User> smallerSet = user1Friends.size() > user2Friends.size() ? user2Friends : user1Friends;
		Set<User> largerSet = user1Friends.size() > user2Friends.size() ? user1Friends : user2Friends;

		for (User usr : smallerSet) {
			if (largerSet.contains(usr)) {
				commonFriends.add(usr);
			}
		}

		return extractEmailsFromUsers(commonFriends);
	}

	@Override
	public void subscribeToUpdates(String requestorEmail, String targetEmail) throws UserNotFoundException {
		logger.info("Going to subscribe" + requestorEmail + " to " + targetEmail);

		User requestor = friendsMgmtRepository.findByEmail(requestorEmail);
		User target = friendsMgmtRepository.findByEmail(targetEmail);

		if (requestor == null || target == null) {
			throw new UserNotFoundException("One or more User(s) do not exist");
		}
		target.getSubscribersList().add(requestor);
		friendsMgmtRepository.save(requestor);
		logger.info("Users subscription Successful");
	}

	@Override
	public void blockUser(String requestorEmail, String targetEmail) throws UserNotFoundException {
		logger.info("Going to block " + targetEmail + " for " + requestorEmail);
		User requestor = friendsMgmtRepository.findByEmail(requestorEmail);
		User target = friendsMgmtRepository.findByEmail(targetEmail);

		if (requestor == null || target == null) {
			throw new UserNotFoundException("One or more User(s) do not exist");
		}

		requestor.getBlockedList().add(target);
		friendsMgmtRepository.save(requestor);
		logger.info("User blocked Successfully");
	}

	@Override
	public Set<String> getRecipientsList(String senderEmail, String messageText) throws UserNotFoundException {

		logger.info("Going to find the recipient list for " + senderEmail);

		Set<String> recipients = new HashSet<>();
		User sender = friendsMgmtRepository.findByEmail(senderEmail);
		for (User user : sender.getFriendsList()) {
			if (!user.getBlockedList().contains(sender)) {
				recipients.add(user.getEmail());
			}
		}

		for (User user : sender.getSubscribersList()) {
			if (!user.getBlockedList().contains(sender)) {
				recipients.add(user.getEmail());
			}
		}

		List<String> mentionedEmails = FriendsMgmtUtils.findEmailsinString(messageText);
		for (String email : mentionedEmails) {
			User user = friendsMgmtRepository.findByEmail(email);
			if (user != null) {
				recipients.add(user.getEmail());
			}

		}

		return recipients;
	}

	private Set<String> extractEmailsFromUsers(Set<User> users) {
		Set<String> emails = new HashSet<>();
		for (User user : users) {
			emails.add(user.getEmail());
		}
		return emails;
	}

}
