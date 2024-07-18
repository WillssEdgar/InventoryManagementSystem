package com.example;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.example.models.Category;
import com.example.models.User;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

/**
 * InventoryController
 */
public class InventoryController {

  private User user;
  private Stage stage;

  public InventoryController(User user, Stage stage) {
    this.user = user;
    this.stage = stage;
  }

  Connection connection = GlobalState.getInstance().getConnection();

  @FXML
  private TableView<Category> Table;
  @FXML
  private TableColumn<Category, String> CategoriesColumn;

  @FXML
  private TextField AddColumnTextField;

  @FXML
  private Button AddColumnButton;

  @FXML
  public void initialize() {

    CategoriesColumn.setCellValueFactory(new PropertyValueFactory<>("name"));

    try {
      List<Category> strlst = getCategories();

      Table.getItems().addAll(strlst);
    } catch (SQLException e) {
      e.printStackTrace();
    }

  }

  @FXML
  public void removeCategory() throws SQLException {
    Category cat = Table.getSelectionModel().getSelectedItem();

    String sql = "DELETE FROM category where name = ?";

    try (PreparedStatement statement = connection.prepareStatement(sql)) {
      statement.setString(1, cat.getName());
      statement.executeUpdate();
    }

    updateTable();
  }

  @FXML
  public void addCategory() throws SQLException {
    int company_id = user.getCompany();
    String name = AddColumnTextField.getText();

    String sql = "INSERT INTO category (name, company_id) VALUES (?,?)";

    try (PreparedStatement statement = connection.prepareStatement(sql)) {
      statement.setString(1, name);
      statement.setInt(2, company_id);
      statement.executeUpdate();
    }
    AddColumnTextField.clear();
    updateTable();

  }

  private void updateTable() {
    try {
      List<Category> categories = getCategories();
      Table.getItems().setAll(categories); // Replace all items in the table
    } catch (SQLException e) {
      e.printStackTrace();
    }
  }

  public List<Category> getCategories() throws SQLException {
    int userInt = user.getCompany();
    String sql = "SELECT * FROM category where company_id = ?";
    List<Category> lst = new ArrayList<>();

    try (PreparedStatement statement = connection.prepareStatement(sql)) {
      statement.setInt(1, userInt);
      try (ResultSet resultSet = statement.executeQuery()) {
        while (resultSet.next()) {
          int id = resultSet.getInt("id");
          String name = resultSet.getString("name");

          lst.add(new Category(id, name));
        }
      }
    }
    return lst;
  }
}
