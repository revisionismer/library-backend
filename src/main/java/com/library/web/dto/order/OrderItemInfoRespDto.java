package com.library.web.dto.order;

import java.math.BigDecimal;

import com.library.domain.delivery.Delivery;
import com.library.domain.order.Order;
import com.library.domain.order_item.OrderItem;
import com.library.web.dto.delivery.DeliveryInfoDto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class OrderItemInfoRespDto {

	private OrderItemRespDto orderItem;
	private Long userId;
	private int orderItemCnt;
	private BigDecimal totalOrderItemPrice;

	private DeliveryInfoDto deliveryInfoDto;
	
	public OrderItemInfoRespDto(OrderItem orderItem, Long userId, Order order, Delivery delivery) {
	
		this.orderItem = new OrderItemRespDto(orderItem);
		this.userId = userId;
		this.orderItemCnt = orderItem.getQuantity();
		this.totalOrderItemPrice = orderItem.getTotalPrice();
		this.deliveryInfoDto = new DeliveryInfoDto(delivery, userId);
	}
}
