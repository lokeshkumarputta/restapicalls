package com.elife.restapicalls;

import java.net.URI;
import java.net.URISyntaxException;

import org.springframework.http.MediaType;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

public class GenerateToken {

	public static void main(String[] args) {
		try {
			String access_token = generateToken();
			System.out.println("access_token in  Response ::" + access_token);
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	
	public static String generateToken() {

		RestTemplate restTemplate = new RestTemplate();

		ResponseEntity<String> responseEntity = null;
		String token = null;
		// Need to push value into Property file
		final String accessToken = "R083WmdLS1Z4Smdlak1rYl9OUjBHQ0tBcjN3YTpjREQ3SDdjVmlaY2FSR3hyc1VCMGMxVEJPVXdh";

		String serviceUrl = "any URI ";

		RequestEntity requesetEntity = null;
		try {
			requesetEntity = RequestEntity.post(new URI(serviceUrl)).accept(MediaType.APPLICATION_JSON)
					.header("Authorization", "Bearer " + accessToken)
					.header("content-type", "application/x-www-form-urlencoded").body(null);
		} catch (URISyntaxException e) {
			e.printStackTrace();
		}
		responseEntity = restTemplate.exchange(requesetEntity, String.class);

		String response = responseEntity.getBody();

		System.out.println("Response ::" + response);
		String[] list = response.split(":");
		token = list[1];
		String tokens[] = token.split(",");
		String access_token = tokens[0].replaceAll("\"", "").trim();
		return access_token;
	}

}
