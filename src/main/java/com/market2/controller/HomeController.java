package com.market2.controller;

import com.market2.domain.Item;
import com.market2.service.ItemService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@RequiredArgsConstructor
public class HomeController {

    private final ItemService itemService;

    @GetMapping("/")
    public String boardList(@PageableDefault(size = 5, sort = "id", direction = Sort.Direction.DESC)
                                    Pageable pageable, String searchKeyword, Model model) {

        Page<Item> list = null;

        if (searchKeyword == null) {
            list = itemService.allItemView(pageable);
        } else {
            list = itemService.itemList(searchKeyword, pageable);
        }
        model.addAttribute("list", list);
        return "home";
    }

}