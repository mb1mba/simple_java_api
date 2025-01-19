package com.api.app.services;

import com.api.app.dao.UserDAO;
import com.api.app.entities.User;

import java.util.List;

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
        userDAO.delete(id);
    }

    @Override
    public Class<User> getEntityType() {
        return User.class;
    }

    @Override
    public boolean doesEntityExist(User user) {
        String userEmail = user.getEmail();

        User existingUser = userDAO.findByEmail(userEmail);
        if (existingUser == null) {
            return false;
        }

        return true;
    }
}