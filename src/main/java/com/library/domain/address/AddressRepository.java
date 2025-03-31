package com.library.domain.address;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

public interface AddressRepository extends JpaRepository<Address, Long> {
	// 1-1. 현재 로그인한 회원의 Address 엔티티(주소 정보)를 찾기 위해 쿼리 메소드 추가
	Optional<Address> findByUserId(Long userId);
}
