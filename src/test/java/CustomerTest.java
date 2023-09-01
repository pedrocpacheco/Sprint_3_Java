package test.java;

import main.java.br.com.bikenow.model.dao.user.CustomerDAO;
import main.java.br.com.bikenow.model.db.DB;
import main.java.br.com.bikenow.model.entity.user.Customer;

public class CustomerTest {
  public static void main(String[] args) {
    
    CustomerDAO customerDAO = new CustomerDAO(DB.getConnection());

    Customer customer = new Customer(77, "Pedro", "pedro@gmail.com", "119935556");
    String msg = customerDAO.insert(customer);

    System.out.println(msg);

  }
}
