package com.library.web.dto.order;

import java.util.List;

import com.library.web.dto.cart.CartItemOrderRespDto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@ToString
@Getter @Setter
@NoArgsConstructor
public class OrderCartItemRespDto {
	private List<CartItemOrderRespDto> items;
	
}
