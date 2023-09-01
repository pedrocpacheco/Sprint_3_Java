package main.java.br.com.bikenow.model.dao.user;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import main.java.br.com.bikenow.model.entity.user.Role;
import main.java.br.com.bikenow.model.entity.user.User;
import main.java.br.com.bikenow.model.exceptions.ExceptionHandler;

public class UserDAO {

  // ? Conexão
  private final Connection conn;

  public UserDAO(Connection connection) {
    this.conn = connection;
  }

  // * Metodo de Insert
  public void insert(User user) {
    if (user == null) {
      throw new IllegalArgumentException("User parameter is null!");
    }

    if (userExistsById(user.getId())) {
      throw new IllegalArgumentException("User with id: " + user.getId() + " already exists!");
    }

    String query = "INSERT INTO tb_user (id_user, nm_user, em_user, role_user) VALUES (?, ?, ?, ?)";

    try (PreparedStatement ps = conn.prepareStatement(query)) {
      ps.setInt(1, user.getId());
      ps.setString(2, user.getName());
      ps.setString(3, user.getEmail());
      ps.setString(4, user.getRole().toString());
      ps.executeUpdate();
    } catch (SQLException e) {
      ExceptionHandler.handleSQLException(e, "Error inserting User");
    }
  }

  // ? Metodo de List
  public List<User> list() {
    List<User> userList = new ArrayList<>();
    String query = "SELECT * FROM tb_user";

    try (PreparedStatement ps = conn.prepareStatement(query);
      ResultSet rs = ps.executeQuery()) {

      while (rs.next()) {
        int id = rs.getInt("id_user");
        String name = rs.getString("nm_user");
        String email = rs.getString("em_user");
        String role = rs.getString("role_user");
        Role roleEnum = Role.valueOf(role); // Assuming Role enum values match the role column values

        User user = new User(id, name, email, roleEnum);
        userList.add(user);
        System.out.println("Added " + name);
      }
    } catch (SQLException e) {
      ExceptionHandler.handleSQLException(e, "Error fetching users");
    }

    return userList;
  }

  // ? Metodo de FinByID
  public User findById(int id) {
    if (!userExistsById(id)) {
      throw new IllegalArgumentException("User with id: " + id + " does not exist");
    }

    String query = "SELECT * FROM tb_user WHERE id_user = ?";

    try (PreparedStatement ps = conn.prepareStatement(query)) {
      ps.setInt(1, id);
      
      try (ResultSet rs = ps.executeQuery()) {
        if (rs.next()) {
          int idUser = rs.getInt("id_user");
          String name = rs.getString("nm_user");
          String email = rs.getString("em_user");
          String role = rs.getString("role_user");
          Role roleEnum = Role.valueOf(role);

          return new User(idUser, name, email, roleEnum);
        }
      }
    } catch (SQLException e) {
      ExceptionHandler.handleSQLException(e, "Error Finding User ID");
    }

    return null;
  }

  // * Metodo de Update
  public void update(User user) {
    if (user == null) {
      throw new IllegalArgumentException("User parameter is null!");
    }

    if (!userExistsById(user.getId())) {
      throw new IllegalArgumentException("User with id: " + user.getId() + " does not exist");
    }

    String query = "UPDATE tb_user SET nm_user = ?, em_user = ?, role_user = ? WHERE id_user = ?";

    try (PreparedStatement ps = conn.prepareStatement(query)) {
      ps.setString(1, user.getName());
      ps.setString(2, user.getEmail());
      ps.setString(3, user.getRole().toString());
      ps.setInt(4, user.getId());
      int rowsUpdated = ps.executeUpdate();
      if (rowsUpdated > 0) {
        System.out.println("User with id: " + user.getId() + " updated!");
      } else {
        System.out.println("No user found with id: " + user.getId() + ". No updates were made.");
      }
    } catch (SQLException e) {
      ExceptionHandler.handleSQLException(e, "Error updating User");
    }
  }

  // ! Metodo de Delete
  public void delete(User user) {
    if (user == null) {
      throw new IllegalArgumentException("User parameter is null!");
    }

    if (!userExistsById(user.getId())) {
      throw new IllegalArgumentException("User with id: " + user.getId() + " does not exist");
    }

    String query = "DELETE FROM tb_user WHERE id_user = ?";

    try (PreparedStatement ps = conn.prepareStatement(query)) {
      ps.setInt(1, user.getId());
      ps.executeUpdate();
    } catch (SQLException e) {
      ExceptionHandler.handleSQLException(e, "Error deleting User");
    }
  }

  // ? Metodo de Existe por ID
  private boolean userExistsById(int userId) {
    String query = "SELECT id_user FROM tb_user WHERE id_user = ?";

    try (PreparedStatement ps = conn.prepareStatement(query)) {
      ps.setInt(1, userId);
      try (ResultSet rs = ps.executeQuery()) {
        return rs.next();
      }
    } catch (SQLException e) {
      ExceptionHandler.handleSQLException(e, "Error checking user existence by ID");
      return false;
    }
  }
}
