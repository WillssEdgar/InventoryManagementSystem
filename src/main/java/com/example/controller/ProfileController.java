
package com.example.controller;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import com.example.GlobalState;
import com.example.models.Company;
import com.example.models.User;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;

public class ProfileController {
  private User user;
  private Stage stage;
  private List<Button> buttons;

  public ProfileController(User user, Stage stage) {
    this.user = user;
    this.stage = stage;
  }

  Connection connection = GlobalState.getInstance().getConnection();

  @FXML
  private Label CurrentUserProfileLabel;

  @FXML
  private Label CompanyNameProfileLabel;
  @FXML
  private Label FirstNameLabel;
  @FXML
  private Label LastNameLabel;

  @FXML
  public void initialize() {
    CurrentUserProfileLabel.setText("@" + user.getUsername());
    FirstNameLabel.setText(user.getFirstname());
    LastNameLabel.setText(user.getLastname());
    CompanyNameProfileLabel.setText(getCompany(user.getCompany()).getName());

  }

  public Company getCompany(int companyId) {
    String sql = "SELECT * FROM companies WHERE id = ?";
    try (PreparedStatement statement = connection.prepareStatement(sql)) {
      statement.setInt(1, companyId);
      try (ResultSet resultSet = statement.executeQuery()) {
        if (resultSet.next()) { // Ensure there is a result
          int id = resultSet.getInt("id");
          String name = resultSet.getString("name");
          return new Company(id, name);
        } else {
          // Handle the case where no company is found
          System.err.println("Company with ID " + companyId + " not found.");
          return null;
        }
      }
    } catch (SQLException e) {
      e.printStackTrace();
      return null;
    }
  }

}
