package com.library.service.address;

import java.time.LocalDateTime;
import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.library.domain.address.Address;
import com.library.domain.address.AddressRepository;
import com.library.domain.user.User;
import com.library.handler.exception.CustomApiException;
import com.library.web.dto.address.AddressReqDto;
import com.library.web.dto.address.AddressRespDto;

import lombok.RequiredArgsConstructor;

@Service
@Transactional(rollbackFor = Exception.class)  // RuntimeException 말고도 모든 예외가 터졌을시 롤백시킨다.
@RequiredArgsConstructor
public class AddressService {

	private final AddressRepository addressRepository;
	
	public AddressRespDto searchAddressByUserId(User loginUser) {
		
		Optional<Address> addressOp = addressRepository.findByUserId(loginUser.getId());
		
		if(addressOp.isEmpty()) {
			throw new CustomApiException("등록된 주소가 없습니다.");
		} else {
			Address findAddress = addressOp.get();
			
			return new AddressRespDto(findAddress, loginUser);
		}
	}
	
	public AddressRespDto addAddressInfo(AddressReqDto addressReqDto, User loginUser) {
		
		Optional<Address> addressOp = addressRepository.findByUserId(loginUser.getId());
		
		if(!addressOp.isPresent()) {
			Address newAddress = new Address();
			newAddress.setCountry(addressReqDto.getCountry());
			newAddress.setZipcode(addressReqDto.getZipcode());
			newAddress.setAddressName(addressReqDto.getAddressName());
			newAddress.setDetailName(addressReqDto.getDetailName());
			newAddress.setUser(loginUser);
			newAddress.setCreatedDate(LocalDateTime.now());
			
			Address addressEntity = addressRepository.save(newAddress);
			
			return new AddressRespDto(addressEntity, loginUser);
			
		} else {
			throw new CustomApiException("이미 등록한 주소가 존재합니다.");
		}
	}
	
}
