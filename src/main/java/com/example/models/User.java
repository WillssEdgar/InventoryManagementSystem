package com.example.models;

/**
 * User
 */
public class User {
  private int id;
  private String firstname;
  private String lastname;
  private String username;
  private String password;
  private String email;
  private int company_id;

  public User(int id, String firstname, String lastname, String username, String password, String email,
      int company_id) {
    this.id = id;
    this.firstname = firstname;
    this.lastname = lastname;
    this.username = username;
    this.password = password;
    this.email = email;
    this.company_id = company_id;
  }

  public int getId() {
    return id;
  }

  public String getFirstname() {
    return firstname;
  }

  public String getLastname() {
    return lastname;
  }

  public String getUsername() {
    return username;
  }

  public String getPassword() {
    return password;
  }

  public String getEmail() {
    return email;
  }

  public int getCompany() {
    return company_id;
  }
}
