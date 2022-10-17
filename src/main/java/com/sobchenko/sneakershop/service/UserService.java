package com.sobchenko.sneakershop.service;

import com.sobchenko.sneakershop.dto.UserDTO;
import com.sobchenko.sneakershop.model.Role;
import com.sobchenko.sneakershop.model.User;
import com.sobchenko.sneakershop.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserService implements UserDetailsService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public boolean create(UserDTO userDTO) {
        if (userRepository.findUserByEmail(userDTO.getEmail()) != null) {
            return false;
        }
        if (userDTO.getPassword().length() < 8) {
            return false;
        }
        final User user = User.builder()
                .name(userDTO.getUsername())
                .password(passwordEncoder.encode(userDTO.getPassword()))
                .email(userDTO.getEmail())
                .build();
        if (userDTO.getRole() == null) {
            user.setRole(Role.CLIENT);
        } else {
            user.setRole(Role.valueOf(userDTO.getRole()));
        }
        userRepository.save(user);
        return true;
    }

    @Override
    public UserDetails loadUserByUsername(String email) {
        User user = userRepository.findUserByEmail(email);
        if (user == null) {
            throw new UsernameNotFoundException("Not found user with name " + email);
        }
        List<GrantedAuthority> roles = new ArrayList<>();
        roles.add(new SimpleGrantedAuthority(user.getRole().name()));

        return new org.springframework.security.core.userdetails.User(
                user.getName(),
                user.getPassword(),
                roles
        );
    }
}