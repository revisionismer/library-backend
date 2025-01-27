package com.library.web.dto.auth;

import com.library.constant.user.UserEnum;
import com.library.domain.user.User;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter @Setter
@ToString
public class SignUpRespDto {

	private Long id;
	private String username;
	private String name;
	private UserEnum role;
	
	public SignUpRespDto(Long id, String username) {
		this.id = id;
		this.username = username;
		
	}
	
	public SignUpRespDto(Long id, String username, String name) {
		this.id = id;
		this.username = username;
		this.name = name;
		
	}
	
	public SignUpRespDto(User userEntity) {
		this.id = userEntity.getId();
		this.username = userEntity.getUsername();
		this.name = userEntity.getName();
		this.role = userEntity.getRole();
	}
}
