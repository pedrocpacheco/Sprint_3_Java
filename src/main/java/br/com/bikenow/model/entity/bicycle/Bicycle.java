package main.java.br.com.bikenow.model.entity.bicycle;

import main.java.br.com.bikenow.model.entity.user.Customer;

public class Bicycle {
  
  private Integer id;

  private String serialNumber;

  private String model;

  private String brand;

  private Double price;

  private String year;

  private String description;

  private Customer owner;

  public Bicycle() {
  }

  public Bicycle(Integer id, String serialNumber, String model, String brand, Double price, String year, String description,
      Customer owner) {
    this.id = id;
    this.serialNumber = serialNumber;
    this.model = model;
    this.brand = brand;
    this.price = price;
    this.year = year;
    this.description = description;
    setOwner(owner);
  }

  public Integer getId() {
    return id.intValue();
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

  public String getYear(){
    return this.year;
  }

  public void setYear(String year) {
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

  public String getOwnerCpf(){
    return owner.getCpf();
  }

  public void setOwner(Customer customer) {
    this.owner = customer;
  }

  public String getModel() {
    return model;
  }

  public void setModel(String model) {
    this.model = model;
  }

  @Override
  public String toString() {
    return "Bicycle [id=" + id + ", serialNumber=" + serialNumber + ", model=" + model + ", brand=" + brand + ", price="
        + price + ", year=" + year + ", description=" + description + ", owner=" + owner + "]";
  }

  

}
