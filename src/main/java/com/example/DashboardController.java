
package com.example;

import java.io.IOException;

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

  public DashboardController(User user, Stage stage) {
    this.user = user;
    this.stage = stage;
  }

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
  public void initialize() {
    UsernameLabel.setText("@" + user.getUsername());
    EmailLabel.setText(user.getEmail());
    showPage("/com/example/businessproject/Dashboard.fxml", "Initialization");
    DashboardButton.setOnAction(event -> showPage("/com/example/businessproject/Dashboard.fxml", "Dashboard"));
    InventoryButton.setOnAction(event -> showPage("/com/example/businessproject/Inventory.fxml", "Inventory"));
    SettingsButton.setOnAction(event -> showPage("/com/example/businessproject/Settings.fxml", "Settings"));
  }

  public void showPage(String fxmlFile, String controllerType) {
    try {
      // Parent page = FXMLLoader.load(getClass().getResource(fxmlFile));

      FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlFile));
      switch (controllerType) {
        case "Dashboard":
          DashboardController dashboardController = new DashboardController(user,
              stage);
          loader.setController(dashboardController);
          break;
        case "Inventory":
          InventoryController inventoryController = new InventoryController(user,
              stage);
          loader.setController(inventoryController);
          break;
        case "Settings":
          System.out.println("This is settings");
          break;
        default:
          break;
      }

      Parent page = loader.load();
      mainContent.getChildren().clear();
      mainContent.getChildren().add(page);
    } catch (IOException e) {
      e.printStackTrace();
    }
  }
}
