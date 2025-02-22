package com.library.domain.cart_item;

import com.library.domain.book.Book;
import com.library.domain.cart.Cart;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Builder
@NoArgsConstructor 
@AllArgsConstructor
@Getter @Setter
@Table(name = "cart_item_tb")
@Entity
@ToString
public class CartItem {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "cartId")
	private Cart cart;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "bookId")
	private Book book;
	
	private int count;
	
	// 1-1. 장바구니(Cart)에 상품(Item)을 담을 장바구니 상품(CartItem) 엔티티를 생성하는 메소드
	public static CartItem createCartItem(Cart cart, Book book, int count) {
		CartItem cartItem = new CartItem();
		cartItem.setCart(cart);
		cartItem.setBook(book);
		cartItem.setCount(count);
			
		return cartItem;
	}
		
	// 1-2. 장바구니에 기존에 담겨 있는 상품이였는데, 해당 상품의 수량을 변경해 장바구니에 담을 때 사용될 메소드(더티 체킹)
	public void addCount(int count) {
		this.count += count;
	}
		
	// 1-3. 장바구니 상품 수량 변경하기
	public void updateCount(int count) {
		this.count = count;
	}
	
	
}
