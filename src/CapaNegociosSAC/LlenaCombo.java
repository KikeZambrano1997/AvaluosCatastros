/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package CapaNegociosSAC;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

/**
 *
 * @author Pamela
 */
public class LlenaCombo {
    static Connection conexion=null;
    static Statement sentencia;
    static ResultSet resultado;
    public static void conectar(){
        String ruta="jdbc:postgresql://localhost:5432/BD_Sistema_AvaluosCatastros?autoReconnect=true&relaxAutoCommit=true";
        String user="postgres";
        String pass="kike1997";
        try {
            Class.forName("org.postgresql.Driver");
            conexion=DriverManager.getConnection(ruta,user,pass); 
            sentencia= conexion.createStatement();
            System.out.println("Conectado");
        } catch (Exception e) {
            System.out.println("No conectado");
        }
    }
    public static ArrayList<String> CB_Parroquia(){
        ArrayList<String> lista = new ArrayList<String>();
        String q = "SELECT * FROM parroquia order by id";
        try {
            resultado = sentencia.executeQuery(q);
            while(resultado.next()){
                lista.add(resultado.getString("nombre"));
            }
        } catch (Exception e) {
            
        }
        return lista;
    }
    
    public static ArrayList<String> CB_AccesibilidadRiego(){
        ArrayList<String> lista = new ArrayList<String>();
        String q = "SELECT * FROM accesibilidadriego order by id";
        try {
            resultado = sentencia.executeQuery(q);
            while(resultado.next()){
                lista.add(resultado.getString("frecuencia"));
            }
        } catch (Exception e) {
            
        }
        return lista;
    }
    
    public static ArrayList<String> CB_AccesoVias(){
        ArrayList<String> lista = new ArrayList<String>();
        String q = "SELECT * FROM accesoyvias order by id";
        try {
            resultado = sentencia.executeQuery(q);
            while(resultado.next()){
                lista.add(resultado.getString("accesoyvias"));
            }
        } catch (Exception e) {
            
        }
        return lista;
    }
    
    public static ArrayList<String> CB_CalidadSuelo(){
        ArrayList<String> lista = new ArrayList<String>();
        String q = "SELECT * FROM calidadsuelo order by id";
        try {
            resultado = sentencia.executeQuery(q);
            while(resultado.next()){
                lista.add(resultado.getString("tiporiesgo"));
            }
        } catch (Exception e) {
            
        }
        return lista;
    }
    
    public static ArrayList<String> CB_Drenaje(){
        ArrayList<String> lista = new ArrayList<String>();
        String q = "SELECT * FROM drenaje order by id";
        try {
            resultado = sentencia.executeQuery(q);
            while(resultado.next()){
                lista.add(resultado.getString("drenaje"));
            }
        } catch (Exception e) {
            
        }
        return lista;
    }
    
    public static ArrayList<String> CB_Erosion(){
        ArrayList<String> lista = new ArrayList<String>();
        String q = "SELECT * FROM erosion order by id";
        try {
            resultado = sentencia.executeQuery(q);
            while(resultado.next()){
                lista.add(resultado.getString("erosion"));
            }
        } catch (Exception e) {
            
        }
        return lista;
    }
    
    public static ArrayList<String> CB_FormaPredio(){
        ArrayList<String> lista = new ArrayList<String>();
        String q = "SELECT * FROM formapredio order by id";
        try {
            resultado = sentencia.executeQuery(q);
            while(resultado.next()){
                lista.add(resultado.getString("formapredio"));
            }
        } catch (Exception e) {
            
        }
        return lista;
    }
    
    public static ArrayList<String> CB_PoblacionesCercanas(){
        ArrayList<String> lista = new ArrayList<String>();
        String q = "SELECT * FROM poblcercanas order by id";
        try {
            resultado = sentencia.executeQuery(q);
            while(resultado.next()){
                lista.add(resultado.getString("poblacionescercanas"));
            }
        } catch (Exception e) {
            
        }
        return lista;
    }
    
    
    public static ArrayList<String> CB_ServiciosBasicos(){
        ArrayList<String> lista = new ArrayList<String>();
        String q = "SELECT * FROM servbasicos order by id";
        try {
            resultado = sentencia.executeQuery(q);
            while(resultado.next()){
                lista.add(resultado.getString("serviciosbasicos"));
            }
        } catch (Exception e) {
            
        }
        return lista;
    }
    
    public static ArrayList<String> CB_Superficie(){
        ArrayList<String> lista = new ArrayList<String>();
        String q = "SELECT * FROM superficie order by id";
        try {
            resultado = sentencia.executeQuery(q);
            while(resultado.next()){
                lista.add(resultado.getString("superficie"));
            }
        } catch (Exception e) {
            
        }
        return lista;
    }
    
    public static ArrayList<String> CB_TipoRiesgos(){
        ArrayList<String> lista = new ArrayList<String>();
        String q = "SELECT * FROM tiporiesgos order by id";
        try {
            resultado = sentencia.executeQuery(q);
            while(resultado.next()){
                lista.add(resultado.getString("riesgos"));
            }
        } catch (Exception e) {
            
        }
        return lista;
    }
    
    public static ArrayList<String> CB_Topograficos(){
        ArrayList<String> lista = new ArrayList<String>();
        String q = "SELECT * FROM topograficos order by id";
        try {
            resultado = sentencia.executeQuery(q);
            while(resultado.next()){
                lista.add(resultado.getString("topograficos"));
            }
        } catch (Exception e) {
            
        }
        return lista;
    }
    
    public static ArrayList<String> CB_IngresoCiudades(){
        ArrayList<String> lista = new ArrayList<String>();
        String q = "SELECT * FROM ciudad order by id";
        try {
            resultado = sentencia.executeQuery(q);
            while(resultado.next()){
                lista.add(resultado.getString("nombre"));
            }
        } catch (Exception e) {
            
        }
        return lista;
    }

    
}
