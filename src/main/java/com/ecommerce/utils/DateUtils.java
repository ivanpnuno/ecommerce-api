package com.ecommerce.utils;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class DateUtils {
	
	static final String DATE_FORMAT = "yyyy-MM-dd HH:mm:ss";
	
	public static LocalDateTime formatToDate(String stringDate) throws DateTimeParseException {
		if( stringDate == null) {
			return null;
		}
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern(DATE_FORMAT);
		
		return LocalDateTime.parse(stringDate, formatter);
	}

}
