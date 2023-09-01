package test.java;

import java.util.List;

import main.java.br.com.bikenow.model.dao.user.UserDAO;
import main.java.br.com.bikenow.model.entity.user.Role;
import main.java.br.com.bikenow.model.entity.user.User;
import main.java.br.com.bikenow.model.infra.db.DB;
import main.java.br.com.bikenow.model.service.UserService;

public class UserTest {
  public static void main(String[] args) {

    // Criando UserService
    UserService userService = new UserService(DB.getOracleConnection());

    // CREATE - Adicionando User
    User thiago = new User(34, "Thiago", "Fritz", Role.ANALYST);
    userService.createUser(thiago);

    // READ - Listando Users
    List<User> users = userService.findAll();
    users.forEach(u -> System.out.println(u.toString()));

    // UPDATE- Atualizando User
    thiago.setName("Cesar");
    thiago.setName("Cohen");
    thiago.setRole(Role.CUSTOMER);
    //userService.update(thiago);

    // DELETE - Deletando User
    //userService.deleteUser(thiago);
  }
}
