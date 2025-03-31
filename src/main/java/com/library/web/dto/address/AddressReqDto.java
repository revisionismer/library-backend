package com.library.web.dto.address;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter @Setter
@ToString
@NoArgsConstructor
public class AddressReqDto {
	
	private String country; 
	
	private String zipcode; 
	
	private String addressName; 
	
	private String detailName; 

}
