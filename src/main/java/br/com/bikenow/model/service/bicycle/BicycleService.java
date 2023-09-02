package main.java.br.com.bikenow.model.service.bicycle;

import java.sql.Connection;
import java.util.List;

import main.java.br.com.bikenow.model.dao.bicycle.BicycleDAO;
import main.java.br.com.bikenow.model.dao.user.CustomerDAO;
import main.java.br.com.bikenow.model.entity.bicycle.Bicycle;
import main.java.br.com.bikenow.model.infra.db.DB;

public class BicycleService {

  private BicycleDAO bicycleDAO;

  private CustomerDAO customerDAO = null;

  public BicycleService(Connection connection){
    this.bicycleDAO = new BicycleDAO(connection);
  }

  // * CREATE
  public void createBicycle(Bicycle bicycle){

    if(bicycle == null){
      throw new RuntimeException("Bicycle parameter is null!");
    }

    CustomerDAO customerDAO = new CustomerDAO(DB.getOracleConnection());
    if(!customerDAO.customerExistsByCpf(bicycle.getOwnerCpf())){
      throw new IllegalArgumentException("Customer with cpf: " + bicycle.getOwnerCpf() + " does not exist");
    }

    if(bicycleDAO.bicycleExistsById(bicycle.getId())){
      throw new IllegalArgumentException("Bicycle with id: " + bicycle.getId() + " already exists");
    }

    bicycleDAO.insert(bicycle);

  }

  // ? READ - All
  public List<Bicycle> findAll(){
    return bicycleDAO.list();
  }

  // ? READ - By ID
  public Bicycle findById(Integer id){
    if(!bicycleDAO.bicycleExistsById(id)){
      throw new IllegalArgumentException("Bicycle with id: " + id + " does not exist");
    }
    return bicycleDAO.findById(id);
  }

  // ? READ - By Customer CPF
  public List<Bicycle> findByCustomer(String cpf){

    customerDAO = new CustomerDAO(DB.getOracleConnection());
    if(!customerDAO.customerExistsByCpf(cpf)){
      throw new IllegalArgumentException("Customer with cpf: " + cpf + " does not exist");
    }

    return bicycleDAO.findByCustomer(cpf);

  }

  // * UPDATE
  public void update(Bicycle bicycle){
    if(bicycle == null){
      throw new IllegalArgumentException("Bicycle parameter is null!");
    }
    
    if(!bicycleDAO.bicycleExistsById(bicycle.getId())){
      throw new IllegalArgumentException("Bicycle does not exist!");
    }

    bicycleDAO.update(bicycle);
  }

  // ! DELETE
  public void delete(Bicycle bicycle){
    if(bicycle == null){
      throw new IllegalArgumentException("Bicycle parameter is null!");
    }
    
    if(!bicycleDAO.bicycleExistsById(bicycle.getId())){
      throw new IllegalArgumentException("Bicycle does not exist");
    }

    bicycleDAO.delete(bicycle);

  }

}
