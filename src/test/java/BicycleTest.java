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
    Bicycle bicycle = new Bicycle(3, "10", "M", "B", 20.50, "2005", "D", owner);
  
    //bicycleDAO.insert(bicycle);

    List<Bicycle> bicycles = bicycleDAO.list();
    ////bicycles.forEach(b -> System.out.println(b.toString()));

    //Bicycle bicycle2 = bicycleDAO.findById(1);
    ///System.out.println(bicycle2.toString());
    
    //List<Bicycle> bicycles2 = bicycleDAO.findByCpfCustomer("11111111211");
    //bicycles2.forEach(b2 -> System.out.println(b2.toString()));

    bicycle.setSerialNumber("1");
    bicycle.setModel("N");
    bicycle.setBrand("L");
    bicycle.setPrice(40.20);
    bicycle.setYear("2019");
    bicycle.setDescription("R");
    //bicycleDAO.update(bicycle);

    bicycleDAO.delete(bicycle);

  }
}
