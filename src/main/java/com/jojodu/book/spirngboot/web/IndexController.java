package com.jojodu.book.spirngboot.web;

import com.jojodu.book.spirngboot.service.posts.PostsService;
import com.jojodu.book.spirngboot.web.dto.PostsResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@RequiredArgsConstructor // @RequiredArgsConstructor는 초기화 되지않은 필드나 nonnull이 붙은 필드에 대해 생성자 생성
@Controller
public class IndexController {
    private final PostsService postsService; // RequiredArgsConstructor사용해서 빨간불 안들어옴

    @GetMapping("/")
    public String index(Model model) {
        model.addAttribute("posts", postsService.findAllDesc());

        return "index";
    }

    @GetMapping("/posts/save")
    public String postsSave() {
        return "posts-save";
    }

    @GetMapping("/posts/update/{id}")
    public String postsUpdate(@PathVariable Long id, Model model) {
        PostsResponseDto dto = postsService.findById(id);
        model.addAttribute("post", dto);

        return "posts-update";
    }
}
