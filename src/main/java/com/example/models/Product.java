package com.example.models;

/**
 * Product
 */
public class Product {
  private int id;
  private String name;
  private int quantity;
  private float price;
  private int category_id;

  public Product(int id, String name, int quantity, float price, int category_id) {
    this.id = id;
    this.name = name;
    this.quantity = quantity;
    this.price = price;
    this.category_id = category_id;
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

  public int getCategory() {
    return category_id;
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
