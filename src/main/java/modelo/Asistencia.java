/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package modelo;

import java.time.LocalDate;
import java.time.LocalTime;

/**
 *
 * @author Lenovo LOQ
 */
public class Asistencia {

    private int idAsistencia;
    private int idEmpleado;
    private LocalDate fecha;
    private LocalTime horaEntradaManana;
    private LocalTime horaSalidaManana;
    private LocalTime horaEntradaTarde;
    private LocalTime horaSalidaTarde;
    private int minutosAtraso;
    private double horasTrabajadas;
    private double descuento;

    public Asistencia() {
    }

    public Asistencia(int idAsistencia, int idEmpleado, LocalDate fecha, LocalTime horaEntradaManana, LocalTime horaSalidaManana, LocalTime horaEntradaTarde, LocalTime horaSalidaTarde, int minutosAtraso, double horasTrabajadas, double descuento) {
        this.idAsistencia = idAsistencia;
        this.idEmpleado = idEmpleado;
        this.fecha = fecha;
        this.horaEntradaManana = horaEntradaManana;
        this.horaSalidaManana = horaSalidaManana;
        this.horaEntradaTarde = horaEntradaTarde;
        this.horaSalidaTarde = horaSalidaTarde;
        this.minutosAtraso = minutosAtraso;
        this.horasTrabajadas = horasTrabajadas;
        this.descuento = descuento;
    }

    
    public int getIdAsistencia() {
        return idAsistencia;
    }

    public void setIdAsistencia(int idAsistencia) {
        this.idAsistencia = idAsistencia;
    }

    public int getIdEmpleado() {
        return idEmpleado;
    }

    public void setIdEmpleado(int idEmpleado) {
        this.idEmpleado = idEmpleado;
    }

    public LocalDate getFecha() {
        return fecha;
    }

    public void setFecha(LocalDate fecha) {
        this.fecha = fecha;
    }

    public LocalTime getHoraEntradaManana() {
        return horaEntradaManana;
    }

    public void setHoraEntradaManana(LocalTime horaEntradaManana) {
        this.horaEntradaManana = horaEntradaManana;
    }

    public LocalTime getHoraSalidaManana() {
        return horaSalidaManana;
    }

    public void setHoraSalidaManana(LocalTime horaSalidaManana) {
        this.horaSalidaManana = horaSalidaManana;
    }

    public LocalTime getHoraEntradaTarde() {
        return horaEntradaTarde;
    }

    public void setHoraEntradaTarde(LocalTime horaEntradaTarde) {
        this.horaEntradaTarde = horaEntradaTarde;
    }

    public LocalTime getHoraSalidaTarde() {
        return horaSalidaTarde;
    }

    public void setHoraSalidaTarde(LocalTime horaSalidaTarde) {
        this.horaSalidaTarde = horaSalidaTarde;
    }

    public int getMinutosAtraso() {
        return minutosAtraso;
    }

    public void setMinutosAtraso(int minutosAtraso) {
        this.minutosAtraso = minutosAtraso;
    }

    public double getHorasTrabajadas() {
        return horasTrabajadas;
    }

    public void setHorasTrabajadas(double horasTrabajadas) {
        this.horasTrabajadas = horasTrabajadas;
    }

    public double getDescuento() {
        return descuento;
    }

    public void setDescuento(double descuento) {
        this.descuento = descuento;
    }
    
    
}
