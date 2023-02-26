package com.jojodu.book.spirngboot.domain.member;


import com.jojodu.book.spirngboot.domain.BaseTimeEntity;
import com.jojodu.book.spirngboot.domain.posts.Posts;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
@Getter
@Entity
public class Member extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "member_id")
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String email;

    @Column
    private String picture;

    //Enumerated : JPA로 DB에 저장시 Enum 값을 어떤형태로 저장할지
    //             기본적으로 int로 된 숫자, 숫자로 저장시 의미 파악할 수가 없어서 문자열로 저장될 수 있도록 선언한 것
    @Enumerated(EnumType.STRING) 
    @Column(nullable = false)
    private Role role;

    @OneToMany(fetch = FetchType.LAZY, mappedBy="member", cascade = CascadeType.ALL)
    private List<Posts> posts = new ArrayList<Posts>();

    @Builder
    public Member(String name, String email, String picture, Role role) {
        this.name = name;
        this.email = email;
        this.picture = picture;
        this.role = role;
    }

    public Member update(String name, String picture) {
        this.name = name;
        this.picture = picture;
        return this;
    }

    public String getRoleKey() {
        return this.role.getKey();
    }

}
