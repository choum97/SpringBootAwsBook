package com.jojodu.book.spirngboot.domain.likes;

import org.springframework.data.jpa.repository.JpaRepository;


public interface PostsLikeRepository extends JpaRepository<PostsLike, Long> {

/*    List<PostLike> findByPostAndUser(Long posts, Long user);

    List<PostLike> findByPost(Posts posts);

    Long countByPost(Posts posts);*/
}
