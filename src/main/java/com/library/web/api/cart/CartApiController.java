package com.library.web.api.cart;

import java.util.List;

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
import com.library.service.cart.CartService;
import com.library.web.dto.ResponseDto;
import com.library.web.dto.cart.CartDetailRespDto;
import com.library.web.dto.cart.CartItemReqDto;
import com.library.web.dto.cart.CartItemRespDto;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/carts")
@RequiredArgsConstructor
@CrossOrigin(origins = "http://127.0.0.1:3000")
public class CartApiController {
	
	private final CartService cartService;
	
	@PostMapping("/s/cartItem/add")
	public ResponseEntity<?> saveCartItemInfo(@RequestBody @Valid CartItemReqDto cartItemReqDto, BindingResult bindingResult,  @AuthenticationPrincipal PrincipalDetails principalDetails) {
		
		User loginUser = principalDetails.getUser();
	
		CartItemRespDto cartItemRespDto = cartService.saveCartItem(loginUser, cartItemReqDto);
		
		return new ResponseEntity<>(new ResponseDto<>(1, loginUser.getId() + "번 유저 장바구니에 아이템 담기", cartItemRespDto), HttpStatus.OK);
	}
	
	@GetMapping("/s/cartItem/all")
	public ResponseEntity<?> readCartItems(@AuthenticationPrincipal PrincipalDetails principalDetails) {
		
		User loginUser = principalDetails.getUser();
		
		List<CartDetailRespDto> cartDetailRespDtos = cartService.getCartItemByCartId(loginUser);
		
		return new ResponseEntity<>(new ResponseDto<>(1, loginUser.getId() + "번 유저의 장바구니에 담긴 아이템리스트 조회", cartDetailRespDtos), HttpStatus.OK);
	}
}
