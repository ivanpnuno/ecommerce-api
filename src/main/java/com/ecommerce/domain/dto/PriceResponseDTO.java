package com.ecommerce.domain.dto;

import java.time.LocalDateTime;

import javax.validation.constraints.NotNull;

import com.ecommerce.domain.entity.Price;
import com.ecommerce.domain.model.Currency;

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
public class PriceResponseDTO {
	
	@NotNull(message = "productId is required")
	private Long productId;
	
	@NotNull(message = "brandId is required")
	private Long brandId;
	
	@NotNull(message = "pricesList is required")
	private Long pricesList;
	
	@NotNull(message = "startDate is required")
	private LocalDateTime startDate;
	
	@NotNull(message = "endDate is required")
	private LocalDateTime endDate;
	
	@NotNull(message = "price is required")
	private Currency price;
	
	public static PriceResponseDTO from(Price price) {
		return PriceResponseDTO.builder()
		.pricesList(price.getPricesList())
		.brandId(price.getBrandId())
		.startDate(price.getStartDate())
		.endDate(price.getEndDate())
		.productId(price.getProductId())
		.price(Currency.builder().amount(price.getPrice()).currency(price.getCurrency().getValue()).build())
		.build();
	}
	
}
