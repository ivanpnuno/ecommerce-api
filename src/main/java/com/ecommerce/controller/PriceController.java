package com.ecommerce.controller;

import java.time.format.DateTimeParseException;
import java.util.List;

import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.annotation.ApplicationScope;

import com.ecommerce.commands.SearchCommand;
import com.ecommerce.domain.dto.PriceDTO;
import com.ecommerce.domain.dto.PriceResponseDTO;
import com.ecommerce.service.PriceService;
import com.ecommerce.exceptions.BaseException;
import com.ecommerce.exceptions.CustomUnprocessableEntityException;

@RestController
@RequestMapping("/api")
@ApplicationScope
@Produces(MediaType.APPLICATION_JSON_VALUE)
public class PriceController {

	@Autowired
	private PriceService priceService;
	
	@GetMapping("/prices")
	@Produces(MediaType.APPLICATION_JSON_VALUE)
	public List<PriceDTO> heroes() {
		List<PriceDTO> prices = priceService.findAll();
		return prices;
	}
	
	@GetMapping("/prices/search")
	@Produces(MediaType.APPLICATION_JSON_VALUE)
	public PriceResponseDTO search(@QueryParam("startDate") String startDate, @QueryParam("productId") Long productId,
			@QueryParam("brandId") Long brandId) throws BaseException {		

		PriceResponseDTO price = new PriceResponseDTO();
		try {
			SearchCommand search = SearchCommand.builder().productId(productId).brandId(brandId).startDate(startDate).build();
			search.validate();
			price = priceService.search(search.getBrandId(), search.getProductId(), search.getStartDateFormatted());
			
		} catch (DateTimeParseException e) {
			throw new CustomUnprocessableEntityException(String.format("Error parsing start date: %s, format should be yyyy-MM-dd HH:mm:ss", startDate));
		}
		
		return price;
	}
}
