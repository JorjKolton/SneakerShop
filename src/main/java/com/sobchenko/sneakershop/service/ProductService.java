package com.sobchenko.sneakershop.service;

import com.sobchenko.sneakershop.model.Bucket;
import com.sobchenko.sneakershop.model.Product;
import com.sobchenko.sneakershop.model.User;
import com.sobchenko.sneakershop.repository.ProductRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;

@Service
public class ProductService {
    private static final Logger LOGGER = LoggerFactory.getLogger(ProductService.class);
    private final ProductRepository productRepository;
    private final UserService userService;
    private final BucketService bucketService;

    @Autowired
    public ProductService(ProductRepository productRepository, UserService userService, BucketService bucketService) {
        this.productRepository = productRepository;
        this.userService = userService;
        this.bucketService = bucketService;
    }

    public Page<Product> findPaginated(int pageNo, int pageSize, String sortField, String sortDirection, String filter) {
        Sort sort = sortDirection.equalsIgnoreCase(Sort.Direction.ASC.name()) ? Sort.by(sortField).ascending() :
                Sort.by(sortField).descending();

        Pageable pageable = PageRequest.of(pageNo - 1, pageSize, sort);
        if (filter.isEmpty()) {
            return productRepository.findAll(pageable);
        } else {
            return productRepository.findByIgnoreCaseTitleContaining(filter, pageable);
        }
    }

    @Transactional
    public void saveProduct(Product product) {
        productRepository.save(product);
        LOGGER.debug("The product with id '{}' was saved", product.getId());
    }

    @Transactional
    public void deleteProductById(String id) {
        if (productRepository.existsById(id)) {
            productRepository.deleteById(id);
        } else {
            LOGGER.debug("Product with id '{}' not found", id);
        }
    }

    @Transactional
    public Product findProductById(String id) {
        return productRepository.findById(id).orElseGet(() -> {
            LOGGER.debug("Product with id '{}' not found", id);
            throw new RuntimeException("Product not found with id " + id);
        });
    }

    @Transactional
    public void addToUserBucket(String productId, String userName) {
        User user = userService.getUserByName(userName);
        Bucket bucket = user.getBucket();
        if (bucket == null) {
            Bucket newBucket = bucketService.createBucket(user, Collections.singletonList(productId));
            user.setBucket(newBucket);
            userService.save(user);
        } else {
            bucketService.addProducts(bucket, Collections.singletonList(productId));
        }
    }

}