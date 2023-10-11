package com.xmplar.music.playlist.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.xmplar.music.playlist.dto.APIEndpointResponse;
import com.xmplar.music.playlist.dto.APIRequest;
import com.xmplar.music.playlist.model.User;
import com.xmplar.music.playlist.repository.UserRepository;
import com.xmplar.music.playlist.util.GsonUtils;

@Service
public class UserBO extends BaseBO{
	
	@Autowired
	private UserRepository userRepository;
	
	/**
	 * To register new user details
	 */
	public ResponseEntity<APIEndpointResponse> userRegister(APIRequest apiRequest) {

		// To do Validation
		User user = (User) GsonUtils.serializeObjectFromMap(apiRequest.getMandatoryParams(), User.class);

		if (userRepository.existsByUsername(user.getUsername())) {
			return buildErrorResponse(apiRequest, "Username already exists", "Registration failed");
		} else {
			userRepository.save(user);
			return buildValidResponse(apiRequest, "User successfully registered!");
		}
	}

	/**
	 * For user login
	 */
	public ResponseEntity<APIEndpointResponse> userLogin(APIRequest apiRequest) {

		// Client side validation
		/*
		 * 1) Username or password empty
		 */
		User user = (User) GsonUtils.serializeObjectFromMap(apiRequest.getMandatoryParams(), User.class);

		String username = user.getUsername();
		String password = user.getPassword();

		if (userRepository.existsByUsername(username)) {
			if (password.equals(userRepository.findPassword(username))) {
				return buildValidResponse(apiRequest, "User logged in successfully!");
			} else {
				return buildErrorResponse(apiRequest, "Password does not match", "Log in failed");
			}

		} else {
			return buildErrorResponse(apiRequest, "Username does not exists", "Log in failed");
		}
	}
}
