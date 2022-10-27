package com.shop.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;

@Entity
@Getter
@ToString
public class OrderItem extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "order_item_id")
    private Long id;

    private int orderPrice;

    private int count;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id")
    @Setter
    private Order order;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "item_id")
    private Item item;

    protected OrderItem() {
    }

    private OrderItem(int orderPrice, int count, Order order, Item item) {
        this.orderPrice = orderPrice;
        this.count = count;
        this.order = order;
        this.item = item;
    }

    private OrderItem(Item item, int count, int orderPrice) {
        this.item = item;
        this.count = count;
        this.orderPrice = orderPrice;
    }

    public static OrderItem of(int orderPrice, int count, Order order, Item item) {
        return new OrderItem(orderPrice, count, order, item);
    }

    /* 주문할 상품과 주문 수량을 통해 주문 상품 객체 생성하기 */
    public static OrderItem createOrderItem(Item item, int count) {
        OrderItem orderItem = new OrderItem(item, count, item.getPrice());

        item.removeStock(count);
        return orderItem;
    }

    /* 주문 가격과 주문 수량을 이용하여 주문한 상품의 총 가격 구하기 */
    public int getTotalPrice() {
        return this.orderPrice * this.count;
    }
}
