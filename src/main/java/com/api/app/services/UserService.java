package com.api.app.services;

import java.util.List;

import com.api.app.dao.UserDAO;
import com.api.app.entities.User;

public class UserService implements Service<User> {
    private final UserDAO userDAO;

    public UserService(UserDAO userDAO) {
        this.userDAO = userDAO;
    }

    @Override
    public void createEntity(User user) {
        userDAO.save(user);
    }

    @Override
    public List<User> getEntities() {
        return userDAO.findAll();
    }

}