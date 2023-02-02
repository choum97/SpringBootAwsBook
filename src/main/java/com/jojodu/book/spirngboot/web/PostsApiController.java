package com.jojodu.book.spirngboot.web;

import com.jojodu.book.spirngboot.service.posts.PostsService;
import com.jojodu.book.spirngboot.web.dto.PostsResponseDto;
import com.jojodu.book.spirngboot.web.dto.PostsSaveRequestDto;
import com.jojodu.book.spirngboot.web.dto.PostsUpdateRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
public class PostsApiController {
    private final PostsService postsService;

    /*
    *REST에서 CRUD는 다음과 같은 HTTP 메소드에 매핑된다 함
    * 생성 : POST
    * 읽기 : GET
    * 수정 : PUT
    * 삭제 : DELETE
     */
    @PostMapping("/api/v1/posts")
    public Long save(@RequestBody PostsSaveRequestDto requestDto){
        return postsService.save(requestDto);
    }

    @PutMapping("/api/v1/posts/{id}")
    public Long update(@PathVariable Long id, @RequestBody PostsUpdateRequestDto requestDto) {
        return postsService.update(id, requestDto);
    }

    @GetMapping("/api/v1/posts/{id}")
    public PostsResponseDto findById(@PathVariable Long id) {
        return postsService.findById(id);
    }

    @DeleteMapping("/api/v1/posts/{id}")
    public Long delete(@PathVariable Long id) {
        postsService.delete(id);

        return id;
    }

}
