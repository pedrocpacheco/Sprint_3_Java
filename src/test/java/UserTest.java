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
    User user = new User(40, "user", "Fritz", Role.ANALYST);
    userService.createUser(user);

    // ? READ - Listando Users
    List<User> users = userService.findAll();
    users.forEach(u -> System.out.println(u.toString()));

    // ? READ - Listando User por ID
    User userById = userService.findById(40);
    System.out.println(userById.toString());

    // * UPDATE- Atualizando User
    user.setName("Lauren");
    user.setEmail("Cohen");
    user.setRole(Role.CUSTOMER);
    userService.updateUser(user);

    // ! DELETE - Deletando User
    userService.deleteUser(user);
    
  }
}
