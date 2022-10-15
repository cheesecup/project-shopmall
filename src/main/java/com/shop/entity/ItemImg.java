package com.shop.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.util.Objects;

/* 상품 이미지 정보 엔티티 */
@Entity
@Getter
@ToString
public class ItemImg extends BaseEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String imgName; //이미지 파일명

    private String oriImgName; //원본 이미지 파일명

    private String imgUrl; //이미지 경로

    @Setter
    private String repImgYn; //대표 이미지 여부

    @Setter
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "item_id")
    private Item item;

    protected ItemImg() {
    }

    private ItemImg(Item item) {
        this.item = item;
    }

    public void updateItemImg(String oriImgName, String imgName, String imgUrl) {
        this.oriImgName = oriImgName;
        this.imgName = imgName;
        this.imgUrl = imgUrl;
    }

    public static ItemImg of(Item item) {
        return new ItemImg(item);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ItemImg)) return false;
        ItemImg itemImg = (ItemImg) o;
        return id != null && id.equals(itemImg.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
