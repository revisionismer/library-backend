package com.library.web.dto.order;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class OrderListRespDto {
	
	private List<OrderInfoRespDto> orderList;
	private int orderCnt;
}
