package com.example.authentication;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.example.GlobalState;
import com.example.models.Company;
import com.example.models.User;

/**
 * Authentication
 */
public class Authentication {

  Connection connection = GlobalState.getInstance().getConnection();

  public Company FetchCompany(String CompanyName) throws SQLException {
    String getCompany = "SELECT * FROM companies WHERE name = ?";

    try (PreparedStatement statement = connection.prepareStatement(getCompany)) {
      statement.setString(1, CompanyName);
      try (ResultSet resultSet = statement.executeQuery()) {
        if (resultSet.next()) {
          int id = resultSet.getInt("id");
          String name = resultSet.getString("name");
          Company com = new Company(id, name);
          System.out.println("Company is: " + com.getName());
          return com;

        } else {
          return null;
        }
      }
    }
  }

  public User SignUp(String FirstName, String LastName, String Password,
      String Email, String Username, String CompanyName) throws SQLException {

    Company company = FetchCompany(CompanyName);

    String sql = "INSERT INTO users (firstname, lastname, username, password, email, company_id) VALUES (?,?,?,?,?,?)";

    try (PreparedStatement statement = connection.prepareStatement(sql)) {
      statement.setString(1, FirstName);
      statement.setString(2, LastName);
      statement.setString(3, Username);
      statement.setString(4, Password);
      statement.setString(5, Email);
      statement.setInt(6, company.getId());
      statement.executeUpdate();
      System.out.println("Set the database");
    }

    User user = FetchUser(Email);

    return user;

  }

  public User FetchUser(String Email) throws SQLException {
    String getUser = "SELECT * FROM users WHERE email = ?";

    try (PreparedStatement statement = connection.prepareStatement(getUser)) {
      statement.setString(1, Email);
      try (ResultSet resultSet = statement.executeQuery()) {
        if (resultSet.next()) {
          int id = resultSet.getInt("id");
          String firstname = resultSet.getString("firstname");
          String lastname = resultSet.getString("lastname");
          String username = resultSet.getString("username");
          String password = resultSet.getString("password");
          String email = resultSet.getString("email");
          int company_id = resultSet.getInt("company_id");

          return new User(id, firstname, lastname, username, password, email, company_id);

        } else {
          return null;
        }
      }
    }
  }

  public User SignIn(String Username, String Password) throws SQLException {
    String sql = "SELECT * FROM users WHERE username = ? AND password = ?";
    try (PreparedStatement statement = connection.prepareStatement(sql)) {
      statement.setString(1, Username);
      statement.setString(2, Password);

      try (ResultSet resultSet = statement.executeQuery()) {

        if (resultSet.next()) {
          int id = resultSet.getInt("id");
          String firstname = resultSet.getString("firstname");
          String lastname = resultSet.getString("lastname");
          String username = resultSet.getString("username");
          String password = resultSet.getString("password");
          String email = resultSet.getString("email");
          int company_id = resultSet.getInt("company_id");

          return new User(id, firstname, lastname, username, password, email, company_id);

        } else {
          return null;
        }
      }
    }
  }
}
