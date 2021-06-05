package com.ecommerce.domain.dto;

import java.math.BigDecimal;
import java.sql.Timestamp;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import com.ecommerce.domain.entity.Price;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Builder(toBuilder = true)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PriceDTO {
	
	@NotNull(message = "pricesList is required")
	private Long pricesList;
	
	@NotNull(message = "brandId is required")
	private Long brandId;
	
	@NotNull(message = "startDate is required")
	private Timestamp startDate;
	
	@NotNull(message = "endDate is required")
	private Timestamp endDate;
	
	@NotNull(message = "productId is required")
	private Long productId;
	
	@NotNull(message = "priority is required")
	private Integer priority;
	
	@NotNull(message = "price is required")
	private BigDecimal price;
	
	@NotNull(message = "currency is required")
	@NotEmpty(message = "currency must not be empty")
	private String currency;
	
	public static PriceDTO from(Price price) {
		return PriceDTO.builder()
		.pricesList(price.getPricesList())
		.brandId(price.getBrandId())
		.startDate(price.getStartDate())
		.endDate(price.getEndDate())
		.productId(price.getProductId())
		.priority(price.getPriority())
		.price(price.getPrice())
		.currency(price.getCurrency().getValue())
		.build();
	}
	
}
