package com.library.web.dto.order;

import java.math.BigDecimal;

import com.library.domain.order.Order;
import com.library.domain.order_item.OrderItem;
import com.library.domain.user.User;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter @Setter
@ToString
public class OrderInfoRespDto {
	
	private Long orderId;
	private Long orderItemId;
	private Long customerId;
	private String deliveryAddress;
	private String thumnailImageUrl;
	private BigDecimal totalOrderPrice;
	
	public OrderInfoRespDto(Order order, OrderItem orderItem, User user, String deliveryAddress, BigDecimal totalOrderPrice) {
		this.orderId = order.getId();
		this.orderItemId = orderItem.getId();
		this.customerId = user.getId();
		this.deliveryAddress = deliveryAddress;
		this.thumnailImageUrl = orderItem.getBook().getBookImageUrl();
		this.totalOrderPrice = totalOrderPrice;
	}
	
}
