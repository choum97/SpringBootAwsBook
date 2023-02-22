package com.jojodu.book.spirngboot.config;

import com.jojodu.book.spirngboot.config.auth.CustomOAuth2UserService;
import com.jojodu.book.spirngboot.domain.user.Role;
import lombok.RequiredArgsConstructor;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@RequiredArgsConstructor
@EnableWebSecurity //스프링 시큐리티 설정을 활성화 시킴
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    private final CustomOAuth2UserService customOAuth2UserService;


    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .csrf().disable()
                .headers()
                //.frameOptions().disable()//h2 콘솔 화면을 사용하기 위해서 disable
                .and()

                    .authorizeRequests()// 권한 관리를 설정하는 옵션의 시작점, authorizeRequests가 선언되어야 antMatchers 사용가능
                    //antMatchers : 권한관리 대상 옵션, url, http 메소드별로 관리 가능
                    .antMatchers("/", "/css/**", "/images/**", "/js/**", "/h2-console/**").permitAll() // permitAll() : 전체 열람 권한 준 것
                    .antMatchers("/api/v1/**").hasRole(Role.USER.name()) // /api/v1/** 주소를 가진 API는 user 권한을 가진 사람만 가능하도록
                    .antMatchers("/api/v1/**").hasRole(Role.GUEST.name()) // 편하게 글 수정 작성하려고 임시로 추가함
                    .anyRequest().authenticated() // 나머지 url은 모두 인증된 사용자들(로그인한 사용자들)에게만 허용 가능 / anyRequest : 설정된 값들 외 나머지 url
                .and()
                    .logout()// 로그아웃 기능에 대한 여러 설정의 진입점
                        .logoutSuccessUrl("/") //로그아웃 성공시 "/" 주소로 이동
                .and()
                    .oauth2Login() // OAuth2 로그인 기능에 대한 진입점
                        .userInfoEndpoint() // OAuth2 로그인 성공 이후 사용자 정보를 가져올때의 설정 담당
                            .userService(customOAuth2UserService); // 소셜 로그인 성공 시 후속 조치를 진행할 UserService 인터페이스의 구현체를 등록
    }
}
