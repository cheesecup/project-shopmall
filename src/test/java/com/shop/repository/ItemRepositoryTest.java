package com.shop.repository;

import com.shop.constant.ItemSellStatus;
import com.shop.entity.Item;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.assertThat;

@ActiveProfiles("testdb")
@Transactional
@SpringBootTest
//@TestPropertySource(locations = "classpath:application-test.properties")
class ItemRepositoryTest {

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
}