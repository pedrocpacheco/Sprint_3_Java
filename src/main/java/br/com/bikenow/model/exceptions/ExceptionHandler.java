package main.java.br.com.bikenow.model.exceptions;

import java.sql.SQLException;

public abstract class ExceptionHandler {

    public static void handleSQLException(SQLException e, String errorMessage) {
        System.err.println(errorMessage);
        e.printStackTrace();
    }
    
}