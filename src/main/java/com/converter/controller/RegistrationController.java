package com.converter.controller;

import com.converter.entity.RegistrationForm;
import com.converter.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.validation.Valid;

@Slf4j
@Controller
@RequestMapping("/register")
public class RegistrationController {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public RegistrationController(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @ModelAttribute("registrationForm")
    public RegistrationForm regForm() {
        return new RegistrationForm();
    }

    @GetMapping
    public String registrationForm() {
        return "registration";
    }

    @PostMapping
    public String register(@Valid RegistrationForm registrationForm, BindingResult bindingResult) {
        System.out.println("reg");
        if (userRepository.findByUsername(registrationForm.getUsername()).isPresent()) {
            bindingResult.addError(new FieldError("username", "username", "This username has already taken"));
        }

        if (bindingResult.hasErrors()) {
            return "registration";
        }

        userRepository.save(registrationForm.toUser(passwordEncoder));

        return "redirect:/login";
    }
}
