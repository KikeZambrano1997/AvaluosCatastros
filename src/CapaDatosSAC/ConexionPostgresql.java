/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package capadatossac;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javax.swing.JOptionPane;

/**
 *
 * @author Kike
 */
public class ConexionPostgresql {
    
    private Connection conexionBD;
    public Connection getConexion() {
        return conexionBD;
    }       
    public void setConexion(Connection conexionBD) {
        this.conexionBD = conexionBD;
    }
    
    public ConexionPostgresql conectar() {
        try {
            Class.forName("org.postgresql.Driver");// carga el driver y oracle 

            String BaseDeDatos = "jdbc:postgresql://localhost:5432/BD_Sistema_AvaluosCatastros?autoReconnect=true&relaxAutoCommit=true"; //crea una variable con la direccion el puerto y la instancia (express)
            conexionBD = DriverManager.getConnection(BaseDeDatos, "postgres","kike1997");  // carga la conexion (usuario contraseña)
         
            if (conexionBD != null) {
                 System.out.println("Conectado a la base de datos PostgreSQL!");
            } 
            else {
                 System.out.println("Error en la Conexión!");
            }
        } 
        catch (Exception e) {
             JOptionPane.showMessageDialog(null, e.getMessage()+"aqui es");
        }
        return this;
    }
    
    public boolean ejecutar(String sql) { //
        try {
            Statement sentencia; // objetos para sentencias de oracle 
            sentencia = getConexion().createStatement(ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_READ_ONLY); 
            sentencia.executeUpdate(sql); //ejecuta el insert delete y el updte 
            
            getConexion().commit();          
            
            //sentencia.getConnection().setAutoCommit(false);
        
        } catch (SQLException e) {
            if (e.getErrorCode()==0) return false; //Por error "Cannot commit when autocommit is enabled"
            JOptionPane.showMessageDialog(null, e.getErrorCode());
            return false;
        }        
        return true;
    }
    
    public ResultSet consultar(String sql) { 
        ResultSet resultado = null; 
        try { 
            Statement sentencia; 
            sentencia = getConexion().createStatement(ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_READ_ONLY);
            resultado = sentencia.executeQuery(sql); 
             
        } catch (SQLException e) { 
            e.printStackTrace(); 
            return null; 
        }        
        return resultado; 
    }
}
