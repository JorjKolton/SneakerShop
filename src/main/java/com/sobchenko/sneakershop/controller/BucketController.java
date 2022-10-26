package com.sobchenko.sneakershop.controller;

import com.sobchenko.sneakershop.dto.BucketDTO;
import com.sobchenko.sneakershop.service.BucketService;
import com.sobchenko.sneakershop.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.security.Principal;

@Controller
@RequestMapping("/bucket")
public class BucketController {
    private final BucketService bucketService;
    private final ProductService productService;

    @Autowired
    public BucketController(BucketService bucketService, ProductService productService) {
        this.bucketService = bucketService;
        this.productService = productService;
    }

    @GetMapping("/show")
    public ModelAndView showBucket(ModelAndView modelAndView, Principal principal) {
        BucketDTO bucketDTO = bucketService.getBucketByUser(principal.getName());
        modelAndView.addObject("bucket", bucketDTO);
        modelAndView.setViewName("bucket");
        return modelAndView;
    }

    @GetMapping("/delete-product/{id}")
    public String deleteProductFromBucket(@PathVariable(value = "id") String id, Principal principal) {
        productService.deleteFromUserBucket(id, principal.getName());
        if (bucketService.isNotEmptyBucketByUserName(principal.getName())) {
            return "redirect:/bucket/show";
        }
        return "redirect:/";
    }
}