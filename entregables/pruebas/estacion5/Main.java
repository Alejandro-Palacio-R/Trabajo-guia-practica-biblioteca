package biblioteca;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

public class Main {
    private static int comprobaciones;

    public static void main(String[] args) {
        estacion5();
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

    public static void estacion5() {
        System.out.println("\n=== ESTACION 5: FACADE ===");
        RepositorioReservasMemoria repositorio = new RepositorioReservasMemoria();
        ReservaFacade fachada = new ReservaFacade(new RepositorioSalasMemoria(),
            repositorio, List.of());
        Reserva r = fachada.realizarReserva(new Estudiante("E001", "Laura"),
            LocalDate.now().plusDays(7), LocalTime.of(14, 0), 2, 4,
            new SeleccionPorCapacidad()).orElseThrow();
        comprobar(r.getNombreEstado().equals("Confirmada")
            && repositorio.obtenerTodas().size() == 1
            && repositorio.obtenerTodas().get(0) == r,
            "P06: reserva registrada y confirmada");
        comprobar(fachada.realizarReserva(new Estudiante("E003", "Camila"),
            LocalDate.now().plusDays(7), LocalTime.of(9, 0), 2, 10,
            new SeleccionPorCapacidad()).isEmpty()
            && repositorio.obtenerTodas().size() == 1,
            "P10: para 10 personas no crea ni registra otra reserva");
    }
}
