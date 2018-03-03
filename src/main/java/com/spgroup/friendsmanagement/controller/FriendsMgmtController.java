package com.spgroup.friendsmanagement.controller;

import java.util.List;
import java.util.Set;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.spgroup.friendsmanagement.dto.request.BlockSubscribeDTO;
import com.spgroup.friendsmanagement.dto.request.FriendsDTO;
import com.spgroup.friendsmanagement.dto.request.GetFriendsDTO;
import com.spgroup.friendsmanagement.dto.request.NotificationDTO;
import com.spgroup.friendsmanagement.dto.response.FriendsMgmtResponse;
import com.spgroup.friendsmanagement.service.FriendsMgmtService;
import com.spgroup.friendsmanagement.utils.FriendsMgmtUtils;

@RestController
@RequestMapping("/friendsmanagement/api/v1")
@CrossOrigin("*")
public class FriendsMgmtController {

	private static final Logger logger = LoggerFactory.getLogger(FriendsMgmtController.class);

	@Autowired
	FriendsMgmtService friendsMgmtService;

	@PostMapping("/friends/connect")
	public ResponseEntity<FriendsMgmtResponse> connectFriends(@Valid @RequestBody FriendsDTO connectFriendDto) {

		logger.info("connectFriends API invoked.");
		logger.debug("Request: " + connectFriendDto);

		FriendsMgmtResponse response = null;
		List<String> friends = connectFriendDto.getFriends();
		validateEmailList(friends);

		// All Exceptions handled in FriendsMgmtControllerAdvice
		friendsMgmtService.connectFriends(friends.get(0), friends.get(1));
		response = new FriendsMgmtResponse(true);
		return new ResponseEntity<FriendsMgmtResponse>(response, HttpStatus.OK);
	}

	@PostMapping("friends/list")
	public ResponseEntity<FriendsMgmtResponse> getFriendsList(@Valid @RequestBody GetFriendsDTO getFriendsDTO) {

		logger.info("getFriendsList API invoked.");
		logger.debug("Request: " + getFriendsDTO);
		FriendsMgmtResponse response = null;

		if (!FriendsMgmtUtils.isValidEmail(getFriendsDTO.getEmail())) {
			throw new IllegalArgumentException("Invalid Email Id Format");
		}

		Set<String> friends = friendsMgmtService.getAllFriends(getFriendsDTO.getEmail());
		response = new FriendsMgmtResponse(true);
		response.setFriends(friends);
		response.setCount(friends.size());

		return new ResponseEntity<FriendsMgmtResponse>(response, HttpStatus.OK);
	}

	@PostMapping("/friends/common")
	public ResponseEntity<FriendsMgmtResponse> getCommonFriends(@Valid @RequestBody FriendsDTO commonFriendsDto) {

		logger.info("getCommonFriends API invoked.");
		logger.debug("Request: " + commonFriendsDto);

		FriendsMgmtResponse response = null;
		List<String> userEmails = commonFriendsDto.getFriends();
		validateEmailList(userEmails);

		Set<String> friends = friendsMgmtService.getMutualFriends(userEmails.get(0), userEmails.get(1));
		response = new FriendsMgmtResponse(true);
		response.setFriends(friends);
		response.setCount(friends.size());
		return new ResponseEntity<FriendsMgmtResponse>(response, HttpStatus.OK);
	}

	@PostMapping("/users/subscribe")
	public ResponseEntity<FriendsMgmtResponse> subscribeUpdates(@Valid @RequestBody BlockSubscribeDTO subscribeDto) {

		logger.info("subscribeUpdates API invoked.");
		logger.debug("Request: " + subscribeDto);
		FriendsMgmtResponse response = null;
		validateSubscribeBlockRequest(subscribeDto);

		friendsMgmtService.subscribeToUpdates(subscribeDto.getRequestor(), subscribeDto.getTarget());
		response = new FriendsMgmtResponse(true);
		return new ResponseEntity<FriendsMgmtResponse>(response, HttpStatus.OK);
	}

	@PostMapping("/users/block")
	public ResponseEntity<FriendsMgmtResponse> blockUser(@Valid @RequestBody BlockSubscribeDTO blockDto) {
		logger.info("blockUser API invoked.");
		logger.debug("Request: " + blockDto);

		FriendsMgmtResponse response = null;
		validateSubscribeBlockRequest(blockDto);

		friendsMgmtService.blockUser(blockDto.getRequestor(), blockDto.getTarget());
		response = new FriendsMgmtResponse(true);
		return new ResponseEntity<FriendsMgmtResponse>(response, HttpStatus.OK);
	}

	@PostMapping("/notification/recipients")
	public ResponseEntity<FriendsMgmtResponse> getUpdateRecipientsList(@Valid @RequestBody NotificationDTO dto) {

		FriendsMgmtResponse response = null;

		if (!FriendsMgmtUtils.isValidEmail(dto.getSender())) {
			throw new IllegalArgumentException("Invalid Email Id Format");
		}

		Set<String> recipientsList = friendsMgmtService.getRecipientsList(dto.getSender(), dto.getText());
		response = new FriendsMgmtResponse(true);
		response.setRecipients(recipientsList);
		return new ResponseEntity<FriendsMgmtResponse>(response, HttpStatus.OK);
	}

	private boolean validateEmailList(List<String> emailList) {
		if (emailList.isEmpty() || emailList.size() != 2) {
			throw new IllegalArgumentException("Missing or invalid input");
		}

		if (!FriendsMgmtUtils.isValidEmail(emailList.get(0)) || !FriendsMgmtUtils.isValidEmail(emailList.get(1))) {
			throw new IllegalArgumentException("Invalid Email Id Format");
		}
		return true;

	}

	private boolean validateSubscribeBlockRequest(BlockSubscribeDTO blockSubscribeDto) {
		if (!FriendsMgmtUtils.isValidEmail(blockSubscribeDto.getRequestor())
				|| !FriendsMgmtUtils.isValidEmail(blockSubscribeDto.getTarget())) {
			throw new IllegalArgumentException("Invalid Email Id Format");

		}
		return true;
	}

}
