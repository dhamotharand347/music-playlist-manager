package com.xmplar.music.playlist.dto;

import java.util.Map;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class APIRequest {

	@Expose 
	@SerializedName("apiContext") 
	private APIContext context;

	@Expose 
	@SerializedName("apiMandatoryParams")
	private Map<String, Object> mandatoryParams;

	@Expose 
	@SerializedName("apiAdditionalParams")
	private Map<String, Object> additionalParams;
	
	public APIRequest() {}
	
	public APIRequest(APIContext context, Map<String, Object> mandatoryParams, Map<String, Object> additionalParams) {
		this.context = context;
		this.mandatoryParams = mandatoryParams;
		this.additionalParams = additionalParams;
	}

	public APIContext getContext() {
		return context;
	}

	public void setContext(APIContext context) {
		this.context = context;
	}

	public Map<String, Object> getMandatoryParams() {
		return mandatoryParams;
	}

	public void setMandatoryParams(Map<String, Object> mandatoryParams) {
		this.mandatoryParams = mandatoryParams;
	}

	public Map<String, Object> getAdditionalParams() {
		return additionalParams;
	}

	public void setAdditionalParams(Map<String, Object> additionalParams) {
		this.additionalParams = additionalParams;
	}
	
	
}