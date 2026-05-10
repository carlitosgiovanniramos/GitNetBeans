package controlador;

import dao.AsistenciaDAO;
import modelo.Asistencia;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalTime;

public class AsistenciaController {

    private final AsistenciaDAO asistenciaDAO = new AsistenciaDAO();

    public Asistencia buscarAsistenciaDelDia(int idEmpleado) {
        return asistenciaDAO.buscarPorEmpleadoYFecha(idEmpleado, LocalDate.now());
    }

    private void asegurarAsistenciaDelDia(int idEmpleado) {

        LocalDate fechaActual = LocalDate.now();

        Asistencia asistencia = asistenciaDAO.buscarPorEmpleadoYFecha(idEmpleado, fechaActual);

        if (asistencia == null) {
            Asistencia nueva = new Asistencia();
            nueva.setIdEmpleado(idEmpleado);
            nueva.setFecha(fechaActual);
            asistenciaDAO.crearAsistencia(nueva);
        }
    }

    public String registrarEntradaManana(int idEmpleado) {

        asegurarAsistenciaDelDia(idEmpleado);

        LocalDate fecha = LocalDate.now();
        LocalTime horaActual = LocalTime.now();

        Asistencia asistencia = asistenciaDAO.buscarPorEmpleadoYFecha(idEmpleado, fecha);

        if (asistencia.getHoraEntradaManana() != null) {
            return "Ya registró la entrada de la mañana.";
        }

        boolean actualizado = asistenciaDAO.actualizarEntradaManana(idEmpleado, fecha, horaActual);

        if (actualizado) {
            recalcularAsistencia(idEmpleado);
            return "Entrada de la mañana registrada correctamente.";
        }

        return "Error al registrar entrada de la mañana.";
    }

    public String registrarSalidaManana(int idEmpleado) {

        asegurarAsistenciaDelDia(idEmpleado);

        LocalDate fecha = LocalDate.now();
        LocalTime horaActual = LocalTime.now();

        Asistencia asistencia = asistenciaDAO.buscarPorEmpleadoYFecha(idEmpleado, fecha);

        if (asistencia.getHoraEntradaManana() == null) {
            return "Primero debe registrar la entrada de la mañana.";
        }

        if (asistencia.getHoraSalidaManana() != null) {
            return "Ya registró la salida de la mañana.";
        }

        boolean actualizado = asistenciaDAO.actualizarSalidaManana(idEmpleado, fecha, horaActual);

        if (actualizado) {
            recalcularAsistencia(idEmpleado);
            return "Salida de la mañana registrada correctamente.";
        }

        return "Error al registrar salida de la mañana.";
    }

    public String registrarEntradaTarde(int idEmpleado) {

        asegurarAsistenciaDelDia(idEmpleado);

        LocalDate fecha = LocalDate.now();
        LocalTime horaActual = LocalTime.now();

        Asistencia asistencia = asistenciaDAO.buscarPorEmpleadoYFecha(idEmpleado, fecha);

        if (asistencia.getHoraSalidaManana() == null) {
            return "Primero debe registrar la salida de la mañana.";
        }

        if (asistencia.getHoraEntradaTarde() != null) {
            return "Ya registró la entrada de la tarde.";
        }

        boolean actualizado = asistenciaDAO.actualizarEntradaTarde(idEmpleado, fecha, horaActual);

        if (actualizado) {
            recalcularAsistencia(idEmpleado);
            return "Entrada de la tarde registrada correctamente.";
        }

        return "Error al registrar entrada de la tarde.";
    }

    public String registrarSalidaTarde(int idEmpleado) {

        asegurarAsistenciaDelDia(idEmpleado);

        LocalDate fecha = LocalDate.now();
        LocalTime horaActual = LocalTime.now();

        Asistencia asistencia = asistenciaDAO.buscarPorEmpleadoYFecha(idEmpleado, fecha);

        if (asistencia.getHoraEntradaTarde() == null) {
            return "Primero debe registrar la entrada de la tarde.";
        }

        if (asistencia.getHoraSalidaTarde() != null) {
            return "Ya registró la salida de la tarde.";
        }

        boolean actualizado = asistenciaDAO.actualizarSalidaTarde(idEmpleado, fecha, horaActual);

        if (actualizado) {
            recalcularAsistencia(idEmpleado);
            return "Salida de la tarde registrada correctamente.";
        }

        return "Error al registrar salida de la tarde.";
    }

    private void recalcularAsistencia(int idEmpleado) {

        LocalDate fecha = LocalDate.now();

        Asistencia asistencia = asistenciaDAO.buscarPorEmpleadoYFecha(idEmpleado, fecha);

        if (asistencia == null) {
            return;
        }

        int minutosAtraso = calcularMinutosAtraso(asistencia);
        double descuento = minutosAtraso * 0.20;
        double horasTrabajadas = calcularHorasTrabajadas(asistencia);

        asistencia.setMinutosAtraso(minutosAtraso);
        asistencia.setDescuento(descuento);
        asistencia.setHorasTrabajadas(horasTrabajadas);

        asistenciaDAO.actualizarCalculos(asistencia);
    }

    private int calcularMinutosAtraso(Asistencia asistencia) {

        int total = 0;

        LocalTime horaEntradaMananaPermitida = LocalTime.of(8, 0);
        LocalTime horaEntradaTardePermitida = LocalTime.of(14, 0);

        if (asistencia.getHoraEntradaManana() != null
                && asistencia.getHoraEntradaManana().isAfter(horaEntradaMananaPermitida)) {

            total += Duration.between(
                    horaEntradaMananaPermitida,
                    asistencia.getHoraEntradaManana()
            ).toMinutes();
        }

        if (asistencia.getHoraEntradaTarde() != null
                && asistencia.getHoraEntradaTarde().isAfter(horaEntradaTardePermitida)) {

            total += Duration.between(
                    horaEntradaTardePermitida,
                    asistencia.getHoraEntradaTarde()
            ).toMinutes();
        }

        return total;
    }

    private double calcularHorasTrabajadas(Asistencia asistencia) {

        double totalHoras = 0;

        if (asistencia.getHoraEntradaManana() != null
                && asistencia.getHoraSalidaManana() != null) {

            long minutos = Duration.between(
                    asistencia.getHoraEntradaManana(),
                    asistencia.getHoraSalidaManana()
            ).toMinutes();

            totalHoras += minutos / 60.0;
        }

        if (asistencia.getHoraEntradaTarde() != null
                && asistencia.getHoraSalidaTarde() != null) {

            long minutos = Duration.between(
                    asistencia.getHoraEntradaTarde(),
                    asistencia.getHoraSalidaTarde()
            ).toMinutes();

            totalHoras += minutos / 60.0;
        }

        if (totalHoras > 8) {
            totalHoras = 8;
        }

        return Math.round(totalHoras * 100.0) / 100.0;
    }
}