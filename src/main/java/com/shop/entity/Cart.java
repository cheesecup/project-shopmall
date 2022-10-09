package com.shop.entity;

import lombok.Getter;
import lombok.ToString;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Getter
@ToString
public class Cart {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(fetch = FetchType.EAGER) // 즉시 로딩
    @JoinColumn(name = "member_id")
    private Member member;

    protected Cart() {
    }

    private Cart(Member member) {
        this.member = member;
    }

    public static Cart of(Member member) {
        return new Cart(member);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Cart)) return false;
        Cart cart = (Cart) o;
        return id != null && id.equals(cart.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
