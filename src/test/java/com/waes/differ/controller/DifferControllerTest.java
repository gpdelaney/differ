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
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

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
		ResponseEntity<String> response = restTemplate.getForEntity("/v1/diff/1", String.class);
		assertThat(response.getBody()).isEqualTo("the same");
	}
	
	@Test
	public void compareJson_shouldBeDifferntLengths() {
		String url = ("/v1/diff/1/right");
		byte[] requestBody = Base64.getEncoder().encode("randomString".getBytes());
		restTemplate.put(url, requestBody);
		String urlLeft = ("/v1/diff/1/left");
		byte[] requestBodyLeft = Base64.getEncoder().encode("random".getBytes());
		restTemplate.put(urlLeft, requestBodyLeft);
		ResponseEntity<String> response = restTemplate.getForEntity("/v1/diff/1", String.class);
		assertThat(response.getBody()).isEqualTo("different length");
	}

}