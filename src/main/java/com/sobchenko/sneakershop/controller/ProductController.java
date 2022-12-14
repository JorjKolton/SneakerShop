package com.sobchenko.sneakershop.controller;

import com.sobchenko.sneakershop.dto.ProductDTO;
import com.sobchenko.sneakershop.model.Product;
import com.sobchenko.sneakershop.service.BucketService;
import com.sobchenko.sneakershop.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.security.Principal;
import java.util.List;

@Controller
@RequestMapping("/products")
public class ProductController {
    private final ProductService productService;
    private final BucketService bucketService;

    @Autowired
    public ProductController(ProductService productService, BucketService bucketService) {
        this.productService = productService;
        this.bucketService = bucketService;
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

    @GetMapping("/page/{pageNo}")
    public ModelAndView findPaginated(@PathVariable(value = "pageNo") int pageNo,
                                      @RequestParam("sortField") String sortField,
                                      @RequestParam("sortDir") String sortDir,
                                      @RequestParam("filter") String filter,
                                      @RequestParam("productID") String productID,
                                      Principal principal, ModelAndView modelAndView) {
        int pageSize = 3; // number of products per page for example
        boolean bucket = false;

        if (principal != null) {
            bucket = bucketService.isNotEmptyBucketByUserName(principal.getName());
            if (!productID.isEmpty()) {
                productService.addToUserBucket(productID, principal.getName());
                bucket = true;
            }
        }

        Page<Product> page = productService.findPaginated(pageNo, pageSize, sortField, sortDir, filter);
        List<Product> products = page.getContent();

        modelAndView.addObject("currentPage", pageNo);
        modelAndView.addObject("totalPages", page.getTotalPages());
        modelAndView.addObject("totalItems", page.getTotalElements());

        modelAndView.addObject("sortField", sortField);
        modelAndView.addObject("sortDir", sortDir);
        modelAndView.addObject("reverseSortDir", sortDir.equals("asc") ? "desc" : "asc");
        modelAndView.addObject("filter", filter);


        modelAndView.addObject("bucket", bucket);
        modelAndView.addObject("productID", "");
        modelAndView.addObject("products", products);
        modelAndView.setViewName("index");
        return modelAndView;
    }
}