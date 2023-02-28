package com.jojodu.book.spirngboot.web;

import com.jojodu.book.spirngboot.config.auth.LoginUser;
import com.jojodu.book.spirngboot.config.auth.dto.SessionUser;
import com.jojodu.book.spirngboot.service.posts.PostsService;
import com.jojodu.book.spirngboot.web.dto.PostsResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;

@RequiredArgsConstructor // @RequiredArgsConstructor는 초기화 되지않은 필드나 nonnull이 붙은 필드에 대해 생성자 생성
@Controller
public class IndexController {
    private final PostsService postsService; // RequiredArgsConstructor사용해서 빨간불 안들어옴

    @GetMapping("/")
    public String index(Model model, @LoginUser SessionUser user, @RequestHeader("host") String host) {
        model.addAttribute("posts", postsService.findAllDesc());
        if (user != null) {
            model.addAttribute("users", user);
        }
        System.out.println(host);
        return "index";
    }

    @GetMapping("/posts/save")
    public String postsSave(Model model, @LoginUser SessionUser user) {
        //Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (user != null) {
            model.addAttribute("users", user);
        }
/*
        System.out.println(authentication.getName());
        System.out.println(authentication.getDetails());
        System.out.println(authentication.getAuthorities());
        System.out.println(authentication.getPrincipal());*/

        return "posts-save";
    }

    @GetMapping("/posts/update/{id}")
    public String postsUpdate(@PathVariable Long id, Model model) {
        PostsResponseDto dto = postsService.findById(id);
        model.addAttribute("post", dto);

        return "posts-update";
    }
}
