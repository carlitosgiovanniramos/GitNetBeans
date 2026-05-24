/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controlador;

import dao.AsistenciaDAO;
import dao.EmpleadoDAO;
import java.math.BigDecimal;
import java.util.List;
import modelo.Asistencia;
import modelo.Empleado;
import modelo.EmpleadoTiempoCompleto;
import modelo.EmpleadoTiempoParcial;
import modelo.ReporteTiempoCompleto;
import modelo.ReporteTiempoParcial;

public class ReporteController {

    private EmpleadoDAO empleadoDAO = new EmpleadoDAO();
    private AsistenciaDAO asistenciaDAO = new AsistenciaDAO();

    public String obtenerTipoEmpleado(String cedula) {
        Empleado emp = empleadoDAO.buscarPorCedula(cedula);

        if (emp == null) {
            throw new IllegalArgumentException("El empleado con cédula " + cedula + " no existe.");
        }

        if (emp instanceof EmpleadoTiempoCompleto) {
            return "TIEMPO_COMPLETO";
        }

        if (emp instanceof EmpleadoTiempoParcial) {
            return "TIEMPO_PARCIAL";
        }

        throw new IllegalArgumentException("El tipo de empleado no es válido.");
    }

    public ReporteTiempoCompleto generarReporteTiempoCompleto(String cedula, int mes, int anio) {

        Empleado emp = empleadoDAO.buscarPorCedula(cedula);

        if (emp == null) {
            throw new IllegalArgumentException("El empleado con cédula " + cedula + " no existe.");
        }

        if (!(emp instanceof EmpleadoTiempoCompleto)) {
            throw new IllegalArgumentException("El empleado no es de TIEMPO_COMPLETO. Este reporte no le corresponde.");
        }

        List<Asistencia> asistencias = asistenciaDAO.listarAsistenciasPorMes(emp.getIdEmpleado(), mes, anio);
        double descuentoTotal = asistenciaDAO.calcularDescuentoMensual(emp.getIdEmpleado(), mes, anio);

        double sueldoFijo = ((EmpleadoTiempoCompleto) emp).getSueldoFijo();
        double sueldoFinal = sueldoFijo - descuentoTotal;

        return new ReporteTiempoCompleto(emp, asistencias, sueldoFijo, descuentoTotal, sueldoFinal);
    }

    public ReporteTiempoParcial generarReporteTiempoParcial(String cedula, int mes, int anio) {

        Empleado emp = empleadoDAO.buscarPorCedula(cedula);

        if (emp == null) {
            throw new IllegalArgumentException("El empleado con cédula " + cedula + " no existe.");
        }

        if (!(emp instanceof EmpleadoTiempoParcial)) {
            throw new IllegalArgumentException("El empleado no es de TIEMPO_PARCIAL. Este reporte no le corresponde.");
        }

        List<Asistencia> asistencias = asistenciaDAO.listarAsistenciasPorMes(emp.getIdEmpleado(), mes, anio);

        BigDecimal totalHoras = asistenciaDAO.calcularHorasMensuales(emp.getIdEmpleado(), mes, anio);
        BigDecimal valorHora = BigDecimal.valueOf(((EmpleadoTiempoParcial) emp).getValorHora());
        BigDecimal sueldoPagar = totalHoras.multiply(valorHora);
        BigDecimal descuentoTotal = BigDecimal.valueOf(
            asistenciaDAO.calcularDescuentoMensual(emp.getIdEmpleado(), mes, anio)
        );
        BigDecimal sueldoFinal = sueldoPagar.subtract(descuentoTotal);

        return new ReporteTiempoParcial(
            emp,
            asistencias,
            totalHoras,
            valorHora,
            sueldoPagar,
            descuentoTotal,
            sueldoFinal
        );
    }
}