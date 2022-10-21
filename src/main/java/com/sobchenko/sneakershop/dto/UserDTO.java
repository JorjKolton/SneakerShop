package com.sobchenko.sneakershop.dto;

import com.sobchenko.sneakershop.model.Role;
import com.sobchenko.sneakershop.model.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.Email;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserDTO {
    private String username;
    private String password;
    @Email
    private String email;
    private String role;

    public static User fromDTO(UserDTO userDTO) {
        final User user = User.builder()
                .name(userDTO.getUsername())
                .password(userDTO.getPassword())
                .email(userDTO.getEmail())
                .build();

        if (userDTO.getRole() == null) {
            user.setRole(Role.CLIENT);
        } else {
            user.setRole(Role.valueOf(userDTO.getRole()));
        }

        return user;
    }
}