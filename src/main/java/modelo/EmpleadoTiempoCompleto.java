/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package modelo;

/**
 *
 * @author Lenovo LOQ
 */
public class EmpleadoTiempoCompleto extends Empleado {

    private double sueldoFijo;

    public EmpleadoTiempoCompleto() {
    }

    public EmpleadoTiempoCompleto(int idEmpleado, String cedula, String nombres, String apellidos,
            String telefono, String correo, String tipoEmpleado, double sueldoFijo, boolean estado) {
        super(idEmpleado, cedula, nombres, apellidos, telefono, correo, tipoEmpleado, estado);
        this.sueldoFijo = sueldoFijo;
    }

    public double getSueldoFijo() {
        return sueldoFijo;
    }

    public void setSueldoFijo(double sueldoFijo) {
        this.sueldoFijo = sueldoFijo;
    }
}
