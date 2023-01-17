package com.jojodu.book.spirngboot.web.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

//Getter : 선언된 모든 필드 get 메소드 생성
//RequiredArgsConstructor :
@Getter
@RequiredArgsConstructor
public class HelloResponseDto {
    private final String name;
    private final int amount;
}
