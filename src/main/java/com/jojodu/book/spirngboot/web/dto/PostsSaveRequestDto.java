package com.jojodu.book.spirngboot.web.dto;

import com.jojodu.book.spirngboot.domain.posts.Posts;
import com.jojodu.book.spirngboot.domain.member.Member;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class PostsSaveRequestDto {
    private String title;
    private String content;


    @Builder
    public PostsSaveRequestDto(String title, String content, Member member){
        this.title = title;
        this.content = content;

    }

    public Posts toEntity(Member author){
        return Posts.builder()
                .title(title)
                .content(content)
                .member(author)
                .build();
    }

}
