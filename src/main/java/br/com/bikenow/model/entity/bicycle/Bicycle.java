package main.java.br.com.bikenow.model.entity.bicycle;

import main.java.br.com.bikenow.model.entity.user.Customer;

public class Bicycle {
  
  private Integer id;

  private String serialNumber;

  private String brand;

  private Double price;

  private Integer year;

  private String description;

  private Customer owner;

  public Bicycle() {
  }

  public Bicycle(Integer id, String serialNumber, String brand, Double price, Integer year, String description,
      Customer owner) {
    this.id = id;
    this.serialNumber = serialNumber;
    this.brand = brand;
    this.price = price;
    this.year = year;
    this.description = description;
    this.owner = owner;
  }

  public Integer getId() {
    return id.intValue();
  }

  public void setId(Integer id) {
    this.id = id;
  }

  public String getSerialNumber() {
    return serialNumber;
  }

  public void setSerialNumber(String serialNumber) {
    this.serialNumber = serialNumber;
  }

  public String getBrand() {
    return brand;
  }

  public void setBrand(String brand) {
    this.brand = brand;
  }

  public Double getPrice() {
    return price;
  }

  public void setPrice(Double price) {
    this.price = price;
  }

  public Integer getYear() {
    return year;
  }

  public void setYear(Integer year) {
    this.year = year;
  }

  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public Customer getOwner() {
    return owner;
  }

  public void setOwner(Customer owner) {
    this.owner = owner;
  }

}
