package biblioteca;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

public class MainEstacion5 {
    public static void main(String[] args) {
        System.out.println("=== ESTACION 5: REGISTRAR CON FACADE ===");
        RepositorioReservasMemoria reservas = new RepositorioReservasMemoria();
        ReservaFacade fachada = new ReservaFacade(
            new RepositorioSalasMemoria(), reservas, List.of());

        System.out.println("\nReserva para 4 personas. Esperado: sala B y Confirmada.");
        Reserva reserva = fachada.realizarReserva(
            new Estudiante("E001", "Laura"),
            LocalDate.now().plusDays(7), LocalTime.of(14, 0),
            2, 4, new SeleccionPorCapacidad()
        ).orElseThrow();
        System.out.println(reserva);
        System.out.println("Reservas guardadas: " + reservas.obtenerTodas().size() + " (esperado: 1)");

        System.out.println("\nSolicitud para 10 personas. Esperado: no crear otra reserva.");
        fachada.realizarReserva(
            new Estudiante("E003", "Camila"),
            LocalDate.now().plusDays(8), LocalTime.of(9, 0),
            2, 10, new SeleccionPorCapacidad()
        );
        System.out.println("Reservas guardadas: " + reservas.obtenerTodas().size() + " (esperado: 1)");
    }
}
