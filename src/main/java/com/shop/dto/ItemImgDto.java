package com.shop.dto;

import com.shop.entity.ItemImg;
import lombok.Getter;
import lombok.Setter;

/* 상품 이미지 정보 폼 데이터 */
@Getter
@Setter
public class ItemImgDto {

    private Long id;

    private String imgName;

    private String oriImgName;

    private String imgUrl;

    private String repImgYn;

    public static ItemImgDto of(ItemImg itemImg) {
        ItemImgDto itemImgDto = new ItemImgDto();
        itemImgDto.setId(itemImg.getId());
        itemImgDto.setImgName(itemImg.getImgName());
        itemImgDto.setOriImgName(itemImg.getOriImgName());
        itemImgDto.setImgUrl(itemImg.getImgUrl());
        itemImgDto.setRepImgYn(itemImg.getRepImgYn());

        return itemImgDto;
    }

}
