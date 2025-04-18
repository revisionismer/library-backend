package com.library.domain.cart;

import org.springframework.data.jpa.repository.JpaRepository;

public interface CartRepository extends JpaRepository<Cart, Long> {
	// 1-1. 현재 로그인한 회원의 장바구니(Cart) 엔티티를 찾기 위해 쿼리 메소드 추가
	Cart findByUserId(Long userId);
}
