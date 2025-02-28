package com.library.web.dto.cart;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter @Setter
@ToString
@NoArgsConstructor
public class CartItemOrderRespDto {

	private Long cartId;
	private Long cartItemId;
	private String itemNm;
	private int price;
	private int count;
}
