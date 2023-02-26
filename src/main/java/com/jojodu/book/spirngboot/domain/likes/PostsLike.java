package com.jojodu.book.spirngboot.domain.likes;

import com.jojodu.book.spirngboot.domain.BaseTimeEntity;
import com.jojodu.book.spirngboot.domain.posts.Posts;
import com.jojodu.book.spirngboot.domain.user.Member;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@NoArgsConstructor
@Entity
@Getter
public class PostsLike extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "post_like_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "posts_id")
    private Posts posts;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    public PostsLike(Posts posts, Member member) {
        this.posts = posts;
        this.member = member;
        posts.getPostsLikes().add(this); // Posts 엔티티의 likes 필드에도 이 좋아요 정보를 추가해줍니다.
    }
}
