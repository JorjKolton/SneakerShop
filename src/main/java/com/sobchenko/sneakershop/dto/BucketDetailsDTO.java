package com.sobchenko.sneakershop.dto;

import com.sobchenko.sneakershop.model.Product;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BucketDetailsDTO {
    private String title;
    private String leftImage;
    private String productID;
    private String model;
    private int amount;
    private int price;
    private int sum;

    public BucketDetailsDTO(Product product) {
        title = product.getTitle();
        leftImage = product.getLeftImage();
        productID = product.getId();
        model = product.getModel();
        amount = 1; // default amount of products in the bucket
        price = product.getPrice();
        sum = product.getPrice();
    }
}