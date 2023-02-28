package com.jojodu.book.spirngboot.web.dto;

import com.jojodu.book.spirngboot.domain.posts.Posts;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class PostsResponseDto {
    private Long id;
    private String title;
    private String content;
    private String authorName;
    private String authorPicture;
    private LocalDateTime createdDate;
    private Long likesCount;
    private boolean likedByMe;

    public PostsResponseDto(Posts posts, Long likesCount, boolean likedByMe) {
        this.id = posts.getId();
        this.title = posts.getTitle();
        this.content = posts.getContent();
        this.authorName = posts.getMember().getName();
        this.authorPicture = posts.getMember().getPicture();
        this.createdDate = posts.getCreatedDate();
        this.likesCount = likesCount;
        this.likedByMe = likedByMe;
    }

}
