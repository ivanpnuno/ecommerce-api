package com.ecommerce.service;

import java.util.List;

import com.ecommerce.domain.dto.PriceDTO;

public interface PriceService {

	public List<PriceDTO> findAll();
}
