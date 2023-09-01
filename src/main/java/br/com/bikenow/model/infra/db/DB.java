package main.java.br.com.bikenow.model.infra.db;

import java.sql.Connection;
import java.sql.SQLException;

import oracle.jdbc.datasource.impl.OracleDataSource;

public abstract class DB {

  private static String url = "jdbc:oracle:thin:@oracle.fiap.com.br:1521:ORCL";

  public static Connection getOracleConnection(){
    OracleDataSource ods = null;

    try {
      ods = new OracleDataSource();
    } catch (SQLException e) {
      System.err.println("Error creating OracleDataSource");
      e.printStackTrace();
    }

    ods.setURL(url);
    ods.setUser(Credentials.user);
    ods.setPassword(Credentials.pwd);

    try {
      return ods.getConnection();
    } catch (SQLException e) {
      System.err.println("Error returning Connection");
      e.printStackTrace();
    }
    return null;
  }

}
