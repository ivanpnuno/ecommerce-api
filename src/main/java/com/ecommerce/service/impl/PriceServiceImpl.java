package com.ecommerce.service.impl;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ecommerce.domain.dto.PriceDTO;
import com.ecommerce.domain.dto.PriceResponseDTO;
import com.ecommerce.domain.entity.Price;
import com.ecommerce.exceptions.CustomNotFoundException;
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

	@Override
	public PriceResponseDTO search(Long brandId, Long productId, LocalDateTime date) throws CustomNotFoundException {
		try {
			List<Price> prices = priceRepository.findByBrandIdAndProductIdAndStartDateLessThanEqualAndEndDateGreaterThanEqualOrderByPriorityDesc(brandId, productId, date, date);

			return prices.stream().findFirst().map(price -> PriceResponseDTO.from(price)).get();
		} catch (Exception e) {
			throw new CustomNotFoundException(String.format("Price not found with brandId: %d, productId: %d and date: %s", brandId, productId, date.toString()));
		}	
	}
}
