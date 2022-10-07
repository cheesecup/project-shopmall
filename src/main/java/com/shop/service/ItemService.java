package com.shop.service;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.shop.entity.Item;
import com.shop.entity.QItem;
import com.shop.repository.ItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Service
public class ItemService {

    @PersistenceContext
    private final EntityManager em;
    private final ItemRepository itemRepository;

    public ItemService(@Autowired EntityManager em, @Autowired ItemRepository itemRepository) {
        this.em = em;
        this.itemRepository = itemRepository;
    }

    /* 상품 검색 로직 */
    public List<Item> searchItem(String search) {
        JPAQueryFactory query = new JPAQueryFactory(em);
        QItem qItem = QItem.item;

        List<Item> itemList = query.select(qItem)
                .from(qItem)
                .where(qItem.itemName.like("%" + search + "%"))
                .orderBy(qItem.itemName.asc())
                .fetch();

        return itemList;
    }
}
