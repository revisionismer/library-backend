package com.library.config.auth;

import java.util.Optional;

import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.library.domain.user.User;
import com.library.domain.user.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class PrincipalDetailsService implements UserDetailsService {  // 1-1. UserDetailsService를 IOC에 등록하면 비밀번호 생성하는 로그 안뜸
	
	private final UserRepository userRepository;
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		
		Optional<User> userOp = userRepository.findByUsername(username);
		
		if(userOp.isPresent()) {
			User userPS = userOp.get();
			
			return new PrincipalDetails(userPS);
			
		} else {
			throw new InternalAuthenticationServiceException("아이디 비밀번호를 확인해주세요.");
		} 
		
	}
	
}
