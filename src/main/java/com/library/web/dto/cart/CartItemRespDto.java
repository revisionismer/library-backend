package com.library.web.dto.cart;

import com.library.domain.cart.Cart;
import com.library.domain.cart_item.CartItem;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter @Setter
@ToString
public class CartItemRespDto {

	private Long cartId;
	
	private Long cartItemId;
	
	private Long bookId;
	
	private String bookName;
	
	private int count;
	
	public CartItemRespDto(Cart cart, CartItem cartItem) {
		this.cartId = cart.getId();
		this.cartItemId = cartItem.getId();
		this.bookId = cartItem.getBook().getId();
		this.bookName = cartItem.getBook().getName();
		this.count = cartItem.getCount();
	}
}
