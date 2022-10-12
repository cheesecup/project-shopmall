package com.shop.entity;

import com.shop.constant.Role;
import com.shop.repository.MemberRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.EntityNotFoundException;
import javax.persistence.PersistenceContext;

@SpringBootTest
@Transactional
class MemberTest {

    @PersistenceContext
    EntityManager em;
    private final MemberRepository memberRepository;
    public MemberTest(@Autowired MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }

    @Test
    @DisplayName("Auditor 테스트")
    @WithMockUser(username = "cheesecup", roles = "USER")
    void auditorTest() {
        Member member = Member.of("어디터", "auditor@auditor.com", "12341234", "어디터 집 주소", Role.USER);
        Member savedMember = memberRepository.save(member);

        em.flush();
        em.clear();

        Member findMember = memberRepository.findById(savedMember.getId()).orElseThrow(() -> new EntityNotFoundException());
        System.out.println("createdBy = " + findMember.getCreatedBy());
        System.out.println("regTime = " + findMember.getRegTime());
    }
}