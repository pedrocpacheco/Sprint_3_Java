package main.java.br.com.bikenow.model.entity.user;

public class Analyst extends User{
  
  private String rm;

  public Analyst(String rm) {
    this.rm = rm;
  }

  public Analyst(Integer id, String name, String email, String rm) {
    super(id, name, email, Role.ANALYST);
    setRm(rm);
  }

  public String getRm() {
    return rm;
  }

  public void setRm(String rm) {
    if (rm.length() != 12){
      System.out.println(rm.length() + "qaaaaaa");
      throw new IllegalArgumentException("RM must have 12 caracters with only numbers");
    }
    this.rm = rm;
  }

  @Override
  public String toString() {
    return "Analyst [id=" + this.id + ", name=" + name + ", email=" + email + ", rm= " + rm + ", role=" + role + "]";
  }  

}
