package com.market2.controller;

import com.market2.dto.ItemDto;
import com.market2.service.ItemService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.io.IOException;

@Slf4j
@Controller
@RequiredArgsConstructor
public class ItemController {

    private final ItemService itemService;

    /**      상품 등록      */
    @GetMapping("/admin/item/new")
    public String itemForm(Model model){
        model.addAttribute("itemDto", new ItemDto.RequestDto());
        return "item/items";
    }

    @PostMapping("/admin/item/new")
    public String itemSave(@Validated @ModelAttribute ItemDto.RequestDto itemDto, BindingResult bindingResult) throws IOException {

        if (bindingResult.hasErrors()) {
            return "item/items";
        }

        itemService.write(itemDto);
        return "redirect:/";
    }

    /**   상품 상세 페이지       */
    @GetMapping("/item/{itemId}")
    public String itemDetail(@PathVariable("itemId") Long itemId, Model model){

        ItemDto.ResponseDto responseDto = itemService.getById(itemId);
        model.addAttribute("item", responseDto);
        return "item/itemView";
    }

    /**     수정   - 글 수정 페이지 반환   */
    @GetMapping("/admin/item/update/{id}")
    public String update(@PathVariable("id") Long id, Model model){
        ItemDto.ResponseDto item = itemService.getById(id);
        model.addAttribute("item", item);
            return "item/itemModify";
    }

    @PostMapping("/admin/item/update/pro/{id}")
    public String update(@PathVariable Long id, ItemDto.RequestDto requestDto) throws IOException{
        itemService.update(id, requestDto);
        return "redirect:/";
    }

    /**     상품 삭제    */
    @GetMapping("/item/delete")
    public String delete(Long id) {
        itemService.itemDelete(id);
        return "redirect:/";
    }

    // best 카테고리
    @GetMapping("/best")
    public String daily(Model model, @PageableDefault(size = 5, sort = "id", direction = Sort.Direction.DESC) Pageable pageable) {
        model.addAttribute("items", itemService.categoryList(pageable, "best"));
        return "category/best";
    }

    // new 카테고리
    @GetMapping("/new")
    public String date(Model model, @PageableDefault(size = 5, sort = "id", direction = Sort.Direction.DESC) Pageable pageable) {
        model.addAttribute("items", itemService.categoryList(pageable, "new"));
        return "category/new";
    }

    // fruit 카테고리
    @GetMapping("/fruit")
    public String fruit(Model model, @PageableDefault(size = 5, sort = "id", direction = Sort.Direction.DESC) Pageable pageable) {
        model.addAttribute("items", itemService.categoryList(pageable, "fruit"));
        return "category/fruit";
    }
}
