package com.xmplar.music.playlist.dto;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class APIContext {

	@Expose 
	@SerializedName("applicationId") 
	private String applicationId;

	@Expose 
	@SerializedName("transactionId") 
	private String transactionId;

	@Expose 
	@SerializedName("username") 
	private String username;
	
	public String getApplicationId() {
		return applicationId;
	}

	public void setApplicationId(String applicationId) {
		this.applicationId = applicationId;
	}

	public String getTransactionId() {
		return transactionId;
	}

	public void setTransactionId(String transactionId) {
		this.transactionId = transactionId;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}
}