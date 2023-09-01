package main.java.br.com.bikenow.model.service.user;

import java.sql.Connection;
import java.util.List;

import main.java.br.com.bikenow.model.dao.user.UserDAO;
import main.java.br.com.bikenow.model.entity.user.User;

public class UserService {
  
  // Injetando Dependencia
  private UserDAO userDAO;

  public UserService(Connection connection){
    this.userDAO = new UserDAO(connection);
  }

  // * Metodo CREATE
  public void createUser(User user){
    if (user == null) {
        throw new IllegalArgumentException("User parameter is null!");
      }
  
    if (userDAO.userExistsById(user.getId())) {
      throw new IllegalArgumentException("User with id: " + user.getId() + " already exists!");
    }

    userDAO.insert(user);
  }

  // ? Metodo READ All
  public List<User> findAll(){
    return userDAO.list();
  }

  // ? Metodo READ by ID
  public User findById(Integer id){
    if (!userDAO.userExistsById(id)) {
      throw new IllegalArgumentException("User with id: " + id + " does not exist");
    }

    return userDAO.findById(id);
  }

  // * Metodo UPDATE
  public void updateUser(User user){
    if (user == null) {
      throw new IllegalArgumentException("User parameter is null!");
    }

    if (!userDAO.userExistsById(user.getId())) {
      throw new IllegalArgumentException("User with id: " + user.getId() + " does not exist");
    }

    userDAO.update(user);
  }

  // ! Metodo DELETE
  public void deleteUser(User user){
    if (user == null) {
      throw new IllegalArgumentException("User parameter is null!");
    }

    if (!userDAO.userExistsById(user.getId())) {
      throw new IllegalArgumentException("User with id: " + user.getId() + " does not exist");
    }

    userDAO.delete(user);
  }

}
