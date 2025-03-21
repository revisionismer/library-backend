package com.library.web.dto.order;

import com.library.web.dto.cart.CartItemListRespDto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter @Setter
@ToString
@NoArgsConstructor
public class OrderCartItemRespDto {

	private CartItemListRespDto cartItems;
}
