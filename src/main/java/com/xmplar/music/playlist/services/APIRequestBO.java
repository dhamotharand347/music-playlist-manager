package com.xmplar.music.playlist.services;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.xmplar.music.playlist.dto.APIEndpointResponse;
import com.xmplar.music.playlist.dto.APIRequest;
import com.xmplar.music.playlist.util.GsonUtils;
import com.xmplar.music.playlist.util.TransactionIDValues;

/**
 * Acts as the delegate for the UserAPIEndpoint. Will handle the request and
 * send back the response
 */
@Service
public class APIRequestBO extends BaseAPIResponseHandler {

	@Autowired
	private UserBO userBO;

	@Autowired
	private AdminBO adminBO;

	/**
	 * Handles the delegation request from the UserAPI. Will kick back to the API
	 * caller if the response fails basic validation
	 * 
	 * @param requestBody - the pure String version of the request body
	 */

	public ResponseEntity<APIEndpointResponse> handleAPIRequest(Map<String, Object> requestBody) {
		APIRequest apiRequest = convertHttpRequestBodyMapToAPIRequest(requestBody);

		return generateAPIResponse(apiRequest);
	}

	/**
	 * Converts the API Request body into an APIRequest object
	 * 
	 * @param requestBody - the body of the http request
	 * @return - the new API Request object
	 */
	public APIRequest convertHttpRequestBodyMapToAPIRequest(Map<String, Object> requestBody) {
		return (APIRequest) GsonUtils.serializeObjectFromMap(requestBody, APIRequest.class);
	}

	/**
	 * Generate the API response based on the API request
	 * 
	 * @param apiRequest - the request that was made for the User API
	 * @return - the response for the request
	 */
	private ResponseEntity<APIEndpointResponse> generateAPIResponse(APIRequest apiRequest) {

		switch (apiRequest.getApiContext().getTransactionId()) {

		case TransactionIDValues.ADMIN_REGISTER:
			return adminBO.adminRegister(apiRequest);

		case TransactionIDValues.ADMIN_LOGIN:
			return adminBO.adminLogin(apiRequest);

		case TransactionIDValues.ADD_SONG:
			return adminBO.addSong(apiRequest);

		case TransactionIDValues.EDIT_SONG:
			return adminBO.editSong(apiRequest);
		
		case TransactionIDValues.REMOVE_SONG:
			return adminBO.removeSong(apiRequest);
		
		default:
			return generateInvalidAPIEndpointErrorResponse(apiRequest);
		}
	}

}
