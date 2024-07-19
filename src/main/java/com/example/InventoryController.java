package com.example;

import java.math.BigDecimal;
import java.math.RoundingMode;
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
  private TableView<Product> Table2;
  @FXML
  private TableColumn<Product, String> Products;
  @FXML
  private TableColumn<Product, Integer> Quantity;
  @FXML
  private TableColumn<Product, Float> Price;

  @FXML
  private TableColumn<Category, String> CategoriesColumn;

  @FXML
  private TextField AddColumnTextField;
  @FXML
  private TextField ProductName;
  @FXML
  private TextField ProductQuantity;
  @FXML
  private TextField ProductPrice;

  @FXML
  private Button AddColumnButton;

  @FXML
  private TextField RemoveProductName;
  @FXML
  private TextField RemoveProductQuantity;

  @FXML
  public void initialize() {

    CategoriesColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
    Products.setCellValueFactory(new PropertyValueFactory<>("name"));
    Quantity.setCellValueFactory(new PropertyValueFactory<>("quantity"));
    Price.setCellValueFactory(new PropertyValueFactory<>("price"));

    try {
      List<Category> strlst = getCategories();
      Table.getItems().addAll(strlst);
    } catch (SQLException e) {
      e.printStackTrace();
    }

  }

  public void addTransaction(Product product, TransactionType type, int amount) throws SQLException {

    String sql = "INSERT INTO transactions (name, type, amount, company_id) VALUES (?,?,?,?)";

    try (PreparedStatement statement = connection.prepareStatement(sql)) {
      statement.setString(1, product.getName());
      statement.setString(2, type.name());
      statement.setInt(3, amount);
      statement.setInt(4, user.getCompany());
      statement.executeUpdate();
    }
    updateProductTable();

  }

  @FXML
  public void removeCategory() throws SQLException {
    Category category = Table.getSelectionModel().getSelectedItem();
    if (category == null)
      return;

    String sql = "DELETE FROM category WHERE name = ?";

    try (PreparedStatement statement = connection.prepareStatement(sql)) {
      statement.setString(1, category.getName());
      statement.executeUpdate();
    }

    updateCategoryTable();
  }

  @FXML
  public void removeProduct() throws SQLException {
    Product product = Table2.getSelectionModel().getSelectedItem();
    if (product == null)
      return;

    int amountToRemove = Integer.valueOf(RemoveProductQuantity.getText());

    if (product.getQuantity() <= amountToRemove) {
      String sql = "DELETE FROM products WHERE name = ?";
      try (PreparedStatement statement = connection.prepareStatement(sql)) {
        statement.setString(1, product.getName());
        statement.executeUpdate();
      }

    } else {
      String sql = "UPDATE products SET quantity = quantity - ? WHERE name = ?";
      try (PreparedStatement statement = connection.prepareStatement(sql)) {
        statement.setInt(1, amountToRemove); // Set the quantity to subtract
        statement.setString(2, product.getName()); // Set the product ID
        statement.executeUpdate();

      }
    }
    RemoveProductName.clear();
    RemoveProductQuantity.clear();
    updateProductTable();
    addTransaction(product, TransactionType.SOLD, amountToRemove);
  }

  @FXML
  public void fillProductTable() throws SQLException {
    Category category = Table.getSelectionModel().getSelectedItem();
    if (category == null)
      return;

    String sql = "SELECT * FROM products where category_id = ?";
    List<Product> products = new ArrayList<>();

    try (PreparedStatement statement = connection.prepareStatement(sql)) {
      statement.setInt(1, category.getId());
      try (ResultSet resultSet = statement.executeQuery()) {
        while (resultSet.next()) {
          int id = resultSet.getInt("id");
          String name = resultSet.getString("name");
          int quantity = resultSet.getInt("quantity");
          Float price = resultSet.getFloat("price");
          int category_id = resultSet.getInt("category_id");

          products.add(new Product(id, name, quantity, price, category_id));

        }
      }
    }
    Table2.getItems().addAll(products);

  }

  @FXML
  public void addProduct() throws SQLException {

    Category category = Table.getSelectionModel().getSelectedItem();
    if (category == null)
      return;

    String productName = ProductName.getText();
    int quantity = Integer.valueOf(ProductQuantity.getText());
    String price = ProductPrice.getText();
    BigDecimal bigDecimal = new BigDecimal(price);
    bigDecimal = bigDecimal.setScale(2, RoundingMode.HALF_UP);
    float float_price = bigDecimal.floatValue();

    String sql = "INSERT INTO products (name, quantity, price, category_id) VALUES (?,?,?,?)";

    try (PreparedStatement statement = connection.prepareStatement(sql)) {
      statement.setString(1, productName);
      statement.setInt(2, quantity);
      statement.setFloat(3, float_price);
      statement.setInt(4, category.getId());
      statement.executeUpdate();
    }
    ProductName.clear();
    ProductQuantity.clear();
    ProductPrice.clear();
    updateProductTable();
    addTransaction(new Product(0, productName, quantity, float_price, category.getId()), TransactionType.ADD, quantity);
  }

  @FXML
  public void fillProductName() {
    Product product = Table2.getSelectionModel().getSelectedItem();
    if (product != null) {
      RemoveProductName.setText(product.getName());
    }

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
    updateCategoryTable();

  }

  private void updateCategoryTable() {
    try {
      List<Category> categories = getCategories();
      Table.getItems().setAll(categories); // Replace all items in the table
    } catch (SQLException e) {
      e.printStackTrace();
    }
  }

  private void updateProductTable() {
    try {
      List<Product> products = getProducts();
      Table2.getItems().setAll(products); // Replace all items in the table
    } catch (SQLException e) {
      e.printStackTrace();
    }
  }

  public List<Product> getProducts() throws SQLException {
    Category category = Table.getSelectionModel().getSelectedItem();
    if (category == null)
      return new ArrayList<>();

    String sql = "SELECT * FROM products where category_id = ?";
    List<Product> products = new ArrayList<>();

    try (PreparedStatement statement = connection.prepareStatement(sql)) {
      statement.setInt(1, category.getId());
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

  public List<Category> getCategories() throws SQLException {
    int companyId = user.getCompany();
    String sql = "SELECT * FROM category where company_id = ?";
    List<Category> categories = new ArrayList<>();

    try (PreparedStatement statement = connection.prepareStatement(sql)) {
      statement.setInt(1, companyId);
      try (ResultSet resultSet = statement.executeQuery()) {
        while (resultSet.next()) {
          int id = resultSet.getInt("id");
          String name = resultSet.getString("name");
          int company_id = resultSet.getInt("company_id");

          categories.add(new Category(id, name, company_id));
        }
      }
    }
    return categories;
  }
}
