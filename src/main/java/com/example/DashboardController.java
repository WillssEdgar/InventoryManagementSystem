
package com.example;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.example.models.Category;
import com.example.models.User;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

public class DashboardController {
  private User user;
  private Stage stage;
  private List<Button> buttons;

  public DashboardController(User user, Stage stage) {
    this.user = user;
    this.stage = stage;
  }

  Connection connection = GlobalState.getInstance().getConnection();

  @FXML
  private StackPane mainContent;

  @FXML
  private Label UsernameLabel;

  @FXML
  private Label EmailLabel;

  @FXML
  private Button DashboardButton;

  @FXML
  private Button InventoryButton;

  @FXML
  private Button SettingsButton;

  @FXML
  private Label TotalInventoryLabel;

  @FXML
  public void initialize() {
    try {

      float value = getTotalInventory(user.getCompany());
      TotalInventoryLabel.setText("$" + String.valueOf(value));

    } catch (SQLException e) {
      e.printStackTrace();
    }
  }

  // public List<Integer> getCompanyCategories(int company_id) throws SQLException
  // {
  // String sql = "SELECT id FROM category WHERE company_id = ?";
  //
  // List<Integer> lst = new ArrayList<>();
  //
  // try (PreparedStatement statement = connection.prepareStatement(sql)) {
  // statement.setInt(1, company_id);
  // try (ResultSet resultSet = statement.executeQuery()) {
  // while (resultSet.next()) {
  // lst.add(resultSet.getInt("id"));
  // }
  // }
  // }
  // return lst;
  //
  // }
  //
  // public float getTotalInventory(int company_id) throws SQLException {
  // String sql = "SELECT SUM(price) as value FROM products WHERE category_id =
  // ?";
  //
  // List<Integer> lst = getCompanyCategories(company_id);
  // float sum = 0;
  //
  // for (int i = 0; i < lst.size(); i++) {
  // try (PreparedStatement statement = connection.prepareStatement(sql)) {
  // statement.setInt(1, lst.get(i));
  // try (ResultSet resultSet = statement.executeQuery()) {
  // while (resultSet.next()) {
  // float value = resultSet.getFloat("value");
  // sum += value;
  // }
  // }
  // }
  // }
  //
  // return sum;
  // }
  public float getTotalInventory(int company_id) throws SQLException {
    String sql = "SELECT SUM(p.price * p.quantity) as total_value " +
        "FROM products p " +
        "JOIN category c ON p.category_id = c.id " +
        "WHERE c.company_id = ?";

    try (PreparedStatement statement = connection.prepareStatement(sql)) {
      statement.setInt(1, company_id);
      try (ResultSet resultSet = statement.executeQuery()) {
        if (resultSet.next()) {
          return resultSet.getFloat("total_value");
        }
      }
    }
    return 0;
  }

}
