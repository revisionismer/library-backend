package com.library.web.api.order;

import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.library.config.auth.PrincipalDetails;
import com.library.domain.user.User;
import com.library.service.order.OrderService;
import com.library.web.dto.ResponseDto;
import com.library.web.dto.cart.CartItemListRespDto;
import com.library.web.dto.order.OrderListPageRespDto;
// import com.library.web.dto.order.OrderListRespDto;
import com.library.web.dto.order.OrderRespDto;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/orders")
@RequiredArgsConstructor
@CrossOrigin(origins = "http://127.0.0.1:3000")
public class OrderApiController {
	
	private final OrderService orderService;
	
	@PostMapping("/s/cartItem")
	public ResponseEntity<?> sendCartItems(@RequestBody String jsonString, @AuthenticationPrincipal PrincipalDetails principalDetails) {
		
		User loginUser = principalDetails.getUser();
		
		CartItemListRespDto result = orderService.conveyCartItems(jsonString, loginUser.getId());
		
		return new ResponseEntity<>(new ResponseDto<>(1, loginUser.getId() + "번 유저 장바구니 아이템 정보 불러오기", result), HttpStatus.OK);
	}
	
	@PostMapping("/s/order")
	public ResponseEntity<?> orderItems(@RequestBody String jsonString, @AuthenticationPrincipal PrincipalDetails principalDetails) throws JsonMappingException, JsonProcessingException {
		User loginUser = principalDetails.getUser();
		
		OrderRespDto orderRespDto = orderService.orderItems(jsonString, loginUser.getId());
		
		return new ResponseEntity<>(new ResponseDto<>(1, loginUser.getId() + "번 유저 아이템 주문 하기", orderRespDto), HttpStatus.OK);
	}
	
	@GetMapping("/s")
	public ResponseEntity<?> getOrders(@AuthenticationPrincipal PrincipalDetails principalDetails, @PageableDefault(size = 6) Pageable pageable) {
		
		User loginUser = principalDetails.getUser();
		
	    // OrderListRespDto orderListRespDto = orderService.getOrderInfoList(loginUser.getId());
		OrderListPageRespDto orderListPageRespDto = orderService.getOrderInfoList(loginUser.getId(), pageable);
		
		return new ResponseEntity<>(new ResponseDto<>(1, loginUser.getId() + "번 유저의 주문 목록 리스트 불러오기", orderListPageRespDto), HttpStatus.OK);
	}
	
	@GetMapping("/s/{orderId}/info")
	public ResponseEntity<?> getOrderByOrderId(@PathVariable(value = "orderId") Long orderId, @AuthenticationPrincipal PrincipalDetails principalDetails) {
		
		User loginUser = principalDetails.getUser();
		
		return new ResponseEntity<>(new ResponseDto<>(1, loginUser.getId() + "번 유저의 주문 목록 리스트 불러오기", orderService.getOrderInfo(loginUser.getId(), orderId)), HttpStatus.OK);
	}
	
}
