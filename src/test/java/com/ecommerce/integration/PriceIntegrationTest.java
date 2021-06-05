package com.ecommerce.integration;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import com.ecommerce.domain.dto.PriceDTO;
import com.ecommerce.repository.PriceRepository;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@TestPropertySource(locations = "classpath:application.properties")
public class PriceIntegrationTest {
	
	@Autowired
	private PriceRepository pricesRepository;

	@LocalServerPort
	private int port;
	
	private String baseurl;

	private static RestTemplate restTemplate;

	@BeforeAll
	public void init() {
		restTemplate = new RestTemplate();
		
		baseurl = String.format("http://localhost:%d/api/v1/prices", port);
	}

	@AfterAll
	void cleanupDatabase() {
		pricesRepository.deleteAll();
	}

	@Test
	public void returnPricesList() {
		ResponseEntity<PriceDTO[]> response = restTemplate
				.getForEntity(baseurl, PriceDTO[].class);
		assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
		
		// check database size
		int size = response.getBody().length;
		assertThat(pricesRepository.findAll().size() == size);
	}
}
