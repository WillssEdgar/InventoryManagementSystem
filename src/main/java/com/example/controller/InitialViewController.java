package com.example.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;

import com.example.authentication.Authentication;
import com.example.models.User;

public class InitialViewController {

  private final Connection connection;
  private final Stage stage;
  private Authentication authentication;

  public InitialViewController(Connection connection, Stage stage) {
    this.connection = connection;
    this.stage = stage;
    this.authentication = new Authentication();
  }

  @FXML
  private TextField UsernameSignInInput;
  @FXML
  private TextField PasswordSignInInput;
  @FXML
  private Button SignInButton;
  @FXML
  private Button CreateAccountButton;

  @FXML
  public void initialize() {

    SignInButton.setOnAction(event -> {
      if (UsernameSignInInput.getText() != null && PasswordSignInInput.getText() != null) {
        try {
          User user = authentication.SignIn(UsernameSignInInput.getText(), PasswordSignInInput.getText());
          openDashboard(user);

        } catch (Exception e) {
          e.printStackTrace();
        }
      }
    });

    // Set up the event handler for the CreateAccountButton
    CreateAccountButton.setOnAction(event -> {
      try {
        openSignUpWindow();
      } catch (IOException e) {
        e.printStackTrace();
      }
    });
  }

  /**
   * Creates the first new window when logging in
   */
  @FXML
  public void newWindow(ActionEvent event) throws IOException {

    if (UsernameSignInInput.getText() != null && PasswordSignInInput.getText() != null) {
      try {
        authentication.SignIn(UsernameSignInInput.getText(), PasswordSignInInput.getText());
      } catch (SQLException e) {
        e.printStackTrace();
      }
    }
  }

  /**
   * Opens the SignUp window
   */
  private void openSignUpWindow() throws IOException {
    FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/businessproject/SignUp.fxml"));

    SignUpController controller = new SignUpController(connection, stage);
    loader.setController(controller);

    // Load the FXML file
    Parent root = loader.load();

    // Create and set the scene
    Scene scene = new Scene(root);
    stage.setScene(scene);
    stage.setTitle("Business Storage Manager - Sign Up");
    stage.show();
  }

  /**
   * Opens the SignUp window
   */
  private void openDashboard(User user) throws IOException {
    // FXMLLoader loader = new
    // FXMLLoader(getClass().getResource("/com/example/businessproject/Dashboard.fxml"));

    FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/businessproject/Navigation.fxml"));
    NavigationController controller = new NavigationController(user, stage);
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
