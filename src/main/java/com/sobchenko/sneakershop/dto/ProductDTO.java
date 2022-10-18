package com.sobchenko.sneakershop.dto;

import com.sobchenko.sneakershop.model.Gender;
import com.sobchenko.sneakershop.model.Height;
import com.sobchenko.sneakershop.model.Product;
import com.sobchenko.sneakershop.validator.ProductValidator;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ProductDTO {
    private String id;
    private String title;
    private String model;
    private String titleImage;
    private String rightImage;
    private String leftImage;
    private String backImage;
    private String soleImage;
    private String topImage;
    private Gender gender;
    private Height height;
    private int price;

    public static ProductDTO toDTO(Product product) {
        return new ProductDTOBuilder()
                .id(product.getId())
                .title(product.getTitle())
                .model(product.getModel())
                .titleImage(product.getTitleImage())
                .rightImage(product.getRightImage())
                .leftImage(product.getLeftImage())
                .backImage(product.getBackImage())
                .soleImage(product.getSoleImage())
                .topImage(product.getTopImage())
                .gender(product.getGender())
                .height(product.getHeight())
                .price(product.getPrice())
                .build();
    }

    public static Product fromDTO(ProductDTO dto) {
        final Product product = new Product();
        product.setId(dto.getId());
        product.setTitle(dto.getTitle());
        product.setModel(dto.getModel());
        product.setTitleImage(dto.getTitleImage());
        product.setRightImage(dto.getRightImage());
        product.setLeftImage(dto.getLeftImage());
        product.setBackImage(dto.getBackImage());
        product.setSoleImage(dto.getSoleImage());
        product.setTopImage(dto.getTopImage());
        product.setGender(dto.getGender());
        product.setHeight(dto.getHeight());
        product.setPrice(dto.getPrice());

        ProductValidator.checkImageURL(product);

        return product;
    }
}