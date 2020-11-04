/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package UI;

//import java.awt.HeadlessException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author PANJAITAN
 */
public class MySQL {
    
    private String table = "";
    
    public static java.sql.ResultSet GetData(String query){
        java.sql.ResultSet res = null;
        try {
            java.sql.Connection conn = (Connection)Konfig.configDB();
            java.sql.Statement stm = conn.createStatement();
            res = stm.executeQuery(query);
        } catch (SQLException ex) {
            Logger.getLogger(MySQL.class.getName()).log(Level.SEVERE, null, ex);
        }
        return res;
    }
    
    public static java.sql.ResultSet Insert(String query, String tabel){
        java.sql.ResultSet res = null;
        try {
            
            java.sql.Connection conn = (Connection)Konfig.configDB();
            java.sql.PreparedStatement pstm = conn.prepareStatement(query);
            pstm.execute();
            
            java.sql.Statement stm = conn.createStatement();
            res = stm.executeQuery("SELECT * FROM " + tabel + " ORDER BY id DESC LIMIT 1");
        } catch (SQLException ex) {
            Logger.getLogger(MySQL.class.getName()).log(Level.SEVERE, null, ex);
        }
        return res;
    }
    
     public static java.sql.ResultSet Insert(String query){
        java.sql.ResultSet res = null;
        try {
            java.sql.Connection conn = (Connection)Konfig.configDB();
            java.sql.PreparedStatement pstm = conn.prepareStatement(query);
            pstm.execute();
        } catch (SQLException ex) {
            Logger.getLogger(MySQL.class.getName()).log(Level.SEVERE, null, ex);
        }
        return res;
    }
    
    public static boolean Update(String query, String id){
        boolean res = false;
        String testId = id != null ? id:"";
        try {
            java.sql.Connection conn = (Connection)Konfig.configDB();
            java.sql.Statement stm = conn.createStatement();
            stm.executeQuery(query);
        } catch (SQLException ex) {
            Logger.getLogger(MySQL.class.getName()).log(Level.SEVERE, null, ex);
        }
        return true;
    }
    
    public static boolean Delete(String query){
        try{
            java.sql.Connection conn = (Connection)Konfig.configDB();
            java.sql.PreparedStatement pstm = conn.prepareStatement(query);
            pstm.execute();
        }catch (SQLException ex) {
            Logger.getLogger(MySQL.class.getName()).log(Level.SEVERE, null, ex);
        }
        return true;
    }
    
}
