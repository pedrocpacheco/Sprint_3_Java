package main.java.br.com.bikenow.model.dao.bicycle;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import main.java.br.com.bikenow.model.entity.bicycle.Addon;
import main.java.br.com.bikenow.model.entity.bicycle.Bicycle;
import main.java.br.com.bikenow.model.infra.db.DB;
import main.java.br.com.bikenow.model.infra.exceptions.ExceptionHandler;

public class AddonDAO {
  
  private Connection conn;

  public AddonDAO(Connection connection){
    this.conn = connection;
  }

  // * Metodo CREATE
  public void insert(Addon addon){
    if(addon == null){
      throw new IllegalArgumentException("Addon parameter is null!");
    }

    BicycleDAO bicycleDAO = new BicycleDAO(DB.getOracleConnection());
    if(bicycleDAO.bicycleExistsById(addon.getOwner().getId())){
      throw new IllegalArgumentException("Bicycle with id: " + addon.getOwner().getId() + " already exist");
    } 

    if(addonExistsById(addon.getId())){
      throw new IllegalArgumentException("Addon with id: " + addon.getId() + " already exists");
    }

    String query = "INSERT INTO tb_addon VALUES(?, ?, ?, ?, ?, ?)";

    try(PreparedStatement ps = conn.prepareStatement(query)){
      ps.setInt(1, addon.getId());
      ps.setString(2, addon.getName());
      ps.setString(3, addon.getBrand());
      ps.setDouble(4, addon.getPrice());
      ps.setString(5, addon.getDescription());
      ps.setString(6, addon.getOwner().getSerialNumber());
      ps.execute();
    } catch(SQLException e){
      ExceptionHandler.handleSQLException(e, "Error inserting Addon");
    }
  }

  // ? Metodo Find All
  public List<Addon> list(){
    List<Addon> addons = new ArrayList<>();
    String query = "SELECT * FROM tb_addon";

    try(PreparedStatement ps = conn.prepareStatement(query)){
      try(ResultSet rs = ps.executeQuery()){
        while(rs.next()){
          int id = rs.getInt("id_addon");
          String name = rs.getString("nm_addon");
          String brand = rs.getString("brand_addon");
          Double price = rs.getDouble("price_addon");
          String description = rs.getString("ds_addon");
          BicycleDAO bicycleDAO = new BicycleDAO(DB.getOracleConnection());
          Bicycle bicycle = bicycleDAO.findById(rs.getInt("id_bicycle"));

          Addon addon = new Addon(id, name, brand, price, description, bicycle);
          addons.add(addon);
        }
      }
    } catch(SQLException e){
      ExceptionHandler.handleSQLException(e, "Error updating Customer");
    }
    return addons;
  }

  // ? Metodo Find By Id
  public Addon findById(Integer id){
    if(!addonExistsById(id)){
      throw new IllegalArgumentException("Addon with id: " + id + " does not exist");
    }

    String query = "SELECT * from tb_addon WHERE id_addon = ?";

    try(PreparedStatement ps = conn.prepareStatement(query)){
      ps.setInt(1, id);
      try(ResultSet rs = ps.executeQuery()){
        if(rs.next()){
          String name = rs.getString("nm_addon");
          String brand = rs.getString("brand_addon");
          Double price = rs.getDouble("price_addon");
          String description = rs.getString("ds_addon");
          BicycleDAO bicycleDAO = new BicycleDAO(DB.getOracleConnection());
          Bicycle bicycle = bicycleDAO.findById(rs.getInt("id_bicycle"));

          Addon addon = new Addon(id, name, brand, price, description, bicycle);
          return addon;
        }
      }
    } catch(SQLException e){
      ExceptionHandler.handleSQLException(e, "Error Finding Addon");
    }
    return null;
  }

  // ? Metodo FindByBicycleId
  public List<Addon> findByBicycleId(Integer id){
    BicycleDAO bicycleDAO = new BicycleDAO(DB.getOracleConnection());
    if(!bicycleDAO.bicycleExistsById(id)){
      throw new IllegalArgumentException("Bicycle with cpf: " + id + " does not exist");
    }

    List<Addon> addons = new ArrayList<>();
    String query = "SELECET * FROM tb_addon WHERE id_bicycle = ?";

    try(PreparedStatement ps = conn.prepareStatement(query)){
      ps.setInt(1, id);
      try(ResultSet rs = ps.executeQuery()){
        while(rs.next()){
          String name = rs.getString("nm_addon");
          String brand = rs.getString("brand_addon");
          Double price = rs.getDouble("price_addon");
          String description = rs.getString("ds_addon");
          Bicycle bicycle = bicycleDAO.findById(rs.getInt("id_bicycle"));

          Addon addon = new Addon(id, name, brand, price, description, bicycle);
          addons.add(addon);
        }
      }
    } catch(SQLException e){
      ExceptionHandler.handleSQLException(e, "Error inserting Bicycle");
    }
    return addons;
  }

  // * Metodo UPDATE 
  public void update(Addon addon){
    if(addon == null){
      throw new IllegalArgumentException("Addon parameter is null!");
    }

    if(!addonExistsById(addon.getId())){
      throw new IllegalArgumentException("Addon does not exist!");
    }

    String query = "UPDATE tb_addon SET nm_addon = ?, brand_addon = ?, price_addon = ?, ds_addon = ?";

    try(PreparedStatement ps = conn.prepareStatement(query)){
        ps.setString(1, addon.getName());
        ps.setString(2, addon.getBrand());
        ps.setDouble(3, addon.getPrice());
        ps.setString(4, addon.getDescription());
        ps.executeUpdate();
    } catch(SQLException e){
      ExceptionHandler.handleSQLException(e, "Error inserting Bicycle");
    }
  }

  // ! Metodo Delete
  public void delete(Addon addon){
    if(addon == null){
      throw new IllegalArgumentException("Addon parameter is null");
    }

    if(!addonExistsById(addon.getId())){
      throw new IllegalArgumentException("Addon does not exist");
    }

    String query = "DELETE FROM tb_addon WHERE id_addon = ?";

    try(PreparedStatement ps = conn.prepareStatement(query)){
      ps.setInt(1, addon.getId());
      ps.executeUpdate();
    } catch(SQLException e){
      ExceptionHandler.handleSQLException(e, "Error deleting Bicycle");
    }
  }


  // ? Metodo Existe por ID
  private boolean addonExistsById(int id){
      String query = "SELECT id_addon FROM tb_addon WHERE id_addon = ?";

      try(PreparedStatement ps = conn.prepareStatement(query)){
        ps.setInt(1, id);
        ResultSet rs = ps.executeQuery();
        return rs.next();
      } catch(SQLException e){
        ExceptionHandler.handleSQLException(e, "Error checking Addon existence by ID");
        return false;
      }
  }

}
