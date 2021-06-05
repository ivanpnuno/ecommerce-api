package com.ecomerce.controller;

import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.context.annotation.ApplicationScope;

import com.ecommerce.domain.dto.PriceDTO;
import com.ecommerce.service.PriceService;

@Path("/v1")
@ApplicationScope
@Produces(MediaType.APPLICATION_JSON_VALUE)
public class PriceController {

	@Autowired
	private PriceService priceService;

	@GET
	@Path( "/prices")
	@Produces(MediaType.APPLICATION_JSON_VALUE)
	public Response heroes() {
		List<PriceDTO> prices = priceService.findAll();
		return Response.ok(prices).build();
	}
}
