package com.jojodu.book.spirngboot.domain.likes;

import com.jojodu.book.spirngboot.domain.posts.Posts;
import com.jojodu.book.spirngboot.domain.user.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;


public interface PostLikeRepository extends JpaRepository<PostLike, Long> {

/*    List<PostLike> findByPostAndUser(Long posts, Long user);

    List<PostLike> findByPost(Posts posts);

    Long countByPost(Posts posts);*/
}
