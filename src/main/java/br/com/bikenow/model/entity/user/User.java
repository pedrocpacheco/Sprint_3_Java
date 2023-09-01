package main.java.br.com.bikenow.model.entity.user;

// Tabela "tb_user"
public class User {
  
    private Integer id;

    private String name;

    private String email;

    protected Role role;

    public User() {
    }

    public User(Integer id, String name, String email, Role role) {
      this.id = id;
      this.name = name;
      this.email = email;
      this.role = role;
    }

    public Integer getId() {
      return id;
    }

    public String getName() {
      return name;
    }

    public void setName(String name) {
      this.name = name;
    }

    public String getEmail() {
      return email;
    }

    public void setEmail(String email) {
      this.email = email;
    }

    public Role getRole() {
      return role;
    }

    public void setRole(Role role) {
      this.role = role;
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
      User other = (User) obj;
      if (id == null) {
        if (other.id != null)
          return false;
      } else if (!id.equals(other.id))
        return false;
      return true;
    }

    @Override
    public String toString() {
      return "User [id=" + id + ", name=" + name + ", email=" + email + ", role=" + role + "]";
    }

    

}
