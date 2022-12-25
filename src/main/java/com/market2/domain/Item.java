package com.market2.domain;

import lombok.*;

import javax.persistence.*;

@Entity @Table(name="item")
@Getter @Setter
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Item extends BaseTimeEntity{

    @Id @Column(name="item_id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;                        //상품 코드

    @Column(nullable = false, length = 50)
    private String itemName;                //상품명

    @Lob @Column(nullable = false)
    private String itemDetail;              //상품 상세 설명

    @Column(nullable = false)
    private int price;                      //가격

    private int stockNumber;                //재고수량

    private String fileName;

    private String category;


    public void update(String itemName, String itemDetail, int price, int stockNumber, String category){
        this.itemName = itemName;
        this.itemDetail = itemDetail;
        this.price = price;
        this.stockNumber = stockNumber;
        this.category = category;

    }

//    public void removeStock(int stockNumber){
//        int restStock = this.stockNumber - stockNumber;
//        if(restStock<0){
//            throw new OutOfStockException("상품의 재고가 부족 합니다. (현재 재고 수량: " + this.stockNumber + ")");
//        }
//        this.stockNumber = restStock;
//    }
//
//    public void addStock(int stockNumber){
//        this.stockNumber += stockNumber;
//    }

}