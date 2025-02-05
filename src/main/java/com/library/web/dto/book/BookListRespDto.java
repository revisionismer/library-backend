package com.library.web.dto.book;

import java.util.List;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@NoArgsConstructor
@Getter @Setter
@ToString
public class BookListRespDto {

	private List<BookRespDto> books;
	private int totalCount;
	
	public BookListRespDto(List<BookRespDto> books, int totalCount) {
		this.books = books;
		this.totalCount = totalCount;
	}
}
