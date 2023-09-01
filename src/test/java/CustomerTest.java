package test.java;

import java.util.List;

import main.java.br.com.bikenow.model.dao.user.CustomerDAO;
import main.java.br.com.bikenow.model.entity.user.Customer;
import main.java.br.com.bikenow.model.infra.db.DB;

public class CustomerTest {
  public static void main(String[] args) {
    
    // ? Criando CustomerDAO
    CustomerDAO customerDAO = new CustomerDAO(DB.getOracleConnection());

    // * Inserindo Customer
    Customer customer = new Customer(84, "Pedro", "pedro@gmail.com", "119355156");
    //customerDAO.insert(customer);

    // * Atualizando Customer
    customer.setName("Dante");
    customer.setEmail("Dante@gmail.com");
    //customerDAO.update(customer);

    // ? Listando Customers
    List<Customer> customers = customerDAO.list();
    customers.forEach(c -> System.out.println(c.toString()));

    // ! Deletando Customer
    customerDAO.delete(customer);
    
  }
}
