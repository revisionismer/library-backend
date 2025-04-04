package com.library.service.order;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.library.domain.address.Address;
import com.library.domain.address.AddressRepository;
import com.library.domain.book.Book;
import com.library.domain.book.BookRepository;
import com.library.domain.cart_item.CartItemRepository;
import com.library.domain.delivery.Delivery;
import com.library.domain.delivery.DeliveryRepository;
import com.library.domain.order.Order;
import com.library.domain.order.OrderRepository;
import com.library.domain.order_item.OrderItem;
import com.library.domain.order_item.OrderItemRepository;
import com.library.domain.user.User;
import com.library.domain.user.UserRepository;
import com.library.handler.exception.CustomApiException;
import com.library.web.dto.cart.CartItemListRespDto;
import com.library.web.dto.cart.CartItemOrderRespDto;

import lombok.RequiredArgsConstructor;

@Service
@Transactional(rollbackFor = Exception.class) // RuntimeException 말고도 모든 예외가 터졌을시 롤백시킨다.
@RequiredArgsConstructor
public class OrderService {
	
	private final ObjectMapper om;
	
	private final UserRepository userRepository;
	
	private final AddressRepository addressRepository;
	
	private final DeliveryRepository deliveryRepository;
	
	private final OrderRepository orderRepository;
	private final OrderItemRepository orderItemRepository;
	
	private final BookRepository bookRepository;
	
	private final CartItemRepository cartItemRepository;
	
	public CartItemListRespDto orderCartItem(String jsonString, Long userId) {
		
		CartItemListRespDto cartItemListRespDto = getOrderCartItemList(jsonString);
		
		if(cartItemListRespDto.getCartItems().size() == 0) {
			throw new CustomApiException("재고가 없습니다.");
		}
		
		Optional<Address> addressOp = addressRepository.findByUserId(userId);
		
		if(addressOp.isEmpty()) {
			throw new CustomApiException("배송 주소가 등록 되어있지 않습니다.");
		} else {
			
			User orderUser = userRepository.findOneById(userId);
			
			Address address = addressOp.get();
			
			Delivery delivery = new Delivery();
			delivery.setRecipient(orderUser.getName());
			delivery.setDestination(
					address.getCountry() + ", " + 
					address.getZipcode() + ", " + 
					address.getAddressName() + ", " + 
					address.getDetailName());
			
			delivery.setUser(orderUser);
			delivery.setCreatedDate(LocalDateTime.now());
			
			Delivery deliveryEntity = deliveryRepository.save(delivery);
			
			Order order = new Order();
			order.setCustomer(orderUser);
			order.setDelivery(deliveryEntity);
			order.setCreatedDate(LocalDateTime.now());
			
			Order orderEntity = orderRepository.save(order);
			
			cartItemListRespDto.setOrderId(orderEntity.getId());
			
			for(int i = 0; i < cartItemListRespDto.getCartItems().size(); i++) {
				
				OrderItem orderItem = new OrderItem();
				orderItem.setOrder(orderEntity);
				orderItem.setQuantity(cartItemListRespDto.getCartItems().get(i).getCount());
				orderItem.setTotalPrice(BigDecimal.valueOf(cartItemListRespDto.getCartItems().get(i).getCount() * cartItemListRespDto.getCartItems().get(i).getPrice()));
				
				String bookName = cartItemListRespDto.getCartItems().get(i).getItemNm();
				
				Book orderBook = bookRepository.findByName(bookName);

				orderItem.setBook(orderBook);
				orderItem.setCreatedDate(LocalDateTime.now());
				
				orderItemRepository.save(orderItem);
				
				orderBook.updateStock(cartItemListRespDto.getCartItems().get(i).getCount());
				
				Long cartItemId = cartItemListRespDto.getCartItems().get(i).getCartItemId();
				
				cartItemRepository.deleteById(cartItemId);
				
			}
			
		}
		
		return cartItemListRespDto;
	}
 	
	private CartItemListRespDto getOrderCartItemList(String jsonString) {
		
		// 1-1. carItem List 정보를 담을 to 객체 생성
		CartItemListRespDto cartItemListRespDto = new CartItemListRespDto(); 
	
		// 1-2. ObjectMapper로 프론트단에서 보내오는 자료를 Map형태로 뽑아내기 위해 선언
		Map<String, Object> result = new HashMap<>();
			
		try {
			result = om.readValue(jsonString, new TypeReference<Map<String, Object>>() {});
			
		} catch(Exception e) {
			e.printStackTrace();
		}
		
		// 1-3. CartItemOrderRespDto List 형태로 바꾸기 위해 변수 선언
		List<CartItemOrderRespDto> cartItemOrderRespDtos = new ArrayList<>();
		
		// 1-4. 자료형 변환
		for(int i = 0; i < result.size(); i++) {
			cartItemOrderRespDtos.add(om.convertValue(result.get(String.valueOf(i)), CartItemOrderRespDto.class));
		}
		
		// 1-5. 변환된 1-3을 1-1에 셋팅
		cartItemListRespDto.setCartItems(cartItemOrderRespDtos);
		
		// 1-6. 주문 시에 장바구니 아이템 목록에 있는 주문 수량이 재고보다 많은지 한번 더 확인 후 진행(많으면 Exception 발생)
		for(CartItemOrderRespDto dto : cartItemOrderRespDtos) {
			if(dto.getCount() > dto.getUnitsInStock() ) {
				throw new CustomApiException("주문 수량이 재고 수량보다 더 많습니다.");
			}
		}

		return cartItemListRespDto;
				
	}

}
