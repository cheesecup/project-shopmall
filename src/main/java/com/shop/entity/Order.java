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
public class Order extends BaseEntity {

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


    protected Order() {
    }

    private Order(OrderStatus orderStatus, Member member) {
        this.orderStatus = orderStatus;
        this.member = member;
    }

    public static Order of(OrderStatus orderStatus, Member member) {
        return new Order(orderStatus, member);
    }

    /* 주문 엔티티에 주문 상품 추가하는 메소드 */
    public void addOrderItem(OrderItem orderItem) {
        orderItems.add(orderItem);
        orderItem.setOrder(this);
    }

    /* 회원, 주문상품을 받아서 주문 엔티티 생성하는 메소드 */
    public static Order createOrder(Member member, List<OrderItem> orderItemList) {
        Order order = new Order();
        order.member = member;
        for (OrderItem orderItem : orderItemList) {
            order.addOrderItem(orderItem);
        }

        order.orderStatus = OrderStatus.ORDER;
        order.orderDate = LocalDateTime.now();

        return order;
    }

    /* 주문 총액을 계산하는 메소드 */
    public int getTotalPrice() {
        int totalPrice = 0;
        for (OrderItem orderItem : this.orderItems) {
            totalPrice = orderItem.getTotalPrice();
        }

        return totalPrice;
    }

    /* 주문 취소 시 주문 수량을 재고에 더해주고 주문 상태를 취소로 변경하는 메서드 */
    public void cancelOrder() {
        this.orderStatus = OrderStatus.CANCEL;

        for (OrderItem orderItem : orderItems) {
            orderItem.cancel();
        }
    }
}
