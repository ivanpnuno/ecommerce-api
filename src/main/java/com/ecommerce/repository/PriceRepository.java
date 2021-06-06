package com.ecommerce.repository;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ecommerce.domain.entity.Price;

@Repository
public interface PriceRepository extends JpaRepository<Price, Long> {

	List<Price> findByBrandIdAndProductIdAndStartDateLessThanEqualAndEndDateGreaterThanEqualOrderByPriorityDesc(Long brandId, Long productId, LocalDateTime startDate, LocalDateTime endDate);
}