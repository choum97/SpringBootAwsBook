package com.jojodu.book.spirngboot.service.posts;
import com.jojodu.book.spirngboot.domain.posts.PostsRepository;
import com.jojodu.book.spirngboot.web.dto.PostsSaveRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import javax.transaction.Transactional;

//RequiredArgsConstructor : 생성자를 스프링 @Autowired와 동일한 효과라서 해결해줌
//                          생성자를 직접 안쓰고 롬복 어노테이션 사용이유 : 의존성관계 변경시 생성자 코드를 수정 안하려고
@RequiredArgsConstructor
@Service
public class PostsService {
    private final PostsRepository postsRepository;

    @Transactional
    public Long save(PostsSaveRequestDto requestDto){
        return postsRepository.save(requestDto.toEntity()).getId();
    }

}
