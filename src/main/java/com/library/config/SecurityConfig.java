package com.library.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import com.library.constant.user.UserEnum;
import com.library.domain.user.UserRepository;
import com.library.config.jwt.filter.JwtAuthorizationFilter;
import com.library.config.jwt.service.JwtService;
import com.library.config.jwt.filter.JwtAuthenticationFilter;

import lombok.RequiredArgsConstructor;

@Configuration  // 1-1. IOC에 SecurityConfig 파일을 등록.
@EnableWebSecurity  // 1-2. 시큐리티 활성화 -> 기본 스프링 필터체인에 등록, 주의 : SecurityConfig를 테스트할땐 @EnableWebSecurity 주석
@RequiredArgsConstructor
public class SecurityConfig {
	
	// 1-3. @Slf4j 어노테이션으로 사용해도 된다.
	private final Logger log = LoggerFactory.getLogger(getClass());
	
	// 3-1. AuthenticationConfiguration 객체 만들기 1
	private AuthenticationConfiguration configuration; 
		
	// 3-4. 
	private final JwtService jwtService;
	
	// 1-4. 비밀번호 해시 -> 참고 : @Bean 어노테이션은 상위 클래스에 @Configuraion 어노테이션이 붙어 있는 곳에서만 동작
	@Bean 
	public BCryptPasswordEncoder encode() throws Exception {
		log.info("BCryptPasswordEncoder 빈 등록 완료.");
		return new BCryptPasswordEncoder();
	}
	
	// 3-2. WebSecurityConfigurerAdapter를 상속해서 AuthenticationManager를 bean으로 등록했던걸 직접 등록. -> 이걸 등록해주면 콘솔에 비밀번호 뜨는게 안뜬다.
	@Bean
	public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
		this.configuration = authenticationConfiguration;  // 3-3. authenticationManager로 전달되는 AuthenticationConfiguration을 셋팅(변수명 겹치는거 유의)
		return configuration.getAuthenticationManager();
	}
	
	// 4-1. 
	private final UserRepository userRepository;
		
	@Bean // 1-5. SecurityFilterChain을 반환하는 filterChain 메소드를 커스텀으로 만들어 구현 : 아직 기본 시큐리티 동작중
	public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
		// 1-6. 1-5로 @Bean으로 등록하면 콘솔 로그에 해당 로그 찍히는거 확인 : 아직 기본 시큐리티 동작중
		log.info("filterChain 빈 등록 완료");
		
		// 1-7. 커스텀 시큐리티 설정 시작
		return http
				.headers( (header) -> header.frameOptions( (frameOption) -> frameOption.disable() ) )  // 1-8. http iframe 허용 X
				.csrf( (csrf) -> csrf.disable() )  // 1-9. csrf(Cross-Site Request Forgery, 교차 사이트 요청 위조) 비활성화(postman요청이든 웹페이지에서 요청한거든 구분하지 않겠다)
				.cors( (cors) -> cors.configurationSource(configurationSource()) )  // 1-10. custom cors(Cross Origin Resource Sharing, 교차 출처 리소스 공유) 설정 등록 : HTTP 헤더를 사용하여 서로 다른 출처(도메인, 프로토콜, 포트)에 있는 웹 페이지나 서버가 서로의 자원에 접근할 수 있도록 허용하는 보안 메커니즘
				.sessionManagement( (sessionManagement) -> sessionManagement.sessionCreationPolicy(SessionCreationPolicy.STATELESS) )  // 1-11. jSessionId를 서버에서 관리하지 않는다는 뜻으로, 세션 응답이 종료되면 사라진다는 뜻.(무상태성 설정) : jwt인증을 구현할거기 때문에 추가
				.formLogin( (formLogin) -> formLogin.disable() )  // 1-12. 폼 로그인 방식을 사용하지 않는다고 선언
				.httpBasic( (httpBasic) -> httpBasic.disable() )  // 1-13. httpSecurity가 제공하는 기본인증 기능 disable(브라우저가 팝업창을 하나 띄워 인증을 진행하는걸 막음)
				.authorizeHttpRequests(   // 1-14. 인증 Request를 정의(이 설정이 들어가면 org.springframework.web.util.pattern.PatternParseException이 발생하는데 application.properties에 설정하나를 더 넣어줘야한다.(requestMatchers의 default를 antMatcher로 바꿔줘야한다. 스프링부트 3점대 버전부터 antMatcher가 default가 아니라 path_pattern_parser라서 모든 요청이 restAPI형태로만 들어와야됨)
		  				  				  // 그래서 application.properties에 spring.mvc.pathmatch.matching-strategy=ant_path_matcher 추가해줘야 정상동작.
						(authorizeReqeust) -> authorizeReqeust.requestMatchers("/api/**/s/**").authenticated()  // 1-15. /api/** 형태로 들어오는 url은 인증이 필요하다.
							   				  .requestMatchers("/api/admin/**").hasRole(""  + UserEnum.ADMIN)  // 1-16. /api/admin/**을 호출하기 위해선 설정된 Role이 필요하다.
							   				  .anyRequest()  // 1-17. 1-15, 1-16가 아닌 요청은			
							   				  .permitAll()  // 1-18. 모두 허용
				)
				.addFilterAt(new JwtAuthenticationFilter(authenticationManager(configuration), jwtService), UsernamePasswordAuthenticationFilter.class) // 1-19. 폼로그인을 사용하지 않기 때문에 UsernamePasswordAuthenticationFilter 재정의한 JwtAuthenticationFilter를 등록헤서 인증처리를 진행한다.
				.addFilterBefore(new JwtAuthorizationFilter(authenticationManager(configuration), userRepository, jwtService), UsernamePasswordAuthenticationFilter.class)  // 4-2. 권한 관리 필터 등록. -> SecurityFilterChain 앞에 addFilterBefore로 필터를 등록.
				.build();
	}
	
	public CorsConfigurationSource configurationSource() {  // 2-1. CorsConfigurationSource로 cors 설정 -> 기존엔 filter로 만들어서 등록했었는데 이번엔 CorsConfigurationSource 객체를 이용해 등록.
		log.info("CorsConfigurationSource cors 설정 생성 후 SecurityFilterChain에 등록 후 실행 중");
		
		CorsConfiguration configuration = new CorsConfiguration();
		configuration.addAllowedHeader("*");  // 2-2. 모든 header 응답 허용
		configuration.addAllowedMethod("*");  // 2-3. GET, POST, PUT, DELETE 허용
		configuration.addAllowedOriginPattern("*");  // 2-4. 모든 IP 주소 허용
		configuration.setAllowCredentials(true);  // 2-5. 클라이언트쪽에서 쿠키 요청하는걸 허용(사용자 자격 증명이 지원되는지 여부)
		configuration.addExposedHeader("Authorization");  // 2-6. 브라우저 버전이 바뀌면 default가 아닐 수도 있기 때문에 넣어준다.(2023-07-29)
		
		// 2-7. UrlBasedCorsConfigurationSource 객체 생성
		UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
		source.registerCorsConfiguration("/**", configuration);  // 2-8. 모든 주소 요청시 CorsConfiguration 설정을 적용.
	
		return source;
	}

}
