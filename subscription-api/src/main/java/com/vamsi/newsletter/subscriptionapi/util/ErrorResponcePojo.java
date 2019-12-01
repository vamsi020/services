package com.vamsi.newsletter.subscriptionapi.util;

public class ErrorResponcePojo extends ResponcePojo {
	public ErrorResponcePojo() {
		super();
	}

	public ErrorResponcePojo(String errorMessage) {
		super();
		this.errorMessage = errorMessage;
	}

	private String errorMessage;

	public String getErrorMessage() {
		return errorMessage;
	}

	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}

}
