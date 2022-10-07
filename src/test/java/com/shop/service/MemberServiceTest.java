package com.shop.service;

import com.shop.constant.Role;
import com.shop.entity.Member;
import com.shop.repository.MemberRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

//@ActiveProfiles("testdb")
@SpringBootTest
class MemberServiceTest {

    private final Logger log = LoggerFactory.getLogger(MemberServiceTest.class);

    private final MemberRepository memberRepository;
    private final MemberService memberService;

    MemberServiceTest(@Autowired MemberRepository memberRepository, @Autowired MemberService memberService) {
        this.memberRepository = memberRepository;
        this.memberService = memberService;
    }

    @Test
    @DisplayName("회원 저장 테스트")
    void saveMemberTest() {
        long previousMember = memberRepository.count();
        Member member = Member.of("치즈컵", "cheesecup@gmail.com", "cheesecup1234", "전라남도 여수시 장군산길", Role.ADMIN);
        memberService.saveMember(member);
        long afterMember = memberRepository.count();

        assertThat(afterMember).isEqualTo(previousMember + 1);
    }

    @Test
    @DisplayName("중복 회원 가입 테스트")
    void saveDuplicateMemberTest() {
        Member member = Member.of("치즈컵", "kim@gmail.com", "cheesecup1234", "전라남도 여수시 장군산길", Role.ADMIN);
        Throwable e = assertThrows(IllegalStateException.class, () -> memberService.saveMember(member));

        assertThat(e.getMessage()).isEqualTo("이미 가입된 회원입니다.");
    }
}