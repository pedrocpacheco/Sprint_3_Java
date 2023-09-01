package main.java.br.com.bikenow.model.service.user;

import java.sql.Connection;
import java.util.List;

import main.java.br.com.bikenow.model.dao.user.AnalystDAO;
import main.java.br.com.bikenow.model.entity.user.Analyst;

public class AnalystService {
  
  private AnalystDAO analystDAO;

  public AnalystService(Connection connection){
    this.analystDAO = new AnalystDAO(connection);
  }

  public void createAnalyst(Analyst analyst){
    if(analyst == null){
      throw new RuntimeException("Analyst parameter is null!");
    }

    if(analystDAO.analystExistsById(analyst.getId())){ 
      throw new IllegalArgumentException("Analyst with id: " + analyst.getId() + " already exists");
    }

    analystDAO.insert(analyst);
  }

  public List<Analyst> findAll(){
    return analystDAO.list();
  }

  public void updateAnalyst(Analyst analyst){
    if(analyst == null){
      throw new IllegalArgumentException("Analyst parameter is null!");
    }

    if(!analystDAO.analystExistsById(analyst.getId())){ 
      throw new IllegalArgumentException("analyst with id: " + analyst.getId() + " does not exists");
    }

    analystDAO.update(analyst);
  }

  public void deleteAnalyst(Analyst analyst){
    if (analyst == null) {
      throw new IllegalArgumentException("analyst parameter is null!");
    }

    if(!analystDAO.analystExistsById(analyst.getId())){
      throw new IllegalArgumentException("User with id: " + analyst.getRm() + " does not exist");
    }

    analystDAO.delete(analyst);
  }

}
