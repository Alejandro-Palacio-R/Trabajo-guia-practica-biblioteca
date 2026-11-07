package biblioteca;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

public class Main {
    private static int comprobaciones;

    public static void main(String[] args) {
        estacion2();
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

    public static void estacion2() {
        System.out.println("\n=== ESTACION 2: DOMINIO ===");
        List<Sala> salas = new RepositorioSalasMemoria().obtenerDisponibles();
        salas.forEach(System.out::println);
        comprobar(salas.size() == 4, "Cuatro salas");
        for (int i = 0; i < salas.size(); i++) {
            comprobar(salas.get(i).getCapacidad() == (i + 1) * 2,
                "Capacidad de sala " + salas.get(i).getCodigo());
        }
        comprobar(new Estudiante("E001", "Laura").getNombre().equals("Laura"),
            "Estudiante conserva su nombre");
        rechaza(() -> new Estudiante("", "Laura"), "Identificador vacio");
        rechaza(() -> new Sala("X", "Sala X", 0), "Capacidad cero");
    }
}
