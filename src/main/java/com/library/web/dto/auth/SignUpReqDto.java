package com.library.web.dto.auth;

import java.time.LocalDateTime;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.library.constant.user.UserEnum;
import com.library.domain.user.User;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter @Setter
@ToString
public class SignUpReqDto {

	@Size(min = 2, max = 20)
	@NotBlank  // NULL, 빈 문자열, 스페이스만 있는 문자열 필터링
	@Email(message = "이메일 형식으로 적어주세요.")
	private String username;
	
	@NotBlank(message = "비밀번호는 공백일 수 없습니다.")
	private String password;

	@NotBlank(message = "이름은 공백일 수 없습니다.")
	private String name;
	
	@NotBlank(message = "전화번호는 공백일 수 없습니다.")
	private String phone;
	
	public User toEntity(BCryptPasswordEncoder passwordEncoder) {
		return User.builder()
				.username(username)
				.password(passwordEncoder.encode(password))
				.role(username.equals("admin") ? UserEnum.ADMIN : UserEnum.USER)
				.name(name)
				.phone(phone)
				.createdDate(LocalDateTime.now())
				.build();
	}
	
}
