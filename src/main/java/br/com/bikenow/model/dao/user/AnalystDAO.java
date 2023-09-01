package main.java.br.com.bikenow.model.dao.user;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import main.java.br.com.bikenow.model.entity.user.Analyst;
import main.java.br.com.bikenow.model.entity.user.User;
import main.java.br.com.bikenow.model.infra.db.DB;
import main.java.br.com.bikenow.model.infra.exceptions.ExceptionHandler;

public class AnalystDAO {
  
  // ? Conexão 
  private Connection conn;

  public AnalystDAO(Connection connection){
    this.conn = connection;
  }

  // * Metodo CREATE
  public void insert(Analyst analyst) {
    if(analyst == null){
      throw new RuntimeException("Analyst parameter is null!");
    }

    if(analystExistsById(analyst.getId())){ 
      throw new IllegalArgumentException("Analyst with id: " + analyst.getId() + " already exists");
    }

    String query = "INSERT INTO tb_analyst VALUES (?, ?)";

    try(PreparedStatement ps = conn.prepareStatement(query)){
      UserDAO userDAO = new UserDAO(DB.getOracleConnection());

      if(!userDAO.userExistsById(analyst.getId())){
        userDAO.insert(new User(analyst.getId(), analyst.getName(), 
                               analyst.getEmail(), analyst.getRole()));
        System.out.println("entrei aqui");
      }

      ps.setString(1, analyst.getRm());
      ps.setInt(2, analyst.getId());
      ps.execute();
    } catch(SQLException e){
      System.err.println("Error inserting analyst");
      e.printStackTrace();
    }
  }

  // ? Metodos READ
  public List<Analyst> list(){
    List<Analyst> analysts = new ArrayList<>();
    String query = "SELECT * FROM tb_analyst";

    try(PreparedStatement ps = conn.prepareStatement(query)){
      ResultSet rs = ps.executeQuery();

      while(rs.next()){
        UserDAO userDAO = new UserDAO(DB.getOracleConnection());
        User user = userDAO.findById(rs.getInt("id_user"));
        String rm = rs.getString("rm_analyst").strip();
       
        Analyst analyst = new Analyst(user.getId(), user.getName(), user.getEmail(), rm);
        analysts.add(analyst);
        System.out.println("Added " + user.getName());
      }
    } catch (SQLException e) {
      ExceptionHandler.handleSQLException(e, "Error reading analyst");
  }
    return analysts;
  }

  // * Metodo UPDATE
  public void update(Analyst analyst){
    if(analyst == null){
      throw new IllegalArgumentException("Analyst parameter is null!");
    }

    if(!analystExistsById(analyst.getId())){ 
      throw new IllegalArgumentException("analyst with id: " + analyst.getId() + " does not exists");
    }

    String queryUser = "UPDATE tb_user SET nm_user = ?, em_user = ? WHERE id_user = ?";

    try(PreparedStatement ps = conn.prepareStatement(queryUser)){
      ps.setString(1, analyst.getName());
      ps.setString(2, analyst.getEmail());
      ps.setInt(3, analyst.getId());
      ps.executeUpdate();
    } catch(SQLException e){
      ExceptionHandler.handleSQLException(e, "Error updating analyst");
    }
  }

  // ! Metodo DELETE
  public void delete(Analyst analyst){
    if (analyst == null) {
      throw new IllegalArgumentException("analyst parameter is null!");
    }

    if(!analystExistsById(analyst.getId())){
      throw new IllegalArgumentException("User with id: " + analyst.getRm() + " does not exist");
    }

    String query = "DELETE FROM tb_analyst WHERE id_user = ?";

    try(PreparedStatement ps = conn.prepareStatement(query)) {
      ps.setInt(1, analyst.getId());
      ps.executeUpdate();
    } catch(SQLException e){
      ExceptionHandler.handleSQLException(e, "Error Deleting analyst");
    }

    String queryUser = "DELETE FROM tb_user WHERE id_user = ?";
    try(PreparedStatement ps = conn.prepareStatement(queryUser)) {
      ps.setInt(1, analyst.getId());
      ps.executeUpdate();
    } catch(SQLException e){
      ExceptionHandler.handleSQLException(e, "Error Deleting analyst");
    }
  }

  // ? Metodo Existe por ID
  private boolean analystExistsById(int id){
      String query = "SELECT id_user FROM tb_analyst WHERE id_user = ?";

      try(PreparedStatement ps = conn.prepareStatement(query)){
        ps.setInt(1, id);
        ResultSet rs = ps.executeQuery();
        return rs.next();
      } catch(SQLException e){
        ExceptionHandler.handleSQLException(e, "Error checking customer existence by ID");
        return false;
      }
 
  }

}
