package com.sobchenko.sneakershop.service;

import com.sobchenko.sneakershop.dto.BucketDTO;
import com.sobchenko.sneakershop.dto.BucketDetailsDTO;
import com.sobchenko.sneakershop.model.Bucket;
import com.sobchenko.sneakershop.model.Product;
import com.sobchenko.sneakershop.model.User;
import com.sobchenko.sneakershop.repository.BucketRepository;
import com.sobchenko.sneakershop.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class BucketService {
    private final BucketRepository bucketRepository;
    private final ProductRepository productRepository;
    private final UserService userService;

    @Autowired
    public BucketService(BucketRepository bucketRepository, ProductRepository productRepository, UserService userService) {
        this.bucketRepository = bucketRepository;
        this.productRepository = productRepository;
        this.userService = userService;
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

    public void deleteProduct(Bucket bucket, String productID) {
        bucket.getProducts().removeIf(item -> item.getId().equals(productID));
    }

    public boolean isNotEmptyBucketByUserName(String username) {
        final User user = userService.getUserByName(username);
        if (bucketRepository.existsByUserId(user.getId())) {
            return !user.getBucket().getProducts().isEmpty();
        }
        return false;
    }

    public BucketDTO getBucketByUser(String name) {
        User user = userService.getUserByName(name);
        BucketDTO bucketDTO = new BucketDTO();
        Map<String, BucketDetailsDTO> mapByproductId = new HashMap<>();
        List<Product> products = user.getBucket().getProducts();

        for (Product product : products) {
            BucketDetailsDTO details = mapByproductId.get(product.getId());
            if (details == null) {
                mapByproductId.put(product.getId(), new BucketDetailsDTO(product));
            } else {
                details.setAmount(details.getAmount() + 1);
                details.setSum(details.getSum() + details.getPrice());
            }
        }

        bucketDTO.setDetails(new ArrayList<>(mapByproductId.values()));
        bucketDTO.aggregate();

        return bucketDTO;
    }
}