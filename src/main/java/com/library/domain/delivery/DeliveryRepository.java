package com.library.domain.delivery;

import org.springframework.data.jpa.repository.JpaRepository;

public interface DeliveryRepository extends JpaRepository<Delivery, Long>{

	Delivery findByUserIdAndOrderId(Long userId, Long orderId);
}
