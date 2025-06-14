package com.library.web.dto.order;

import com.library.domain.order.Order;
import com.library.domain.user.User;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter @Setter
@ToString
public class OrderInfoRespDto {
	
	private Long orderId;
	private Long customerId;
	private String deliveryAddress;
	
	public OrderInfoRespDto(Order order, User user, String deliveryAddress) {
		this.orderId = order.getId();
		this.customerId = user.getId();
		this.deliveryAddress = deliveryAddress;
	}
	
}
