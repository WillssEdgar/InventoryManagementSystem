package com.example;

import java.util.ArrayList;
import java.util.List;

import com.example.models.Category;
import com.example.models.User;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
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
    Category cat1 = new Category(1, "Knife");
    Category cat2 = new Category(2, "Gun");

    List<Category> strlst = new ArrayList<>();
    strlst.add(cat1);
    strlst.add(cat2);

    for (int i = 0; i < strlst.size(); i++) {
      Table.getItems().add(strlst.get(i));
    }

  }
}
