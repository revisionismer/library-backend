package com.library.service.cart;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.library.domain.book.Book;
import com.library.domain.book.BookRepository;
import com.library.domain.cart.Cart;
import com.library.domain.cart.CartRepository;
import com.library.domain.cart_item.CartItem;
import com.library.domain.cart_item.CartItemRepository;
import com.library.domain.user.User;
import com.library.handler.exception.CustomApiException;
import com.library.web.dto.cart.CartDetailRespDto;
import com.library.web.dto.cart.CartItemReqDto;
import com.library.web.dto.cart.CartItemRespDto;

import lombok.RequiredArgsConstructor;

@Service
@Transactional(rollbackFor = Exception.class) // RuntimeException 말고도 모든 예외가 터졌을시 롤백시킨다.
@RequiredArgsConstructor
public class CartService {
	
	private final BookRepository bookRepository;
	private final CartRepository cartRepository;
	private final CartItemRepository cartItemRepository;

	// 2-1. 장바구니 상품 조회 서비스
	public List<CartDetailRespDto> getCartItemByCartId(User loginUser) {
		
		List<CartDetailRespDto> result = new ArrayList<>();
		
		// 2-2. 로그인한 유저의 Cart 정보를 가져온다.
		Cart cart = cartRepository.findByUserId(loginUser.getId());

		// 2-3. 장바구니가 비어 있다면 2-3에서 생성한 비어있는 장바구니 반환
		if (cart == null) {
			cart = Cart.createCart(loginUser);
			
			cart = cartRepository.save(cart);
		 
		}

		// 2-4. 해당 유저 아이디로 검색 되는 Cart 아이디로 Cart에 Item을 담은 CartItem 테이블을 가져온다.
		List<CartItem> cartItems = cartItemRepository.findAllByCartId(cart.getId());
	
		// 2-5. CartDetailRespDto형 List로 변환
		for(CartItem cartItem : cartItems) {
			result.add(
					new CartDetailRespDto(
							cart.getId(), 
							cartItem.getId(),
							cartItem.getBook().getName(), 
							cartItem.getBook().getUnitPrice(), 
							cartItem.getCount(), 
							cartItem.getBook().getBookImageUrl(),
							cartItem.getBook().getUnitsInStock()
					)
			);
		}
		
		return result;
	}
	
	public CartItemRespDto saveCartItem(User loginUser, CartItemReqDto cartItemReqDto) {
		
		Optional<Book> bookOp = bookRepository.findById(cartItemReqDto.getBookId());
		
		Cart cart = cartRepository.findByUserId(loginUser.getId());
		
		if(bookOp.isPresent()) {
			Book book = bookOp.get();
			
			if (cart == null) {
				cart = Cart.createCart(loginUser);
				
				cart = cartRepository.save(cart);
			 
			}
			
			CartItem cartItem = CartItem.createCartItem(cart, book, cartItemReqDto.getCount());
			
			CartItem cartItemEntity = cartItemRepository.save(cartItem);
			
			return new CartItemRespDto(cart, cartItemEntity);
			
		} else {
			throw new CustomApiException("해당 도서 정보가 존재하지 않습니다.");
		}
		
	}
}
