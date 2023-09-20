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
import main.resources.infra.db.DB;
import main.resources.infra.exceptions.ExceptionHandler;

public class BicycleDAO {
  
  // ? Conexão
  private Connection conn;

  public BicycleDAO(Connection connection){
    this.conn = connection;
  }

  // ? Metodo Existe por ID
  public boolean bicycleExistsById(int id){
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

  // * Metodo CREATE
  public void createBicycle(Bicycle bicycle){
    String query = "INSERT INTO tb_bicycle (num_serie_bicycle, nm_bicycle, brand_bicycle, price_bicycle, year_bicycle, ds_bicycle, id_bicycle, cpf_customer) " + 
                  "VALUES(?, ?, ?, ?, ?, ?, ?, ?)";
 
    try(PreparedStatement ps = conn.prepareStatement(query)){
      setParameters(ps, bicycle);
      ps.setString(8, bicycle.getOwnerCpf());
      ps.execute();
    } catch(SQLException e){
      ExceptionHandler.handleSQLException(e, "Error inserting Bicycle");
    }
  }

  // ? Metodo List
  public List<Bicycle> findAll(){
    List<Bicycle> bicycles = new ArrayList<>();
    String query = "SELECT * FROM tb_bicycle";

    try(PreparedStatement ps = conn.prepareStatement(query)){
      ResultSet rs = ps.executeQuery();

      while(rs.next()){
        bicycles.add(bicycleFromResultSet(rs));
      }
    
    } catch(SQLException e){
      ExceptionHandler.handleSQLException(e, "Error updating Customer");
    }
    return bicycles;
  }

  // ? Metodo FindById
  public Bicycle findById(Integer id){
    String query = "SELECT * FROM tb_bicycle WHERE id_bicycle = ?";

    try(PreparedStatement ps = conn.prepareStatement(query)){
      ps.setInt(1, id);
      try(ResultSet rs = ps.executeQuery()){
        if(rs.next()){
          return bicycleFromResultSet(rs);
        }
      }
      
    } catch(SQLException e){
      ExceptionHandler.handleSQLException(e, "Error finding bicycle");
    }
    return null;
  }

  // ? Metodo FindByCpfCustomer
  public List<Bicycle> findByCustomer(String cpf){
    List<Bicycle> bicycles = new ArrayList<>();
    String query = "SELECT * FROM tb_bicycle WHERE cpf_customer = ?";

    try(PreparedStatement ps = conn.prepareStatement(query)){
      ps.setString(1, cpf);
      try(ResultSet rs = ps.executeQuery()){
        while(rs.next()){
          bicycles.add(bicycleFromResultSet(rs));
          return bicycles;
        }
      }
    } catch(SQLException e){
      ExceptionHandler.handleSQLException(e, "Error finding Bicycle");
    }
    return null;

  }

  // * Metodo UPDATE
  public void updateBicycle(Bicycle bicycle){
    String query = "UPDATE tb_bicycle SET num_serie_bicycle = ?, nm_bicycle = ?, " +
        "brand_bicycle = ?, price_bicycle = ?, year_bicycle = ?, ds_bicycle = ? WHERE id_bicycle = ?";

    try(PreparedStatement ps = conn.prepareStatement(query)){
      setParameters(ps, bicycle);
      ps.executeUpdate();
    } catch(SQLException e){
      ExceptionHandler.handleSQLException(e, "Error updating Bicycle");
    }
  }

  // ! Metodo DELETE
  public void deleteBicycle(Bicycle bicycle){
    
    String query = "DELETE FROM tb_bicycle WHERE id_bicycle = ?";

    try(PreparedStatement ps = conn.prepareStatement(query)){
      ps.setInt(1, bicycle.getId());
      ps.executeUpdate();
    } catch(SQLException e){
      ExceptionHandler.handleSQLException(e, "Error deleting Bicycle");
    }
}

  // Create PS From Bicycle 
  private void setParameters(PreparedStatement ps, Bicycle bicycle) throws SQLException{
      ps.setString(1, bicycle.getSerialNumber());
      ps.setString(2, bicycle.getModel());
      ps.setString(3, bicycle.getBrand());
      ps.setDouble(4, bicycle.getPrice());
      ps.setString(5, bicycle.getYear());
      ps.setString(6, bicycle.getDescription());
      ps.setInt(7, bicycle.getId());
  }

  // Create Bicycle From RS
  private Bicycle bicycleFromResultSet(ResultSet rs) throws SQLException{
    Integer id = rs.getInt("id_bicycle");
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
