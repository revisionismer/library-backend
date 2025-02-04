package com.library.constant.book;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum BookConditionEnum {
	NEWBOOK("신규도서"), EBOOK("E-BOOK"), USEDBOOK("중고도서");
	
	private String value;
}
