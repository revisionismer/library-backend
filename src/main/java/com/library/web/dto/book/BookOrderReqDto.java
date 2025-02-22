package com.library.web.dto.book;

import java.math.BigDecimal;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@NoArgsConstructor
@Getter @Setter
@ToString
public class BookOrderReqDto {
	
	private Long bookId;

	private String bookName;
	
	private BigDecimal unitPrice;
	
	private String author;
	
	private String description; 
	
	private String publisher; 
	
	private String category; 
	
	private long unitsInStock; 
	
	private String releaseDate; 
	
	private String bookCondition;
}
