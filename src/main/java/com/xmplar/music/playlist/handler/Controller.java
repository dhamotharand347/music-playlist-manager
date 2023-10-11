package com.xmplar.music.playlist.handler;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.xmplar.music.playlist.dto.APIEndpointResponse;
import com.xmplar.music.playlist.services.APIRequestBO;

@Component
@RestController
public class Controller {

	@Autowired
	private APIRequestBO response;

	@PostMapping("/api/music")
	public ResponseEntity<APIEndpointResponse> request(@RequestBody Map<String, Object> requestBody) {
		return response.handleAPIRequest(requestBody);
	}
}