package com.jojodu.book.spirngboot.domain.posts;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

// Entity 클래스와 Entity Repository는 함께 있어야 한다
// Entity는 기본 Repository가 없이 재대로 역할 불가
public interface PostsRepository extends JpaRepository<Posts, Long> {

    @Query("SELECT p FROM Posts p ORDER BY p.id DESC")
    List<Posts> findAllDesc();

    //해당 게시글의 좋아요 수를 가져오는 메서드


}
