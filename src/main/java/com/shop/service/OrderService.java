package com.shop.service;

import com.shop.dto.OrderDto;
import com.shop.dto.OrderHisDto;
import com.shop.dto.OrderItemDto;
import com.shop.entity.*;
import com.shop.repository.ItemImgRepository;
import com.shop.repository.ItemRepository;
import com.shop.repository.MemberRepository;
import com.shop.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.thymeleaf.util.StringUtils;

import javax.persistence.EntityNotFoundException;
import java.util.ArrayList;
import java.util.List;

/* 주문 로직 */
@Service
@Transactional
public class OrderService {

    private final OrderRepository orderRepository;
    private final ItemRepository itemRepository;
    private final MemberRepository memberRepository;
    private final ItemImgRepository itemImgRepository;

    public OrderService(@Autowired OrderRepository orderRepository,
                        @Autowired ItemRepository itemRepository,
                        @Autowired MemberRepository memberRepository,
                        @Autowired ItemImgRepository itemImgRepository) {
        this.orderRepository = orderRepository;
        this.itemRepository = itemRepository;
        this.memberRepository = memberRepository;
        this.itemImgRepository = itemImgRepository;
    }

    /* 주문 기능을 수행하는 로직 */
    public Long order(OrderDto orderDto, String email) {
        Item item = itemRepository.findById(orderDto.getItemId()).orElseThrow(EntityNotFoundException::new);
        Member member = memberRepository.findByEmail(email);

        List<OrderItem> orderItemList = new ArrayList<>();
        OrderItem orderItem = OrderItem.createOrderItem(item, orderDto.getCount());
        orderItemList.add(orderItem);

        Order order = Order.createOrder(member, orderItemList);
        orderRepository.save(order);

        return order.getId();
    }

    /* 주문 목록을 조회하는 로직 */
    @Transactional(readOnly = true)
    public Page<OrderHisDto> getOrderList(String email, Pageable pageable) {
        List<Order> orders = orderRepository.findOrders(email, pageable); //주문 목록 조회
        Long totalCount = orderRepository.countOrder(email); //유저의 주문 총 개수

        List<OrderHisDto> orderHisDtoList = new ArrayList<>();

        for (Order order : orders) {
            OrderHisDto orderHisDto = new OrderHisDto(order);
            List<OrderItem> orderItems = order.getOrderItems();

            for (OrderItem orderItem : orderItems) {
                ItemImg itemImg = itemImgRepository.findByItemIdAndRepImgYn(orderItem.getItem().getId(), "Y");
                OrderItemDto orderItemDto = new OrderItemDto(orderItem, itemImg.getImgUrl());
                orderHisDto.addOrderItemDto(orderItemDto);
            }

            orderHisDtoList.add(orderHisDto);
        }

        return new PageImpl<OrderHisDto>(orderHisDtoList, pageable, totalCount);
    }

    /* 현재 로그인한 사용자와 주문을 한 사용자가 같은지 검증하는 로직
    * 같으면 true, 다르면 false */
    @Transactional(readOnly = true)
    public boolean validateOrder(Long orderId, String email) {
        Member curMember = memberRepository.findByEmail(email);
        Order order = orderRepository.findById(orderId).orElseThrow(EntityNotFoundException::new);
        Member savedMember = order.getMember();

        if (!StringUtils.equals(curMember.getEmail(), savedMember.getEmail())) {
            return false;
        }

        return true;
    }

    /* 주문 취소 로직 */
    public void cancelOrder(Long orderId) {
        Order order = orderRepository.findById(orderId).orElseThrow(EntityNotFoundException::new);
        order.cancelOrder();
    }
}
