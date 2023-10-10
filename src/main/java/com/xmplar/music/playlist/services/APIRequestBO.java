package com.xmplar.music.playlist.services;

import java.util.Map;

import org.springframework.stereotype.Component;

import com.xmplar.music.playlist.dto.APIRequest;
import com.xmplar.music.playlist.util.GsonUtils;

/**
 * Service class for APIRequests
 */
@Component
public class APIRequestBO {

	/**
	 * Converts the API Request body into an APIRequest object
	 * 
	 * @param requestBody - the body of the http request
	 * @return - the new API Request object
	 */
	public APIRequest convertHttpRequestBodyMapToAPIRequest(Map<String, Object> requestBody) {
		return (APIRequest) GsonUtils.serializeObjectFromMap(requestBody, APIRequest.class);
	}

}
