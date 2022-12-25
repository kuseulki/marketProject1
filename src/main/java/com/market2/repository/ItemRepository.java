package com.market2.repository;


import com.market2.domain.Item;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ItemRepository extends JpaRepository<Item, Long> {

    Page<Item> findByCategory(Pageable pageable, String category);
    Page<Item> findByItemNameContaining(String searchKeyword, Pageable pageable);
}
