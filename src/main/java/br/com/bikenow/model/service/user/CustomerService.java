package main.java.br.com.bikenow.model.service.user;

import java.sql.Connection;
import java.util.List;

import main.java.br.com.bikenow.model.dao.user.CustomerDAO;
import main.java.br.com.bikenow.model.entity.user.Customer;

public class CustomerService {
  
  private CustomerDAO customerDAO;

  public CustomerService(Connection connection){
    this.customerDAO = new CustomerDAO(connection);
  }

  public void createCustomer(Customer customer){
    if(customer == null){
      throw new IllegalArgumentException("Customer parameter is null!");
    }

    if(customerDAO.customerExistsById(customer.getId())){
      throw new IllegalArgumentException("Customer with id: " + customer.getId() + " already exist");
    }

    if(customerDAO.customerExistsByCpf(customer.getCpf())){
      throw new IllegalArgumentException("Customer with cpf: " + customer.getCpf() + " already exist");
    }

    customerDAO.createCustomer(customer);
  }

  public List<Customer> findAll(){
    return customerDAO.findAll();
  }

  public Customer findById(Integer id){
    if (!customerDAO.customerExistsById(id)) {
      throw new IllegalArgumentException("Customer with id: " + id + " does not exist");
    }

    return customerDAO.findById(id);
  }
  

  public Customer findByCpf(String cpf){
    if (!customerDAO.customerExistsByCpf(cpf)) {
      throw new IllegalArgumentException("Customer with cpf: " + cpf + " does not exist");
    }

    return customerDAO.findByCpf(cpf);
  }

  public void updateCustomer(Customer customer){
    if(customer == null){
      throw new IllegalArgumentException("Customer parameter is null!");
    }

    if(!customerDAO.customerExistsByCpf(customer.getCpf())){ 
      throw new IllegalArgumentException("Customer with cpf: " + customer.getCpf() + " does not exists");
    }

    customerDAO.updateCustomer(customer);
  }

  public void deleteCustomer(Customer customer){
    if (customer == null) {
      throw new IllegalArgumentException("Customer parameter is null!");
    }

    if(!customerDAO.customerExistsByCpf(customer.getCpf())){
      throw new IllegalArgumentException("User with cpf: " + customer.getCpf() + " does not exist");
    }

    customerDAO.deleteCustomer(customer);
  }

}
