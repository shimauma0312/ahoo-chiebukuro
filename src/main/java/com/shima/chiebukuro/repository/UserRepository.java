package com.shima.chiebukuro.repository;

import java.util.Optional;

import com.shima.chiebukuro.model.User;

public interface UserRepository {
    Optional<User> findByUsername(String username);
    void save(User user);
    boolean existsByUsername(String username);
}
