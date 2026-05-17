/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controlador;

import dao.EmpleadoDAO;
import dao.UsuarioDAO;
import java.util.List;
import modelo.Empleado;
import modelo.Usuario;

/**
 *
 * @author Lenovo LOQ
 */
public class EmpleadoController {

    private EmpleadoDAO empleadoDAO = new EmpleadoDAO();
    private UsuarioDAO usuarioDAO = new UsuarioDAO();

    public String registrarEmpleado(Empleado empleado) {

        normalizarDatos(empleado);

        String validacion = validarEmpleado(empleado);

        if (!validacion.equals("OK")) {
            return validacion;
        }

        if (empleadoDAO.existeCedula(empleado.getCedula())) {
            return "La cédula ya está registrada.";
        }

        if (empleadoDAO.existeCorreo(empleado.getCorreo())) {
            return "El Correo ya está registrado.";
        }

        aplicarReglasTipoEmpleado(empleado);
        empleado.setEstado(true);

        int idEmpleado = empleadoDAO.insertarEmpleadoRetornarId(empleado);

        if (idEmpleado > 0) {

            Usuario usuario = new Usuario();

            usuario.setIdEmpleado(idEmpleado);
            usuario.setUsuario(empleado.getCorreo());
            usuario.setPassword(empleado.getCedula());
            usuario.setRol("EMPLEADO");
            usuario.setEstado(true);

            usuarioDAO.insertarUsuario(usuario);

            return "REGISTRADO";
        }

        return "Error al registrar el empleado.";
    }

    public String actualizarEmpleado(Empleado empleado) {

        if (empleado.getIdEmpleado() <= 0) {
            return "Seleccione un empleado para editar.";
        }

        normalizarDatos(empleado);

        String validacion = validarEmpleado(empleado);

        if (!validacion.equals("OK")) {
            return validacion;
        }

        if (empleadoDAO.existeCedulaEnOtroEmpleado(
                empleado.getCedula(),
                empleado.getIdEmpleado()
        )) {
            return "La cédula ya está registrada en otro empleado.";
        }

        aplicarReglasTipoEmpleado(empleado);

        boolean actualizado = empleadoDAO.actualizarEmpleado(empleado);

        if (actualizado) {
            usuarioDAO.actualizarUsuarioPorEmpleado(
                    empleado.getIdEmpleado(),
                    empleado.getCorreo(),
                    empleado.getCedula()
            );
            return "ACTUALIZADO";
        }

        return "Error al actualizar el empleado.";
    }

    public String eliminarEmpleado(int idEmpleado) {

        if (idEmpleado <= 0) {
            return "Seleccione un empleado para eliminar.";
        }

        boolean eliminado = empleadoDAO.eliminarEmpleado(idEmpleado);

        if (eliminado) {
            return "ELIMINADO";
        }

        return "Error al eliminar el empleado.";
    }

    public Empleado buscarEmpleadoPorCedula(String cedula) {
        return empleadoDAO.buscarPorCedula(cedula);
    }
    
    private void normalizarDatos(Empleado empleado) {
        empleado.setCedula(limpiarTexto(empleado.getCedula()));
        empleado.setNombres(limpiarTexto(empleado.getNombres()));
        empleado.setApellidos(limpiarTexto(empleado.getApellidos()));
        empleado.setTelefono(limpiarTexto(empleado.getTelefono()));
        empleado.setCorreo(limpiarTexto(empleado.getCorreo()));
    }

    public String validarEmpleado(Empleado empleado) {

        if (empleado.getCedula().isEmpty()) {
            return "El campo cédula es obligatorio.";
        }

        if (empleado.getCedula().contains(" ")) {
            return "La cédula no debe contener espacios.";
        }

        if (!empleado.getCedula().matches("\\d{10}")) {
            return "La cédula debe tener exactamente 10 números.";
        }

        if (empleado.getNombres().isEmpty()) {
            return "El campo nombre es obligatorio.";
        }

        if (empleado.getNombres().contains(" ")) {
            return "El nombre no debe contener espacios.";
        }

        if (!empleado.getNombres().matches("[A-Za-zÁÉÍÓÚáéíóúÑñ]+")) {
            return "El nombre solo debe contener letras.";
        }

        if (empleado.getApellidos().isEmpty()) {
            return "El campo apellido es obligatorio.";
        }

        if (empleado.getApellidos().contains(" ")) {
            return "El apellido no debe contener espacios.";
        }

        if (!empleado.getApellidos().matches("[A-Za-zÁÉÍÓÚáéíóúÑñ]+")) {
            return "El apellido solo debe contener letras.";
        }

        if (empleado.getTelefono().isEmpty()) {
            return "El campo teléfono es obligatorio.";
        }

        if (empleado.getTelefono().contains(" ")) {
            return "El teléfono no debe contener espacios.";
        }

        if (!empleado.getTelefono().matches("\\d{10}")) {
            return "El teléfono debe tener exactamente 10 números.";
        }

        if (empleado.getCorreo().isEmpty()) {
            return "El campo correo es obligatorio.";
        }

        if (empleado.getCorreo().contains(" ")) {
            return "El correo no debe contener espacios.";
        }

        if (!empleado.getCorreo().matches("^[\\w.-]+@[\\w.-]+\\.[a-zA-Z]{2,}$")) {
            return "Ingrese un correo válido. Ejemplo: usuario@gmail.com";
        }

        if (empleado.getTipoEmpleado() == null
                || empleado.getTipoEmpleado().equals("Seleccione...")) {
            return "Seleccione un tipo de empleado.";
        }

        return "OK";
    }

    private void aplicarReglasTipoEmpleado(Empleado empleado) {

        if (empleado.getTipoEmpleado().equals("TIEMPO_COMPLETO")) {
            empleado.setSueldoFijo(1500);
            empleado.setValorHora(0);
        } else if (empleado.getTipoEmpleado().equals("TIEMPO_PARCIAL")) {
            empleado.setSueldoFijo(0);
            empleado.setValorHora(5);
        }
    }

    private String limpiarTexto(String texto) {
        if (texto == null) {
            return "";
        }
        return texto.trim();
    }

    public List<Empleado> listarEmpleados() {
        return empleadoDAO.listarEmpleadosActivos();
    }
}
