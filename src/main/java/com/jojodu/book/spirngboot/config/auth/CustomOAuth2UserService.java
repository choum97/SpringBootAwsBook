package com.jojodu.book.spirngboot.config.auth;

import com.jojodu.book.spirngboot.config.auth.dto.OAuthAttributes;
import com.jojodu.book.spirngboot.config.auth.dto.SessionUser;
import com.jojodu.book.spirngboot.domain.user.User;
import com.jojodu.book.spirngboot.domain.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpSession;
import java.util.Collections;

@RequiredArgsConstructor
@Service
public class CustomOAuth2UserService implements OAuth2UserService<OAuth2UserRequest, OAuth2User> {
    private final UserRepository userRepository;
    private final HttpSession httpSession;

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        OAuth2UserService<OAuth2UserRequest, OAuth2User> delegate = new DefaultOAuth2UserService();
        OAuth2User oAuth2User = delegate.loadUser(userRequest);

        String registrationId = userRequest.getClientRegistration().getRegistrationId(); //registrationId :현재 로그인 진행중인 서비스를 구분하는 코드 , 구글은 불필요하나 네이버 연동시 둘을 구분하기 위함
        //userNameAttributeName : OAuth2 로그인 진행시 키가 되는 필드값을 가르킴 프라이머리키와 같은 의미
        //                        구글의 경우 기본적 코드 지원하나 네이버 카카오 지원 x, 구글 기본 코드는 sub, 네이버 구글 로그인을 동시 지원시 사용
        String userNameAttributeName = userRequest.getClientRegistration().getProviderDetails()
                .getUserInfoEndpoint().getUserNameAttributeName();

        //OAuthAttributes : OAuth2UserService를 통해 가져온 OAuth2User의 attribute를 담을 클래스 , 다른 소셜로그인도 이 클래스 사용한다함
        OAuthAttributes attributes = OAuthAttributes.of(registrationId, userNameAttributeName, oAuth2User.getAttributes());
        User user = saveOrUpdate(attributes);

        // SessionUser : 사용자 정보 저장하기위한 DTO클래스
        //  User 클래스를 사용하면 안되는지?
        // 1. 직렬화 구현하지 않았다는 에러 발생함
        // 2. User클래스가 엔티티이기에
        // 3. 엔티티 클래스에는 다른 엔티티와 관계가 형성될지 모르고,
        //    자식 엔티티를 갖고 있다면 직렬화 대상에 자식까지 포함되기에 성능이슈, 부수효과가 발생할 수 있음
        // 4. 따라서 직렬화 기능을 가진 세션 DTO를 하나 추가로 만드는 것이 운영 및 유지보수에 도움이 된다 함

        httpSession.setAttribute("user", new SessionUser(user));

        return new DefaultOAuth2User(Collections.singleton(new SimpleGrantedAuthority(user.getRoleKey())), attributes.getAttributes(), attributes.getNameAttributeKey());
    }


    private User saveOrUpdate(OAuthAttributes attributes) {
        User user = userRepository.findByEmail(attributes.getEmail())
                .map(entity -> entity.update(attributes.getName(), attributes.getPicture()))
                .orElse(attributes.toEntity());
        return userRepository.save(user);
    }

}
