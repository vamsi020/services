package com.vamsi.newsletter.subscriptionapi.util;

/**
 * 
 * @author Vamsi Krihna
 * 
 *         Error response model
 * 
 *         Can add the default error response data to any request
 *
 */
public class ErrorResponsePojo extends ResponsePojo {
	public ErrorResponsePojo() {
		super();
	}

	public ErrorResponsePojo(String errorMessage) {
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
