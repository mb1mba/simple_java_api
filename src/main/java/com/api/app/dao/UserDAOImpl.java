package com.api.app.dao;

import com.api.app.database.Database;
import com.api.app.entities.User;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class UserDAOImpl implements UserDAO {
    private final Database db = new Database();

    @Override
    public User findById(UUID id) {
        String query = "SELECT * FROM users WHERE id = ?";
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;

        try {
            connection = db.connect();
            statement = db.prepareStatement(connection, query);
            statement.setObject(1, id);

            resultSet = statement.executeQuery();

            if (resultSet.next()) {
                return mapToUser(resultSet);
            }
        } catch (SQLException e) {
            System.out.println(e);
        } finally {
            db.closeResultSet(resultSet);
            db.closeStatement(statement);
            db.closeConnection(connection);
        }

        return null;
    }

    @Override
    public User findByEmail(String email) {
        String query = "SELECT * FROM users WHERE email = ?";
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;

        try {
            connection = db.connect();
            statement = db.prepareStatement(connection, query);
            statement.setString(1, email);

            resultSet = statement.executeQuery();

            if (resultSet.next()) {
                return mapToUser(resultSet);
            }
        } catch (SQLException e) {
            System.out.println(e);
        } finally {
            db.closeResultSet(resultSet);
            db.closeStatement(statement);
            db.closeConnection(connection);
        }

        return null;
    }

    @Override
    public List<User> findAll() {
        String query = "SELECT * FROM users";
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        List<User> users = new ArrayList<>();

        try {
            connection = db.connect();
            statement = db.prepareStatement(connection, query);

            resultSet = statement.executeQuery();

            while (resultSet.next()) {
                User user = mapToUser(resultSet);
                users.add(user);
            }
        } catch (SQLException e) {
            System.out.println(e);
        } finally {
            db.closeResultSet(resultSet);
            db.closeStatement(statement);
            db.closeConnection(connection);
        }

        return users;
    }

    @Override
    public void save(User user) {
        String query = "INSERT INTO users (name, email) VALUES (?, ?)";
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;

        try {
            connection = db.connect();

            statement = db.prepareStatement(connection, query);
            statement.setString(1, user.getName());
            statement.setString(2, user.getEmail());

            resultSet = statement.executeQuery();
        } catch (SQLException e) {
            System.out.println(e);
        } finally {
            db.closeResultSet(resultSet);
            db.closeStatement(statement);
            db.closeConnection(connection);
            System.out.println("User created successfully");
        }
    }

    @Override
    public void update(User user) {
        String query = "UPDATE users SET name = ?, email = ? WHERE id = ?";
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;

        try {
            connection = db.connect();
            statement = db.prepareStatement(connection, query);
            statement.setString(1, user.getName());
            statement.setString(2, user.getEmail());
            statement.setString(3, user.getId());

            resultSet = statement.executeQuery();
        } catch (SQLException e) {
            System.out.println(e);
        } finally {
            db.closeResultSet(resultSet);
            db.closeStatement(statement);
            db.closeConnection(connection);
            System.out.println("User updated successfully");
        }
    }

    @Override
    public void delete(UUID id) {
        String query = "DELETE FROM users WHERE id = ?";
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;

        try {
            connection = db.connect();
            statement = db.prepareStatement(connection, query);
            statement.setObject(1, id);

            resultSet = statement.executeQuery();

        } catch (SQLException e) {
            System.out.println(e);
        } finally {
            db.closeResultSet(resultSet);
            db.closeStatement(statement);
            db.closeConnection(connection);
            System.out.println("User deleted successfully");
        }
    }

    private User mapToUser(ResultSet resultSet) throws SQLException {
        User user = new User(null, null, null);
        user.setId(resultSet.getString("id"));
        user.setName(resultSet.getString("name"));
        user.setEmail(resultSet.getString("email"));
        return user;
    }
}
