package com.xmplar.music.playlist.dto;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class APIResponse {

	@Expose
	@SerializedName("responseContext")
	private APIResponseContext responseContext;

	@Expose
	@SerializedName("responseData")
	private Object responseData;

	public APIResponse() {
		responseContext = new APIResponseContext();
	}

	public APIResponseContext getResponseContext() {
		return responseContext;
	}

	public void setResponseContext(APIResponseContext responseContext) {
		this.responseContext = responseContext;
	}

	public Object getResponseData() {
		return responseData;
	}

	public void setResponseData(Object responseData) {
		this.responseData = responseData;
	}
}