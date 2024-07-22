
package com.example;

import java.io.IOException;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;

import com.example.models.User;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class NavigationController {
  private User user;
  private Stage stage;
  private List<Button> buttons;

  public NavigationController(User user, Stage stage) {
    this.user = user;
    this.stage = stage;
  }

  Connection connection = GlobalState.getInstance().getConnection();

  @FXML
  private VBox mainContent;

  @FXML
  private Label UsernameLabel;

  @FXML
  private Label EmailLabel;

  @FXML
  private Button DashboardButton;

  @FXML
  private Button InventoryButton;

  @FXML
  private Button ProfileButton;

  @FXML
  private Button LogoutButton;

  @FXML
  public void initialize() {
    UsernameLabel.setText("@" + user.getUsername());
    EmailLabel.setText(user.getEmail());

    buttons = new ArrayList<>();
    buttons.add(DashboardButton);
    buttons.add(InventoryButton);
    buttons.add(ProfileButton);
    System.out.println(buttons);

    // Set initial page and button style
    showPage("/com/example/businessproject/Dashboard.fxml", "Dashboard",
        DashboardButton);
    DashboardButton
        .setOnAction(event -> showPage("/com/example/businessproject/Dashboard.fxml", "Dashboard", DashboardButton));
    InventoryButton
        .setOnAction(event -> showPage("/com/example/businessproject/Inventory.fxml", "Inventory", InventoryButton));
    ProfileButton
        .setOnAction(event -> showPage("/com/example/businessproject/UserProfile.fxml", "Profile", ProfileButton));
  }

  @FXML
  public void Logout() throws IOException {

    // Create an FXMLLoader instance
    FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/businessproject/SignIn.fxml"));

    InitialViewController controller = new InitialViewController(connection, stage);

    // Set the controller on the FXMLLoader
    loader.setController(controller);

    // Load the FXML file
    Parent root = loader.load();

    // Create a new Scene with the loaded FXML
    Scene scene = new Scene(root);

    // Optionally add a stylesheet if needed
    scene.getStylesheets().add(getClass().getResource("/com/example/businessproject/Navigation.css").toExternalForm());
    stage.close();
    // Obtain the current stage
    Stage primaryStage = (Stage) stage.getScene().getWindow();

    // Set the new scene on the current stage
    primaryStage.setScene(scene);

    // Optionally set the stage title and size
    primaryStage.setTitle("Business Storage Manager");
    primaryStage.setWidth(600);
    primaryStage.setHeight(600);

    // Show the stage (this is redundant but ensures visibility)
    primaryStage.show();
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
        case "Profile":
          ProfileController profileController = new ProfileController(user, stage);
          loader.setController(profileController);
          break;
        default:
          break;
      }

      Parent page = loader.load();
      VBox.setVgrow(page, javafx.scene.layout.Priority.ALWAYS);
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
