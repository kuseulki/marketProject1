package com.market2.dto;

import com.market2.domain.Item;
import lombok.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotNull;

public class ItemDto {

    @Getter
    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    @Setter
    public static class RequestDto {

        private Long id;
        @NotNull(message = "상품명은 필수 입력 값입니다.")
        private String itemName;

        @NotNull(message ="가격은 필수 입력 값입니다.")
        private String itemDetail;

        @NotNull(message = "이름은 필수 입력 값입니다.")
        private Integer price;

        @NotNull(message = "재고는 필수 입력 값입니다.")
        private int stockNumber;

        private String fileName;
        private MultipartFile file;
        private String category;


        /* Dto -> Entity */
        public Item toEntity() {
            Item item = Item.builder()
                    .id(id)
                    .itemName(itemName)
                    .itemDetail(itemDetail)
                    .price(price)
                    .stockNumber(stockNumber)
                    .fileName(fileName)
                    .category(category)
                    .build();
            return item;
        }

        /* 서버가 관리하는 파일명 추가 */
        public void addFileName(String storeFileName) {
            this.fileName = storeFileName;
        }
    }

    @Getter @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class ResponseDto {
        private Long id;
        private String itemName;
        private String itemDetail;
        private int price;
        private int stockNumber;
        private String fileName;
        private String category;
        private String createdDate, updatedDate;


        /** Entity -> Dto */
        public ResponseDto(Item item) {
            this.id = item.getId();
            this.itemName = item.getItemName();
            this.itemDetail = item.getItemDetail();
            this.price = item.getPrice();
            this.stockNumber = item.getStockNumber();
            this.fileName = item.getFileName();
            this.category = item.getCategory();
            this.createdDate = item.getCreatedDate();
            this.updatedDate = item.getUpdatedDate();
        }
    }

    /* 페이징 객체  리스트*/
    @Getter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class ResponsePageDto {
        private Long id;
        private String itemName;
        private String itemDetail;
        private int price;
        private int stockNumber;
        private String fileName;
        private String category;

        /** Entity -> Dto */
        public ResponsePageDto(Item item) {
            this.id = item.getId();
            this.itemName = item.getItemName();
            this.itemDetail = item.getItemDetail();
            this.price = item.getPrice();
            this.stockNumber = item.getStockNumber();
            this.fileName = item.getFileName();
            this.category = item.getCategory();
        }
    }


}
