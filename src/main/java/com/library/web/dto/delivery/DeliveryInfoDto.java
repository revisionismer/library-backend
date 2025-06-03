package com.library.web.dto.delivery;

import java.time.LocalDateTime;

import com.library.domain.delivery.Delivery;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class DeliveryInfoDto {
	
	private Long deliveryId;
	private Long userId;
	private String destination;
	private LocalDateTime deliveryDate;
	private String recipient;
	
	public DeliveryInfoDto(Delivery delivery, Long userId) {
		this.deliveryId = delivery.getId();
		this.userId = userId;
		this.destination = delivery.getDestination();
		this.deliveryDate = delivery.getDeliveryDate();
		this.recipient = delivery.getRecipient();
	}

}
