package com.library.domain.order;

import java.time.LocalDateTime;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.library.domain.delivery.Delivery;
import com.library.domain.user.User;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
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

@Builder
@NoArgsConstructor 
@AllArgsConstructor
@Getter @Setter
@Table(name = "order_tb")
@Entity
public class Order {

	@Id 
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;  // 5-1 PK		
			
	@ManyToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "userId")
	private User customer;  // 5-2. 주문자
	
	@ManyToOne(cascade = CascadeType.ALL)	
	@JoinColumn(name = "deliveryId")
	private Delivery delivery;  // 5-3. 배송지
	
	@CreatedDate
 	@JsonFormat(pattern = "yyyy-MM-dd HH:mm")
 	@Column(nullable = false)
 	private LocalDateTime createdDate;  // 5-4. 생성일자
 	 
 	@LastModifiedDate
 	@JsonFormat(pattern = "yyyy-MM-dd HH:mm")
 	@Column(nullable = true)
 	private LocalDateTime updatedDate;  // 5-5. 수정일자
}
