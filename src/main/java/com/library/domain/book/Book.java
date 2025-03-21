package com.library.domain.book;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.library.constant.book.BookConditionEnum;
import com.library.domain.user.User;
import com.library.web.dto.book.BookReqDto;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Builder
@NoArgsConstructor 
@AllArgsConstructor
@Getter @Setter
@Table(name = "book_tb")
@Entity
public class Book {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;  // 2-1. pk
	
	@Size(min = 4, max = 50)
	private String name;  // 2-2. 이름
	
	private String author; // 2-3. 작가
	
	@Min(value = 0, message = "0보다 큰 금액이여야 합니다.")
	@Digits(integer = 8, fraction = 0, message = "1억원 미만으로 설정해야 하고 소수점은 허용하지 않습니다.")  // 정수 부분과 소수 부분의 자릿수 제한을 검증한다. integer 속성은 정수의 자릿수를, fraction 속성은 소수 이하 자릿수를 지정한다.
	private BigDecimal unitPrice;  // 2-4. 단가
	
	private String description; // 2-5. 설명	
	
	private String publisher; // 2-6. 출판사
	
	private String category; // 2-7. 분류	
	
	private long unitsInStock; // 2-8. 재고
	
	private String releaseDate; // 2-9. 출판일
	
	@Enumerated(value = EnumType.STRING)
	@Column(nullable = false)
	private BookConditionEnum bookCondition; // 2-10. 신규도서/E-Book/중고도서
	
	private String bookImageUrl;  // 2-11. 도서 이미지 저장 경로
	
	private String fileName; // 2-12. 도서 이미지 파일
	
	@JoinColumn(name = "userId")  // @JoinColumn : 연관관계의 주인이라는 뜻, Object를 선언할때는 외래키의 이름을 지정
	@ManyToOne(fetch = FetchType.LAZY)
	private User user;   // 2-13. 도서 등록자(N:1)
	
	@CreatedDate
 	@JsonFormat(pattern = "yyyy-MM-dd HH:mm")
 	@Column(nullable = false)
 	private LocalDateTime createdDate;  // 2-14. 생성일
	
	@LastModifiedDate
 	@JsonFormat(pattern = "yyyy-MM-dd HH:mm")
 	@Column(nullable = true)
 	private LocalDateTime updatedDate;  // 2-15. 수정일
	
	public Book toEntity(BookReqDto bookReqDto, User loginUser) {
		return Book.builder()
				.name(bookReqDto.getBookName())
				.author(bookReqDto.getAuthor())
				.unitPrice(bookReqDto.getUnitPrice())
				.description(bookReqDto.getDescription())
				.publisher(bookReqDto.getPublisher())
				.category(bookReqDto.getCategory())
				.unitsInStock(bookReqDto.getUnitsInStock())
				.releaseDate(bookReqDto.getReleaseDate())
				.bookCondition(BookConditionEnum.valueOf(bookReqDto.getBookCondition()))
				.user(loginUser)
				.createdDate(LocalDateTime.now())
				.build();
	}
	
	public Book toEntity(BookReqDto bookReqDto, String savedImgFileUrl, String originalFileName, User loginUser) {
		return Book.builder()
				.name(bookReqDto.getBookName())
				.author(bookReqDto.getAuthor())
				.unitPrice(bookReqDto.getUnitPrice())
				.description(bookReqDto.getDescription())
				.publisher(bookReqDto.getPublisher())
				.category(bookReqDto.getCategory())
				.unitsInStock(bookReqDto.getUnitsInStock())
				.releaseDate(bookReqDto.getReleaseDate())
				.bookCondition(BookConditionEnum.valueOf(bookReqDto.getBookCondition()))
				.bookImageUrl(savedImgFileUrl)
				.fileName(originalFileName)
				.user(loginUser)
				.createdDate(LocalDateTime.now())
				.build();
	}
	
	// 2-16. 주문 후 재고 감소(더티 체킹)
	public void updateStock(int count) {
		this.unitsInStock = unitsInStock - count;
	}
	
	
}
