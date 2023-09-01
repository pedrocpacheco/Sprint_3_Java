package main.java.br.com.bikenow.model.dao.user;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import main.java.br.com.bikenow.model.entity.user.Role;
import main.java.br.com.bikenow.model.entity.user.User;
import main.java.br.com.bikenow.model.infra.exceptions.ExceptionHandler;

public class UserDAO {

  // ? Conexão
  private Connection conn;

  public UserDAO(Connection connection){
    this.conn = connection;
  }

  // * Metodo de Insert
  public void insert(User user) {
    String query = "INSERT INTO tb_user (nm_user, em_user, role_user, id_user) VALUES (?, ?, ?, ?)";

    try (PreparedStatement ps = conn.prepareStatement(query)) {
      setParameters(ps, user);
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
        userList.add(createUserFromResultSet(rs));
      }
    } catch (SQLException e) {
      ExceptionHandler.handleSQLException(e, "Error fetching users");
    }

    return userList;
  }

  // ? Metodo de FinByID
  public User findById(int id) {
    String query = "SELECT * FROM tb_user WHERE id_user = ?";

    try (PreparedStatement ps = conn.prepareStatement(query)) {
      ps.setInt(1, id);
      
      try (ResultSet rs = ps.executeQuery()) {
        if (rs.next()) {
          createUserFromResultSet(rs);
        }
      }
    } catch (SQLException e) {
      ExceptionHandler.handleSQLException(e, "Error Finding User ID");
    }

    return null;
  }

  // * Metodo de Update
  public void update(User user) {
    String query = "UPDATE tb_user SET nm_user = ?, em_user = ?, role_user = ? WHERE id_user = ?";

    try (PreparedStatement ps = conn.prepareStatement(query)) {
      setParameters(ps, user);
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
    String query = "DELETE FROM tb_user WHERE id_user = ?";

    try (PreparedStatement ps = conn.prepareStatement(query)) {
      ps.setInt(1, user.getId());
      ps.executeUpdate();
    } catch (SQLException e) {
      ExceptionHandler.handleSQLException(e, "Error deleting User");
    }
  }

  // ? Metodo Existe por ID
  public boolean userExistsById(int userId) {
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

  // ? 
  private void setParameters(PreparedStatement ps, User user) throws SQLException {
    ps.setString(1, user.getName());
    ps.setString(2, user.getEmail());
    ps.setString(3, user.getRole().toString());
    ps.setInt(4, user.getId());
}

  // ? Creat User From RS
  private User createUserFromResultSet(ResultSet rs) throws SQLException{
    int idUser = rs.getInt("id_user");
    String name = rs.getString("nm_user");
    String email = rs.getString("em_user");
    String role = rs.getString("role_user");
    Role roleEnum = Role.valueOf(role);

    return new User(idUser, name, email, roleEnum);
  }
}
