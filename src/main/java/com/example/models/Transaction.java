package com.example.models;

/**
 * transactions
 */
public class Transaction {

  private int id;
  private String name;
  private TransactionType type;
  private int amount;
  private int company_id;

  public Transaction(int id, String name, TransactionType type, int amount, int company_id) {
    this.id = id;
    this.name = name;
    this.type = type;
    this.amount = amount;
    this.company_id = company_id;
  }

  public int getId() {
    return id;
  }

  public String getName() {
    return name;
  }

  public TransactionType getType() {
    return type;
  }

  public int getAmount() {
    return amount;
  }

  public int getCompany() {
    return company_id;
  }
}
