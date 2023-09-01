package main.java.br.com.bikenow.model.dao.bicycle;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import main.java.br.com.bikenow.model.dao.user.CustomerDAO;
import main.java.br.com.bikenow.model.entity.bicycle.Bicycle;
import main.java.br.com.bikenow.model.entity.user.Customer;
import main.java.br.com.bikenow.model.infra.db.DB;
import main.java.br.com.bikenow.model.infra.exceptions.ExceptionHandler;

public class BicycleDAO {
  
  // ? Conexão
  private Connection conn;

  public BicycleDAO(Connection connection){
    this.conn = connection;
  }

  // * Metodo CREATE
  public void insert(Bicycle bicycle){
    if(bicycle == null){
      throw new RuntimeException("Bicycle parameter is null!");
    }

    if(bicycleExistsById(bicycle.getId())){
      throw new IllegalArgumentException("Bicycle with id: " + bicycle.getId() + " already exists");
    }

    String query = "INSERT INTO tb_bicycle VALUES(?, ?, ?, ?, ?, ?, ?, ?)";
 
    try(PreparedStatement ps = conn.prepareStatement(query)){
      ps.setInt(1, bicycle.getId());
      ps.setString(2, bicycle.getModel());
      ps.setString(3, bicycle.getSerialNumber());
      ps.setString(4, bicycle.getBrand());
      ps.setDouble(5, bicycle.getPrice());
      ps.setString(6, bicycle.getYear());
      ps.setString(7, bicycle.getDescription());
      ps.setString(8, bicycle.getOwnerCpf());
      ps.execute();
    } catch(SQLException e){
      ExceptionHandler.handleSQLException(e, "Error inserting Bicycle");
    }
  }

  // ? Metodo List
  public List<Bicycle> list(){
    List<Bicycle> bicycles = new ArrayList<>();
    String query = "SELECT * FROM tb_bicycle";

    try(PreparedStatement ps = conn.prepareStatement(query)){
      ResultSet rs = ps.executeQuery();

      while(rs.next()){
        int id = rs.getInt("id_bicycle");
        String serialNumber = rs.getString("num_serie_bicycle");
        String model = rs.getString("nm_bicycle");
        String brand = rs.getString("brand_bicycle");
        Double price = rs.getDouble("price_bicycle");
        String year = rs.getString("year_bicycle");
        String description = rs.getString("ds_bicycle");
        CustomerDAO customerDAO = new CustomerDAO(DB.getOracleConnection());
        Customer customer = customerDAO.findByCpf(rs.getString("cpf_customer"));
      
        Bicycle bicycle = new Bicycle(id, serialNumber, model, brand, price, year, description, customer);
        bicycles.add(bicycle);
      }
    } catch(SQLException e){
      ExceptionHandler.handleSQLException(e, "Error updating Customer");
    }
    return bicycles;
  }

  // ? Metodo FindById
  public Bicycle findById(Integer id){
    if(!bicycleExistsById(id)){
      throw new IllegalArgumentException("Bicycle with id: " + id + " does not exist");
    }

    String query = "SELECT * FROM tb_bicycle WHERE id_bicycle = ?";

    try(PreparedStatement ps = conn.prepareStatement(query)){
      ps.setInt(1, id);
      try(ResultSet rs = ps.executeQuery()){
        if(rs.next()){
          String serialNumber = rs.getString("num_serie_bicycle");
          String model = rs.getString("nm_bicycle");
          String brand = rs.getString("brand_bicycle");
          Double price = rs.getDouble("price_bicycle");
          String year = rs.getString("year_bicycle");
          String description = rs.getString("ds_bicycle");
          CustomerDAO customerDAO = new CustomerDAO(DB.getOracleConnection());
          Customer customer = customerDAO.findByCpf(rs.getString("cpf_customer"));

          return new Bicycle(id, serialNumber, model, brand, price, year, description, customer);
        }
      }
    } catch(SQLException e){
      throw new IllegalArgumentException("Customer with id: " + id + " does not exist");
    }
    return null;
  }

  // ? Metodo FindByCpfCustomer
  public List<Bicycle> findByCpfCustomer(String cpf){
    CustomerDAO customerDAO = new CustomerDAO(DB.getOracleConnection());
    if(!customerDAO.customerExistsByCpf(cpf)){
      throw new IllegalArgumentException("Customer with cpf: " + cpf + " does not exist");
    }

    List<Bicycle> bicycles = new ArrayList<>();
    String query = "SELECT * FROM tb_bicycle WHERE cpf_customer = ?";

    try(PreparedStatement ps = conn.prepareStatement(query)){
      ps.setString(1, cpf);
      try(ResultSet rs = ps.executeQuery()){
        while(rs.next()){
          Integer id = rs.getInt("id_bicycle");
          String serialNumber = rs.getString("num_serie_bicycle");
          String model = rs.getString("nm_bicycle");
          String brand = rs.getString("brand_bicycle");
          Double price = rs.getDouble("price_bicycle");
          String year = rs.getString("year_bicycle");
          String description = rs.getString("ds_bicycle");
          Customer customer = customerDAO.findByCpf(rs.getString("cpf_customer"));

          Bicycle bicycle = new Bicycle(id, serialNumber, model, brand, price, year, description, customer);
          bicycles.add(bicycle);
          return bicycles;
        }
      }
    } catch(SQLException e){
      throw new IllegalArgumentException("Customer with id: " + cpf + " does not exist");
    }
    return null;

  }

  // * Metodo UPDATE
  public void update(Bicycle bicycle){
    if(bicycle == null){
      throw new IllegalArgumentException("Bicycle parameter is null!");
    }
    
    if(!bicycleExistsById(bicycle.getId())){
      throw new IllegalArgumentException("Bicycle does not exist!");
    }

    String query = "UPDATE tb_bicycle SET num_serie_bicycle = ?, nm_bicycle = ?, " +
        "brand_bicycle = ?, price_bicycle = ?, year_bicycle = ?, ds_bicycle = ? WHERE id_bicycle = ?";

    try(PreparedStatement ps = conn.prepareStatement(query)){
      ps.setString(1, bicycle.getSerialNumber());
      ps.setString(2, bicycle.getModel());
      ps.setString(3, bicycle.getBrand());
      ps.setDouble(4, bicycle.getPrice());
      ps.setString(5, bicycle.getYear());
      ps.setString(6, bicycle.getDescription());
      ps.setInt(7, bicycle.getId());
      ps.executeUpdate();
    } catch(SQLException e){
      ExceptionHandler.handleSQLException(e, "Error updating Bicycle");
    }
  }

  // ! Metodo DELETE
  public void delete(Bicycle bicycle){
    if(bicycle == null){
      throw new IllegalArgumentException("Bicycle parameter is null!");
    }
    
    if(!bicycleExistsById(bicycle.getId())){
      throw new IllegalArgumentException("Bicycle does not exist");
    }

    String query = "DELETE FROM tb_bicycle WHERE id_bicycle = ?";

    try(PreparedStatement ps = conn.prepareStatement(query)){
      ps.setInt(1, bicycle.getId());
      ps.executeUpdate();
    } catch(SQLException e){
      ExceptionHandler.handleSQLException(e, "Error deleting Bicycle");
    }
}

  // ? Metodo Existe por ID
  private boolean bicycleExistsById(int id){
      String query = "SELECT id_bicycle FROM tb_bicycle WHERE id_bicycle = ?";

      try(PreparedStatement ps = conn.prepareStatement(query)){
        ps.setInt(1, id);
        ResultSet rs = ps.executeQuery();
        return rs.next();
      } catch(SQLException e){
        ExceptionHandler.handleSQLException(e, "Error checking bicycle existence by ID");
        return false;
      }
  }

}
