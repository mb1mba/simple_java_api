package com.api.app.dao;

import java.util.List;
import com.api.app.entities.User;

public interface UserDAO {
    User findById(int id);

    List<User> findAll();

    void save(User user);

    void update(User user);

    void delete(int id);
}
