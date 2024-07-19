package com.example.models;

/**
 * Category
 */
public class Category {
  private int id;
  private String name;
  private int company_id;

  public Category(int id, String name, int company_id) {
    this.id = id;
    this.name = name;
    this.company_id = company_id;
  }

  public int getId() {
    return id;
  }

  public String getName() {
    return name;
  }

  public int getCompany() {
    return company_id;
  }

  public void setName(String name) {
    this.name = name;
  }
}
