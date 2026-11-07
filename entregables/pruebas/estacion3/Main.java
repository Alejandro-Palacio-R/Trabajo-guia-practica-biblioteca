package biblioteca;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

public class Main {
    private static int comprobaciones;

    public static void main(String[] args) {
        estacion3();
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

    public static void estacion3() {
        System.out.println("\n=== ESTACION 3: BUILDER ===");
        Reserva r = datos().build();
        System.out.println(r);
        comprobar(r.getNombreEstado().equals("Pendiente")
            && r.getCantidadPersonas() == 4 && r.getSala().getCodigo().equals("B"),
            "P01: construccion valida, estado inicial Pendiente");
        rechaza(() -> datos().estudiante(null).build(), "P02: sin estudiante");
        rechaza(() -> datos().cantidadPersonas(5).build(), "Capacidad insuficiente");
        rechaza(() -> datos().fecha(LocalDate.now().minusDays(1)).build(), "Fecha pasada");
        rechaza(() -> datos().duracionHoras(0).build(), "Duracion cero");
        rechaza(() -> datos().cantidadPersonas(0).build(), "Personas cero");
    }
}
