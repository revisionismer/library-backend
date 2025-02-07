package com.library.constant.book;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum BookConditionEnum {
	NEWBOOK("신규도서"), USEDBOOK("중고도서"), EBOOK("E-BOOK");
	
	private String value;
}
