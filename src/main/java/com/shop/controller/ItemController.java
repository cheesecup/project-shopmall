package com.shop.controller;

import com.shop.entity.Item;
import com.shop.service.ItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/item")
public class ItemController {

    private final ItemService itemService;

    public ItemController(@Autowired ItemService itemService) {
        this.itemService = itemService;
    }

    @GetMapping("/search")
    @ResponseBody
    public List<Item> searchItem(@RequestParam String search) {
        List<Item> items = itemService.searchItem(search);
        return items;
    }
}
