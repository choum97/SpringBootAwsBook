package com.jojodu.book.spirngboot.service.posts;

import com.jojodu.book.spirngboot.domain.posts.Posts;
import com.jojodu.book.spirngboot.domain.posts.PostsRepository;
import com.jojodu.book.spirngboot.web.dto.PostsListResponseDto;
import com.jojodu.book.spirngboot.web.dto.PostsResponseDto;
import com.jojodu.book.spirngboot.web.dto.PostsSaveRequestDto;
import com.jojodu.book.spirngboot.web.dto.PostsUpdateRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

//import javax.transaction.Transactional; 아래로 변경, readOnly = true 사용 가능, 어노테이션 옵션 허용안해서 그런거라 함
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

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

        return new PostsResponseDto(entity);
    }
    
    @Transactional(readOnly = true) // readOnly = true : 조회 속도 개선, 등록 수정 삭제 기능이 없는 서비스 메소드에 추천
    public List<PostsListResponseDto> findAllDesc() {
        return postsRepository.findAllDesc().stream()
                .map(PostsListResponseDto::new)
                .collect(Collectors.toList());
    }
}
