package com.library.constant.user;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum UserEnum {
	ADMIN("관리자"), USER("일반 사용자");
	
	private String value;
	
}