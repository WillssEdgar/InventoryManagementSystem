
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
    // UsernameLabel.setText("@" + user.getUsername());
    // EmailLabel.setText(user.getEmail());
    showPage("/com/example/businessproject/Dashboard.fxml");
    DashboardButton.setOnAction(event -> showPage("/com/example/businessproject/Dashboard.fxml"));
    InventoryButton.setOnAction(event -> showPage("/com/example/businessproject/Inventory.fxml"));
    SettingsButton.setOnAction(event -> showPage("/com/example/businessproject/Settings.fxml"));
  }

  public void showPage(String fxmlFile) {
    try {
      Parent page = FXMLLoader.load(getClass().getResource(fxmlFile));
      mainContent.getChildren().clear();
      mainContent.getChildren().add(page);
    } catch (IOException e) {
      e.printStackTrace();
    }
  }
}
