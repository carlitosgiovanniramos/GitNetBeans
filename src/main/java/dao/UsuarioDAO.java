/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;

import com.mysql.cj.jdbc.PreparedStatementWrapper;
import conexion.Conexion;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import modelo.Usuario;

/**
 *
 * @author Lenovo LOQ
 */
public class UsuarioDAO {

    public Usuario login(String usuario, String password) {

        Usuario user = null;

        String sql = """
                     SELECT id_usuario, id_empleado, usuario, password, rol, estado
                     FROM usuarios
                     WHERE usuario = ?
                     AND password = ?
                     AND estado = TRUE
                     """;

        try {
            Conexion cn = new Conexion();
            Connection cc = cn.conectar();
            PreparedStatement ps = cc.prepareStatement(sql);

            ps.setString(1, usuario);
            ps.setString(2, password);

            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                user = new Usuario();

                user.setIdUsuario(rs.getInt("id_usuario"));
                user.setIdEmpleado(rs.getInt("id_empleado"));
                user.setUsuario(rs.getString("usuario"));
                user.setPassword(rs.getString("password"));
                user.setRol(rs.getString("rol"));
                user.setEstado(rs.getBoolean("estado"));
            }

        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, "Error en Login: " + ex.getMessage());
        }
        return user;
    }

    public boolean insertarUsuario(Usuario usuario) {

        String sql = """
        INSERT INTO usuarios
        (id_empleado, usuario, password, rol, estado)
        VALUES (?, ?, ?, ?, ?)
    """;

        try {

            Conexion conexion = new Conexion();
            Connection con = conexion.conectar();

            PreparedStatement ps = con.prepareStatement(sql);

            ps.setInt(1, usuario.getIdEmpleado());
            ps.setString(2, usuario.getUsuario());
            ps.setString(3, usuario.getPassword());
            ps.setString(4, usuario.getRol());
            ps.setBoolean(5, usuario.isEstado());

            return ps.executeUpdate() > 0;

        } catch (Exception e) {

            System.out.println(
                    "Error al registrar usuario: " + e.getMessage()
            );

            return false;
        }
    }

    public boolean actualizarUsuarioPorEmpleado(int idEmpleado, String usuario, String password) {

        String sql = """
        UPDATE usuarios
        SET usuario = ?,
            password = ?
        WHERE id_empleado = ?
    """;

        try {
            Conexion conexion = new Conexion();
            Connection con = conexion.conectar();

            PreparedStatement ps = con.prepareStatement(sql);

            ps.setString(1, usuario);
            ps.setString(2, password);
            ps.setInt(3, idEmpleado);

            return ps.executeUpdate() > 0;

        } catch (Exception e) {
            System.out.println("Error al actualizar usuario del empleado: " + e.getMessage());
            return false;
        }
    }
}
