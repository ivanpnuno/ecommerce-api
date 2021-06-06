package com.ecommerce.integration;

import static org.assertj.core.api.Assertions.assertThat;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.ParseException;

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

import com.ecommerce.commands.SearchCommand;
import com.ecommerce.domain.dto.PriceDTO;
import com.ecommerce.domain.dto.PriceResponseDTO;
import com.ecommerce.repository.PriceRepository;
import com.ecommerce.utils.DateUtils;

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
		
		baseurl = String.format("http://localhost:%d/api/prices", port);
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
	
	@Test
	public void searchWithEmptyBrandShouldReturnException() {
		Long productId = 35455L;
		String date = "2020-06-14 10:00:00";
		
		try {
			restTemplate.getForEntity(baseurl + "/search?productId={1}&startDate={2}",
					PriceResponseDTO.class, productId, date);
		} catch (RestClientException e) {
			assertThat(e.getMessage()).contains("400");
			assertThat(e.getMessage()).contains("brandId is required");
		}
	}
	
	@Test
	public void searchWithNonValidDateShouldReturnException() {
		Long brandId = 1L;
		Long productId = 35455L;
		String date = "2020-06-14";
		
		try {
			restTemplate.getForEntity(baseurl + "/search?brandId={1}&productId={2}&startDate={3}",
					PriceResponseDTO.class, brandId, productId, date);
		} catch (RestClientException e) {
			assertThat(e.getMessage()).contains("422");
			assertThat(e.getMessage()).contains("Error parsing start date");
		}
	}
	
	@Test
	public void searchForNonExistingValueShouldReturnException() {
		Long brandId = 1L;
		Long productId = 35456L;
		String date = "2020-06-14 10:00:00";
		
		try {
			restTemplate.getForEntity(baseurl + "/search?brandId={1}&productId={2}&startDate={3}",
					PriceResponseDTO.class, brandId, productId, date);
		} catch (RestClientException e) {
			assertThat(e.getMessage()).contains("404");
			assertThat(e.getMessage()).contains("Price not found");
		}
	}
	
	@Test
	public void firstSearchRequestTest() throws ParseException {		
		Long brandId = 1L;
		Long productId = 35455L;
		String date = "2020-06-14 10:00:00";
		
		BigDecimal expectedAmount = new BigDecimal(35.50).setScale(2, RoundingMode.UP);
		
		ResponseEntity<PriceResponseDTO> response = restTemplate.getForEntity(baseurl + "/search?brandId={1}&productId={2}&startDate={3}",
				PriceResponseDTO.class, brandId, productId, date);
		assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
		assertThat(response.getBody().getBrandId()).isEqualTo(brandId);
		assertThat(response.getBody().getProductId()).isEqualTo(productId);
		assertThat(response.getBody().getStartDate()).isBefore(DateUtils.formatToDate(date));
		assertThat(response.getBody().getEndDate()).isAfter(DateUtils.formatToDate(date));
		assertThat(response.getBody().getPrice().getAmount()).isEqualTo(expectedAmount);
		assertThat(response.getBody().getPrice().getCurrency()).isEqualTo("EUR");
	}
	
	@Test
	public void secondSearchRequestTest() throws ParseException {		
		Long brandId = 1L;
		Long productId = 35455L;
		String date = "2020-06-14 16:00:00";
		
		BigDecimal expectedAmount = new BigDecimal(25.45).setScale(2, RoundingMode.UP);
		
		ResponseEntity<PriceResponseDTO> response = restTemplate.getForEntity(baseurl + "/search?brandId={1}&productId={2}&startDate={3}",
				PriceResponseDTO.class, brandId, productId, date);
		assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
		assertThat(response.getBody().getBrandId()).isEqualTo(brandId);
		assertThat(response.getBody().getProductId()).isEqualTo(productId);
		assertThat(response.getBody().getStartDate()).isBefore(DateUtils.formatToDate(date));
		assertThat(response.getBody().getEndDate()).isAfter(DateUtils.formatToDate(date));
		assertThat(response.getBody().getPrice().getAmount()).isEqualTo(expectedAmount);
		assertThat(response.getBody().getPrice().getCurrency()).isEqualTo("EUR");
	}
	
	@Test
	public void thirdSearchRequestTest() throws ParseException {		
		Long brandId = 1L;
		Long productId = 35455L;
		String date = "2020-06-14 21:00:00";
		
		BigDecimal expectedAmount = new BigDecimal(35.50).setScale(2, RoundingMode.UP);
		
		ResponseEntity<PriceResponseDTO> response = restTemplate.getForEntity(baseurl + "/search?brandId={1}&productId={2}&startDate={3}",
				PriceResponseDTO.class, brandId, productId, date);
		assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
		assertThat(response.getBody().getBrandId()).isEqualTo(brandId);
		assertThat(response.getBody().getProductId()).isEqualTo(productId);
		assertThat(response.getBody().getStartDate()).isBefore(DateUtils.formatToDate(date));
		assertThat(response.getBody().getEndDate()).isAfter(DateUtils.formatToDate(date));
		assertThat(response.getBody().getPrice().getAmount()).isEqualTo(expectedAmount);
		assertThat(response.getBody().getPrice().getCurrency()).isEqualTo("EUR");
	}
	
	@Test
	public void fourthSearchRequestTest() throws ParseException {		
		Long brandId = 1L;
		Long productId = 35455L;
		String date = "2020-06-15 10:00:00";
		
		BigDecimal expectedAmount = new BigDecimal(30.50).setScale(2, RoundingMode.UP);
		
		ResponseEntity<PriceResponseDTO> response = restTemplate.getForEntity(baseurl + "/search?brandId={1}&productId={2}&startDate={3}",
				PriceResponseDTO.class, brandId, productId, date);
		assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
		assertThat(response.getBody().getBrandId()).isEqualTo(brandId);
		assertThat(response.getBody().getProductId()).isEqualTo(productId);
		assertThat(response.getBody().getStartDate()).isBefore(DateUtils.formatToDate(date));
		assertThat(response.getBody().getEndDate()).isAfter(DateUtils.formatToDate(date));
		assertThat(response.getBody().getPrice().getAmount()).isEqualTo(expectedAmount);
		assertThat(response.getBody().getPrice().getCurrency()).isEqualTo("EUR");
	}
	
	@Test
	public void fifthSearchRequestTest() throws ParseException {		
		Long brandId = 1L;
		Long productId = 35455L;
		String date = "2020-06-16 21:00:00";
		
		BigDecimal expectedAmount = new BigDecimal(38.950).setScale(2, RoundingMode.DOWN);
		
		ResponseEntity<PriceResponseDTO> response = restTemplate.getForEntity(baseurl + "/search?brandId={1}&productId={2}&startDate={3}",
				PriceResponseDTO.class, brandId, productId, date);
		assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
		assertThat(response.getBody().getBrandId()).isEqualTo(brandId);
		assertThat(response.getBody().getProductId()).isEqualTo(productId);
		assertThat(response.getBody().getStartDate()).isBefore(DateUtils.formatToDate(date));
		assertThat(response.getBody().getEndDate()).isAfter(DateUtils.formatToDate(date));
		assertThat(response.getBody().getPrice().getAmount()).isEqualTo(expectedAmount);
		assertThat(response.getBody().getPrice().getCurrency()).isEqualTo("EUR");
	}
}
