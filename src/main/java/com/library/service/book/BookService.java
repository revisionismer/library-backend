package com.library.service.book;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.library.domain.book.Book;
import com.library.domain.book.BookRepository;
import com.library.domain.user.User;
import com.library.handler.exception.CustomApiException;
import com.library.web.dto.book.BookListRespDto;
import com.library.web.dto.book.BookReqDto;
import com.library.web.dto.book.BookRespDto;

import lombok.RequiredArgsConstructor;

@Service
@Transactional(rollbackFor = Exception.class)  // RuntimeException 말고도 모든 예외가 터졌을시 롤백시킨다.
@RequiredArgsConstructor
public class BookService {

	private final BookRepository bookRepository;
	private final FileService fileService;
	
	@Transactional(readOnly = true)
	public BookListRespDto getAllBookList(User loginUser) {
		
		List<BookRespDto> result = new ArrayList<>();
		
		List<Book> bookList = bookRepository.findAll();
		
		for(Book book : bookList) {
			result.add(new BookRespDto(book));
		}
		
		return new BookListRespDto(result, result.size());
	}
	
	public BookRespDto setNewBook(User loginUser, String bookString, MultipartFile bookImageFile) {
	
		ObjectMapper om = new ObjectMapper();
	
		Book bookEntity = null;
		
		BookReqDto bookReqDto = null;
		
		try {
			bookReqDto = om.readValue(bookString, BookReqDto.class);

		} catch(Exception e) {
			throw new CustomApiException(e.getMessage());
		}
	
		if(bookImageFile == null) {
			bookEntity = new Book().toEntity(bookReqDto, loginUser);	
			
		} else {
			try {
				String savedImgFileUrl = fileService.uploadFile(bookImageFile.getOriginalFilename(), bookImageFile.getBytes());
			
				bookEntity = new Book().toEntity(bookReqDto, savedImgFileUrl, bookImageFile.getOriginalFilename(), loginUser);
			} catch(Exception e) {
				throw new CustomApiException(e.getMessage());
			}
		}
		
		Book newBook = bookRepository.save(bookEntity);
		
		return new BookRespDto(newBook);
		
	}
}
