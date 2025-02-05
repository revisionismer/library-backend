package com.library.web.dto.book;

import java.math.BigDecimal;

import com.library.constant.book.BookConditionEnum;
import com.library.domain.book.Book;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter @Setter
@ToString
public class BookRespDto {

	private Long bookId;
	
	private String bookName;
	
	private BigDecimal unitPrice;
	
	private String description; 
	
	private String publisher; 
	
	private String category; 
	
	private long unitsInStock; 
	
	private String releaseDate; 
	
	private BookConditionEnum bookCondition; 
	
	private String bookImageUrl; 
	
	private String fileName;
	
	private Long userId;
	
	public BookRespDto(Book bookEntity) {
		this.bookId = bookEntity.getId();
		this.bookName = bookEntity.getName();
		this.unitPrice = bookEntity.getUnitPrice();
		this.description = bookEntity.getDescription();
		this.publisher = bookEntity.getPublisher();
		this.category = bookEntity.getCategory();
		this.unitsInStock = bookEntity.getUnitsInStock();
		this.releaseDate = bookEntity.getReleaseDate();
		this.bookCondition = bookEntity.getBookCondition();
		this.bookImageUrl = bookEntity.getBookImageUrl();
		this.fileName = bookEntity.getFileName();
		this.userId = bookEntity.getUser().getId();
	}
}
