package com.shima.chiebukuro.service;

import com.shima.chiebukuro.model.User;

public interface UserService {
    void registerUser(String username, String password);
    User getCurrentUser();
    boolean existsByUsername(String username);
}
