package com.library.web.dto.cart;

import java.util.List;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter @Setter
@ToString
@NoArgsConstructor
public class CartItemListRespDto {

	private List<CartItemOrderRespDto> cartItems;
	private int totalPrice;
}
