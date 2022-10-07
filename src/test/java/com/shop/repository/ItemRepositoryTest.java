package com.shop.repository;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.shop.constant.ItemSellStatus;
import com.shop.entity.Item;
import com.shop.entity.QItem;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@ActiveProfiles("testdb")
@Transactional
@SpringBootTest
//@TestPropertySource(locations = "classpath:application-test.properties")
class ItemRepositoryTest {

    @PersistenceContext
    EntityManager em;
    private final ItemRepository itemRepository;

    ItemRepositoryTest(@Autowired ItemRepository itemRepository) {
        this.itemRepository = itemRepository;
    }

    @Test
    @DisplayName("상품 조회 테스트")
    void readItemTest() {
        Item item = itemRepository.findById(1L).orElseThrow();
        System.out.println(item);
    }

    @Test
    @DisplayName("상품 생성 테스트")
    void createItemTest() {
        long previousCount = itemRepository.count();
        Item item = Item.of("테스트 상품", 12000, 100, "테스트 상품 상세 설명", ItemSellStatus.SELL);
        itemRepository.save(item);

        assertThat(itemRepository.count()).isEqualTo(previousCount + 1);
    }

    @Test
    @DisplayName("상품 수정 테스트")
    void updateItemTest() {
        Item findItem = itemRepository.findById(1L).orElseThrow();
        String updateItemName = "수정된 테스트 상품 이름";

        findItem.setItemName(updateItemName);
        Item savedItem = itemRepository.save(findItem);

        assertThat(savedItem.getItemName()).isEqualTo(updateItemName);
    }

    @Test
    @DisplayName("상품 삭제 테스트")
    void deleteItemTest() {
        long previousCount = itemRepository.count();
        Item findItem = itemRepository.findById(1L).orElseThrow();
        itemRepository.delete(findItem);

        assertThat(itemRepository.count()).isEqualTo(previousCount - 1);
    }

    @Test
    @DisplayName("querydsl 을 이용하여 상품 이름 검색 테스트")
    void useQuerydsl_searchItemTest() {
        JPAQueryFactory query = new JPAQueryFactory(em);
        QItem qItem = QItem.item;

        List<Item> pencilItemList = query.select(qItem)
                .from(qItem)
                .where(qItem.itemSellStatus.eq(ItemSellStatus.SELL))
                .where(qItem.itemName.like("%연필%"))
                .orderBy(qItem.price.asc())
                .fetch();

        assertThat(pencilItemList.size()).isEqualTo(2);
    }

    @Test
    @DisplayName("querydsl 을 사용하여 상품 검색 조회 테스트2")
    void useQuerydsl_searchItemTest2() {
        BooleanBuilder booleanBuilder = new BooleanBuilder();
        QItem qItem = QItem.item;

        String name = "연필";
        int price = 1000;
        String status = "SELL";

        BooleanBuilder itemName = booleanBuilder.and(qItem.itemName.like("%" + name + "%"));
        BooleanBuilder itemPrice = booleanBuilder.and(qItem.price.loe(price));

        Pageable pageable = PageRequest.of(0, 5);
        Page<Item> itemPagingResult = itemRepository.findAll(booleanBuilder, pageable);

        List<Item> itemList = itemPagingResult.getContent();
        for (Item item : itemList) {
            System.out.println("검색된 상품 이름 : " + item.getItemName());
        }

    }
}