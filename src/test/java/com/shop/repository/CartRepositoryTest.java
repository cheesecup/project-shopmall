package com.shop.repository;

import com.shop.entity.Cart;
import com.shop.entity.Member;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.persistence.EntityManager;
import javax.persistence.EntityNotFoundException;
import javax.persistence.PersistenceContext;

import static org.assertj.core.api.Assertions.assertThat;


@SpringBootTest
//@Transactional
class CartRepositoryTest {

    @PersistenceContext
    EntityManager em;
    private final CartRepository cartRepository;
    private final MemberRepository memberRepository;

    CartRepositoryTest(@Autowired CartRepository cartRepository,
                       @Autowired MemberRepository memberRepository) {
        this.cartRepository = cartRepository;
        this.memberRepository = memberRepository;
    }

    @Test
    @DisplayName("장바구니 등록 테스트")
    void saveCartTest() {
        Member member = memberRepository.findById(1L).orElseThrow();
        Cart cart = Cart.of(member);
        Cart savedCart = cartRepository.save(cart);

//        em.flush();
//        em.clear();

        System.out.println("장바구니 코드 번호 : " + savedCart.getId());
        Cart findCart = cartRepository.findById(1L).orElseThrow(() -> new EntityNotFoundException());
        assertThat(member.getId()).isEqualTo(findCart.getMember().getId());
    }
}