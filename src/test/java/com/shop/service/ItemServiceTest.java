package com.shop.service;

import com.shop.constant.ItemSellStatus;
import com.shop.dto.ItemFormDto;
import com.shop.entity.Item;
import com.shop.entity.ItemImg;
import com.shop.repository.ItemImgRepository;
import com.shop.repository.ItemRepository;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.EntityNotFoundException;
import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Transactional
@Slf4j
class ItemServiceTest {

    private final ItemService itemService;
    private final ItemRepository itemRepository;
    private final ItemImgRepository itemImgRepository;

    public ItemServiceTest(@Autowired ItemService itemService,
                           @Autowired ItemRepository itemRepository,
                           @Autowired ItemImgRepository itemImgRepository) {
        this.itemService = itemService;
        this.itemRepository = itemRepository;
        this.itemImgRepository = itemImgRepository;
    }


    @Test
    @DisplayName("상품 등록 테스트")
    @WithMockUser(username = "admin", roles = "ADMIN")
    void saveItemTest() throws Exception{
        //Given
        ItemFormDto itemFormDto = new ItemFormDto();
        itemFormDto.setItemNm("테스트 상품");
        itemFormDto.setPrice(12000);
        itemFormDto.setStockNumber(100);
        itemFormDto.setItemSellStatus(ItemSellStatus.SELL);
        itemFormDto.setItemDetail("테스트 상품 상세 내용");

        List<MultipartFile> itemImgFileList = new ArrayList<>();
        for (int i=0; i<5; i++) {
            String path = "C:/Project_Folder/project-shopmall/shop/item/";
            String imageName = "testImg" + i + ".jpg";

            MockMultipartFile multipartFile = new MockMultipartFile(path, imageName, "image/jpg", new byte[]{1,2,3,4});
            itemImgFileList.add(multipartFile);
        }

        //When
        Long itemId = itemService.saveItem(itemFormDto, itemImgFileList);

        //Then
        List<ItemImg> itemImgList = itemImgRepository.findByItemIdOrderByIdAsc(itemId);
        Item item = itemRepository.findById(itemId).orElseThrow(EntityNotFoundException::new);
        assertThat(itemFormDto.getItemNm()).isEqualTo(item.getItemName());
        assertThat(itemImgFileList.get(0).getOriginalFilename()).isEqualTo(itemImgList.get(0).getOriImgName());
    }
}