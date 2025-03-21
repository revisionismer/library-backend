package com.library.domain.cart_item;

import java.time.LocalDateTime;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.library.domain.book.Book;
import com.library.domain.cart.Cart;

import jakarta.persistence.Column;
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
	private Long id;  // 4-1. PK
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "cartId")
	private Cart cart;  // 4-2. 장바구니 
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "bookId")
	private Book book;  // 4-3. 장바구니에 담긴 책
	 
	private int count;  // 4-4. 장바구니에 담긴 책 수량
	
	@CreatedDate
 	@JsonFormat(pattern = "yyyy-MM-dd HH:mm")
 	@Column(nullable = false)
 	private LocalDateTime createdDate;
	
	@LastModifiedDate
 	@JsonFormat(pattern = "yyyy-MM-dd HH:mm")
 	@Column(nullable = true)
 	private LocalDateTime updatedDate;
	
	// 4-5. 장바구니(Cart)에 상품(Item)을 담을 장바구니 상품(CartItem) 엔티티를 생성하는 메소드
	public static CartItem createCartItem(Cart cart, Book book, int count) {
		CartItem cartItem = new CartItem();
		cartItem.setCart(cart);
		cartItem.setBook(book);
		cartItem.setCount(count);
		cartItem.setCreatedDate(LocalDateTime.now());
			
		return cartItem;
	}
		
	// 4-6. 장바구니에 기존에 담겨 있는 상품이였는데, 해당 상품의 수량을 변경해 장바구니에 담을 때 사용될 메소드(더티 체킹)
	public void addCount(int count) {
		this.count += count;
	}
		
	// 4-7. 장바구니 상품 수량 변경하기
	public void updateCount(int count) {
		this.count = count;
	}
	
}
