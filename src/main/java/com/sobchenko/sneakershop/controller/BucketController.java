package com.sobchenko.sneakershop.controller;

import com.sobchenko.sneakershop.dto.BucketDTO;
import com.sobchenko.sneakershop.service.BucketService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.security.Principal;

@Controller
@RequestMapping("/bucket")
public class BucketController {
    private final BucketService bucketService;

    @Autowired
    public BucketController(BucketService bucketService) {
        this.bucketService = bucketService;
    }

    @GetMapping("/show")
    public ModelAndView showBucket(ModelAndView modelAndView, Principal principal) {
        BucketDTO bucketDTO = bucketService.getBucketByUser(principal.getName());
        modelAndView.addObject("bucket", bucketDTO);
        modelAndView.setViewName("bucket");
        return modelAndView;
    }
}