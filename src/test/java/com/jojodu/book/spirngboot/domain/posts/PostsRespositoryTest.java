package com.jojodu.book.spirngboot.domain.posts;


import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import static org.assertj.core.api.Java6Assertions.assertThat;

import java.util.List;

//JUnit5로 넘어오면서 @RunWith는 @ExtendWith로 변환하게 되었습니다.
//@RunWith(SpringRunner.class) ->
@ExtendWith(SpringExtension.class)
@SpringBootTest
public class PostsRespositoryTest {

    @Autowired
    PostsRepository postsRepository;

    @AfterEach //junit5는 이거로 , 4가 After만 쓰는듯, 테스트간 데이터 침범 막기위해서 쓴다함
    public void cleanup(){
        postsRepository.deleteAll();
    }

    @Test
    public void 게시글_저장_불러오기() {
        // given
        String title = "테스트 게시글";
        String content = "테스트 본문";

        //테이블 posts에 insert/update 쿼리 실행, id값이 있다면 update 없다면 insert 쿼리 실행
        postsRepository.save(Posts.builder()
                .title(title)
                .content(content)
                .author("choum97@gmail.com")
                .build());

        // when
        List<Posts> postsList = postsRepository.findAll(); // findAll() : 모든 데이터를 조회해오는 메소드

        // then
        Posts posts = postsList.get(0);

        assertThat(posts.getTitle()).isEqualTo(title);
        assertThat(posts.getContent()).isEqualTo(content);
    }
}
