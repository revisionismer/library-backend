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

	// 1-1. 장바구니 상품 아이디
	private Long cartId;

	// 1-2. 상품명
	private String itemNm;

	// 1-3. 상품 금액
	private BigDecimal price;

	// 1-4. 수량
	private int count;

	// 1-5. 상품 이미지 경로
	private String bookImageUrl; 

	public CartDetailRespDto(Long cartId, String itemNm, BigDecimal price, int count, String bookImageUrl) {
		this.cartId = cartId;
		this.itemNm = itemNm;
		this.price = price;
		this.count = count;
		this.bookImageUrl = bookImageUrl;
	}
}
