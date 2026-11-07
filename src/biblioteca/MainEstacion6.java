package biblioteca;

import java.time.LocalDate;
import java.time.LocalTime;

public class MainEstacion6 {
    public static void main(String[] args) {
        System.out.println("=== ESTACION 6: NOTIFICAR CAMBIOS ===");
        Reserva reserva = new ReservaBuilder()
            .estudiante(new Estudiante("E001", "Laura"))
            .fecha(LocalDate.now().plusDays(7))
            .horaInicio(LocalTime.of(14, 0))
            .duracionHoras(2)
            .cantidadPersonas(4)
            .sala(new Sala("B", "Sala B", 4))
            .build();

        reserva.agregarObservador(new NotificadorEstudiante());
        reserva.agregarObservador(new RegistroEventos());

        System.out.println("\nConfirmar: deben aparecer los dos mensajes de los observadores.");
        reserva.confirmar();
        System.out.println("Estado: " + reserva.getNombreEstado());

        System.out.println("\nCancelar: deben aparecer nuevamente los dos mensajes.");
        reserva.cancelar();
        System.out.println("Estado: " + reserva.getNombreEstado());

        System.out.println("\nConfirmar cancelada: debe rechazarse sin notificaciones.");
        try {
            reserva.confirmar();
            System.out.println("ERROR: se confirmo una reserva cancelada.");
        } catch (IllegalStateException ex) {
            System.out.println("Rechazada: " + ex.getMessage());
        }
    }
}
