package com.library.web.dto.order;

import java.util.List;

import com.library.domain.order.Order;
import com.library.web.dto.cart.CartItemOrderRespDto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter @Setter
@ToString
public class OrderRespDto {

	private Long orderId;
	private String orderUsername;
	
	private List<CartItemOrderRespDto> dtos;
	
	public OrderRespDto(Order orderEntity, List<CartItemOrderRespDto> cartItemOrderRespDtos) {
		this.orderId = orderEntity.getId();
		this.orderUsername = orderEntity.getCustomer().getUsername();
		this.dtos = cartItemOrderRespDtos;
	}
}
