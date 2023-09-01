package test.java;

import java.util.List;

import main.java.br.com.bikenow.model.entity.user.Analyst;
import main.java.br.com.bikenow.model.infra.db.DB;
import main.java.br.com.bikenow.model.service.user.AnalystService;

public class AnalystTest {
  public static void main(String[] args) {

    // ? Criando AnalystService
    AnalystService analystService = new AnalystService(DB.getOracleConnection());

    // * Inserindo Analyst
    Analyst analyst = new Analyst(1000, "Analista99", "analista99@gmail.com", "111132111111");
    analystService.createAnalyst(analyst);

    // ? Listando Analysts
    List<Analyst> analysts = analystService.findAll();
    analysts.forEach(a -> System.out.println(a.toString()));

    // ? Listando Analyst por Rm
    Analyst analystByRm = analystService.findByRm("111132111111");
    System.out.println(analystByRm.toString());

    // * Atualizando Analyst
    analyst.setName("OI");
    analyst.setEmail("dante@gmail.com");
    analystService.updateAnalyst(analyst);

    // ! Deletando Analyst
    analystService.deleteAnalyst(analyst);
    
  }
}
