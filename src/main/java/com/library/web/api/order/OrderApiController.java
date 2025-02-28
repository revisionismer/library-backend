package com.library.web.api.order;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.library.config.auth.PrincipalDetails;
import com.library.domain.user.User;
import com.library.service.order.OrderService;
import com.library.web.dto.ResponseDto;
import com.library.web.dto.cart.CartItemOrderRespDto;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/orders")
@RequiredArgsConstructor
@CrossOrigin(origins = "http://127.0.0.1:3000")
public class OrderApiController {
	
	private final OrderService orderService;
	
	@PostMapping("/s/cartItem")
	public ResponseEntity<?> orderCartItems(@RequestBody String jsonString, @AuthenticationPrincipal PrincipalDetails principalDetails) {
		
		User loginUser = principalDetails.getUser();
		
		List<CartItemOrderRespDto> dtos = orderService.orderCartItem(jsonString, loginUser);
		
		return new ResponseEntity<>(new ResponseDto<>(1, loginUser.getId() + "번 유저 장바구니 아이템 주문 하기", dtos), HttpStatus.OK);
	}
}
