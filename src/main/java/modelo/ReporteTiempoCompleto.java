/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package modelo;

import java.util.List;

public class ReporteTiempoCompleto {
    private Empleado empleado;
    private List<Asistencia> asistencias;
    private double sueldoFijo;
    private double descuentoTotal;
    private double sueldoFinal;

    public ReporteTiempoCompleto(Empleado empleado, List<Asistencia> asistencias, 
                                 double sueldoFijo, double descuentoTotal, double sueldoFinal) {
        this.empleado = empleado;
        this.asistencias = asistencias;
        this.sueldoFijo = sueldoFijo;
        this.descuentoTotal = descuentoTotal;
        this.sueldoFinal = sueldoFinal;
    }

    public Empleado getEmpleado() { return empleado; }
    public List<Asistencia> getAsistencias() { return asistencias; }
    public double getSueldoFijo() { return sueldoFijo; }
    public double getDescuentoTotal() { return descuentoTotal; }
    public double getSueldoFinal() { return sueldoFinal; }
}