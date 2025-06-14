package com.library.domain.order_item;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface OrderItemRepository extends JpaRepository<OrderItem, Long> {
	
	List<OrderItem> findAllByOrderId(Long orderId);
	
	@Query(value = "SELECT * FROM order_item_tb WHERE orderId = :orderId AND id = :orderItemId", nativeQuery = true)
	OrderItem findOneById(@Param(value =  "orderId") Long orderId, @Param(value = "orderItemId") Long orderItemId);

}
