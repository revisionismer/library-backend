package com.library.web.dto.order;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class OrderListPageRespDto {

	private List<OrderInfoRespDto> orderList;
	private Long orderCnt;

	private Integer prev;
	private Integer next;
	private List<Integer> pageNumbers;

	private Boolean isPrev;
	private Boolean isNext;

	private Boolean isFirst;
}
