package com.library.service.order;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.library.domain.user.User;
import com.library.handler.exception.CustomApiException;
import com.library.web.dto.cart.CartItemListRespDto;
import com.library.web.dto.cart.CartItemOrderRespDto;

import lombok.RequiredArgsConstructor;

@Service
@Transactional(rollbackFor = Exception.class) // RuntimeException 말고도 모든 예외가 터졌을시 롤백시킨다.
@RequiredArgsConstructor
public class OrderService {
	
	private final ObjectMapper om;
	
	public CartItemListRespDto orderCartItem(String jsonString, User loginUser) {
		
		CartItemListRespDto cartItemListRespDto = getOrderCartItemList(jsonString, loginUser);
		
		if(cartItemListRespDto.getCartItems().size() == 0) {
			throw new CustomApiException("재고가 없습니다.");
		}
		// 2025-03-06 : 배송 정보를 등록하거나 적는 란이 필요할거 같음.
		
		return cartItemListRespDto;
	}
 	
	private CartItemListRespDto getOrderCartItemList(String jsonString, User loginUser) {
		
		// 1-1. carItem List 정보를 담을 to 객체 생성
		CartItemListRespDto cartItemListRespDto = new CartItemListRespDto(); 
	
		// 1-2. ObjectMapper로 프론트단에서 보내오는 자료를 Map형태로 뽑아내기 위해 선언
		Map<String, Object> result = new HashMap<>();
			
		try {
			result = om.readValue(jsonString, new TypeReference<Map<String, Object>>() {});
			
		} catch(Exception e) {
			e.printStackTrace();
		}
		
		// 1-3. CartItemOrderRespDto List 형태로 바꾸기 위해 변수 선언
		List<CartItemOrderRespDto> cartItemOrderRespDtos = new ArrayList<>();
		
		// 1-4. 자료형 변환
		for(int i = 0; i < result.size(); i++) {
			cartItemOrderRespDtos.add(om.convertValue(result.get(String.valueOf(i)), CartItemOrderRespDto.class));
		}
		
		// 1-5. 변환된 1-3을 1-1에 셋팅
		cartItemListRespDto.setCartItems(cartItemOrderRespDtos);
		
		// 1-6. 주문 시에 장바구니 아이템 목록에 있는 주문 수량이 재고보다 많은지 한번 더 확인 후 진행(많으면 Exception 발생)
		for(CartItemOrderRespDto dto : cartItemOrderRespDtos) {
			if(dto.getCount() > dto.getUnitsInStock() ) {
				throw new CustomApiException("주문 수량이 재고 수량보다 더 많습니다.");
			}
		}

		return cartItemListRespDto;
				
	}

}
