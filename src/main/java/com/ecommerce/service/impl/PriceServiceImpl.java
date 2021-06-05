package com.ecommerce.service.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ecommerce.domain.dto.PriceDTO;
import com.ecommerce.domain.entity.Price;
import com.ecommerce.repository.PriceRepository;
import com.ecommerce.service.PriceService;

@Service("pricesService")
public class PriceServiceImpl implements PriceService {

	@Autowired
	private PriceRepository priceRepository;

	@Override
	public List<PriceDTO> findAll() {
		List<Price> prices = priceRepository.findAll();

		return prices.stream().map(price -> PriceDTO.from(price)).collect(Collectors.toList());
	}
}
