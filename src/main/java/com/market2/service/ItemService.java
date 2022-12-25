package com.market2.service;

import com.market2.domain.Item;
import com.market2.dto.ItemDto;
import com.market2.file.FileStore;
import com.market2.file.UploadFile;
import com.market2.repository.ItemRepository;
import com.market2.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
public class ItemService {

    /** 파일 저장 처리 객체 **/
    private final FileStore fileStore;

    private final ItemRepository itemRepository;
    private final MemberRepository memberRepository;

    /**  상품 등록 */
    public Long write(ItemDto.RequestDto requestDto) throws IOException {

        UploadFile uploadFile = fileStore.storeFile(requestDto.getFile());

        requestDto.addFileName(uploadFile.getStoreFileName());

        Item item = requestDto.toEntity();
        return itemRepository.save(item).getId();
    }

    /** 게시물 반환 (조회) **/
    public ItemDto.ResponseDto getById(Long id) {
        Item item = itemRepository.findById(id).orElseThrow(() ->
                new IllegalArgumentException("해당 상품이 존재하지 않습니다."));
        return new ItemDto.ResponseDto(item);
    }

    // 상품 리스트 불러오기
    @Transactional(readOnly = true)
    public Page<Item> allItemView(Pageable pageable) {
        return itemRepository.findAll(pageable);
    }

    public Page<Item> itemList(String searchKeyword, Pageable pageable) {
        return itemRepository.findByItemNameContaining(searchKeyword, pageable);
    }


    // 카테고리별 아이템 목록
    @Transactional(readOnly = true)
    public Page<Item> categoryList(Pageable pageable, String category) {
        return itemRepository.findByCategory(pageable, category);
    }

    public void update(Long id, ItemDto.RequestDto requestDto) throws IOException  {

        Item item = itemRepository.findById(id).orElseThrow(() ->
                new IllegalArgumentException("해당 상품이 존재하지 않습니다."));

        item.update(requestDto.getItemName(), requestDto.getItemDetail(), requestDto.getPrice(),
                requestDto.getStockNumber(),requestDto.getCategory());
    }


    /** delete **/
    public void itemDelete(Long id){
        Item item = itemRepository.findById(id).orElseThrow(() ->
                new IllegalArgumentException("해당 상품이 존재하지 않습니다."));
        itemRepository.delete(item);
    }


}
