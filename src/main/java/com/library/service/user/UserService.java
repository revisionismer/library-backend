package com.library.service.user;

import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.library.domain.user.User;
import com.library.domain.user.UserRepository;
import com.library.handler.exception.CustomApiException;
import com.library.web.dto.user.UserInfoRespDto;

import lombok.RequiredArgsConstructor;

@Service
@Transactional(rollbackFor = Exception.class)  // RuntimeException 말고도 모든 예외가 터졌을시 롤백시킨다.
@RequiredArgsConstructor
public class UserService {

	private final UserRepository userRepository;
	
	public UserInfoRespDto userInfoByUserId(Long userId) {
		
		Optional<User> userOp = userRepository.findById(userId);
		
		if(userOp.isPresent()) {
			User user = userOp.get();
			
			return new UserInfoRespDto(user);
		} else { 
			throw new CustomApiException("해당 유저가 존재하지 않습니다.");
		}
	}
}
