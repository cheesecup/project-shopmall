package com.shop.entity;

import lombok.Getter;
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

    public static OrderItem of(int orderPrice, int count, Order order, Item item) {
        return new OrderItem(orderPrice, count, order, item);
    }
}
