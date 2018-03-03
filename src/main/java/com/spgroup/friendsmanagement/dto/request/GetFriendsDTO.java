package com.spgroup.friendsmanagement.dto.request;

public class GetFriendsDTO {

	private String email;

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	@Override
	public String toString() {
		return "GetFriendsDTO [email=" + email + "]";
	}	

}
