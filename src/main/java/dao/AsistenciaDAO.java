package dao;

import conexion.Conexion;
import modelo.Asistencia;

import java.sql.*;
import java.time.LocalDate;
import java.time.LocalTime;

public class AsistenciaDAO {

    public Asistencia buscarPorEmpleadoYFecha(int idEmpleado, LocalDate fecha) {

        String sql = """
            SELECT * FROM asistencias
            WHERE id_empleado = ? AND fecha = ?
        """;

        try {
            Conexion cn = new Conexion();
            Connection cc = cn.conectar();
            PreparedStatement ps = cc.prepareStatement(sql);

            ps.setInt(1, idEmpleado);
            ps.setDate(2, Date.valueOf(fecha));

            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                Asistencia a = new Asistencia();

                a.setIdAsistencia(rs.getInt("id_asistencia"));
                a.setIdEmpleado(rs.getInt("id_empleado"));
                a.setFecha(rs.getDate("fecha").toLocalDate());

                Time entradaManana = rs.getTime("hora_entrada_manana");
                Time salidaManana = rs.getTime("hora_salida_manana");
                Time entradaTarde = rs.getTime("hora_entrada_tarde");
                Time salidaTarde = rs.getTime("hora_salida_tarde");

                if (entradaManana != null) {
                    a.setHoraEntradaManana(entradaManana.toLocalTime());
                }

                if (salidaManana != null) {
                    a.setHoraSalidaManana(salidaManana.toLocalTime());
                }

                if (entradaTarde != null) {
                    a.setHoraEntradaTarde(entradaTarde.toLocalTime());
                }

                if (salidaTarde != null) {
                    a.setHoraSalidaTarde(salidaTarde.toLocalTime());
                }

                a.setMinutosAtraso(rs.getInt("minutos_atraso"));
                a.setHorasTrabajadas(rs.getDouble("horas_trabajadas"));
                a.setDescuento(rs.getDouble("descuento"));

                return a;
            }

        } catch (SQLException e) {
            System.out.println("Error al buscar asistencia: " + e.getMessage());
        }

        return null;
    }

    public boolean crearAsistencia(Asistencia asistencia) {

        String sql = """
            INSERT INTO asistencias
            (id_empleado, fecha)
            VALUES (?, ?)
        """;

        try {
            Conexion cn = new Conexion();
            Connection cc = cn.conectar();
            PreparedStatement ps = cc.prepareStatement(sql);

            ps.setInt(1, asistencia.getIdEmpleado());
            ps.setDate(2, Date.valueOf(asistencia.getFecha()));

            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            System.out.println("Error al crear asistencia: " + e.getMessage());
            return false;
        }
    }

    public boolean actualizarEntradaManana(int idEmpleado, LocalDate fecha, LocalTime hora) {
        String sql = """
            UPDATE asistencias
            SET hora_entrada_manana = ?
            WHERE id_empleado = ? AND fecha = ?
        """;

        return actualizarHora(sql, idEmpleado, fecha, hora);
    }

    public boolean actualizarSalidaManana(int idEmpleado, LocalDate fecha, LocalTime hora) {
        String sql = """
            UPDATE asistencias
            SET hora_salida_manana = ?
            WHERE id_empleado = ? AND fecha = ?
        """;

        return actualizarHora(sql, idEmpleado, fecha, hora);
    }

    public boolean actualizarEntradaTarde(int idEmpleado, LocalDate fecha, LocalTime hora) {
        String sql = """
            UPDATE asistencias
            SET hora_entrada_tarde = ?
            WHERE id_empleado = ? AND fecha = ?
        """;

        return actualizarHora(sql, idEmpleado, fecha, hora);
    }

    public boolean actualizarSalidaTarde(int idEmpleado, LocalDate fecha, LocalTime hora) {
        String sql = """
            UPDATE asistencias
            SET hora_salida_tarde = ?
            WHERE id_empleado = ? AND fecha = ?
        """;

        return actualizarHora(sql, idEmpleado, fecha, hora);
    }

    private boolean actualizarHora(String sql, int idEmpleado, LocalDate fecha, LocalTime hora) {

        try {
            Conexion cn = new Conexion();
            Connection cc = cn.conectar();
            PreparedStatement ps = cc.prepareStatement(sql);

            ps.setTime(1, Time.valueOf(hora));
            ps.setInt(2, idEmpleado);
            ps.setDate(3, Date.valueOf(fecha));

            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            System.out.println("Error al actualizar hora: " + e.getMessage());
            return false;
        }
    }

    public boolean actualizarCalculos(Asistencia asistencia) {

        String sql = """
            UPDATE asistencias
            SET minutos_atraso = ?, horas_trabajadas = ?, descuento = ?
            WHERE id_empleado = ? AND fecha = ?
        """;

        try {
            Conexion cn = new Conexion();
            Connection cc = cn.conectar();
            PreparedStatement ps = cc.prepareStatement(sql);

            ps.setInt(1, asistencia.getMinutosAtraso());
            ps.setDouble(2, asistencia.getHorasTrabajadas());
            ps.setDouble(3, asistencia.getDescuento());
            ps.setInt(4, asistencia.getIdEmpleado());
            ps.setDate(5, Date.valueOf(asistencia.getFecha()));

            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            System.out.println("Error al actualizar cálculos: " + e.getMessage());
            return false;
        }
    }
}