
package com.example;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.example.models.Category;
import com.example.models.Product;
import com.example.models.Transaction;
import com.example.models.TransactionType;
import com.example.models.User;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
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
  private TableView<Product> LowStockProductsTable;
  @FXML
  private TableColumn<Product, String> LowStockProductColumn;
  @FXML
  private TableColumn<Product, Integer> LowStockQuantityColumn;
  @FXML
  private TableColumn<Product, Float> LowStockPriceColumn;

  @FXML
  private TableView<Transaction> TransactionTable;
  @FXML
  private TableColumn<Transaction, String> TransactionProductColumn;
  @FXML
  private TableColumn<Transaction, TransactionType> TransactionTypeColumn;
  @FXML
  private TableColumn<Transaction, Integer> TransactionAmountColumn;

  @FXML
  public void initialize() {
    try {

      float value = getTotalInventory(user.getCompany());
      TotalInventoryLabel.setText("$" + String.valueOf(value));

      LowStockProductColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
      LowStockQuantityColumn.setCellValueFactory(new PropertyValueFactory<>("quantity"));
      LowStockPriceColumn.setCellValueFactory(new PropertyValueFactory<>("price"));

      List<Product> products = getLowStockProducts();
      LowStockProductsTable.getItems().addAll(products);

      TransactionProductColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
      TransactionTypeColumn.setCellValueFactory(new PropertyValueFactory<>("type"));
      TransactionAmountColumn.setCellValueFactory(new PropertyValueFactory<>("amount"));

      List<Transaction> transactions = getTransactionList();
      TransactionTable.getItems().addAll(transactions);

    } catch (SQLException e) {
      e.printStackTrace();
    }
  }

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

  public List<Product> getLowStockProducts() throws SQLException {
    String sql = "SELECT * " +
        "FROM products p " +
        "JOIN category c ON p.category_id = c.id " +
        "WHERE c.company_id = ? AND p.quantity < 15";
    List<Product> products = new ArrayList<>();

    try (PreparedStatement statement = connection.prepareStatement(sql)) {
      statement.setInt(1, user.getCompany());
      try (ResultSet resultSet = statement.executeQuery()) {
        while (resultSet.next()) {

          int id = resultSet.getInt("id");
          String name = resultSet.getString("name");
          int quantity = resultSet.getInt("quantity");
          float price = resultSet.getFloat("price");
          int category_id = resultSet.getInt("category_id");

          products.add(new Product(id, name, quantity, price, category_id));

        }
      }
    }
    return products;
  }

  public List<Transaction> getTransactionList() throws SQLException {
    String sql = "SELECT * FROM transactions WHERE company_id = ?";

    List<Transaction> transactions = new ArrayList<>();

    try (PreparedStatement statement = connection.prepareStatement(sql)) {
      statement.setInt(1, user.getCompany());
      try (ResultSet resultSet = statement.executeQuery()) {
        while (resultSet.next()) {
          int id = resultSet.getInt("id");
          String name = resultSet.getString("name");
          TransactionType type = resultSet.getString("type").equals(TransactionType.SOLD.name()) ? TransactionType.SOLD
              : TransactionType.ADD;
          int amount = resultSet.getInt("amount");
          int company_id = resultSet.getInt("company_id");

          transactions.add(new Transaction(id, name, type, amount, company_id));
        }
      }
    }
    return transactions;
  }

}
