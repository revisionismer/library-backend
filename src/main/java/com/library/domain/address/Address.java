package com.library.domain.address;

import java.time.LocalDateTime;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import com.fasterxml.jackson.annotation.JsonFormat;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
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
@Table(name = "address_tb")
@Entity
public class Address {

	@Id 
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;  // 4-1. PK
	 
	private String country; // 4-2. 국가명
	
	private String zipcode; // 4-3. 우편번호	
	
	private String addressName; // 4-4. 주소 
	
	private String detailName; // 4-5. 세부주소	
	
	@CreatedDate
 	@JsonFormat(pattern = "yyyy-MM-dd HH:mm")
 	@Column(nullable = false)
 	private LocalDateTime createdDate;  // 4-6. 생성일
	
	@LastModifiedDate
 	@JsonFormat(pattern = "yyyy-MM-dd HH:mm")
 	@Column(nullable = true)
 	private LocalDateTime updatedDate;  // 4-7. 수정일
}
