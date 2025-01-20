package com.api.app.services;

import com.api.app.dao.UserDAO;
import com.api.app.entities.User;

import java.util.List;
import java.util.UUID;

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

    @Override
    public void deleteEntity(String id) {
        UUID userId = UUID.fromString(id);
        User user = userDAO.findById(userId);

        if (user != null) {
            userDAO.delete(userId);
        } else {
            throw new RuntimeException("User not found.");
        }
    }

    @Override
    public Class<User> getEntityType() {
        return User.class;
    }

    @Override
    public boolean doesEntityExist(String id) {
        UUID userId = UUID.fromString(id);
        User existingUser = userDAO.findById(userId);
        if (existingUser == null) {
            return false;
        }

        return true;
    }
}