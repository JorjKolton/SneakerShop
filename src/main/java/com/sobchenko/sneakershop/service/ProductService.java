package com.sobchenko.sneakershop.service;

import com.sobchenko.sneakershop.model.Product;
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

@Service
public class ProductService {
    private static final Logger LOGGER = LoggerFactory.getLogger(ProductService.class);
    private final ProductRepository productRepository;

    @Autowired
    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public Page<Product> findPaginated(int pageNo, int pageSize, String sortField, String sortDirection) {
        Sort sort = sortDirection.equalsIgnoreCase(Sort.Direction.ASC.name()) ? Sort.by(sortField).ascending() :
                Sort.by(sortField).descending();

        Pageable pageable = PageRequest.of(pageNo - 1, pageSize, sort);
        return productRepository.findAll(pageable);
    }

    @Transactional
    public void saveProduct(Product product) {
        productRepository.save(product);
    }

    @Transactional
    public void deleteProductById(String id) {
        if (productRepository.existsById(id)) {
            productRepository.deleteById(id);
        } else {
            LOGGER.info("Product with id '{}' not found", id);
        }
    }

    @Transactional
    public Product findProductById(String id) {
        return productRepository.findById(id).orElseGet(() -> {
            LOGGER.info("Product with id '{}' not found", id);
            throw new RuntimeException("Product not found with id " + id);
        });
    }

}