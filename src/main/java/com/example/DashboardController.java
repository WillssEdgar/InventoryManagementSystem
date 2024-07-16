
package com.example;

import java.io.IOException;

import com.example.models.User;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;

/**
 * DashboardController
 */
public class DashboardController {
  private User user;
  private Stage stage;

  public DashboardController(User user, Stage stage) {
    this.user = user;
    this.stage = stage;
  }

  @FXML
  private Label UsernameLabel;
  @FXML
  private Label EmailLabel;
  @FXML
  private Button InventoryButton;

  @FXML
  public void initialize() {
    UsernameLabel.setText("@" + user.getUsername());
    EmailLabel.setText(user.getEmail());

    InventoryButton.setOnAction(event -> {
      try {

        openInventory(user);

      } catch (Exception e) {
        e.printStackTrace();
      }
    });
  }

  /**
   * Opens the SignUp window
   */
  private void openInventory(User user) throws IOException {
    FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/businessproject/Inventory.fxml"));

    InventoryController controller = new InventoryController(user, stage);
    loader.setController(controller);

    // Load the FXML file
    Parent root = loader.load();

    // Create and set the scene
    Scene scene = new Scene(root);
    stage.setScene(scene);
    stage.setTitle("Business Storage Manager - Dashboard");
    stage.show();
  }
}
