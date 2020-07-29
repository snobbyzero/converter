package com.converter.controller;

import com.converter.entity.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.validation.Valid;

@Slf4j
@Controller
@RequestMapping("/login")
public class LoginController {

    @ModelAttribute(name = "user")
    public User user() {
        return new User();
    }

    @GetMapping
    public String loginForm() {
        return "login";
    }

    @PostMapping(params = {"Login=login"})
    public String auth(@Valid User user, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "login";
        }
        return "redirect:/converter";
    }
}
