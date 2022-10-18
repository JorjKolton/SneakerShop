package com.sobchenko.sneakershop.validator;

import com.sobchenko.sneakershop.model.Product;

import java.util.Arrays;
import java.util.List;

public final class ProductValidator {
    private ProductValidator() {
    }

    public static boolean checkImageURL(Product product) {
        final List<String> urls = Arrays.asList(product.getBackImage(),
                product.getLeftImage(),
                product.getRightImage(),
                product.getSoleImage(),
                product.getTitleImage(),
                product.getTopImage());
        for (String url : urls) {
            if (!url.matches("^https?://\\S+\\.(?:jpg|jpeg|png|JPG|JPEG|PNG)$")) {
                throw new IllegalArgumentException("Invalid url address to image");
            }
        }
        return true;
    }

}