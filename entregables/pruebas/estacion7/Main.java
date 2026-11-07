package biblioteca;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

public class Main {
    private static int comprobaciones;

    public static void main(String[] args) {
        estacion7();
    }

    private static void comprobar(boolean condicion, String descripcion) {
        if (!condicion) throw new AssertionError(descripcion);
        comprobaciones++;
        System.out.println("OK: " + descripcion);
    }

    private static void rechaza(Runnable accion, String descripcion) {
        try {
            accion.run();
        } catch (IllegalStateException | IllegalArgumentException ex) {
            comprobar(true, descripcion + " | " + ex.getMessage());
            return;
        }
        throw new AssertionError("No rechazo: " + descripcion);
    }

    private static ReservaBuilder datos() {
        return new ReservaBuilder()
            .estudiante(new Estudiante("E001", "Laura"))
            .fecha(LocalDate.now().plusDays(7))
            .horaInicio(LocalTime.of(14, 0))
            .duracionHoras(2).cantidadPersonas(4)
            .sala(new Sala("B", "Sala B", 4));
    }

    public static void estacion7() {
        System.out.println("\n=== ESTACION 7: STATE ===");
        Reserva r = datos().build();
        rechaza(r::finalizar, "No se finaliza una Pendiente");
        comprobar(r.getNombreEstado().equals("Pendiente"), "Rechazo conserva estado");
        r.confirmar();
        rechaza(r::confirmar, "No se confirma dos veces");
        int[] eventos = {0};
        r.agregarObservador((reserva, anterior, nuevo) -> eventos[0]++);
        r.finalizar();
        comprobar(r.getNombreEstado().equals("Finalizada") && eventos[0] == 1,
            "P11: finalizar confirmada cambia estado y notifica");
        rechaza(r::cancelar, "No se cancela una Finalizada");
        rechaza(r::confirmar, "No se confirma una Finalizada");
        rechaza(r::finalizar, "No se finaliza dos veces");
        comprobar(eventos[0] == 1 && r.getNombreEstado().equals("Finalizada"),
            "Estado terminal conserva estado y no notifica rechazos");
        Reserva pendiente = datos().build();
        pendiente.cancelar();
        comprobar(pendiente.getNombreEstado().equals("Cancelada"), "Pendiente -> Cancelada");
        rechaza(pendiente::cancelar, "No se cancela dos veces");
        rechaza(pendiente::finalizar, "No se finaliza una Cancelada");
    }
}
