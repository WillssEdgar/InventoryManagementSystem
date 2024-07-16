package com.example;

import java.io.IOException;
import java.sql.Connection;

import com.example.authentication.Authentication;
import com.example.models.User;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

/**
 * SignUpController
 */
public class SignUpController {
  private final Connection connection;
  private final Stage stage;
  Authentication authentication;

  public SignUpController(Connection connection, Stage stage) {
    this.connection = connection;
    this.stage = stage;
    this.authentication = new Authentication();
  }

  @FXML
  private TextField FirstNameSignUpInput;
  @FXML
  private TextField LastNameSignUpInput;
  @FXML
  private TextField UsernameSignUpInput;
  @FXML
  private TextField EmailSignUpInput;
  @FXML
  private TextField PasswordSignUpInput;
  @FXML
  private TextField CompanyNameSignUpInput;
  @FXML
  private Button SignUpButton;
  @FXML
  private Button BackButton;

  @FXML
  public void initialize() {

    SignUpButton.setOnAction(event -> {
      try {
        if (UsernameSignUpInput.getText() != null &&
            PasswordSignUpInput.getText() != null &&
            EmailSignUpInput.getText() != null &&
            FirstNameSignUpInput.getText() != null &&
            LastNameSignUpInput.getText() != null &&
            CompanyNameSignUpInput.getText() != null) {
          User user = authentication.SignUp(FirstNameSignUpInput.getText(), LastNameSignUpInput.getText(),
              PasswordSignUpInput.getText(), EmailSignUpInput.getText(), UsernameSignUpInput.getText(),
              CompanyNameSignUpInput.getText());
          openDashboard(user);
        }
      } catch (Exception e) {
        e.printStackTrace();
      }
    });
    // Set up the event handler for the CreateAccountButton
    BackButton.setOnAction(event -> {
      try {
        openSignInWindow();
      } catch (IOException e) {
        e.printStackTrace();
      }
    });
  }

  /**
   * Opens the SignUp window
   */
  private void openSignInWindow() throws IOException {
    FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/businessproject/SignIn.fxml"));

    InitialViewController controller = new InitialViewController(connection, stage);
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
    FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/businessproject/Dashboard.fxml"));

    DashboardController controller = new DashboardController(user, stage);
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
