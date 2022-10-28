package com.shop.dto;

import com.shop.entity.OrderItem;
import lombok.Getter;
import lombok.Setter;

/* 주문한 상품 정보를 담는 DTO */
@Getter
@Setter
public class OrderItemDto {

    private String itemNm; //상품명

    private int count; //주문 수량

    private int orderPrice; //주문 금액

    private String imgUrl; //상품 이미지 경로

    /* 주문상품과 이미지 경로를 받아서 DTO로 변환 */
    public OrderItemDto(OrderItem orderItem, String imgUrl) {
        this.itemNm = orderItem.getItem().getItemName();
        this.count = orderItem.getCount();
        this.orderPrice = orderItem.getOrderPrice();
        this.imgUrl = imgUrl;
    }
}
