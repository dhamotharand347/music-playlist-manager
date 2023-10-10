package com.xmplar.music.playlist.services;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.xmplar.music.playlist.dto.APIEndpointResponse;
import com.xmplar.music.playlist.dto.APIRequest;



@Service
public class AdminBO extends BaseBO{
	
//	/**
//	 * For admin login
//	 */
//	public ResponseEntity<APIEndpointResponse> adminLogin(APIRequest apiRequest) {
//		
//		return buildValidResponse(apiRequest, users);
//	}
//	
//	/**
//	 * To register new admin details
//	 */
//	public ResponseEntity<APIEndpointResponse> adminRegister(APIRequest apiRequest) {
//		
//		return buildValidResponse(apiRequest, users);
//	}
//	
//	/**
//	 * To add new songs in the database
//	 */
//	public ResponseEntity<APIEndpointResponse> addSongs(APIRequest apiRequest) {
//		
//		return buildValidResponse(apiRequest, users);
//	}
//	
//	/**
//	 * To edit existing song in the database
//	 */
//	public ResponseEntity<APIEndpointResponse> editSongs(APIRequest apiRequest) {
//		
//		return buildValidResponse(apiRequest, users);
//	}
//	
//	/**
//	 * To remove existing song in the database
//	 */
//	public ResponseEntity<APIEndpointResponse> removeSongs(APIRequest apiRequest) {
//		
//		return buildValidResponse(apiRequest, users);
//	}
}
