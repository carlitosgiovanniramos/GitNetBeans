package dao;

import conexion.Conexion;
import modelo.Asistencia;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Time;
import java.time.LocalDate;
import java.time.LocalTime;

public class AsistenciaDAO {

    public Asistencia buscarPorEmpleadoYFecha(int idEmpleado, LocalDate fecha) {

        String sql = """
            SELECT *
            FROM asistencias
            WHERE id_empleado = ? AND fecha = ?
        """;

        try (
                Connection cc = new Conexion().conectar();
                PreparedStatement ps = cc.prepareStatement(sql)
        ) {

            ps.setInt(1, idEmpleado);
            ps.setDate(2, Date.valueOf(fecha));

            try (ResultSet rs = ps.executeQuery()) {

                if (rs.next()) {
                    Asistencia asistencia = new Asistencia();

                    asistencia.setIdAsistencia(rs.getInt("id_asistencia"));
                    asistencia.setIdEmpleado(rs.getInt("id_empleado"));
                    asistencia.setFecha(rs.getDate("fecha").toLocalDate());

                    Time entradaManana = rs.getTime("hora_entrada_manana");
                    Time salidaManana = rs.getTime("hora_salida_manana");
                    Time entradaTarde = rs.getTime("hora_entrada_tarde");
                    Time salidaTarde = rs.getTime("hora_salida_tarde");

                    if (entradaManana != null) {
                        asistencia.setHoraEntradaManana(entradaManana.toLocalTime());
                    }

                    if (salidaManana != null) {
                        asistencia.setHoraSalidaManana(salidaManana.toLocalTime());
                    }

                    if (entradaTarde != null) {
                        asistencia.setHoraEntradaTarde(entradaTarde.toLocalTime());
                    }

                    if (salidaTarde != null) {
                        asistencia.setHoraSalidaTarde(salidaTarde.toLocalTime());
                    }

                    asistencia.setMinutosAtraso(rs.getInt("minutos_atraso"));
                    asistencia.setHorasTrabajadas(rs.getDouble("horas_trabajadas"));
                    asistencia.setDescuento(rs.getDouble("descuento"));

                    return asistencia;
                }
            }

        } catch (SQLException e) {
            System.out.println("Error al buscar asistencia: " + e.getMessage());
        }

        return null;
    }

    public boolean crearAsistencia(Asistencia asistencia) {

        String sql = """
            INSERT INTO asistencias
            (id_empleado, fecha, minutos_atraso, horas_trabajadas, descuento)
            VALUES (?, ?, ?, ?, ?)
        """;

        try (
                Connection cc = new Conexion().conectar();
                PreparedStatement ps = cc.prepareStatement(sql)
        ) {

            ps.setInt(1, asistencia.getIdEmpleado());
            ps.setDate(2, Date.valueOf(asistencia.getFecha()));
            ps.setInt(3, asistencia.getMinutosAtraso());
            ps.setDouble(4, asistencia.getHorasTrabajadas());
            ps.setDouble(5, asistencia.getDescuento());

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

        try (
                Connection cc = new Conexion().conectar();
                PreparedStatement ps = cc.prepareStatement(sql)
        ) {

            ps.setTime(1, Time.valueOf(hora));
            ps.setInt(2, idEmpleado);
            ps.setDate(3, Date.valueOf(fecha));

            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            System.out.println("Error al actualizar hora de asistencia: " + e.getMessage());
            return false;
        }
    }

    public boolean actualizarCalculos(Asistencia asistencia) {

        String sql = """
            UPDATE asistencias
            SET minutos_atraso = ?,
                horas_trabajadas = ?,
                descuento = ?
            WHERE id_empleado = ? AND fecha = ?
        """;

        try (
                Connection cc = new Conexion().conectar();
                PreparedStatement ps = cc.prepareStatement(sql)
        ) {

            ps.setInt(1, asistencia.getMinutosAtraso());
            ps.setDouble(2, asistencia.getHorasTrabajadas());
            ps.setDouble(3, asistencia.getDescuento());
            ps.setInt(4, asistencia.getIdEmpleado());
            ps.setDate(5, Date.valueOf(asistencia.getFecha()));

            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            System.out.println("Error al actualizar cálculos de asistencia: " + e.getMessage());
            return false;
        }
    }
}