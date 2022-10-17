package com.sobchenko.sneakershop.controller;

import com.sobchenko.sneakershop.dto.UserDTO;
import com.sobchenko.sneakershop.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.BindingResult;
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

    @Transactional
    @PostMapping
    public String saveUser(@ModelAttribute("user") @Valid UserDTO userDTO, BindingResult bindingResult, ModelAndView modelAndView) {
        if (bindingResult.hasErrors()) {
            return "redirect:/users/create";
        }
        modelAndView.addObject("user", userDTO);
        if (!userService.create(userDTO)) {
            return "redirect:/users/create";
        }
        return "redirect:/";
    }
}
