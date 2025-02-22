package com.library.domain.cart_item;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

public interface CartItemRepository extends JpaRepository<CartItem, Long> {

	// 1-1. 장바구니 아이디를 톹해 상품이 장바구니에 담겨있는지 조회하는 메소드
	List<CartItem> findAllByCartId(Long cartId);
}
