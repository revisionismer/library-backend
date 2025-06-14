package com.library.domain.order;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface OrderRepository extends JpaRepository<Order, Long>{
	
	@Query(value = "SELECT * FROM order_tb WHERE userId = :userId ORDER BY id DESC ", nativeQuery = true)
	List<Order> findAllByUserId(@Param("userId") Long userId);
	
	@Query(value = "SELECT * FROM order_tb WHERE userId = :userId ORDER BY id DESC ", nativeQuery = true)
	Page<Order> findAllByUserIdPaging(@Param("userId") Long userId, Pageable pageable);
	
}
