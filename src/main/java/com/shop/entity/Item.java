package com.shop.entity;

import com.shop.constant.ItemSellStatus;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Objects;

@Entity
@Getter
@ToString
public class Item {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; // 상품 코드

    @Setter
    @Column(nullable = false, length = 50)
    private String itemName; // 상품명

    @Setter
    @Column(nullable = false)
    private int price; // 가격

    @Setter
    @Column(nullable = false)
    private int stockNumber; // 재고수량

    @Setter
    @Column(nullable = false)
    private String itemDetail; // 상품 상세 설명

    @Setter
    @Enumerated(EnumType.STRING)
    private ItemSellStatus itemSellStatus; // 상품 판매 상태

    private String createdBy; // 생성자

    private LocalDateTime regTime; // 등록일

    private String updatedBy; // 수정자

    private LocalDateTime updateTime; // 수정일

    protected Item() {
    }

    private Item(String itemName, int price, int stockNumber, String itemDetail, ItemSellStatus itemSellStatus) {
        this.itemName = itemName;
        this.price = price;
        this.stockNumber = stockNumber;
        this.itemDetail = itemDetail;
        this.itemSellStatus = itemSellStatus;
    }

    public static Item of(String itemName, int price, int stockNumber, String itemDetail, ItemSellStatus itemSellStatus) {
        return new Item(itemName, price, stockNumber, itemDetail, itemSellStatus);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Item)) return false;
        Item item = (Item) o;
        return id != null && id.equals(item.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
