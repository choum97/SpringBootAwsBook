package com.jojodu.book.spirngboot.web.dto;

import com.jojodu.book.spirngboot.domain.posts.Posts;
import com.jojodu.book.spirngboot.domain.user.Member;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class PostsSaveRequestDto {
    private String title;
    private String content;
    private Member author;

    @Builder
    public PostsSaveRequestDto(String title, String content, Member author){
        this.title = title;
        this.content = content;
        this.author = author;
    }

    public Posts toEntity(){
        return Posts.builder()
                .title(title)
                .content(content)
                .member(author)
                .build();
    }
}
