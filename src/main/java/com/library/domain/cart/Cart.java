package com.library.domain.cart;

import java.time.LocalDateTime;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.library.domain.user.User;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Builder
@NoArgsConstructor 
@AllArgsConstructor
@Getter @Setter
@Table(name = "cart_tb")
@Entity
@ToString
public class Cart {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@OneToOne(fetch = FetchType.LAZY)  // 1-1. @OneToOne 어노테이션을 이용해 회원 엔티티와 일대일로 매핑(fetch 전략은 default가 EAGER)
	@JoinColumn(name = "userId")  // 1-2. @JoinColumn 어노테이션을 이용해 외래키를 지정합니다. (user table의 id값을 userId 컬럼으로 만들어서 외래키로 지정)
	private User user;
	
	@CreatedDate
 	@JsonFormat(pattern = "yyyy-MM-dd HH:mm")
 	@Column(nullable = false)
 	private LocalDateTime createdDate;  // 1-3. 생성일
	
	@LastModifiedDate
 	@JsonFormat(pattern = "yyyy-MM-dd HH:mm")
 	@Column(nullable = true)
 	private LocalDateTime updatedDate;  // 1-4. 수정일
	
	// 1-5. 회원 엔티티를 파라미터로 받아서 장바구니 엔티티를 생성해주는 메소드
	public static Cart createCart(User user) {
		Cart cart = new Cart();
		cart.setUser(user);
		cart.setCreatedDate(LocalDateTime.now());
		return cart;
	}
		
}
