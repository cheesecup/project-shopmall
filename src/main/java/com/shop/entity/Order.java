package com.shop.entity;

import com.shop.constant.OrderStatus;
import lombok.Getter;
import lombok.ToString;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@ToString
@Table(name = "orders") // MySQL 예약어 `ORDER`와 클래스 이름이 겹쳐 테이블 이름을 orders 로 변경
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "order_id")
    private Long id;

    @Enumerated(EnumType.STRING)
    private OrderStatus orderStatus;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL,
               orphanRemoval = true, fetch = FetchType.LAZY)
    private List<OrderItem> orderItems = new ArrayList<>();

    private LocalDateTime orderDate;

    private LocalDateTime regTime;

    private LocalDateTime updateTime;

    protected Order() {
    }

    private Order(OrderStatus orderStatus, Member member) {
        this.orderStatus = orderStatus;
        this.member = member;
    }

    public static Order of(OrderStatus orderStatus, Member member) {
        return new Order(orderStatus, member);
    }
}
