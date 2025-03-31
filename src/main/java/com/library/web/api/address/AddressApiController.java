package com.library.web.api.address;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.library.config.auth.PrincipalDetails;
import com.library.domain.user.User;
import com.library.service.address.AddressService;
import com.library.web.dto.ResponseDto;
import com.library.web.dto.address.AddressReqDto;
import com.library.web.dto.address.AddressRespDto;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/addresses")
@RequiredArgsConstructor
@CrossOrigin(origins = "http://127.0.0.1:3000")
public class AddressApiController {
	
	private final AddressService addressService;

	@GetMapping("/s/info")
	public ResponseEntity<?> getAddressInfo(@AuthenticationPrincipal PrincipalDetails principalDetails) {
		
		User loginUser = principalDetails.getUser();
		
		AddressRespDto addressRespDto =	addressService.searchAddressByUserId(loginUser);
		
		return new ResponseEntity<>(new ResponseDto<>(1, loginUser.getId() + "번 유저 주소 정보 불러오기 성공", addressRespDto), HttpStatus.OK);
	}
	
	// 2025-03-27 : 여기까지
	@PostMapping("/s/add")
	public ResponseEntity<?> registerAddressInfo(@RequestBody @Valid AddressReqDto addressReqDto, BindingResult bindingResult, @AuthenticationPrincipal PrincipalDetails principalDetails) {
		
		User loginUser = principalDetails.getUser();
		
		AddressRespDto addressRespDto = addressService.addAddressInfo(addressReqDto, loginUser);
		
		return new ResponseEntity<>(new ResponseDto<>(1, loginUser.getId() + "번 유저 주소 정보 등록하기 성공", addressRespDto), HttpStatus.OK);
	}
	
}
