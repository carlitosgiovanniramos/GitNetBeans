package controlador;

import dao.AsistenciaDAO;
import modelo.Asistencia;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalTime;

public class AsistenciaController {

    private final AsistenciaDAO asistenciaDAO = new AsistenciaDAO();

    private static final LocalTime HORA_ENTRADA_MANANA = LocalTime.of(8, 0);
    private static final LocalTime HORA_SALIDA_MANANA = LocalTime.of(13, 0);
    private static final LocalTime HORA_ENTRADA_TARDE = LocalTime.of(14, 0);
    private static final LocalTime HORA_SALIDA_TARDE = LocalTime.of(17, 0);
    private static final double VALOR_DESCUENTO_POR_MINUTO = 0.20;
    private static final double MAXIMO_HORAS_DIARIAS = 8.0;

    public Asistencia buscarAsistenciaDelDia(int idEmpleado) {
        return asistenciaDAO.buscarPorEmpleadoYFecha(idEmpleado, LocalDate.now());
    }

    private boolean asegurarAsistenciaDelDia(int idEmpleado) {

        LocalDate fechaActual = LocalDate.now();

        Asistencia asistencia = asistenciaDAO.buscarPorEmpleadoYFecha(idEmpleado, fechaActual);

        if (asistencia != null) {
            return true;
        }

        Asistencia nueva = new Asistencia();
        nueva.setIdEmpleado(idEmpleado);
        nueva.setFecha(fechaActual);
        nueva.setMinutosAtraso(0);
        nueva.setHorasTrabajadas(0.0);
        nueva.setDescuento(0.0);

        return asistenciaDAO.crearAsistencia(nueva);
    }

    public String registrarEntradaManana(int idEmpleado) {

        if (!asegurarAsistenciaDelDia(idEmpleado)) {
            return "No se pudo crear la asistencia del día.";
        }

        LocalDate fecha = LocalDate.now();
        LocalTime horaActual = LocalTime.now();

        Asistencia asistencia = asistenciaDAO.buscarPorEmpleadoYFecha(idEmpleado, fecha);

        if (asistencia == null) {
            return "No se encontró la asistencia del día.";
        }

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

        if (!asegurarAsistenciaDelDia(idEmpleado)) {
            return "No se pudo crear la asistencia del día.";
        }

        LocalDate fecha = LocalDate.now();
        LocalTime horaActual = LocalTime.now();

        Asistencia asistencia = asistenciaDAO.buscarPorEmpleadoYFecha(idEmpleado, fecha);

        if (asistencia == null) {
            return "No se encontró la asistencia del día.";
        }

        if (asistencia.getHoraEntradaManana() == null) {
            return "Primero debe registrar la entrada de la mañana.";
        }

        if (asistencia.getHoraSalidaManana() != null) {
            return "Ya registró la salida de la mañana.";
        }

        if (horaActual.isBefore(asistencia.getHoraEntradaManana())) {
            return "La salida de la mañana no puede ser menor que la entrada.";
        }

        boolean actualizado = asistenciaDAO.actualizarSalidaManana(idEmpleado, fecha, horaActual);

        if (actualizado) {
            recalcularAsistencia(idEmpleado);
            return "Salida de la mañana registrada correctamente.";
        }

        return "Error al registrar salida de la mañana.";
    }

    public String registrarEntradaTarde(int idEmpleado) {

        if (!asegurarAsistenciaDelDia(idEmpleado)) {
            return "No se pudo crear la asistencia del día.";
        }

        LocalDate fecha = LocalDate.now();
        LocalTime horaActual = LocalTime.now();

        Asistencia asistencia = asistenciaDAO.buscarPorEmpleadoYFecha(idEmpleado, fecha);

        if (asistencia == null) {
            return "No se encontró la asistencia del día.";
        }

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

        if (!asegurarAsistenciaDelDia(idEmpleado)) {
            return "No se pudo crear la asistencia del día.";
        }

        LocalDate fecha = LocalDate.now();
        LocalTime horaActual = LocalTime.now();

        Asistencia asistencia = asistenciaDAO.buscarPorEmpleadoYFecha(idEmpleado, fecha);

        if (asistencia == null) {
            return "No se encontró la asistencia del día.";
        }

        if (asistencia.getHoraEntradaTarde() == null) {
            return "Primero debe registrar la entrada de la tarde.";
        }

        if (asistencia.getHoraSalidaTarde() != null) {
            return "Ya registró la salida de la tarde.";
        }

        if (horaActual.isBefore(asistencia.getHoraEntradaTarde())) {
            return "La salida de la tarde no puede ser menor que la entrada.";
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
        double horasTrabajadas = calcularHorasTrabajadas(asistencia);
        double descuento = calcularDescuento(minutosAtraso);

        asistencia.setMinutosAtraso(minutosAtraso);
        asistencia.setHorasTrabajadas(horasTrabajadas);
        asistencia.setDescuento(descuento);

        asistenciaDAO.actualizarCalculos(asistencia);
    }

    private int calcularMinutosAtraso(Asistencia asistencia) {

        int total = 0;

        if (asistencia.getHoraEntradaManana() != null
                && asistencia.getHoraEntradaManana().isAfter(HORA_ENTRADA_MANANA)) {

            total += (int) Duration.between(
                    HORA_ENTRADA_MANANA,
                    asistencia.getHoraEntradaManana()
            ).toMinutes();
        }

        if (asistencia.getHoraEntradaTarde() != null
                && asistencia.getHoraEntradaTarde().isAfter(HORA_ENTRADA_TARDE)) {

            total += (int) Duration.between(
                    HORA_ENTRADA_TARDE,
                    asistencia.getHoraEntradaTarde()
            ).toMinutes();
        }

        return total;
    }

    private double calcularHorasTrabajadas(Asistencia asistencia) {

        double totalHoras = 0.0;

        if (asistencia.getHoraEntradaManana() != null
                && asistencia.getHoraSalidaManana() != null
                && asistencia.getHoraSalidaManana().isAfter(asistencia.getHoraEntradaManana())) {

            long minutos = Duration.between(
                    asistencia.getHoraEntradaManana(),
                    asistencia.getHoraSalidaManana()
            ).toMinutes();

            totalHoras += minutos / 60.0;
        }

        if (asistencia.getHoraEntradaTarde() != null
                && asistencia.getHoraSalidaTarde() != null
                && asistencia.getHoraSalidaTarde().isAfter(asistencia.getHoraEntradaTarde())) {

            long minutos = Duration.between(
                    asistencia.getHoraEntradaTarde(),
                    asistencia.getHoraSalidaTarde()
            ).toMinutes();

            totalHoras += minutos / 60.0;
        }

        if (totalHoras > MAXIMO_HORAS_DIARIAS) {
            totalHoras = MAXIMO_HORAS_DIARIAS;
        }

        return redondearDosDecimales(totalHoras);
    }

    private double calcularDescuento(int minutosAtraso) {
        return redondearDosDecimales(minutosAtraso * VALOR_DESCUENTO_POR_MINUTO);
    }

    private double redondearDosDecimales(double valor) {
        return Math.round(valor * 100.0) / 100.0;
    }
}