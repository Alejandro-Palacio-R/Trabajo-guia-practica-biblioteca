package biblioteca;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

public class Main {
    private static int comprobaciones;

    public static void main(String[] args) {
        estacion4();
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

    public static void estacion4() {
        System.out.println("\n=== ESTACION 4: STRATEGY ===");
        List<Sala> salas = new RepositorioSalasMemoria().obtenerDisponibles();
        comprobar(new SeleccionPorCapacidad().seleccionar(salas, 4)
            .orElseThrow().getCodigo().equals("B"), "P03: capacidad para 4 -> B");
        comprobar(new SeleccionSalaPreferida("C").seleccionar(salas, 4)
            .orElseThrow().getCodigo().equals("C"), "P04: preferida C para 4 -> C");
        comprobar(new SeleccionSalaPreferida("B").seleccionar(salas, 7).isEmpty(),
            "P05: preferida B para 7 -> vacio");
        comprobar(new SeleccionPorCapacidad().seleccionar(salas, 3)
            .orElseThrow().getCodigo().equals("B"), "Elige la menor capacidad suficiente");
    }
}
