/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package conexion;

/**
 *
 * @author Lenovo LOQ
 */
import javax.swing.JOptionPane;
import java.sql.*;

public class Conexion {
    public Connection conectar() {
        Connection cn = null;
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            cn = DriverManager.getConnection("jdbc:mysql://localhost/sistema_empleados", "root", "");
            System.out.println((cn == null) ? "no valio" : "valio");
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
        }
        return cn;
    }
}