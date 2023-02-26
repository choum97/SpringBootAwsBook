package com.jojodu.book.spirngboot.domain.posts;

import com.jojodu.book.spirngboot.domain.BaseTimeEntity;
import com.jojodu.book.spirngboot.domain.likes.PostsLike;
import com.jojodu.book.spirngboot.domain.member.Member;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

//Posts는 실제 DB 테이블과 매칭될 클래스, Entity클래스라고도 함
/*
* Entity : 테이블과 링크될 클래스임을 나타냄, 언더스코어 네이밍으로 한다함
*          Entity클래스는 Setter 메소드 만들지 않는다함
*          책에서는 값 변경이 필요하면 명확한 목적과 의도 알수 있는 메소드 추가를 권고함 - 92page 예시 있음
* GeneratedValue : PK 생설 규칙 나타냄
* Column : 굳이 선언 안해도 해당 클래스 필드는 모두 컬럼, 문자열은 기본 Varchar(255)가 기본
* */

@NoArgsConstructor
@Entity
@Getter
public class Posts extends BaseTimeEntity {
    
    // 부트 2.0버전에서는 GenerationType.IDENTITY해야 오토인크리먼트 된다함
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "posts_id")
    private Long id; // 해당 테이블의 PK필드

    @Column(length = 500, nullable = false)
    private String title;

    @Column(columnDefinition = "TEXT", nullable = false)
    private String content;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "member_id")
    private Member member;

    //mappedBy = "posts" : 외래키를 갖는 쪽 즉, 연관관계의 주인이 되는 쪽을 정해주는 것
    //OneToMany 관계에서 fetch = FetchType.LAZY는 기본옵션이지만 명시해주는 것을 권장함
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "posts", cascade = CascadeType.ALL)
    private List<PostsLike> postsLikes = new ArrayList<>();

    @Builder
    public Posts(String title, String content, Member member) {
        this.title = title;
        this.content = content;
        this.member = member;
    }

    public void update(String title, String content) {
        this.title = title;
        this.content = content;
    }

    public String getAuthor() {
        return member.getEmail();
    }
}
