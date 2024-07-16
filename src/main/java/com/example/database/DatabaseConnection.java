package com.example.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

import com.example.models.Company;

/**
 * DatabaseConnection
 */
public class DatabaseConnection {
  // jdbc:postgresql://aws-0-us-east-1.pooler.supabase.com:6543/postgres?user=postgres.sktduyxvvrhywzephyaa&password=[YOUR-PASSWORD]
  static String url = "jdbc:postgresql://aws-0-us-east-1.pooler.supabase.com:6543/postgres?user=postgres.sktduyxvvrhywzephyaa&password=mCNaJmt7ehuSHK";
  static String user = "postgres.sktduyxvvrhywzephyaa";
  static String password = "mCNaJmt7ehuSHK";

  public static Connection getConnection() {
    // Supabase connection details

    Connection conn = null;

    try {
      // Establishing the connection
      conn = DriverManager.getConnection(url, user, password);
      System.out.println("Connected to the database!");

    } catch (Exception e) {
      e.printStackTrace();
    }

    return conn;
  }

}
