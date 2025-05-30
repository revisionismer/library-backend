package com.library.web.dto.order;

import java.math.BigDecimal;

import com.library.domain.order_item.OrderItem;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter @Setter
@ToString
@NoArgsConstructor
public class OrderItemRespDto {

	private Long orderId;
	private Long orderItemId;
	
	private String itemNm;
	private BigDecimal price;
	private int count;
	private Long unitsInStock;
	
	private String thumnailImageUrl;
	
	private BigDecimal totalOrderItemPrice;
	
	public OrderItemRespDto(OrderItem orderItem) {
		this.orderId = orderItem.getOrder().getId();
		this.orderItemId = orderItem.getId();
		this.itemNm = orderItem.getBook().getName();
		this.price = orderItem.getBook().getUnitPrice();
		this.count = orderItem.getQuantity();
		this.unitsInStock = orderItem.getBook().getUnitsInStock();
		this.thumnailImageUrl = orderItem.getBook().getBookImageUrl();
		this.totalOrderItemPrice = orderItem.getTotalPrice();
		
	}
}
