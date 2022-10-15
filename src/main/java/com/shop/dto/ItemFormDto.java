package com.shop.dto;

import com.shop.constant.ItemSellStatus;
import com.shop.entity.Item;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

/* 상품 정보, 상품 이미지 정보 폼 데이터 */
@Getter @Setter
public class ItemFormDto {

    private Long id;

    @NotBlank(message = "상품명을 입력해주세요.")
    private String itemNm;

    @NotNull(message = "가격을 입력해주세요.")
    private Integer price;

    @NotBlank(message = "상품 내용을 입력해주세요.")
    private String itemDetail;

    @NotNull(message = "수량을 입력해주세요.")
    private Integer stockNumber;

    private ItemSellStatus itemSellStatus;

    private List<ItemImgDto> itemImgDtoList = new ArrayList<>();

    private List<Long> itemImgIds = new ArrayList<>();

    public ItemFormDto() {
    }

    public Item createItem() {
        return Item.of(this.itemNm, this.price, this.stockNumber, this.itemDetail, this.itemSellStatus);
    }

    public static ItemFormDto of(Item item) {
        ItemFormDto itemFormDto = new ItemFormDto();
        itemFormDto.setId(item.getId());
        itemFormDto.setItemNm(item.getItemName());
        itemFormDto.setPrice(item.getPrice());
        itemFormDto.setItemDetail(item.getItemDetail());
        itemFormDto.setStockNumber(item.getStockNumber());
        itemFormDto.setItemSellStatus(item.getItemSellStatus());

        return itemFormDto;
    }
}
