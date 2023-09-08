package main.java.br.com.bikenow.model.dao.user;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import main.java.br.com.bikenow.model.entity.user.Customer;
import main.java.br.com.bikenow.model.entity.user.User;
import main.resources.infra.db.DB;
import main.resources.infra.exceptions.ExceptionHandler;

public class CustomerDAO {
  
  // ? Conexão 
  private Connection conn;

  public CustomerDAO(Connection connection){
    this.conn = connection;
  }

  // * Metodo CREATE
  public void createCustomer(Customer customer) {
    String query = "INSERT INTO tb_customer VALUES (?, ?)";

    try(PreparedStatement ps = conn.prepareStatement(query)){
      UserDAO userDAO = new UserDAO(DB.getOracleConnection());
      if(!userDAO.userExistsById(customer.getId())){
        userDAO.createUser(new User(customer.getId(), customer.getName(), 
                               customer.getEmail(), customer.getRole()));
      }

      ps.setString(1, customer.getCpf());
      ps.setInt(2, customer.getId());
      ps.execute();
    } catch(SQLException e){
      ExceptionHandler.handleSQLException(e, "Error inserting Customer");
    }
  }

  // ? Metodos READ
  public List<Customer> findAll(){
    List<Customer> customers = new ArrayList<>();
    String query = "SELECT * FROM tb_customer";

    try(PreparedStatement ps = conn.prepareStatement(query)){
      ResultSet rs = ps.executeQuery();

      while(rs.next()){
       customers.add(createCustomerFromResultSet(rs));
      }

    } catch (SQLException e) {
      ExceptionHandler.handleSQLException(e, "Error fetching users");
  }
    return customers;
  }

  // ? Metodo de FinByID
  public Customer findByCpf(String cpf) {
    String query = "SELECT * FROM tb_customer WHERE cpf_customer = ?";

    try (PreparedStatement ps = conn.prepareStatement(query)) {
      ps.setString(1, cpf);
      
      try (ResultSet rs = ps.executeQuery()) {
        if (rs.next()) {
          return createCustomerFromResultSet(rs);
        }
      }
    } catch (SQLException e) {
      ExceptionHandler.handleSQLException(e, "Error Finding User ID");
    }

    return null;
  }

  // * Metodo UPDATE
  public void updateCustomer(Customer customer){
    String query = "UPDATE tb_user SET nm_user = ?, em_user = ? WHERE id_user = ?";

    try(PreparedStatement ps = conn.prepareStatement(query)){
      UserDAO userDAO = new UserDAO(DB.getOracleConnection());
      userDAO.setParameters(ps, customer);
      ps.executeUpdate();
    } catch(SQLException e){
      ExceptionHandler.handleSQLException(e, "Error updating Customer");
    }
  }

  // ! Metodo DELETE
  public void deleteCustomer(Customer customer){
    String query = "DELETE FROM tb_customer WHERE id_user = ?";

    try(PreparedStatement ps = conn.prepareStatement(query)) {
      ps.setInt(1, customer.getId());
      ps.executeUpdate();
    } catch(SQLException e){
      ExceptionHandler.handleSQLException(e, "Error Deleting Customer");
    }

    String queryUser = "DELETE FROM tb_user WHERE id_user = ?";
    try(PreparedStatement ps = conn.prepareStatement(queryUser)) {
      ps.setInt(1, customer.getId());
      ps.executeUpdate();
    } catch(SQLException e){
      ExceptionHandler.handleSQLException(e, "Error Deleting Customer");
    }
  }

  // ? Metodo Existe por CPF
  public boolean customerExistsByCpf(String cpf){
      String query = "SELECT cpf_customer FROM tb_customer WHERE cpf_customer = ?";

      try(PreparedStatement ps = conn.prepareStatement(query)){
        ps.setString(1, cpf);
        ResultSet rs = ps.executeQuery();
        return rs.next();
      } catch(SQLException e){
        ExceptionHandler.handleSQLException(e, "Error checking customer existence by ID");
        return false;
      }
  }

  // ? Metodo Existe por ID
  public boolean customerExistsById(Integer id){
    UserDAO userDAO = new UserDAO(DB.getOracleConnection());
    return userDAO.userExistsById(id);
  }

  // ? Create Customer From ResultSet
  private Customer createCustomerFromResultSet(ResultSet rs) throws SQLException{
    UserDAO userDAO = new UserDAO(DB.getOracleConnection());
    User user = userDAO.findById(rs.getInt("id_user"));
    String cpfGetted = rs.getString("cpf_customer");

    return new Customer(user.getId(), user.getName(), user.getEmail(), cpfGetted);
  }
  
}
