package biblioteca;

import java.time.LocalDate;
import java.time.LocalTime;

public class MainEstacion7 {
    public static void main(String[] args) {
        System.out.println("=== ESTACION 7: CAMBIOS DE ESTADO ===");
        Reserva primera = new ReservaBuilder()
            .estudiante(new Estudiante("E001", "Laura"))
            .fecha(LocalDate.now().plusDays(7))
            .horaInicio(LocalTime.of(14, 0))
            .duracionHoras(2)
            .cantidadPersonas(4)
            .sala(new Sala("B", "Sala B", 4))
            .build();

        System.out.println("\nPrimera reserva: Pendiente -> Confirmada -> Cancelada.");
        System.out.println("Estado inicial: " + primera.getNombreEstado());
        primera.confirmar();
        System.out.println("Despues de confirmar: " + primera.getNombreEstado());
        primera.cancelar();
        System.out.println("Despues de cancelar: " + primera.getNombreEstado());

        System.out.println("\nConfirmar una cancelada: debe rechazarse.");
        try {
            primera.confirmar();
            System.out.println("ERROR: se confirmo una reserva cancelada.");
        } catch (IllegalStateException ex) {
            System.out.println("Rechazada: " + ex.getMessage());
        }

        Reserva segunda = new ReservaBuilder()
            .estudiante(new Estudiante("E002", "Andres"))
            .fecha(LocalDate.now().plusDays(8))
            .horaInicio(LocalTime.of(10, 0))
            .duracionHoras(1)
            .cantidadPersonas(2)
            .sala(new Sala("A", "Sala A", 2))
            .build();

        System.out.println("\nSegunda reserva: Pendiente -> Confirmada -> Finalizada.");
        segunda.confirmar();
        System.out.println("Despues de confirmar: " + segunda.getNombreEstado());
        segunda.finalizar();
        System.out.println("Despues de finalizar: " + segunda.getNombreEstado());

        System.out.println("\nCancelar una finalizada: debe rechazarse.");
        try {
            segunda.cancelar();
            System.out.println("ERROR: se cancelo una reserva finalizada.");
        } catch (IllegalStateException ex) {
            System.out.println("Rechazada: " + ex.getMessage());
        }
    }
}
