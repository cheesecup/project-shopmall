package com.shop;

import com.shop.dto.MemberFormDto;
import com.shop.entity.Member;
import com.shop.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.annotation.PostConstruct;

public class DataInit {

    private final PasswordEncoder passwordEncoder;
    private final MemberService memberService;


    public DataInit(@Autowired PasswordEncoder passwordEncoder,
                    @Autowired MemberService memberService) {
        this.passwordEncoder = passwordEncoder;
        this.memberService = memberService;
    }

    @PostConstruct
    public void init() {
        
        // 사용자 계정 생성
        MemberFormDto memberFormDto1 = MemberFormDto.builder()
                .name("사용자")
                .password("123456789")
                .address("사용자 집 주소")
                .email("user@test.com")
                .build();
        Member userMember = Member.createMember(memberFormDto1, passwordEncoder);
        memberService.saveMember(userMember);

        // 관리자 계정 생성
        MemberFormDto memberFormDto2 = MemberFormDto.builder()
                .name("관리자")
                .password("123456789")
                .address("관리자 집 주소")
                .email("admin@test.com")
                .build();
        Member adminMember = Member.createAdminMember(memberFormDto2, passwordEncoder);
        memberService.saveMember(adminMember);
    }
}
