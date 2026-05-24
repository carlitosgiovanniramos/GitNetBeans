/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package modelo;

/**
 *
 * @author Lenovo LOQ
 */
public class EmpleadoTiempoParcial extends Empleado {

    private double valorHora;

    public EmpleadoTiempoParcial() {
    }

    public EmpleadoTiempoParcial(int idEmpleado, String cedula, String nombres, String apellidos,
            String telefono, String correo, String tipoEmpleado, double valorHora, boolean estado) {
        super(idEmpleado, cedula, nombres, apellidos, telefono, correo, tipoEmpleado, estado);
        this.valorHora = valorHora;
    }

    public double getValorHora() {
        return valorHora;
    }

    public void setValorHora(double valorHora) {
        this.valorHora = valorHora;
    }
}
