/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package capadatossac;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 *
 * @author Pamela
 */
public class ConexionTablas {
    private Connection con=null;
    
    public Connection conexion() {
        try {
            Class.forName("org.postgresql.Driver");// carga el driver y oracle 

            String BaseDeDatos = "jdbc:postgresql://localhost:5432/BD_Sistema_AvaluosCatastros?autoReconnect=true&relaxAutoCommit=true"; //crea una variable con la direccion el puerto y la instancia (express)
            con = DriverManager.getConnection(BaseDeDatos, "postgres","kike1997");  // carga la conexion (usuario contraseña)
         
        } 
        catch (ClassNotFoundException | SQLException e) {
        }
        return con;
    }
}
