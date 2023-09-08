package main.java.br.com.bikenow.model.dao.user;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import main.java.br.com.bikenow.model.entity.user.Analyst;
import main.java.br.com.bikenow.model.entity.user.User;
import main.resources.infra.db.DB;
import main.resources.infra.exceptions.ExceptionHandler;

public class AnalystDAO {
  
  // ? Conexão 
  private Connection conn;

  public AnalystDAO(Connection connection){
    this.conn = connection;
  }

  // * Metodo CREATE
  public void createAnalyst(Analyst analyst) {
    String query = "INSERT INTO tb_analyst VALUES (?, ?)";

    try(PreparedStatement ps = conn.prepareStatement(query)){
      UserDAO userDAO = new UserDAO(DB.getOracleConnection());

      if(!userDAO.userExistsById(analyst.getId())){
        userDAO.createUser(new User(analyst.getId(), analyst.getName(), 
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
  public List<Analyst> findAll(){
    List<Analyst> analysts = new ArrayList<>();
    String query = "SELECT * FROM tb_analyst";

    try(PreparedStatement ps = conn.prepareStatement(query)){
      ResultSet rs = ps.executeQuery();

      while(rs.next()){
        analysts.add(createAnalystFromResultSet(rs));
      }
      
    } catch (SQLException e) {
      ExceptionHandler.handleSQLException(e, "Error reading analyst");
  }
    return analysts;
  }

  public Analyst findByRm(String rm) {
    String query = "SELECT * FROM tb_analyst WHERE rm_analyst = ?";

    try (PreparedStatement ps = conn.prepareStatement(query)) {
      ps.setString(1, rm);
      
      try (ResultSet rs = ps.executeQuery()) {
        if (rs.next()) {
          return createAnalystFromResultSet(rs);
        }
      }
    } catch (SQLException e) {
      ExceptionHandler.handleSQLException(e, "Error Finding Analyst RM");
    }

    return null;
  }

  // * Metodo UPDATE
  public void updateAnalyst(Analyst analyst){
    String queryUser = "UPDATE tb_user SET nm_user = ?, em_user = ? WHERE id_user = ?";

    try(PreparedStatement ps = conn.prepareStatement(queryUser)){
      UserDAO userDAO = new UserDAO(DB.getOracleConnection());
      userDAO.setParameters(ps, analyst);
      ps.executeUpdate();
    } catch(SQLException e){
      ExceptionHandler.handleSQLException(e, "Error updating analyst");
    }
  }

  // ! Metodo DELETE
  public void deleteAnalyst(Analyst analyst){
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

  // ? Metodo Existe por RM
  public boolean analystExistsByRm(String rm){
    String query = "SELECT rm_analyst FROM tb_analyst WHERE rm_analyst = ?";

    try(PreparedStatement ps = conn.prepareStatement(query)){
      ps.setString(1, rm);
      ResultSet rs = ps.executeQuery();
      return rs.next();
    } catch(SQLException e){
      ExceptionHandler.handleSQLException(e, "Error checking customer existence by ID");
      return false;
    }
  }


  // ? Metodo Existe por ID
  public boolean analystExistsById(int id){
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

// ? Create Analyst From ResultSet
  private Analyst createAnalystFromResultSet(ResultSet rs) throws SQLException{
    UserDAO userDAO = new UserDAO(DB.getOracleConnection());
    User user = userDAO.findById(rs.getInt("id_user"));
    String rm = rs.getString("rm_analyst");
       
    return new Analyst(user.getId(), user.getName(), user.getEmail(), rm);  
  }

}
