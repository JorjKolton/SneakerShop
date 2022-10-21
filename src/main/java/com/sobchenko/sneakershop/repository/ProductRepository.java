package com.sobchenko.sneakershop.repository;

import com.sobchenko.sneakershop.model.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepository extends CrudRepository<Product, String>, PagingAndSortingRepository<Product, String> {
    Product getById(String id);
    Page<Product> findByIgnoreCaseTitleContaining(String filter, Pageable pageable);
}