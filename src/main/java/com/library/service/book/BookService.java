package com.library.service.book;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.library.domain.book.Book;
import com.library.domain.book.BookRepository;
import com.library.domain.user.User;
import com.library.web.dto.book.BookListRespDto;
import com.library.web.dto.book.BookRespDto;

import lombok.RequiredArgsConstructor;

@Service
@Transactional(rollbackFor = Exception.class)  // RuntimeException 말고도 모든 예외가 터졌을시 롤백시킨다.
@RequiredArgsConstructor
public class BookService {

	private final BookRepository bookRepository;
	
	public BookListRespDto getAllBookList(User loginUser) {
		
		List<BookRespDto> result = new ArrayList<>();
		
		List<Book> bookList = bookRepository.findAll();
		
		for(Book book : bookList) {
			result.add(new BookRespDto(book));
		}
		
		return new BookListRespDto(result, result.size());
	}
}
