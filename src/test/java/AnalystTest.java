package test.java;

import java.util.List;

import main.java.br.com.bikenow.model.dao.user.AnalystDAO;
import main.java.br.com.bikenow.model.entity.user.Analyst;
import main.java.br.com.bikenow.model.infra.db.DB;

public class AnalystTest {
  public static void main(String[] args) {

    // ? Criando CustomerDAO
    AnalystDAO analystDAO = new AnalystDAO(DB.getOracleConnection());

    // * Inserindo Customer
    Analyst analyst = new Analyst(99, "Pedro", "pedro@gmail.com", "9999");
    analystDAO.insert(analyst);

    // * Atualizando Customer
    analyst.setName("Dante");
    analyst.setEmail("dante@gmail.com");
    analystDAO.update(analyst);

    // ? Listando Customers
    List<Analyst> analysts = analystDAO.list();
    analysts.forEach(a -> System.out.println(a.toString()));

    // ! Deletando Customer
    analystDAO.delete(analyst);
    
  }
}
