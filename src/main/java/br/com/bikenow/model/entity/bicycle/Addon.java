package main.java.br.com.bikenow.model.entity.bicycle;

public class Addon {
  
  private Integer id;

  private String name;

  private String brand;

  private Double price;

  private String description;

  private Bicycle owner;

  public Addon() {
  }

  public Addon(Integer id, String name, String brand, Double price, String description, Bicycle owner) {
    setId(id);
    this.name = name;
    this.brand = brand;
    this.price = price;
    this.description = description;
    this.owner = owner;
  }

  public Integer getId() {
    return id;
  }

  public void setId(Integer id) {
    if(id > 9999 || id < 0){
      throw new IllegalArgumentException("Id must be a value between 0 and 9999");
    }
    this.id = id;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
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

  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public Bicycle getOwner() {
    return owner;
  }

  public void setOwner(Bicycle owner) {
    this.owner = owner;
  }

  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + ((id == null) ? 0 : id.hashCode());
    return result;
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj)
      return true;
    if (obj == null)
      return false;
    if (getClass() != obj.getClass())
      return false;
    Addon other = (Addon) obj;
    if (id == null) {
      if (other.id != null)
        return false;
    } else if (!id.equals(other.id))
      return false;
    return true;
  }

  @Override
  public String toString() {
    return "Addon [id=" + id + ", name=" + name + ", brand=" + brand + ", price=" + price + ", description="
        + description + ", owner=" + owner + "]";
  }

}
