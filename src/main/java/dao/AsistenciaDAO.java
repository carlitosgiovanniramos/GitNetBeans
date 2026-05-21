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
import java.util.ArrayList;
import java.util.List;

public class AsistenciaDAO {

    public Asistencia buscarPorEmpleadoYFecha(int idEmpleado, LocalDate fecha) {

        String sql = """
            SELECT *
            FROM asistencias
            WHERE id_empleado = ? AND fecha = ?
        """;

        try (
                Connection cc = new Conexion().conectar(); PreparedStatement ps = cc.prepareStatement(sql)) {

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
                Connection cc = new Conexion().conectar(); PreparedStatement ps = cc.prepareStatement(sql)) {

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
                Connection cc = new Conexion().conectar(); PreparedStatement ps = cc.prepareStatement(sql)) {

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
                Connection cc = new Conexion().conectar(); PreparedStatement ps = cc.prepareStatement(sql)) {

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

    public List<Asistencia> listarAsistenciasPorMes(int idEmpleado, int mes, int anio) {
        List<Asistencia> lista = new ArrayList<>();
        String sql = "SELECT * FROM asistencias WHERE id_empleado = ? AND MONTH(fecha) = ? AND YEAR(fecha) = ? ORDER BY fecha ASC";

        try (Connection con = new Conexion().conectar(); PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, idEmpleado);
            ps.setInt(2, mes);
            ps.setInt(3, anio);

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    Asistencia asis = new Asistencia();
                    asis.setIdAsistencia(rs.getInt("id_asistencia"));
                    asis.setFecha(rs.getDate("fecha").toLocalDate());

                    Time entradaManana = rs.getTime("hora_entrada_manana");
                    Time salidaManana = rs.getTime("hora_salida_manana");
                    Time entradaTarde = rs.getTime("hora_entrada_tarde");
                    Time salidaTarde = rs.getTime("hora_salida_tarde");

                    if (entradaManana != null) {
                        asis.setHoraEntradaManana(entradaManana.toLocalTime());
                    }
                    if (salidaManana != null) {
                        asis.setHoraSalidaManana(salidaManana.toLocalTime());
                    }
                    if (entradaTarde != null) {
                        asis.setHoraEntradaTarde(entradaTarde.toLocalTime());
                    }
                    if (salidaTarde != null) {
                        asis.setHoraSalidaTarde(salidaTarde.toLocalTime());
                    }

                    asis.setMinutosAtraso(rs.getInt("minutos_atraso"));

                    // Usamos getDouble para no chocar con el modelo
                    asis.setHorasTrabajadas(rs.getDouble("horas_trabajadas"));
                    asis.setDescuento(rs.getDouble("descuento"));

                    lista.add(asis);
                }
            }
        } catch (SQLException e) {
            System.out.println("Error al listar asistencias: " + e.getMessage());
        }
        return lista;
    }

    public double calcularDescuentoMensual(int idEmpleado, int mes, int anio) {
        double totalDescuento = 0.0;
        String sql = "SELECT SUM(descuento) AS total_desc FROM asistencias WHERE id_empleado = ? AND MONTH(fecha) = ? AND YEAR(fecha) = ?";

        try (Connection con = new Conexion().conectar(); PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, idEmpleado);
            ps.setInt(2, mes);
            ps.setInt(3, anio);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    totalDescuento = rs.getDouble("total_desc");
                }
            }
        } catch (SQLException e) {
            System.out.println("Error al calcular descuento: " + e.getMessage());
        }
        return totalDescuento;
    }

    public java.math.BigDecimal calcularHorasMensuales(int idEmpleado, int mes, int anio) {
        java.math.BigDecimal totalHoras = java.math.BigDecimal.ZERO;

        String sql = """
        SELECT COALESCE(SUM(horas_trabajadas), 0) AS total_horas
        FROM asistencias
        WHERE id_empleado = ?
          AND MONTH(fecha) = ?
          AND YEAR(fecha) = ?
          AND horas_trabajadas <= 8
    """;

        try (Connection con = new Conexion().conectar(); PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, idEmpleado);
            ps.setInt(2, mes);
            ps.setInt(3, anio);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    totalHoras = rs.getBigDecimal("total_horas");
                }
            }

        } catch (SQLException e) {
            System.out.println("Error al calcular horas mensuales: " + e.getMessage());
        }

        return totalHoras;
    }

    public java.math.BigDecimal calcularSueldoTiempoParcial(int idEmpleado, int mes, int anio) {
        java.math.BigDecimal valorHora = new java.math.BigDecimal("5.00");
        java.math.BigDecimal totalHoras = calcularHorasMensuales(idEmpleado, mes, anio);

        return totalHoras.multiply(valorHora);
    }
}
