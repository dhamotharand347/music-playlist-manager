package com.xmplar.music.playlist.handler;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.xmplar.music.playlist.dto.APIEndpointResponse;
import com.xmplar.music.playlist.services.RequestBO;

import jakarta.servlet.http.HttpServletRequest;

@Component
@RestController
public class Controller {
	
	@Autowired
	private RequestBO response;

	@PostMapping("/api/music")
	public ResponseEntity<APIEndpointResponse> request(HttpServletRequest httpRequest,
			@RequestBody Map<String, Object> requestBody) {
		return response.handleAPIRequest(httpRequest, requestBody);
	}
}