package com.library.web.dto.address;

import com.library.domain.address.Address;
import com.library.domain.user.User;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter @Setter
@ToString
@NoArgsConstructor
public class AddressRespDto {
	
	private String country; 
	
	private String zipcode; 
	
	private String addressName; 
	
	private String detailName; 
	
	private Long userId;
	
	private String name;
	
	public AddressRespDto(Address addressEntity, User userEntity) {
		this.country = addressEntity.getCountry();
		this.zipcode = addressEntity.getZipcode();
		this.addressName = addressEntity.getAddressName();
		this.detailName = addressEntity.getDetailName();
		this.userId = userEntity.getId();
		this.name = userEntity.getName();
	}
}
