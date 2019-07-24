package com.cormacLaptops.test.util;

public class RestResponse {
	final private int statusCode;
	final private String responseBody;

	public RestResponse(final int statusCode, final String responseBody) {
		this.statusCode = statusCode;
		this.responseBody = responseBody;

	}

	public int getStatusCode() {
		return statusCode;
	}

	public String getResponseBody() {
		return responseBody;
	}

	@Override
	public String toString() {
		return String.format("Status Code : %1s Body : %2s", this.statusCode, this.responseBody);
	}
}
