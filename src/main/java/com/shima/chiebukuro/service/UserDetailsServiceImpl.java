package com.shima.chiebukuro.service;

import com.shima.chiebukuro.model.User;
import com.shima.chiebukuro.repository.SQLiteDBOperations;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

/**
 * Spring Securityのユーザー詳細サービス実装
 */
@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    private final SQLiteDBOperations dbOperations;

    public UserDetailsServiceImpl(SQLiteDBOperations dbOperations) {
        this.dbOperations = dbOperations;
    }

    /**
     * ユーザー名からユーザー詳細を取得
     * @param username ユーザー名
     * @return ユーザー詳細
     * @throws UsernameNotFoundException ユーザーが見つからない場合
     */
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = dbOperations.findUserByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found: " + username));

        return org.springframework.security.core.userdetails.User
                .withUsername(user.getUsername())
                .password(user.getPassword())
                .roles(user.getRole())
                .build();
    }
}