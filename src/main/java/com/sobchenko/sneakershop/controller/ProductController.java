package com.sobchenko.sneakershop.controller;

import com.sobchenko.sneakershop.dto.ProductDTO;
import com.sobchenko.sneakershop.model.Product;
import com.sobchenko.sneakershop.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/products")
public class ProductController {
    private final ProductService productService;

    @Autowired
    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping("/delete/{id}")
    public String deleteEmployee(@PathVariable(value = "id") String id) {
        productService.deleteProductById(id);
        return "redirect:/";
    }

    @PostMapping("/save")
    public String saveEmployee(@ModelAttribute ProductDTO productDTO) {
        final Product product = ProductDTO.fromDTO(productDTO);
        productService.saveProduct(product);
        return "redirect:/";
    }

    @GetMapping("/update/{id}")
    public ModelAndView updateProduct(@PathVariable(value = "id") String id, ModelAndView modelAndView) {
        final ProductDTO productDTO = ProductDTO.toDTO(productService.findProductById(id));
        modelAndView.addObject("product", productDTO);
        modelAndView.setViewName("product-update");
        return modelAndView;
    }
}