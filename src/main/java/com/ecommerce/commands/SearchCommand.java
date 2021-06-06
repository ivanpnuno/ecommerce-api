package com.ecommerce.commands;

import java.time.LocalDateTime;
import java.time.format.DateTimeParseException;
import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import com.ecommerce.utils.DateUtils;

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
public class SearchCommand {

	@NotNull(message = "startDate is required")
	@NotEmpty(message = "startDate must not be empty")
	private String startDate;
	
	@NotNull(message = "productId is required")
	private Long productId;
	
	@NotNull(message = "brandId is required")
	private Long brandId;
	
	private LocalDateTime startDateFormatted;
	
	public LocalDateTime getStartDateFormatted() throws DateTimeParseException {
		return DateUtils.formatToDate(startDate);
	}
	
	public void validate() {
		Validator validator = Validation.buildDefaultValidatorFactory().getValidator();
		Set<ConstraintViolation<SearchCommand>> violations = validator.validate(this);
		
		if(!violations.isEmpty()) {
			throw new ConstraintViolationException(violations);
		}
	}
}
