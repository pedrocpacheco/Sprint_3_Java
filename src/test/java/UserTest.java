package test.java;

import java.util.List;

import main.java.br.com.bikenow.model.dao.user.UserDAO;
import main.java.br.com.bikenow.model.entity.user.Role;
import main.java.br.com.bikenow.model.entity.user.User;
import main.java.br.com.bikenow.model.infra.db.DB;

public class UserTest {
  public static void main(String[] args) {

    // Criando UserDAO
    UserDAO userDao = new UserDAO(DB.getOracleConnection());

    // CREATE - Adicionando User
    User thiago = new User(45, "Thiago", "Fritz", Role.ANALYST);
    //userDao.insert(thiago);

    // READ - Listando Users
    List<User> users = userDao.list();
    users.forEach(u -> System.out.println(u.toString()));

    // UPDATE- Atualizando User
    thiago.setName("Cesar");
    thiago.setName("Cohen");
    thiago.setRole(Role.CUSTOMER);
    userDao.update(thiago);

    // DELETE - Deletando User
    //userDao.delete(thiago);
  }
}
