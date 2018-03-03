package com.spgroup.friendsmanagement.dto.request;

public class BlockSubscribeDTO {

	private String requestor;
	private String target;

	public String getRequestor() {
		return requestor;
	}

	public void setRequestor(String requestor) {
		this.requestor = requestor;
	}

	public String getTarget() {
		return target;
	}

	public void setTarget(String target) {
		this.target = target;
	}

	@Override
	public String toString() {
		return "BlockSubscribeDTO [requestor=" + requestor + ", target=" + target + "]";
	}

}
