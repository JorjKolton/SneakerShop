package com.sobchenko.sneakershop.repository;

import com.sobchenko.sneakershop.model.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends CrudRepository<User, String> {
    User findUserByEmail(String email);
}