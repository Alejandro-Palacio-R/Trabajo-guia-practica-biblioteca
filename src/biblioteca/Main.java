package biblioteca;

import java.util.List;
import java.time.LocalDate;
import java.time.LocalTime;

public class Main {
    public static void main(String[] args) {
        RepositorioSalas salas = new RepositorioSalasMemoria();
        RepositorioReservasMemoria reservas = new RepositorioReservasMemoria();
        ReservaFacade fachada = new ReservaFacade(salas, reservas);
        fachada.realizarReserva(new Estudiante("E001", "Laura"),
            LocalDate.now().plusDays(7), LocalTime.of(14, 0), 2, 4,
            new SeleccionPorCapacidad()).ifPresent(System.out::println);
        System.out.println("Reservas registradas: " + reservas.obtenerTodas().size());
    }
}
