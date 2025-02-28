package com.library.web.dto.cart;

import java.math.BigDecimal;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter @Setter
@NoArgsConstructor
@ToString
public class CartDetailRespDto {

	// 1-1. 장바구니 아이디
	private Long cartId;
	
	// 1-2. 장바구니 상품 아이디
	private Long cartItemId;

	// 1-3. 상품명
	private String itemNm;

	// 1-4. 상품 금액
	private BigDecimal price;

	// 1-5. 수량
	private int count;

	// 1-6. 상품 이미지 경로
	private String bookImageUrl; 
	
	// 1-7. 상품 재고
	private long unitsInStock;

	public CartDetailRespDto(Long cartId, Long cartItemId, String itemNm, BigDecimal price, int count, String bookImageUrl, long unitsInStock) {
		this.cartId = cartId;
		this.cartItemId = cartItemId;
		this.itemNm = itemNm;
		this.price = price;
		this.count = count;
		this.bookImageUrl = bookImageUrl;
		this.unitsInStock = unitsInStock;
	}
}
