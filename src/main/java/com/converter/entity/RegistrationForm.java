package com.converter.entity;

import lombok.Data;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.validation.constraints.Size;

@Data
public class RegistrationForm {
    @Size(min = 4)
    private String username;

    @Size(min = 7)
    private String password;

    public User toUser(PasswordEncoder passwordEncoder) {

        return new User(username, passwordEncoder.encode(password));
    }
}
