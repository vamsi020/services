package com.vamsi.newsletter.subscriptionapi.util;

/**
 * 
 * @author Vamsi Krihna
 * 
 *         Success responce model
 * 
 *         Can add the default success response data
 *
 */
public class SuccessResponsePojo extends ResponsePojo {

	public SuccessResponsePojo() {
		super();
	}

	public SuccessResponsePojo(Object data) {
		super();
		this.data = data;
	}

	private Object data;

	public Object getData() {
		return data;
	}

	public void setData(Object data) {
		this.data = data;
	}

}
