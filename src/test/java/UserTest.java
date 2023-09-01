package test.java;

import java.util.List;

import main.java.br.com.bikenow.model.entity.user.Role;
import main.java.br.com.bikenow.model.entity.user.User;
import main.java.br.com.bikenow.model.infra.db.DB;
import main.java.br.com.bikenow.model.service.user.UserService;

public class UserTest {
  public static void main(String[] args) {

    //  * Criando UserService
    UserService userService = new UserService(DB.getOracleConnection());

    // * CREATE - Adicionando User
    User thiago = new User(40, "Thiago", "Fritz", Role.ANALYST);
    userService.createUser(thiago);

    // ? READ - Listando Users
    List<User> users = userService.findAll();
    users.forEach(u -> System.out.println(u.toString()));

    // ? READ - Listando User por ID
    User user = userService.findById(40);
    System.out.println(user.toString());

    // * UPDATE- Atualizando User
    thiago.setName("AAAAA");
    thiago.setEmail("Cohen");
    thiago.setRole(Role.CUSTOMER);
    userService.updateUser(thiago);

    // ! DELETE - Deletando User
    userService.deleteUser(thiago);
    
  }
}
