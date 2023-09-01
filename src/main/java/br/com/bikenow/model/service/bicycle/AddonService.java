package main.java.br.com.bikenow.model.service.bicycle;

import java.sql.Connection;
import java.util.List;

import main.java.br.com.bikenow.model.dao.bicycle.AddonDAO;
import main.java.br.com.bikenow.model.dao.bicycle.BicycleDAO;
import main.java.br.com.bikenow.model.entity.bicycle.Addon;
import main.java.br.com.bikenow.model.infra.db.DB;

public class AddonService {
  
  private AddonDAO addonDAO;

  private BicycleDAO bicycleDAO = null;

  public AddonService(Connection connection){
    this.addonDAO = new AddonDAO(connection);
  }

  public void createAddon(Addon addon){
    if(addon == null){
      throw new IllegalArgumentException("Addon parameter is null!");
    }

    if(addonDAO.addonExistsById(addon.getId())){
      throw new IllegalArgumentException("Addon with id: " + addon.getId() + " already exists");
    }

    bicycleDAO = new BicycleDAO(DB.getOracleConnection());
    if(!bicycleDAO.bicycleExistsById(addon.getOwner().getId())){
      throw new IllegalArgumentException("Addon with id: " + addon.getOwner().getId() + " does not exist");
    } 

    addonDAO.insert(addon);
  }

  public List<Addon> findAll(){
    return addonDAO.list();
  }

  public Addon findById(Integer id){
    if(!addonDAO.addonExistsById(id)){
      throw new IllegalArgumentException("Addon with id: " + id + " does not exist");
    }

    return addonDAO.findById(id);
  }

  public List<Addon> findByBicycleId(Integer id){
    return addonDAO.findByBicycleId(id);
  }

  public void updateBicycle(Addon addon){
    if(addon == null){
      throw new IllegalArgumentException("Addon parameter is null!");
    }

    if(!addonDAO.addonExistsById(addon.getId())){
      throw new IllegalArgumentException("Addon does not exist!");
    }

    addonDAO.update(addon);
  }

  public void deleteAddon(Addon addon){
    if(addon == null){
      throw new IllegalArgumentException("Addon parameter is null");
    }

    if(!addonDAO.addonExistsById(addon.getId())){
      throw new IllegalArgumentException("Addon does not exist");
    }

    addonDAO.delete(addon);
  }

}
