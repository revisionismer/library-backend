package com.library.web.api.book;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.library.config.auth.PrincipalDetails;

import com.library.web.dto.ResponseDto;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/books")
@RequiredArgsConstructor
@CrossOrigin(origins = "http://127.0.0.1:3000")
public class BookApiController {
	
	@GetMapping("/s/info")
	public ResponseEntity<?> bookInfo(@AuthenticationPrincipal PrincipalDetails principalDetails) {
		
	
		return new ResponseEntity<>(new ResponseDto<>(1, "도서 정보 리스트 조회 성공", null), HttpStatus.OK);
	}

}
