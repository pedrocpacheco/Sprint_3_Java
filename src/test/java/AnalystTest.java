package test.java;

import java.util.List;

import main.java.br.com.bikenow.model.entity.user.Analyst;
import main.java.br.com.bikenow.model.service.user.AnalystService;
import main.resources.infra.db.DB;

public class AnalystTest {
  public static void main(String[] args) {

    // ? Criando AnalystService
    AnalystService analystService = new AnalystService(DB.getOracleConnection());

    // * CREATE - Inserindo Analyst
    Analyst analyst = new Analyst(1000, "Analista99", "analista99@gmail.com", "111132111111");
    analystService.createAnalyst(analyst);

    // ? READ - Listando Analysts
    List<Analyst> analysts = analystService.findAll();
    analysts.forEach(a -> System.out.println(a.toString()));

    // ? READ - Listando Analyst por Rm
    Analyst analystByRm = analystService.findByRm("111132111111");
    System.out.println(analystByRm.toString());

    // * UPDATE-  Atualizando Analyst
    analyst.setName("OI");
    analyst.setEmail("dante@gmail.com");
    analystService.updateAnalyst(analyst);

    // ! DELETE - Deletando Analyst
    analystService.deleteAnalyst(analyst);
    
  }
}
