package com.jojodu.book.spirngboot.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

// @Configuration : 설정 정보 또는 외부 라이브러리를 빈으로 등록해서 사용해야 한다면
// 직접 Config 클래스를 만들어 Configuration과 Bean 어노테이션을 명시해 빈으로 등록해야 하는 경우 사용
@Configuration
@EnableJpaAuditing
public class JpaConfig {

}
