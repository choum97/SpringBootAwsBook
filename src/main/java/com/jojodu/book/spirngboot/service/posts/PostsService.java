package com.jojodu.book.spirngboot.service.posts;

import com.jojodu.book.spirngboot.domain.likes.PostsLike;
import com.jojodu.book.spirngboot.domain.likes.PostsLikeRepository;
import com.jojodu.book.spirngboot.domain.member.Member;
import com.jojodu.book.spirngboot.domain.member.MemberRepository;
import com.jojodu.book.spirngboot.domain.posts.Posts;
import com.jojodu.book.spirngboot.domain.posts.PostsRepository;
import com.jojodu.book.spirngboot.web.dto.PostsResponseDto;
import com.jojodu.book.spirngboot.web.dto.PostsSaveRequestDto;
import com.jojodu.book.spirngboot.web.dto.PostsUpdateRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

//import javax.transaction.Transactional; 아래로 변경, readOnly = true 사용 가능, 어노테이션 옵션 허용안해서 그런거라 함
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/*
Lombok : 자바에서 @Getter, @Setter 같은 annotation 기반으로 멤버 변수에 대한
         Getter/Setter Method, Equals(), hashCode(), ToString(), 생성자 등을 자동으로 생성해 주는 라이브러리
         필요에 맞게 사용해야 한다고 함

 - DI(의존성 주입) 방식은 3가지 -
1. 필드 주입   (Field Injection)
2. 수정자 주입 (Setter Injection)
3. 생성자 주입 (Constructor Injection)

 - 생성자 주입 방식 -
 생성자를 스프링 @Autowired와 동일한 효과라서 해결해줌
 생성자를 직접 안쓰고 롬복 어노테이션 사용이유 : 의존성관계 변경시 생성자 코드를 수정 안하려고, Lombok을 사용하면 생성자도 자동으로 생성 가능
 @Autowired가 아닌 RequiredArgsConstructor 권장
 장점 : 객체 변이 방지 (final 가능), 생성자 호출시점에 딱 1번만 호출(불변)
       순환참조에 대한 안전성(방지): 순환참조시 Exception 발생, 순환참조일 경우 실제코드가 호출될 때까지 문제를 알 수 없다고 함
       주입 데이터를 누락했을때 컴파일 시점에 컴파일 오류가 발생
 @NoArgsConstructor : 파라미터가 없는 기본 생성자를 생성, @NoArgsConstructor(force=true)사용하면 null, 0 등 기본 값으로 초기화
 @RequiredArgsConstructor : final이나 @NonNull인 필드 값만 파라미터로 받는 생성자 생성
 @AllArgsConstructor : 모든 필드에 대한 생성자 생성

참고 @Data :  @Getter, @Setter, @RequiredArgsConstructor, @ToString, @EqualsAndHashCode을 한꺼번에 설정 가능,
              발생 가능한 문제점 : setter가 안전하지 못하다고함, 객체를 언제든 수정 가능하기에 안전성 보장받기가 힘들다고
                                tosrting dto에 양방향 연관관계에서 순환참조 문제 발생
--
@Autowired)
Autowired는 필드주입방식의 의존성 관리 방법
 생성자가 1개만 있을 경우에 @Autowired를 생략해도 주입이 가능하도록 Spring 프레임워크에서 지원
 장점 : 편리하단 것
 단점 : final옵션을 사용 x,
       DI가 타입이 같은 빈이 발견되면 그냥 주입 객체가 여러개일 경우 문제,
       순환참조의 가능성,
       프레임워크에 의존적이고 객체지향적으로 좋지 않다고 함
지정방법 : @Qualifier("빈 이름"), @Primary 사용
       
@Resource :
 Field, Setter Method에 사용 가능, 생성자에는 불가능, name 속성을 사용해서 의존성 주입받을 Bean을 지정
 기본적으로 참조 변수의 이름과 동일한 빈이 존재하면 해당 빈을 주입
 Java9 이후부터는 삭제되어 사용 불가
 name(이름)으로 Bean을 찾지 못하면 Type을 기준으로 의존성 주입
 -> 호출하지 말아야 할 메서드를 호출하게 될 가능성,
    따라서 의존 관계가 실행 중에 동적으로 변경되기 때문에 동적으로 변경되지 않도록 생성자를 통한 의존성 주입을 권장
지정방법 : @Resource(name="빈 이름")

@Inject : Java에서 지원하는 어노테이션, 특정 프레임 워크에 종속적이지 않다
          @Aurowired와 동일하게 작동하지만 찾는 순서가 다르다고 함
          Field, Setter Method, 생성자에 사용 가능

          이름을 기준으로 의존성을 주입할 때 @Qualifier을 사용해서 주입될 빈 지정 가능
          동일한 타입의 빈이 여러 개 존재할 경우 @Primary을 사용해서 주입될 빈 지정 가능
          이름을 기준으로 의존성을 주입할 때 @Named을 사용해서 주입될 빈 지정 가능

@Qualifier("") : 주입대상이 한개여야 하는데 두개 이상이 존재할 때 에러가 발생함, Autowired를 동일한 타입에 쓴곳이 있다면
                그래서 한정자를 설정해줘서 해결 가능함
--
수정자 주입 방식 : 생성자 주입 방식과는 다르게 주입받는 객체가 변경될 가능성이 있는 경우에 사용,
                변경 가능성을 배제하고 불변성을 보장하는게 좋다고 함
*/
@RequiredArgsConstructor
@Service
public class PostsService {
    private final PostsRepository postsRepository;
    private final MemberRepository memberRepository;
    private final PostsLikeRepository postsLikeRepository;

    @Transactional
    public Long save(PostsSaveRequestDto requestDto){
        return postsRepository.save(requestDto.toEntity()).getId();
    }

    @Transactional
    public Long update(Long id, PostsUpdateRequestDto requestDto) {
        Posts posts = postsRepository.findById(id)
                        .orElseThrow(() -> new IllegalArgumentException("해당 게시글이 없습니다. id : " + id));

        posts.update(requestDto.getTitle(), requestDto.getContent());

        return id;
    }

    public PostsResponseDto findById(Long id) {
        Posts entity = postsRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("해당 게시글이 없습니다. id : " + id));

        return new PostsResponseDto(entity, postsLikeRepository.countByPostsId(id) ,checkLikedByMe(id));
    }
    
    @Transactional(readOnly = true) // readOnly = true : 조회 속도 개선, 등록 수정 삭제 기능이 없는 서비스 메소드에 추천
    public List<PostsResponseDto> findAllDesc() {
/*        return postsRepository.findAllDesc().stream()
                .map(PostsListResponseDto::new)
                .collect(Collectors.toList());*/
        List<Posts> postsList = postsRepository.findAll();
        List<PostsResponseDto> postsResponseDtoList = new ArrayList<>();

        for (Posts posts : postsList) {
            Long likesCount = postsLikeRepository.countByPostsId(posts.getId());
            boolean likedByMe = checkLikedByMe(posts.getId());

            PostsResponseDto postsResponseDto = new PostsResponseDto(posts, likesCount, likedByMe);
            postsResponseDtoList.add(postsResponseDto);
        }

        return postsResponseDtoList;
    }

    @Transactional
    public void delete(Long id){
        //orElseThrow : 코드 가독성 향상
        //              https://velog.io/@chiyongs/orElseThrow
        Posts posts = postsRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("해당 게시글이 없습니다. id = " + id));

        // 예외 처리 하는 방법
        // RuntimeException : 모든 예외 처리 가능, 세분화 불가
        // IllegalArgumentException : 어떤 에러 발생해도 내가 정의한 에러로 던지기 가능
        // 다른방법 : 커스텀 익셉션으로, 모든 익셉션에 대해서 함수 작성
        // 출처 : https://www.saichoiblog.com/exception/


        //JPARepository에서 이미 delete 메소드 지원해서 활용 한 것이라 함
        //존재하는 posts인지 확인을 위해 엔티티조회 후 그대로 삭제
        //deleteById 메소드 이용하면 id로 삭제 가능
        postsRepository.delete(posts);
    }

    private boolean checkLikedByMe(Long postsId) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (!(authentication instanceof AnonymousAuthenticationToken)) {
            Member member = memberRepository.findByEmail(authentication.getName())
                    .orElseThrow(() -> new IllegalArgumentException("해당 사용자가 없습니다. email=" + authentication.getName()));

            Optional<PostsLike> postsLike = postsLikeRepository.findByPostsIdAndMemberId(postsId, member.getId());
            return postsLike.isPresent();
        }
        return false;
    }

}
