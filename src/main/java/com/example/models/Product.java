package com.example.models;

/**
 * Product
 */
public class Product {
  private int id;
  private String name;
  private int quantity;
  private float price;

  public Product(int id, String name, int quantity, float price) {
    this.id = id;
    this.name = name;
    this.quantity = quantity;
    this.price = price;
  }

  public int getId() {
    return id;
  }

  public String getName() {
    return name;
  }

  public float getQuantity() {
    return quantity;
  }

  public float getPrice() {
    return price;
  }

  public void setName(String name) {
    this.name = name;
  }

  public void setQuantity(int quantity) {
    this.quantity = quantity;
  }

  public void setPrice(float price) {
    this.price = price;
  }
}
