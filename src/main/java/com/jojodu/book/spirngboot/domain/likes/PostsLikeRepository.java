package com.jojodu.book.spirngboot.domain.likes;

import com.jojodu.book.spirngboot.domain.posts.Posts;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;


public interface PostsLikeRepository extends JpaRepository<PostsLike, Long> {

    //좋아요 여부를 확인
    Optional<PostsLike> findByPostsIdAndMemberId(Long posts, Long member);

    //좋아요 수 확인
    Long countByPostsId(Long postsId);

}
