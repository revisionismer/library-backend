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
import com.library.web.dto.cart.CartItemOrderRespDto;

import lombok.RequiredArgsConstructor;

@Service
@Transactional(rollbackFor = Exception.class) // RuntimeException 말고도 모든 예외가 터졌을시 롤백시킨다.
@RequiredArgsConstructor
public class OrderService {
	
	private final ObjectMapper om;
	
	public List<CartItemOrderRespDto> orderCartItem(String jsonString, User loginUser) {
		// 2025-02-27: 데이터 받기 성공
		Map<String, Object> result = new HashMap<>();
			
		try {
			result = om.readValue(jsonString, new TypeReference<Map<String, Object>>() {});
			
		} catch(Exception e) {
			e.printStackTrace();
		}
		
		List<CartItemOrderRespDto> list = new ArrayList<>();
		
		for(int i = 0; i < result.size(); i++) {
			list.add(om.convertValue(result.get(String.valueOf(i)), CartItemOrderRespDto.class));
		}

		return list;
				
	}

}
