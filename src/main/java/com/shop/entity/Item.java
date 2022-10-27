package com.shop.entity;

import com.shop.constant.ItemSellStatus;
import com.shop.dto.ItemFormDto;
import com.shop.exception.OutOfStockException;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Getter
@ToString
public class Item extends BaseEntity {

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

    public void updateItem(ItemFormDto itemFormDto) {
        this.itemName = itemFormDto.getItemNm();
        this.price = itemFormDto.getPrice();
        this.itemDetail = itemFormDto.getItemDetail();
        this.stockNumber = itemFormDto.getStockNumber();
        this.itemSellStatus = itemFormDto.getItemSellStatus();
    }

    /* 주문 수량만큼 재고 감소시키는 메서드 */
    public void removeStock(int stockNumber) {
        int restStock = this.stockNumber - stockNumber;

        if (restStock < 0) {
            throw new OutOfStockException("상품의 재고가 부족합니다. (현재 재고 수량: " + this.stockNumber + ")");
        }

        this.stockNumber = restStock;
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
