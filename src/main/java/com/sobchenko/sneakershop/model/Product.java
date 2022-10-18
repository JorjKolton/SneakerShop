package com.sobchenko.sneakershop.model;

import com.sobchenko.sneakershop.validator.ProductValidator;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "products")
public class Product {
    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    private String id;
    @NotBlank
    private String title;
    @NotBlank
    private String model;
    @NotBlank
    private String titleImage;
    private String rightImage;
    private String leftImage;
    private String backImage;
    private String soleImage;
    private String topImage;
    @Enumerated(EnumType.STRING)
    private Gender gender;
    @Enumerated(EnumType.STRING)
    private Height height;
    @Min(2000)
    private int price;
    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<Size> sizes;

    @PrePersist
    public void prePersist() {
        ProductValidator.checkImageURL(this);
    }

    @PreUpdate
    public void preUpdate() {
        ProductValidator.checkImageURL(this);
    }

}