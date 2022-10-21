package com.sobchenko.sneakershop.service;

import com.sobchenko.sneakershop.model.Bucket;
import com.sobchenko.sneakershop.model.Product;
import com.sobchenko.sneakershop.model.User;
import com.sobchenko.sneakershop.repository.BucketRepository;
import com.sobchenko.sneakershop.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
public class BucketService {
    private final BucketRepository bucketRepository;
    private final ProductRepository productRepository;

    @Autowired
    public BucketService(BucketRepository bucketRepository, ProductRepository productRepository) {
        this.bucketRepository = bucketRepository;
        this.productRepository = productRepository;
    }

    @Transactional
    public Bucket createBucket(User user, List<String> productsId) {
        final Bucket bucket = new Bucket();
        bucket.setUser(user);
        List<Product> products = getProductsByIds(productsId);
        bucket.setProducts(products);
        return bucketRepository.save(bucket);
    }

    private List<Product> getProductsByIds(List<String> productsId) {
        return productsId.stream()
                .map(productRepository::getById)
                .toList();
    }

    public void addProducts(Bucket bucket, List<String> productsId) {
        List<Product> products = bucket.getProducts();
        List<Product> newProductsList = products == null ? new ArrayList<>() : new ArrayList<>(products);
        newProductsList.addAll(getProductsByIds(productsId));
        bucket.setProducts(newProductsList);
        bucketRepository.save(bucket);
    }
}