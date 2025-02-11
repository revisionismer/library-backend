package com.library.web.api.user;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.library.config.auth.PrincipalDetails;
import com.library.service.user.UserService;
import com.library.web.dto.ResponseDto;
import com.library.web.dto.user.UserInfoRespDto;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
@CrossOrigin(origins = "http://127.0.0.1:3000")
public class UserApiController {
	
	private final UserService userService;
	
	@GetMapping("/{id}/info")
	public ResponseEntity<?> userInfoByUserId(@PathVariable(value = "id") Long id, @AuthenticationPrincipal PrincipalDetails principalDetails) {
		
		UserInfoRespDto userInfoRespDto = userService.userInfoByUserId(id);
		
		return new ResponseEntity<>(new ResponseDto<>(1, id + "번 유저 정보 조회 성공", userInfoRespDto), HttpStatus.OK);
	}
}
