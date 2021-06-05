package com.ecommerce.domain.entity;

import java.math.BigDecimal;
import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.ecommerce.domain.model.Currency;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "PRICES")
public class Price {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(unique = true)
	private Long pricesList;
	
	@Column(nullable = false)
	private Long brandId;
	
	@Column(nullable = false)
	private Timestamp startDate;
	
	@Column(nullable = false)
	private Timestamp endDate;
	
	@Column(nullable = false)
	private Long productId;
	
	@Column(nullable = false)
	private Integer priority;
	
	@Column(nullable = false)
	private BigDecimal price;
	
	@Column(nullable = false)
	@Enumerated(EnumType.STRING)
	private Currency currency;
}
