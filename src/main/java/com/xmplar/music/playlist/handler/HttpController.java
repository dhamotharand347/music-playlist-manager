package com.xmplar.music.playlist.handler;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

public class HttpController {

	private static final String URL = "http://localhost:8080/Operations";

	public static String performOperation(String operationName, String jsonPayload) {
		HttpHeaders headers = new HttpHeaders();
		headers.set("Content-Type", "application/json");

		HttpEntity<String> entity = new HttpEntity<>(jsonPayload, headers);

			RestTemplate restTemplate = new RestTemplate();
			ResponseEntity<String> response = restTemplate.exchange(URL + "?operation=" + operationName,
					HttpMethod.POST, entity, String.class);

			return response.getBody();

	}
}

