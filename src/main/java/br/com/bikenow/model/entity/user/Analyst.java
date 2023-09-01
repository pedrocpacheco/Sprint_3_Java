package main.java.br.com.bikenow.model.entity.user;

public class Analyst extends User{
  
  private String rm;

  public Analyst(String rm) {
    this.rm = rm;
  }

  public Analyst(Integer id, String name, String email,  Role role, String rm) {
    super(id, name, email, role);
    this.rm = rm;
  }

  public String getRm() {
    return rm;
  }

  public void setRm(String rm) {
    this.rm = rm;
  }  

}
