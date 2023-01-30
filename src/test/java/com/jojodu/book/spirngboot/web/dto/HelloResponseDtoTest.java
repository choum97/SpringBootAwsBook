package com.jojodu.book.spirngboot.web.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
public class HelloResponseDtoTest {

    @Test
    public void Lombok_Function_Test() {
        // given
        String name = "test";
        int amount = 1000;

        // when
        HelloResponseDto dto = new HelloResponseDto(name, amount);

        // then
        assertThat(dto.getName()).isEqualTo(name);  //assertThat : 검증 메소드임, 메소드를 인자값으로 받음
        assertThat(dto.getAmount()).isEqualTo(amount);// isEqualTo : 값 비교해서 같을 때만 성공
    }

}
