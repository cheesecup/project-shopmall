package com.shop.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;

@Getter
@Setter
public class MemberFormDto {

    @NotBlank(message = "이름을 입력해 주세요.")
    private String name;

    @NotEmpty(message = "이메일을 입력해 주세요.")
    @Email(message = "이메일 형식이 아닙니다.")
    private String email;

    @NotEmpty(message = "비밀번호를 입력해 주세요.")
    @Length(min = 8, max = 15, message = "비밀번호는 8자 이상, 15자 이하로 입력해주세요.")
    private String password;

    @NotEmpty(message = "주소를 입력해 주세요.")
    private String address;

    public MemberFormDto() {
    }

    @Builder
    public MemberFormDto(String name, String email, String password, String address) {
        this.name = name;
        this.email = email;
        this.password = password;
        this.address = address;
    }
}
