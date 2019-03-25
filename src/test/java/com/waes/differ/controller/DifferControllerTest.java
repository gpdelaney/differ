package com.waes.differ.controller;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Base64;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

import com.waes.differ.model.EncodedJsonResponseDTO;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class DifferControllerTest {

	@Autowired
	DifferController differController;

	@Autowired
	TestRestTemplate restTemplate;

	@Test
	public void whenValidJsonLeft_itShouldBePersisted() {
		String url = ("/v1/diff/1/left");
		byte[] requestBody = Base64.getEncoder().encode("randomString".getBytes());
		HttpEntity<byte[]> entity = new HttpEntity<byte[]>(requestBody);
		ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.PUT, entity, String.class);
		assertThat(response.getStatusCode().equals(HttpStatus.OK));
	}

	@Test
	public void whenValidJsonRight_itShouldBePersisted() {
		String url = ("/v1/diff/1/right");
		byte[] requestBody = Base64.getEncoder().encode("randomString".getBytes());
		HttpEntity<byte[]> entity = new HttpEntity<byte[]>(requestBody);
		ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.PUT, entity, String.class);
		assertThat(response.getStatusCode().equals(HttpStatus.OK));
	}

	@Test
	public void compareJson_shouldBeEquals() {
		String url = ("/v1/diff/1/right");
		byte[] requestBody = Base64.getEncoder().encode("randomString".getBytes());
		restTemplate.put(url, requestBody);
		String urlLeft = ("/v1/diff/1/left");
		byte[] requestBodyLeft = Base64.getEncoder().encode("randomString".getBytes());
		restTemplate.put(urlLeft, requestBodyLeft);
		ResponseEntity<EncodedJsonResponseDTO> response = restTemplate.getForEntity("/v1/diff/1", EncodedJsonResponseDTO.class);
		assertThat(response.getBody().getJsonOperationResult()).isEqualTo("the same");
	}

	@Test
	public void compareJson_shouldBeDifferentLengths() {
		String url = ("/v1/diff/1/right");
		byte[] requestBody = Base64.getEncoder().encode("randomString".getBytes());
		restTemplate.put(url, requestBody);
		String urlLeft = ("/v1/diff/1/left");
		byte[] requestBodyLeft = Base64.getEncoder().encode("random".getBytes());
		restTemplate.put(urlLeft, requestBodyLeft);
		ResponseEntity<EncodedJsonResponseDTO> response = restTemplate.getForEntity("/v1/diff/1", EncodedJsonResponseDTO.class);
		assertThat(response.getBody().getJsonOperationResult()).isEqualTo("Different Encoded Json Length");
	}
	
	@Test
	public void compareJson_shouldBeDifferent() {
		String url = ("/v1/diff/1/right");
		byte[] requestBody = Base64.getEncoder().encode("randomString".getBytes());
		restTemplate.put(url, requestBody);
		String urlLeft = ("/v1/diff/1/left");
		byte[] requestBodyLeft = Base64.getEncoder().encode("randomStrong".getBytes());
		restTemplate.put(urlLeft, requestBodyLeft);
		ResponseEntity<EncodedJsonResponseDTO> response = restTemplate.getForEntity("/v1/diff/1", EncodedJsonResponseDTO.class);
		assertThat(response.getBody().getJsonOperationResult()).isEqualTo("Different! The offset is the last 2 characters from the right array ");
	}
	
	@Test
	public void compareJsonWithNull_shouldBe404() {
		String urlLeft = ("/v1/diff/4/left");
		byte[] requestBodyLeft = Base64.getEncoder().encode("randomStrong".getBytes());
		restTemplate.put(urlLeft, requestBodyLeft);
		ResponseEntity<EncodedJsonResponseDTO> response = restTemplate.getForEntity("/v1/diff/4", EncodedJsonResponseDTO.class);
		assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
	}
	
	@Test
	public void decodeJson_shoudlReturnStringDecoded() {
		String url = ("/v1/diff/decode/{encodedJson}");
		byte[] requestBody = Base64.getEncoder().encode("randomStrong".getBytes());
		ResponseEntity<EncodedJsonResponseDTO> response = restTemplate.getForEntity(url, EncodedJsonResponseDTO.class,new String(requestBody));
		assertThat(response.getBody().getJsonOperationResult()).isEqualTo("randomStrong");
	}
	
	@Test
	public void decodeJson_shoudlReturnStringEncoded() {
		String url = ("/v1/diff/encode/{plainJson}");
		byte[] requestBody = Base64.getEncoder().encode("{\"json\":\"new Json!W\"}".getBytes());
		ResponseEntity<EncodedJsonResponseDTO> response = restTemplate.getForEntity(url, EncodedJsonResponseDTO.class,"{\"json\":\"new Json!W\"}");
		assertThat(response.getBody().getJsonOperationResult().getBytes()).isEqualTo(requestBody);
	}
	
	@Test
	public void compareJsonInHeader_shouldBeTheSame() {
		String url = ("/v1/diff/header/1/right");
		HttpHeaders headers = new HttpHeaders();
		headers.set("encoded-json","ewoiZW5jb2RlZC1qc29uIjoiZW5jb2RlIgp9");
		HttpEntity entity = new HttpEntity(headers);
		restTemplate.put(url, entity);
		String urlLeft = ("/v1/diff/header/1/left");
		restTemplate.put(urlLeft, entity);
		ResponseEntity<EncodedJsonResponseDTO> response = restTemplate.getForEntity("/v1/diff/1", EncodedJsonResponseDTO.class);
		assertThat(response.getBody().getJsonOperationResult()).isEqualTo("the same");
	}
	@Test
	public void compareJsonInHeader_shouldBeDiffLengths() {
		String url = ("/v1/diff/header/1/right");
		HttpHeaders headers = new HttpHeaders();
		headers.set("encoded-json","ewoiZW5jb2RlZC1qc29uIjoiZW5jb2RlIgp9");
		HttpEntity entity = new HttpEntity(headers);
		restTemplate.put(url, entity);
		String urlLeft = ("/v1/diff/header/1/left");
		HttpHeaders headersLeft = new HttpHeaders();
		headersLeft.set("encoded-json","ewoiZW5jb2");
		HttpEntity entityLeft = new HttpEntity(headersLeft);
		restTemplate.put(urlLeft, entityLeft);
		ResponseEntity<EncodedJsonResponseDTO> response = restTemplate.getForEntity("/v1/diff/1", EncodedJsonResponseDTO.class);
		assertThat(response.getBody().getJsonOperationResult()).isEqualTo("Different Encoded Json Length");
	}
	
	@Test
	public void compareJsonInHeader_shouldBeDiffSameLengths() {
		String url = ("/v1/diff/header/1/right");
		HttpHeaders headers = new HttpHeaders();
		headers.set("encoded-json","ewogImhlbGxvIjoianNhbiIKew==");
		HttpEntity entity = new HttpEntity(headers);
		restTemplate.put(url, entity);
		String urlLeft = ("/v1/diff/header/1/left");
		HttpHeaders headersLeft = new HttpHeaders();
		headersLeft.set("encoded-json","ewogImhlbGxvIjoianNvbiIKew==");
		HttpEntity entityLeft = new HttpEntity(headersLeft);
		restTemplate.put(urlLeft, entityLeft);
		ResponseEntity<EncodedJsonResponseDTO> response = restTemplate.getForEntity("/v1/diff/1", EncodedJsonResponseDTO.class);
		assertThat(response.getBody().getJsonOperationResult()).isEqualTo("Different! The offset is the last 8 characters from the right array ");
	}
}
