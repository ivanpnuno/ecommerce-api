package com.ecommerce.service;

import java.time.LocalDateTime;
import java.util.List;

import com.ecommerce.domain.dto.PriceDTO;
import com.ecommerce.domain.dto.PriceResponseDTO;
import com.ecommerce.exceptions.CustomNotFoundException;

public interface PriceService {

	public List<PriceDTO> findAll();
	public PriceResponseDTO search(Long brandId, Long productId, LocalDateTime startDate) throws CustomNotFoundException;
}
