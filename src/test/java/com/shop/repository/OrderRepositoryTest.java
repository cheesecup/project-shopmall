package com.shop.repository;

import com.shop.constant.OrderStatus;
import com.shop.entity.Item;
import com.shop.entity.Member;
import com.shop.entity.Order;
import com.shop.entity.OrderItem;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.EntityNotFoundException;
import javax.persistence.PersistenceContext;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Transactional
class OrderRepositoryTest {

    @PersistenceContext
    EntityManager em;

    private final OrderRepository orderRepository;
    private final MemberRepository memberRepository;
    private final ItemRepository itemRepository;

    OrderRepositoryTest(@Autowired OrderRepository orderRepository,
                        @Autowired MemberRepository memberRepository,
                        @Autowired ItemRepository itemRepository) {
        this.orderRepository = orderRepository;
        this.memberRepository = memberRepository;
        this.itemRepository = itemRepository;
    }

    @Test
    @DisplayName("영속성 전이 테스트")
    void cascadeTest() {
        Member member = memberRepository.findById(1L).orElseThrow();
        Order order = Order.of(OrderStatus.ORDER, member);

        for (int i=1; i<=3; i++) {
            long id = i;
            Item item = itemRepository.findById(id).orElseThrow();
            OrderItem orderItem = OrderItem.of(i * 10000, i, order, item);
            order.getOrderItems().add(orderItem);
        }

        orderRepository.save(order);

        em.flush();
        em.clear();

        Order findOrder = orderRepository.findById(1L).orElseThrow(() -> new EntityNotFoundException("해당 주문이 없습니다."));

        assertThat(3).isEqualTo(findOrder.getOrderItems().size());
    }

    @Test
    @DisplayName("고아 객체 제거 테스트")
    void orphanRemovalTest() {
        Member member = memberRepository.findById(1L).orElseThrow();
        Order order = Order.of(OrderStatus.ORDER, member);

        for (int i=1; i<=3; i++) {
            long id = i;
            Item item = itemRepository.findById(id).orElseThrow();
            OrderItem orderItem = OrderItem.of(i * 10000, i, order, item);
            order.getOrderItems().add(orderItem);
        }

        Order savedOrder = orderRepository.save(order);

        savedOrder.getOrderItems().remove(0);
        em.flush();
    }

}