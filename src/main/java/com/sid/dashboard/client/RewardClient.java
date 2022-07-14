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

import com.sid.dashboard.dto.AddRewardDTO;
import com.sid.dashboard.dto.Response;
import com.sid.dashboard.util.Constants;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

@Component
public class RewardClient {

	private RestTemplate restTemplate = new RestTemplate();
	
	public List<Map<String, Object>> fetchAllRewards(Object object, String path) {
		
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

	public Response addReward(List<AddRewardDTO> rewards, String path) {

		try {
			byte[] plainCredsBytes = Constants.AUTH.getBytes();
			String base64CredsBytes = Base64.getEncoder().encodeToString(plainCredsBytes);

			HttpHeaders headers = new HttpHeaders();
			headers.add("Authorization", "Basic " + base64CredsBytes);
			HttpEntity<List<AddRewardDTO>> request = new HttpEntity<List<AddRewardDTO>>(rewards, headers);
			
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

	public Response toggleAccountStatus(List<Map<String, String>> requestBody, String path) {
		try {
			byte[] plainCredsBytes = Constants.AUTH.getBytes();
			String base64CredsBytes = Base64.getEncoder().encodeToString(plainCredsBytes);

			HttpHeaders headers = new HttpHeaders();
			headers.add("Authorization", "Basic " + base64CredsBytes);
			HttpEntity<List<Map<String, String>>> request = new HttpEntity<List<Map<String, String>>>(requestBody, headers);
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

	public Response deleteRewardsClient(List<Map<String, String>> requestBody, String path) {
		try {
			byte[] plainCredsBytes = Constants.AUTH.getBytes();
			String base64CredsBytes = Base64.getEncoder().encodeToString(plainCredsBytes);

			HttpHeaders headers = new HttpHeaders();
			headers.add("Authorization", "Basic " + base64CredsBytes);
			HttpEntity<List<Map<String, String>>> request = new HttpEntity<List<Map<String, String>>>(requestBody, headers);
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

	public Response updateReward(AddRewardDTO reward, String path) {
		try {
			byte[] plainCredsBytes = Constants.AUTH.getBytes();
			String base64CredsBytes = Base64.getEncoder().encodeToString(plainCredsBytes);

			HttpHeaders headers = new HttpHeaders();
			headers.add("Authorization", "Basic " + base64CredsBytes);
			HttpEntity<AddRewardDTO> request = new HttpEntity<AddRewardDTO>(reward, headers);
			
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
