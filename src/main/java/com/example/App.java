
package com.example;

import java.io.IOException;
import java.sql.Connection;

import com.example.controller.InitialViewController;
import com.example.database.DatabaseConnection;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class App extends Application {
  private Connection connection;

  @Override
  public void init() throws Exception {
    connection = DatabaseConnection.getConnection();
    GlobalState.getInstance().setConnection(connection);
  }

  @Override
  public void start(Stage stage) throws IOException {
    // Create an FXMLLoader instance
    FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/businessproject/SignIn.fxml"));

    // Create the controller instance with the database connection
    InitialViewController controller = new InitialViewController(connection, stage);

    // Set the controller on the FXMLLoader
    loader.setController(controller);

    // Load the FXML file
    Parent root = loader.load();

    // Create and set the scene
    Scene scene = new Scene(root);
    scene.getStylesheets().add(getClass().getResource("/com/example/businessproject/Navigation.css").toExternalForm());
    stage.setScene(scene);
    stage.setTitle("Business Storage Manager");
    stage.setWidth(600);
    stage.setHeight(400);
    stage.show();
  }

  @Override
  public void stop() throws Exception {
    System.out.println("The database is Closed");
    connection.close(); // Close connection on stop
    super.stop();
  }

  public static void main(String[] args) {
    launch(args);
  }
}
