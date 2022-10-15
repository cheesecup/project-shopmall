package com.shop.dto;

import com.shop.constant.ItemSellStatus;
import lombok.Getter;
import lombok.Setter;

/**
 * 상품 데이터 조회 시 조회 조건을 가지고 있는 DTO
 */
@Getter
@Setter
public class ItemSearchDto {

    //현재 시간과 상품 등록일을 비교해서 상품 데이터를 조회
    private String searchDateType;

    //판매 상태를 기준으로 상품 데이터 조회
    private ItemSellStatus searchSellStatus;

    //어떤 유형으로 조회할지 선택
    private String searchBy;

    //조회할 검색어를 저장할 변수
    private String searchQuery = "";
}
