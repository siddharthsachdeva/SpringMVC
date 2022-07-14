package com.sid.dashboard.client;

import java.util.Base64;
import java.util.List;
import java.util.Map;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import com.sid.dashboard.dto.Response;
import com.sid.dashboard.util.Constants;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

@Component
public class ReviewClient {

	private RestTemplate restTemplate = new RestTemplate();
	
	public List<Map<String, Object>> fetchAllUsersReviewsDetails(Object object, String path) {
		try {
			byte[] plainCredsBytes = Constants.AUTH.getBytes();
			String base64CredsBytes = Base64.getEncoder().encodeToString(plainCredsBytes);

			HttpHeaders headers = new HttpHeaders();
			headers.add("Authorization", "Basic " + base64CredsBytes);
			HttpEntity<String> request = new HttpEntity<String>(headers);
			ResponseEntity<String> responseEntity = restTemplate.exchange(Constants.BASE_URL+path, HttpMethod.GET, request,
					String.class);
			String data = responseEntity.getBody();
			ObjectMapper mapper = new ObjectMapper();
			List<Map<String, Object>> response = mapper.readValue(data, new TypeReference<List<Map<String, Object>>>(){});
			return response;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

		public Response removeReviewsClient(List<Map<String, Object>> requestBody, String path) {
		try {
			byte[] plainCredsBytes = Constants.AUTH.getBytes();
			String base64CredsBytes = Base64.getEncoder().encodeToString(plainCredsBytes);

			HttpHeaders headers = new HttpHeaders();
			headers.add("Authorization", "Basic " + base64CredsBytes);
			HttpEntity<List<Map<String, Object>>> request = new HttpEntity<List<Map<String, Object>>>(requestBody, headers);
			ResponseEntity<String> responseEntity = restTemplate.exchange(Constants.BASE_URL+path, HttpMethod.POST, request,
					String.class);
			String data = responseEntity.getBody();
			ObjectMapper mapper = new ObjectMapper();
			Response response = mapper.readValue(data, new TypeReference<Response>(){});
			return response;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

}
