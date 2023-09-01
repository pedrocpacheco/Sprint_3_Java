package main.java.br.com.bikenow.model.dao.user;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import main.java.br.com.bikenow.model.entity.user.Customer;
import main.java.br.com.bikenow.model.entity.user.User;
import main.java.br.com.bikenow.model.infra.db.DB;
import main.java.br.com.bikenow.model.infra.exceptions.ExceptionHandler;

public class CustomerDAO {
  
  // ? Conexão 
  private Connection conn;

  public CustomerDAO(Connection connection){
    this.conn = connection;
  }

  // * Metodo CREATE
  public void insert(Customer customer) {
    if(customer == null){
      throw new RuntimeException("Customer parameter is null!");
    }

    if(customerExistsById(customer.getId())){ 
      throw new IllegalArgumentException("Customer with id: " + customer.getId() + " already exists");
    }

    String query = "INSERT INTO tb_customer VALUES (?, ?)";

    try(PreparedStatement ps = conn.prepareStatement(query)){
      UserDAO userDAO = new UserDAO(DB.getOracleConnection());

      if(!userDAO.userExistsById(customer.getId())){
        userDAO.insert(new User(customer.getId(), customer.getName(), 
                               customer.getEmail(), customer.getRole()));
        System.out.println("entrei aqui");
      }

      ps.setString(1, customer.getCpf());
      ps.setInt(2, customer.getId());
      ps.execute();
    } catch(SQLException e){
      System.err.println("Error inserting Customer");
      e.printStackTrace();
    }
  }

  // ? Metodos READ
  public List<Customer> list(){
    List<Customer> customers = new ArrayList<>();
    String query = "SELECT * FROM tb_customer";

    try(PreparedStatement ps = conn.prepareStatement(query)){
      ResultSet rs = ps.executeQuery();

      while(rs.next()){
        UserDAO userDAO = new UserDAO(DB.getOracleConnection());
        User user = userDAO.findById(rs.getInt("id_user"));
        String cpf = rs.getString("cpf_customer");
       
        Customer customer = new Customer(user.getId(), user.getName(), user.getEmail(), cpf);
        customers.add(customer);
        System.out.println("Added " + user.getName());
      }
    } catch (SQLException e) {
      ExceptionHandler.handleSQLException(e, "Error fetching users");
  }
    return customers;
  }

  // * Metodo UPDATE
  public void update(Customer customer){
    if(customer == null){
      throw new IllegalArgumentException("Customer parameter is null!");
    }

    if(!customerExistsById(customer.getId())){ 
      throw new IllegalArgumentException("Customer with id: " + customer.getId() + " does not exists");
    }

    String queryUser = "UPDATE tb_user SET nm_user = ?, em_user = ? WHERE id_user = ?";

    try(PreparedStatement ps = conn.prepareStatement(queryUser)){
      ps.setString(1, customer.getName());
      ps.setString(2, customer.getEmail());
      ps.setInt(3, customer.getId());
      ps.executeUpdate();
    } catch(SQLException e){
      ExceptionHandler.handleSQLException(e, "Error updating Customer");
    }
  }

  // ! Metodo DELETE
  public void delete(Customer customer){
    if (customer == null) {
      throw new IllegalArgumentException("Customer parameter is null!");
    }

    if(!customerExistsById(customer.getId())){
      throw new IllegalArgumentException("User with id: " + customer.getCpf() + " does not exist");
    }

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

  // ? Metodo Existe por ID
  private boolean customerExistsById(int id){
      String query = "SELECT id_user FROM tb_customer WHERE id_user = ?";

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
