package com.xmplar.music.playlist.dto;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class APIEndpointResponse {

	@Expose 
	@SerializedName("apiResponse") 
	private APIResponse apiResponse;

	@Expose 
	@SerializedName("apiRequest") 
	private APIRequest apiRequest;

	@Expose 
	@SerializedName("apiError") 
	private APIError apiError;
	
	public APIEndpointResponse() {
		apiResponse = new APIResponse();
	}

	public APIResponse getApiResponse() {
		return apiResponse;
	}

	public void setApiResponse(APIResponse apiResponse) {
		this.apiResponse = apiResponse;
	}

	public APIRequest getApiRequest() {
		return apiRequest;
	}

	public void setApiRequest(APIRequest apiRequest) {
		this.apiRequest = apiRequest;
	}

	public APIError getApiError() {
		return apiError;
	}

	public void setApiError(APIError apiError) {
		this.apiError = apiError;
	}	
}
