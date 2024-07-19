
package com.example;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.example.models.User;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

public class NavigationController {
  private User user;
  private Stage stage;
  private List<Button> buttons;

  public NavigationController(User user, Stage stage) {
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

    buttons = new ArrayList<>();
    buttons.add(DashboardButton);
    buttons.add(InventoryButton);
    buttons.add(SettingsButton);
    System.out.println(buttons);

    // Set initial page and button style
    showPage("/com/example/businessproject/Dashboard.fxml", "Dashboard",
        DashboardButton);
    DashboardButton
        .setOnAction(event -> showPage("/com/example/businessproject/Dashboard.fxml", "Dashboard", DashboardButton));
    InventoryButton
        .setOnAction(event -> showPage("/com/example/businessproject/Inventory.fxml", "Inventory", InventoryButton));
    SettingsButton
        .setOnAction(event -> showPage("/com/example/businessproject/Settings.fxml", "Settings", SettingsButton));
  }

  public void showPage(String fxmlFile, String controllerType, Button clickedButton) {

    updateButtonStyles(clickedButton);
    try {
      FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlFile));
      switch (controllerType) {
        case "Dashboard":
          DashboardController dashboardController = new DashboardController(user, stage);
          loader.setController(dashboardController);
          break;
        case "Inventory":
          InventoryController inventoryController = new InventoryController(user, stage);
          loader.setController(inventoryController);
          break;
        case "Settings":
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

  private void updateButtonStyles(Button selectedButton) {
    for (Button button : buttons) {
      if (button == selectedButton) {
        if (!button.getStyleClass().contains("selected")) {
          button.getStyleClass().add("selected");
        }
      } else {
        button.getStyleClass().remove("selected");
      }
    }
  }
}
