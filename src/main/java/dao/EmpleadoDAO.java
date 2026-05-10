/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;

import conexion.Conexion;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import modelo.Empleado;
import java.util.ArrayList;
import java.util.List;
import java.sql.ResultSet;

/**
 *
 * @author Lenovo LOQ
 */
public class EmpleadoDAO {

    public List<Empleado> listarEmpleadosActivos() {

        List<Empleado> lista = new ArrayList<>();

        String sql = """
        SELECT id_empleado, cedula, nombres, apellidos, telefono,
               correo, tipo_empleado, sueldo_fijo, valor_hora, estado
        FROM empleados
        WHERE estado = 1
        ORDER BY id_empleado DESC
    """;

        try {
            Conexion conexion = new Conexion();
            Connection con = conexion.conectar();

            PreparedStatement ps = con.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                Empleado empleado = new Empleado();

                empleado.setIdEmpleado(rs.getInt("id_empleado"));
                empleado.setCedula(rs.getString("cedula"));
                empleado.setNombres(rs.getString("nombres"));
                empleado.setApellidos(rs.getString("apellidos"));
                empleado.setTelefono(rs.getString("telefono"));
                empleado.setCorreo(rs.getString("correo"));
                empleado.setTipoEmpleado(rs.getString("tipo_empleado"));
                empleado.setSueldoFijo(rs.getDouble("sueldo_fijo"));
                empleado.setValorHora(rs.getDouble("valor_hora"));
                empleado.setEstado(rs.getBoolean("estado"));

                lista.add(empleado);
            }

        } catch (Exception e) {
            System.out.println("Error al listar empleados: " + e.getMessage());
        }

        return lista;
    }

    public int insertarEmpleadoRetornarId(Empleado empleado) {

        String sql = """
        INSERT INTO empleados 
        (cedula, nombres, apellidos, telefono, correo, tipo_empleado, sueldo_fijo, valor_hora, estado)
        VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)
    """;

        try {
            Conexion conexion = new Conexion();
            Connection con = conexion.conectar();

            PreparedStatement ps = con.prepareStatement(
                    sql,
                    PreparedStatement.RETURN_GENERATED_KEYS
            );

            ps.setString(1, empleado.getCedula());
            ps.setString(2, empleado.getNombres());
            ps.setString(3, empleado.getApellidos());
            ps.setString(4, empleado.getTelefono());
            ps.setString(5, empleado.getCorreo());
            ps.setString(6, empleado.getTipoEmpleado());
            ps.setDouble(7, empleado.getSueldoFijo());
            ps.setDouble(8, empleado.getValorHora());
            ps.setBoolean(9, empleado.isEstado());

            int filas = ps.executeUpdate();

            if (filas > 0) {

                ResultSet rs = ps.getGeneratedKeys();

                if (rs.next()) {
                    return rs.getInt(1);
                }
            }

        } catch (Exception e) {
            System.out.println("Error al registrar empleado: " + e.getMessage());
        }

        return 0;
    }

    public boolean existeCedula(String cedula) {

        String sql = "SELECT COUNT(*) FROM empleados WHERE cedula = ?";

        try {
            Conexion conexion = new Conexion();
            Connection con = conexion.conectar();

            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, cedula);

            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                return rs.getInt(1) > 0;
            }

        } catch (Exception e) {
            System.out.println("Error al verificar cédula: " + e.getMessage());
        }

        return false;
    }

    public boolean existeCorreo(String correo) {

        String sql = "SELECT COUNT(*) FROM empleados WHERE correo = ?";

        try {
            Conexion conexion = new Conexion();
            Connection con = conexion.conectar();

            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, correo);

            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                return rs.getInt(1) > 0;
            }

        } catch (Exception e) {
            System.out.println("Error al verificar correo: " + e.getMessage());
        }

        return false;
    }

    public boolean actualizarEmpleado(Empleado empleado) {

        String sql = """
        UPDATE empleados
        SET cedula = ?,
            nombres = ?,
            apellidos = ?,
            telefono = ?,
            correo = ?,
            tipo_empleado = ?,
            sueldo_fijo = ?,
            valor_hora = ?
        WHERE id_empleado = ?
    """;

        try {
            Conexion conexion = new Conexion();
            Connection con = conexion.conectar();

            PreparedStatement ps = con.prepareStatement(sql);

            ps.setString(1, empleado.getCedula());
            ps.setString(2, empleado.getNombres());
            ps.setString(3, empleado.getApellidos());
            ps.setString(4, empleado.getTelefono());
            ps.setString(5, empleado.getCorreo());
            ps.setString(6, empleado.getTipoEmpleado());
            ps.setDouble(7, empleado.getSueldoFijo());
            ps.setDouble(8, empleado.getValorHora());
            ps.setInt(9, empleado.getIdEmpleado());

            return ps.executeUpdate() > 0;

        } catch (Exception e) {
            System.out.println("Error al actualizar empleado: " + e.getMessage());
            return false;
        }
    }

    public boolean existeCedulaEnOtroEmpleado(String cedula, int idEmpleado) {

        String sql = """
        SELECT COUNT(*) 
        FROM empleados 
        WHERE cedula = ? 
        AND id_empleado <> ?
    """;

        try {
            Conexion conexion = new Conexion();
            Connection con = conexion.conectar();

            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, cedula);
            ps.setInt(2, idEmpleado);

            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                return rs.getInt(1) > 0;
            }

        } catch (Exception e) {
            System.out.println("Error al verificar cédula en otro empleado: " + e.getMessage());
        }

        return false;
    }

    public boolean eliminarEmpleado(int idEmpleado) {

        String sqlEmpleado = """
        UPDATE empleados
        SET estado = false
        WHERE id_empleado = ?
    """;

        String sqlUsuario = """
        UPDATE usuarios
        SET estado = false
        WHERE id_empleado = ?
    """;

        try {
            Conexion conexion = new Conexion();
            Connection con = conexion.conectar();

            PreparedStatement psEmpleado = con.prepareStatement(sqlEmpleado);
            psEmpleado.setInt(1, idEmpleado);
            int filasEmpleado = psEmpleado.executeUpdate();

            PreparedStatement psUsuario = con.prepareStatement(sqlUsuario);
            psUsuario.setInt(1, idEmpleado);
            psUsuario.executeUpdate();

            return filasEmpleado > 0;

        } catch (Exception e) {
            System.out.println("Error al eliminar empleado y usuario: " + e.getMessage());
            return false;
        }
    }

    public Empleado buscarPorCedula(String cedula) {

        String sql = """
        SELECT *
        FROM empleados
        WHERE cedula = ?
        AND estado = true
    """;

        try {
            Conexion conexion = new Conexion();
            Connection con = conexion.conectar();

            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, cedula);

            ResultSet rs = ps.executeQuery();

            if (rs.next()) {

                Empleado empleado = new Empleado();

                empleado.setIdEmpleado(rs.getInt("id_empleado"));
                empleado.setEstado(rs.getBoolean("estado"));
                empleado.setCedula(rs.getString("cedula"));
                empleado.setNombres(rs.getString("nombres"));
                empleado.setApellidos(rs.getString("apellidos"));
                empleado.setTelefono(rs.getString("telefono"));
                empleado.setCorreo(rs.getString("correo"));
                empleado.setTipoEmpleado(rs.getString("tipo_empleado"));
                empleado.setSueldoFijo(rs.getDouble("sueldo_fijo"));
                empleado.setValorHora(rs.getDouble("valor_hora"));

                return empleado;
            }

        } catch (Exception e) {
            System.out.println("Error al buscar empleado: " + e.getMessage());
        }

        return null;
    }

}
