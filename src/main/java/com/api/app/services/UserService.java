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
    public Class<User> getEntityType() {
        return User.class;
    }

    @Override
    public boolean doesEntityExist(User user) {
        int user_id = user.getId();

        User existingUser = userDAO.findById(user_id);

        if (existingUser == null) {
            return false;
        }

        return true;
    }
}