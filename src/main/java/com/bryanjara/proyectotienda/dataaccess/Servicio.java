/*
 * Servicio.java
 *
 * Created on 25 de abril de 2007, 03:45 PM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package dataaccess;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

public class Servicio {

    protected Connection conexion= null;
    
    public Servicio() {
        
    }
    
    protected void conectar() throws SQLException,ClassNotFoundException 
    {
            Class.forName("oracle.jdbc.driver.OracleDriver");
       // try {
            conexion = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:XE","dropshipping","Tienda2025$");
            //conexion = getJdbcMydbsource();
       /* } catch (NamingException ex) {
            ex.printStackTrace();
        }*/
        
    }
    
    protected void desconectar() throws SQLException{
        if(!conexion.isClosed())
        {
            conexion.close();
        }
    }
    
    public static void main(String[] args){
       Servicio myDB = new Servicio();
        try {
            myDB.conectar();
            myDB.desconectar();
            System.out.println("Conexion exitosa");
        } catch (SQLException | ClassNotFoundException ex) {
            Logger.getLogger(Servicio.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}