package com.library.web.dto.order;

import java.util.List;

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
public class OrderInfoListRespDto {

	private List<OrderItemRespDto> orderItems;
	private int orderCnt;
	private Long totalOrderPrice;

	private DeliveryInfoDto deliveryInfoDto;
}
