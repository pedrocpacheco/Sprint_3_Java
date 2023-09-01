package test.java;

import java.util.List;

import main.java.br.com.bikenow.model.dao.user.CustomerDAO;
import main.java.br.com.bikenow.model.entity.bicycle.Bicycle;
import main.java.br.com.bikenow.model.entity.user.Customer;
import main.java.br.com.bikenow.model.infra.db.DB;
import main.java.br.com.bikenow.model.service.bicycle.BicycleService;

public class BicycleTest {
  public static void main(String[] args) {
    
    // ? Criando Bicycle Service
    BicycleService bicycleService = new BicycleService(DB.getOracleConnection());

    // * CREATE - Criando e Salvando Biciclceta
    Customer owner = new CustomerDAO(DB.getOracleConnection()).findByCpf("11111111111");
    Bicycle bicycle = new Bicycle(5, "10", "M", "B", 20.50, "2005", "D", owner);
  
    bicycleService.createBicycle(bicycle);

    // ? READ - Lendo todas as bicicletas
    List<Bicycle> bicycles = bicycleService.findAll();
    bicycles.forEach(b -> System.out.println(b.toString()));

    // ? READ - Lendo bicicleta por bicycleId
    Bicycle bicycleById = bicycleService.findById(5);
    System.out.println(bicycleById.toString());

    // ? READ - Lendo bicicletas por customerCpf
    List<Bicycle> bicyclesByCpf = bicycleService.findByCustomer(bicycle.getOwnerCpf());
    bicyclesByCpf.forEach(bc -> System.out.println(bc.toString()));

    // * UPDATE - Atualizando Bicicleta
    bicycle.setSerialNumber("1");
    bicycle.setModel("N");
    bicycle.setBrand("L");
    bicycle.setPrice(40.20);
    bicycle.setYear("2019");
    bicycle.setDescription("R");
    bicycleService.update(bicycle);

    // ! DELETE - Deletando Bicicleta
    bicycleService.delete(bicycle);

  }
}
