package com.api.app.dao;

import java.util.List;
import java.util.UUID;

import com.api.app.entities.User;

public interface UserDAO {
    User findById(UUID id);

    User findByEmail(String email);

    List<User> findAll();

    void save(User user);

    void update(User user);

    void delete(UUID id);
}
