package com.shima.chiebukuro.repository;

import java.util.Optional;

import org.springframework.stereotype.Repository;

import com.shima.chiebukuro.model.User;

@Repository
public class UserRepositoryImpl implements UserRepository {
    
    private final SQLiteDBOperations dbOperations;

    public UserRepositoryImpl(SQLiteDBOperations dbOperations) {
        this.dbOperations = dbOperations;
    }

    @Override
    public Optional<User> findByUsername(String username) {
        return dbOperations.findUserByUsername(username);
    }

    @Override
    public void save(User user) {
        dbOperations.insertUser(user.getUsername(), user.getPassword(), user.getRole());
    }

    @Override
    public boolean existsByUsername(String username) {
        return dbOperations.findUserByUsername(username).isPresent();
    }
}
