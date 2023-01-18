package com.jojodu.book.spirngboot.web.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

//Getter : 선언된 모든 필드 get 메소드 생성
//RequiredArgsConstructor : 선언된 모든 final필드가 포함된 생성자를 생성, final이 없는 필드는 생성자 포함 안함
@Getter
@RequiredArgsConstructor
public class HelloResponseDto {
    private final String name;
    private final int amount;
}
