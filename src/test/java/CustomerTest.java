package test.java;

import java.util.List;

import main.java.br.com.bikenow.model.entity.user.Customer;
import main.java.br.com.bikenow.model.service.user.CustomerService;
import main.resources.infra.db.DB;

public class CustomerTest {
  public static void main(String[] args) {
    
    // ? Criando CustomerDAO
    CustomerService customerService = new CustomerService(DB.getOracleConnection());

    // * CREATE - Inserindo Customer
    Customer customer = new Customer(89, "Pedro", "pedro@gmail.com", "11211111311");
    customerService.createCustomer(customer);

    // ? READ - Listando todos
    List<Customer> customers = customerService.findAll();
    customers.forEach(c -> System.out.println(c.toString()));

    // ? READ - Listando por ID
    Customer customerById = customerService.findById(customer.getId());
    System.out.println(customerById.toString());

    // ? READ - Listando por CPF
    Customer customerByCpf = customerService.findByCpf(customer.getCpf());
    System.out.println(customerByCpf.toString());

    // * UPDADE - Atualizando Customer
    customer.setName("Dante");
    customer.setEmail("Dante@gmail.com");
    customerService.updateCustomer(customer);

    // ! Deletando Customer
    customerService.deleteCustomer(customer);
    
  }
}
