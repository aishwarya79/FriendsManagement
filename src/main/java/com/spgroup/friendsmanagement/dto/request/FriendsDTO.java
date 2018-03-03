package com.spgroup.friendsmanagement.dto.request;

import java.util.List;

public class FriendsDTO {

	private List<String> friends;

	public List<String> getFriends() {
		return friends;
	}

	public void setFriends(List<String> friends) {
		this.friends = friends;
	}

	@Override
	public String toString() {
		return "FriendsDTO [friends=" + friends + "]";
	}
}
