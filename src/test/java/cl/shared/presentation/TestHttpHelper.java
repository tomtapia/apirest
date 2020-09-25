package cl.shared.presentation;

import java.nio.charset.Charset;
import java.util.Base64;
import java.util.Collections;

import org.json.JSONObject;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

public class TestHttpHelper {

	private int port;
	private String clientId;
	private String clientSecret;
	private TestRestTemplate restTemplate;
	private boolean isAuthz = false;
	private String accessToken;

	public TestHttpHelper(int port, String clientId, String clientSecret, TestRestTemplate restTemplate) {
		this.port = port;
		this.clientId = clientId;
		this.clientSecret = clientSecret;
		this.restTemplate = restTemplate;
		this.isAuthz = false;
		this.accessToken = null;
	}

	public JSONObject get(String path, Object... params) {
		if (!isAuthz) {
			authenticate();
		}
		return restTemplate.getForObject("http://localhost:" + port + path, JSONObject.class, params);
	}

	public JSONObject post(String path, Object requestBody, Object... params) {
		if (!isAuthz) {
			authenticate();
		}
		return restTemplate.postForObject("http://localhost:" + port + path, requestBody, JSONObject.class, params);
	}

	private void authenticate() {
		ObjectMapper objectMapper = new ObjectMapper();
		String authUrl = "http://localhost:" + port + "/oauth/token?grant_type=client_credentials&scope=any";
		try {
			ResponseEntity<String> authRersponse = restTemplate.exchange(authUrl, HttpMethod.POST,
					new HttpEntity<>(null, createHeaders(clientId, clientSecret)), String.class);
			JsonNode jsonResponse = objectMapper.readTree(authRersponse.getBody());
			System.out.println(jsonResponse.toString());
			accessToken = jsonResponse.get("access_token").textValue();
			restTemplate.getRestTemplate().setInterceptors(Collections.singletonList((request, body, execution) -> {
				request.getHeaders().add("authorization: Bearer ", accessToken);
				return execution.execute(request, body);
			}));
			this.isAuthz = true;
		} catch (Exception e) {
			this.isAuthz = false;
			e.printStackTrace();
		}
	}

	@SuppressWarnings("serial")
	private HttpHeaders createHeaders(String username, String password) {
		return new HttpHeaders() {
			{
				String auth = username + ":" + password;
				byte[] encodedAuth = Base64.getEncoder().encode(auth.getBytes(Charset.forName("US-ASCII")));
				String authHeader = "Basic " + new String(encodedAuth);
				set("Authorization", authHeader);
			}
		};

	}

}
