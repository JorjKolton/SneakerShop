package com.sobchenko.sneakershop.controller;

import com.sobchenko.sneakershop.dto.UserDTO;
import com.sobchenko.sneakershop.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;
import java.util.List;

@Controller
@RequestMapping("/users")
public class UserController {
    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/create")
    public ModelAndView createUser(ModelAndView modelAndView) {
        final UserDTO userDTO = new UserDTO();
        modelAndView.addObject("user", userDTO);
        modelAndView.addObject("roles", List.of("MANAGER", "ADMIN"));
        modelAndView.setViewName("registration");
        return modelAndView;
    }

    @RequestMapping("/registration-error")
    public ModelAndView loginError(UserDTO userDTO, ModelAndView modelAndView) {
        modelAndView.addObject("registrationError", true);
        modelAndView.addObject("user", userDTO);
        modelAndView.setViewName("registration");
        return modelAndView;
    }

    @PostMapping
    public String saveUser(@ModelAttribute("user") @Valid UserDTO userDTO, ModelAndView modelAndView) {
        modelAndView.addObject("user", userDTO);
        if (!userService.create(userDTO)) {
            return "redirect:/users/registration-error";
        }
        return "redirect:/";
    }
}