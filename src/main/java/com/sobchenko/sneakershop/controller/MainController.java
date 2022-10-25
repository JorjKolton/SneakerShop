package com.sobchenko.sneakershop.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.security.Principal;

@Controller
public class MainController {
    private final ProductController productController;

    @Autowired
    public MainController(ProductController productController) {
        this.productController = productController;
    }

    @GetMapping({"", "/"})
    public ModelAndView viewHomePage(ModelAndView modelAndView, Principal principal) {
        return productController
                .findPaginated(1, "title", "asc", "", "", principal, modelAndView);
    }

    @RequestMapping("/login")
    public String login() {
        return "login";
    }

    @RequestMapping("/login-error")
    public ModelAndView loginError(ModelAndView modelAndView) {
        modelAndView.addObject("loginError", true);
        modelAndView.setViewName("login");
        return modelAndView;
    }
}