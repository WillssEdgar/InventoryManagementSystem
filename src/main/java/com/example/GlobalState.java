package com.example;

import java.sql.Connection;

/**
 * GlobalState
 */
public class GlobalState {

  private static GlobalState instance;
  private Connection connection;

  private GlobalState() {

  }

  public static synchronized GlobalState getInstance() {
    if (instance == null) {
      instance = new GlobalState();

    }
    return instance;
  }

  public Connection getConnection() {
    return connection;
  }

  public void setConnection(Connection connection) {
    this.connection = connection;
  }
}
