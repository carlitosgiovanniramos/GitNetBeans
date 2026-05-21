/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package modelo;
import java.math.BigDecimal;
import java.util.List;

/**
 *
 * @author Samu
 */
public class ReporteTiempoParcial {

    private Empleado empleado;
    private List<Asistencia> asistencias;
    private BigDecimal totalHoras;
    private BigDecimal valorHora;
    private BigDecimal sueldoPagar;

    public ReporteTiempoParcial(Empleado empleado, List<Asistencia> asistencias,
                                BigDecimal totalHoras, BigDecimal valorHora, BigDecimal sueldoPagar) {
        this.empleado = empleado;
        this.asistencias = asistencias;
        this.totalHoras = totalHoras;
        this.valorHora = valorHora;
        this.sueldoPagar = sueldoPagar;
    }

    public Empleado getEmpleado() {
        return empleado;
    }

    public List<Asistencia> getAsistencias() {
        return asistencias;
    }

    public BigDecimal getTotalHoras() {
        return totalHoras;
    }

    public BigDecimal getValorHora() {
        return valorHora;
    }

    public BigDecimal getSueldoPagar() {
        return sueldoPagar;
    }
}