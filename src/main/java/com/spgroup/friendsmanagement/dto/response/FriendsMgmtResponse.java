package com.spgroup.friendsmanagement.dto.response;

import java.util.Set;
import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class FriendsMgmtResponse {
	
	private Boolean success;
	private Set<String> friends;
	private Set<String> recipients;
	private Integer count;
	private String errorMsg;
	
	public FriendsMgmtResponse() {
		
	}
	
	public FriendsMgmtResponse(Boolean success) {
		this.success = success;
	}

	public Boolean getSuccess() {
		return success;
	}

	public void setSuccess(Boolean success) {
		this.success = success;
	}

	public Set<String> getFriends() {
		return friends;
	}

	public void setFriends(Set<String> friends) {
		this.friends = friends;
	}

	public Set<String> getRecipients() {
		return recipients;
	}

	public void setRecipients(Set<String> recipients) {
		this.recipients = recipients;
	}

	public Integer getCount() {
		return count;
	}

	public void setCount(Integer count) {
		this.count = count;
	}
	
	public void setSuccess(boolean success) {
		this.success = success;
	}

	public String getErrorMsg() {
		return errorMsg;
	}

	public void setErrorMsg(String errorMsg) {
		this.errorMsg = errorMsg;
	}

}
