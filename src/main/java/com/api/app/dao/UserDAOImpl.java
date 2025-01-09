package com.api.app.dao;

import com.api.app.database.Database;
import com.api.app.entities.User;
import java.sql.*;
import java.util.List;

public class UserDAOImpl implements UserDAO {
    private final Database db = new Database();

    @Override
    public User findById(int id) {
        String query = "SELECT * FROM users WHERE id = ?";
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;

        try {
            connection = db.connect();
            statement = db.prepareStatement(connection, query);
            statement.setInt(1, id);

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

        throw new UnsupportedOperationException("Unimplemented method 'findAll'");
    }

    @Override
    public void save(User user) {
        String query = "INSERT INTO users (id, name, email) VALUES (?, ?, ?)";
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;

        try {
            connection = db.connect();

            statement = db.prepareStatement(connection, query);
            statement.setInt(1, user.getId());
            statement.setString(2, user.getName());
            statement.setString(3, user.getEmail());

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

        throw new UnsupportedOperationException("Unimplemented method 'update'");
    }

    @Override
    public void delete(int id) {

        throw new UnsupportedOperationException("Unimplemented method 'delete'");
    }

    private User mapToUser(ResultSet resultSet) throws SQLException {
        User user = new User(0, null, null);
        user.setId(resultSet.getInt("id"));
        user.setName(resultSet.getString("name"));
        user.setEmail(resultSet.getString("email"));
        return user;
    }
}
