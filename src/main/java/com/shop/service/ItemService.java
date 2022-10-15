package com.shop.service;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.shop.dto.ItemFormDto;
import com.shop.dto.ItemImgDto;
import com.shop.dto.ItemSearchDto;
import com.shop.entity.Item;
import com.shop.entity.ItemImg;
import com.shop.entity.QItem;
import com.shop.repository.ItemImgRepository;
import com.shop.repository.ItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.EntityManager;
import javax.persistence.EntityNotFoundException;
import javax.persistence.PersistenceContext;
import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
public class ItemService {

    @PersistenceContext
    private final EntityManager em;
    private final ItemRepository itemRepository;
    private final ItemImgService itemImgService;
    private final ItemImgRepository itemImgRepository;

    public ItemService(@Autowired EntityManager em,
                       @Autowired ItemRepository itemRepository,
                       @Autowired ItemImgService itemImgService,
                       @Autowired ItemImgRepository itemImgRepository) {
        this.em = em;
        this.itemRepository = itemRepository;
        this.itemImgService = itemImgService;
        this.itemImgRepository = itemImgRepository;
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

    /* 상품 등록 서비스 로직 */
    public Long saveItem(ItemFormDto itemFormDto, List<MultipartFile> itemImgFileList) throws Exception {
        //상품 등록
        Item item = itemFormDto.createItem();
        itemRepository.save(item);

        //이미지 등록
        for (int i=0; i<itemImgFileList.size(); i++) {
            ItemImg itemImg = ItemImg.of(item);

            if (i == 0) {
                itemImg.setRepImgYn("Y");
            } else {
                itemImg.setRepImgYn("N");
            }

            itemImgService.saveItemImg(itemImg, itemImgFileList.get(i));
        }

        return item.getId();
    }

    /* 등록된 상품 조회 서비스 로직 */
    @Transactional(readOnly = true)
    public ItemFormDto getItemDtl(Long itemId) {
        //등록된 상품 이미지 불러오기
        List<ItemImg> itemImgList = itemImgRepository.findByItemIdOrderByIdAsc(itemId);

        List<ItemImgDto> itemImgDtoList = new ArrayList<>();
        for (ItemImg itemImg : itemImgList) {
            ItemImgDto itemImgDto = ItemImgDto.of(itemImg);
            itemImgDtoList.add(itemImgDto);
        }

        Item item = itemRepository.findById(itemId).orElseThrow(EntityNotFoundException::new);
        ItemFormDto itemFormDto = ItemFormDto.of(item);
        itemFormDto.setItemImgDtoList(itemImgDtoList);

        return itemFormDto;
    }

    /* 상품 수정 */
    public Long updateItem(ItemFormDto itemFormDto, List<MultipartFile> itemImgFileList) throws Exception {

        Item item = itemRepository.findById(itemFormDto.getId()).orElseThrow(EntityNotFoundException::new);
        item.updateItem(itemFormDto);

        List<Long> itemImgIds = itemFormDto.getItemImgIds();

        for (int i = 0; i < itemImgFileList.size(); i++) {
            itemImgService.updateItemImg(itemImgIds.get(i), itemImgFileList.get(i));
        }

        return item.getId();
    }

    @Transactional(readOnly = true)
    public Page<Item> getAdminItemPage(ItemSearchDto itemSearchDto, Pageable pageable) {
        return itemRepository.getAdminItemPage(itemSearchDto, pageable);
    }
}
