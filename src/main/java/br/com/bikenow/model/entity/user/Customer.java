package main.java.br.com.bikenow.model.entity.user;

public class Customer extends User{
  
  private String cpf;

  public Customer(String cpf) {
    this.cpf = cpf;
  }

  public Customer(Integer id, String name, String email, String cpf) {
    super(id, name, email, Role.CUSTOMER);
    this.cpf = cpf;
  }

  public String getCpf() {
    return cpf;
  }

  @Override
  public String toString() {
    return "Customer [id=" + id + ", name=" + name + ", email=" + email + ", role=" + role + ", cpf=" + cpf + "]";
  }

  // ? Não será permitido mudar o CPF de um Customer já criado
  // ? Por isso não existe um setCPF()

}
