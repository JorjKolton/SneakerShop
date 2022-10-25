package com.sobchenko.sneakershop.service;

import com.sobchenko.sneakershop.dto.UserDTO;
import com.sobchenko.sneakershop.model.User;
import com.sobchenko.sneakershop.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserService implements UserDetailsService {
    private static final Logger LOGGER = LoggerFactory.getLogger(UserService.class);
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Transactional
    public boolean create(UserDTO userDTO) {
        if (userRepository.existsByEmail(userDTO.getEmail()) || userRepository.existsByName(userDTO.getUsername())) {
            return false;
        }
        if (userDTO.getPassword().length() < 8) {
            return false;
        }
        final User user = UserDTO.fromDTO(userDTO);
        user.setPassword(passwordEncoder.encode(userDTO.getPassword()));
        return save(user);
    }

    @Transactional
    public boolean save(User user) {
        userRepository.save(user);
        LOGGER.debug("User with username '{}' was created", user.getName());
        return true;
    }

    public User getUserByEmail(String email) {
        User user = userRepository.findUserByEmail(email);
        if (user == null) {
            throw new UsernameNotFoundException("Not found user with email " + email);
        }
        return user;
    }

    public User getUserByName(String name) {
        User user = userRepository.findUserByName(name);
        if (user == null) {
            throw new UsernameNotFoundException("Not found user with name " + name);
        }
        return user;
    }

    @Override
    public UserDetails loadUserByUsername(String email) {
        User user = getUserByEmail(email);
        List<GrantedAuthority> roles = new ArrayList<>();
        roles.add(new SimpleGrantedAuthority(user.getRole().name()));

        return new org.springframework.security.core.userdetails.User(
                user.getName(),
                user.getPassword(),
                roles
        );
    }
}