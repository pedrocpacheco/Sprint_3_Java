package main.java.br.com.bikenow.model.dao.user;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import main.java.br.com.bikenow.model.entity.user.Customer;
import main.java.br.com.bikenow.model.entity.user.User;

public class CustomerDAO {
  
  private Connection conn;

  public CustomerDAO(Connection connection){
    this.conn = connection;
  }

  // CREATE
  public String insert(Customer customer) {
    if(customer == null){
      throw new RuntimeException("Customer parameter is null!");
    }

    String query = "INSERT INTO tb_customer VALUES (?, ?)";

    try{
      if(customerExistsById(customer.getId())){ 
        throw new IllegalArgumentException("Customer with id: " + customer.getId() + " already exists");
      }

      UserDAO userDAO = new UserDAO(conn);
      userDAO.insert(new User(customer.getId(), customer.getName(), customer.getEmail(), customer.getRole()));

      PreparedStatement ps = conn.prepareStatement(query);
      ps.setString(1, customer.getCpf());
      ps.setInt(2, customer.getId());
      ps.execute();
    } catch(SQLException e){
      System.err.println("Error inserting Customer");
      e.printStackTrace();
    }

    return "Customer Inserted";
  }

  // READ
  public List<Customer> list(){
    List<Customer> customers = new ArrayList<>();
    String query = "SELECT * FROM tb_customer";

    try{
      PreparedStatement ps = conn.prepareStatement(query);
      ResultSet rs = ps.executeQuery();

      while(rs.next()){
        UserDAO userDAO = new UserDAO(conn);
        User user = userDAO.findById(rs.getInt("id_user"));
        String cpf = rs.getString("cpf_customer");
        Customer customer = new Customer(user.getId(), user.getName(), user.getEmail(), cpf);
        customers.add(customer);
      }
    }

  }


  private boolean customerExistsById(int customerId) throws SQLException {
      String query = "SELECT id_user FROM tb_user WHERE id_user = ?";

      PreparedStatement ps = conn.prepareStatement(query);
      ps.setInt(1, customerId);

      ResultSet rs = ps.executeQuery();

      return rs.next(); 
  }
  
}
