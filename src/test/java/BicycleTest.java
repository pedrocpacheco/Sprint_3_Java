package test.java;

import java.util.List;

import main.java.br.com.bikenow.model.dao.bicycle.BicycleDAO;
import main.java.br.com.bikenow.model.dao.user.CustomerDAO;
import main.java.br.com.bikenow.model.entity.bicycle.Bicycle;
import main.java.br.com.bikenow.model.entity.user.Customer;
import main.java.br.com.bikenow.model.infra.db.DB;

public class BicycleTest {
  public static void main(String[] args) {
    
    BicycleDAO bicycleDAO = new BicycleDAO(DB.getOracleConnection());

    Customer owner = new CustomerDAO(DB.getOracleConnection()).findByCpf("11111111211");
    System.out.println(owner.getCpf() + "a");
    Bicycle bicycle = new Bicycle(3, "10", "M", "B", 20.50, "2005", "D", owner);
  
   bicycleDAO.insert(bicycle);

    List<Bicycle> bicycles = bicycleDAO.list();
    bicycles.forEach(b -> System.out.println(b.toString()));



  }
}
