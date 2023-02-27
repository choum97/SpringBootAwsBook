package com.jojodu.book.spirngboot.config.auth;

import com.jojodu.book.spirngboot.config.auth.dto.SessionUser;
import lombok.RequiredArgsConstructor;
import org.springframework.core.MethodParameter;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import javax.servlet.http.HttpSession;


/*
@Component : Spring에서 관리되는 객체임을 표시하기 위해 사용하는 가장 기본적인 annotation
             객체를 만드는 가장 기본적인 어노테이션
             컴포넌트 스캔의 대상이 되어 어플리케이션 컨텍스트에 스프링 빈으로 등록됨
@Controller : Presentation Layer, mvc패턴에서 컨트롤러 역할 하는 클래스에 사용
@Service : 	Business Layer
@Repository : Persistence Layer, 영속성을 가지는 속성(파일, 데이터베이스 등), DB에 접근하는 코드
* */
@RequiredArgsConstructor
@Component
public class LoginUserArgumentResolver implements HandlerMethodArgumentResolver {
    private final HttpSession httpSession;

    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        boolean isLoginUserAnnotation = parameter.getParameterAnnotation(LoginUser.class) != null;
        boolean isUserClass = SessionUser.class.equals(parameter.getParameterType());
        return isLoginUserAnnotation && isUserClass;
    }

    @Override
    public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer, NativeWebRequest webRequest, WebDataBinderFactory binderFactory) throws Exception {
        return httpSession.getAttribute("user");
    }
}
