package com.shop.entity;

import lombok.Getter;
import lombok.ToString;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Getter
@ToString
public class CartItem extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private int count;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "cart_id")
    private Cart cart;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "item_id")
    private Item item;

    protected CartItem() {
    }

    private CartItem(int count, Cart cart, Item item) {
        this.count = count;
        this.cart = cart;
        this.item = item;
    }

    public static CartItem of(int count, Cart cart, Item item) {
        return new CartItem(count, cart, item);
    }

    /* 장바구니에 담을 상품 엔티티를 생성하는 메소드 */
    public static CartItem createCartItem(Cart cart, Item item, int count) {
        CartItem cartItem = new CartItem();
        cartItem.cart = cart;
        cartItem.item = item;
        cartItem.count = count;

        return cartItem;
    }

    /* 장바구니에 담을 수량을 주가시켜 주는 메소드 */
    public void addCount(int count) {
        this.count += count;
    }

    /* 장바구니에 담겨 있는 상품 수량 변경하는 메소드 */
    public void updateCount(int count) {
        this.count = count;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof CartItem)) return false;
        CartItem cartItem = (CartItem) o;
        return id != null && id.equals(cartItem.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
