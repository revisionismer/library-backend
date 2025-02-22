package com.library.web.dto.cart;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter @Setter
@ToString
@NoArgsConstructor
public class CartItemReqDto {

	private Long bookId;
	private int count;
}
