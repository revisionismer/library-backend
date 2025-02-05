package com.library.web.api.book;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.library.config.auth.PrincipalDetails;
import com.library.service.book.BookService;
import com.library.web.dto.ResponseDto;
import com.library.web.dto.book.BookListRespDto;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/books")
@RequiredArgsConstructor
@CrossOrigin(origins = "http://127.0.0.1:3000")
public class BookApiController {
	
	private final BookService bookService;
	
	@GetMapping("/s/all")
	public ResponseEntity<?> bookInfoList(@AuthenticationPrincipal PrincipalDetails principalDetails) {
		
		BookListRespDto bookList = bookService.getAllBookList(principalDetails.getUser());
	
		return new ResponseEntity<>(new ResponseDto<>(1, "도서 정보 리스트 조회 성공", bookList), HttpStatus.OK);
	}

}
