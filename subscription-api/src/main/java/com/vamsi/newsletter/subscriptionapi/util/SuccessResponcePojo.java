package com.vamsi.newsletter.subscriptionapi.util;

public class SuccessResponcePojo extends ResponcePojo {

	public SuccessResponcePojo() {
		super();
	}

	public SuccessResponcePojo(Object data) {
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
