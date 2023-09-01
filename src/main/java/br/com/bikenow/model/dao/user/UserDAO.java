package main.java.br.com.bikenow.model.dao.user;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import main.java.br.com.bikenow.model.entity.user.Role;
import main.java.br.com.bikenow.model.entity.user.User;

public class UserDAO {
  
  private Connection conn;

  public UserDAO(Connection connection){
    this.conn = connection;
  }

  // Metodo POST
  public String insert(User user){
    if (user == null) {
      throw new RuntimeException("User parameter is null!");
    }

    String query = "INSERT INTO tb_user VALUES(?, ?, ?, ?)";

    try{
      if(userExistsById(user.getId())){
          throw new IllegalArgumentException("User with id: " + user.getId() + " already exists!");
      }

      PreparedStatement ps = conn.prepareStatement(query);
      ps.setInt(1, user.getId());
      ps.setString(2, user.getName());
      ps.setString(3, user.getEmail());
      ps.setString(4, user.getRole().toString());
      ps.execute();
    } catch(SQLException e){
      if(this.conn == null){
        System.err.println("Connection is Null");
      }
      e.printStackTrace();
    }

    return "User Inserted!";
  }

  // Metodo DELETE
  public String delete(User user){
    if (user == null) {
      throw new RuntimeException("User parameter is null!");
    }

    String query = "DELETE FROM tb_user WHERE id_user = ?";

    try{
      if(!userExistsById(user.getId())){
          throw new IllegalArgumentException("User with id: " + user.getId() + "does not exist");
      }

      PreparedStatement ps = conn.prepareStatement(query);
      ps.setInt(1, user.getId());
      ps.execute();
    }catch(SQLException e){
      System.err.println("Error to delete User");
      e.printStackTrace();
    }
    
    return "User with id: " + user.getId() + " deleted!";
  }

  // Metodo PUT
  public String update(User user){
    if (user == null) {
      throw new RuntimeException("User parameter is null!");
    }

    String query = "UPDATE tb_user SET nm_user = ?, em_user = ?, role_user WHERE id_user = ?";

    try{
        if(!userExistsById(user.getId())){
          throw new IllegalArgumentException("User with id: " + user.getId() + "does not exist");
        }

        PreparedStatement ps = conn.prepareStatement(query);
        ps.setString(1, user.getName());
        ps.setString(2, user.getEmail());
        ps.setString(3, user.getRole().toString());
        ps.setInt(4, user.getId());
        int rowsUpdated = ps.executeUpdate();

        if (rowsUpdated > 0) {
            return "User with id: " + user.getId() + " updated!";
        } else {
            return "No user found with id: " + user.getId() + ". No updates were made.";
        }
    } catch(SQLException e){
        System.err.println("Error updating User");
        e.printStackTrace();
    }

    return "Failed to update user with id: " + user.getId();
  }

  // Metodo List
  public List<User> list() {
    List<User> userList = new ArrayList<>();
    String query = "SELECT * FROM tb_user";

    try {
      PreparedStatement ps = conn.prepareStatement(query);
      ResultSet rs = ps.executeQuery();

      while (rs.next()) {
        int id = rs.getInt("id_user");
        String name = rs.getString("nm_user");
        String email = rs.getString("em_user");
        String role = rs.getString("role_user");
        Role roleEnum = null;
        if(role == "CUSTOMER"){ roleEnum = Role.CUSTOMER; }
        else{ roleEnum = Role.ANALYST; }

        User user = new User(id, name, email, roleEnum);
        userList.add(user);
        System.out.println("Adicionado" + name);
      }
      rs.close();
    } catch (SQLException e) {
      System.err.println("Error fetching users");
      e.printStackTrace();
    }

    return userList;
  }

  // Encontrar por ID
  public User findById(int id) throws SQLException{
    if(!userExistsById(id)){
      throw new IllegalArgumentException("User with id: " + id + "does not exist");
    }
    String query = "SELECT * FROM tb_user WHERE id_user = ?";

    try{
      PreparedStatement ps = conn.prepareStatement(query);
      ps.setInt(1, id);
      
      ResultSet rs = ps.executeQuery();

      if(rs.next()){
        int idUser = rs.getInt("id_user");
        String name = rs.getString("nm_user");
        String email = rs.getString("em_user");
        String role = rs.getString("role_user");
        Role roleEnum = null;
        if(role == "CUSTOMER"){ roleEnum = Role.CUSTOMER; }
        else{ roleEnum = Role.ANALYST; }

        User user = new User(idUser, name, email, roleEnum);
        return user;
      }
    } catch(SQLException e){
      System.err.println("Error Finding User ID");
      e.printStackTrace();
    }
    return null;
  }

  // Metodo para ver se user ja existe pelo seu id
  private boolean userExistsById(int userId) throws SQLException {
    String query = "SELECT id_user FROM tb_user WHERE id_user = ?";

    PreparedStatement ps = conn.prepareStatement(query);
    ps.setInt(1, userId);

    ResultSet rs = ps.executeQuery();

    return rs.next(); // Retorna true se o usuário existe, caso contrário, retorna false
  }
}
  
