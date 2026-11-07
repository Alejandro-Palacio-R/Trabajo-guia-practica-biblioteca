package biblioteca;

import java.time.LocalDate;
import java.time.LocalTime;

public class MainEstacion3 {
    public static void main(String[] args) {
        System.out.println("=== ESTACION 3: CONSTRUIR UNA RESERVA ===");
        Reserva reserva = new ReservaBuilder()
            .estudiante(new Estudiante("E001", "Laura"))
            .fecha(LocalDate.now().plusDays(7))
            .horaInicio(LocalTime.of(14, 0))
            .duracionHoras(2)
            .cantidadPersonas(4)
            .sala(new Sala("B", "Sala B", 4))
            .build();
        System.out.println("Esperado: sala B y estado Pendiente.");
        System.out.println(reserva);

        System.out.println("\nPrueba: construir sin estudiante. Debe rechazarse.");
        try {
            new ReservaBuilder()
                .fecha(LocalDate.now().plusDays(7))
                .horaInicio(LocalTime.of(14, 0))
                .duracionHoras(2)
                .cantidadPersonas(4)
                .sala(new Sala("B", "Sala B", 4))
                .build();
            System.out.println("ERROR: se acepto una reserva sin estudiante.");
        } catch (IllegalStateException ex) {
            System.out.println("Rechazada: " + ex.getMessage());
        }

        System.out.println("\nPrueba: 5 personas en sala de capacidad 4. Debe rechazarse.");
        try {
            new ReservaBuilder()
                .estudiante(new Estudiante("E001", "Laura"))
                .fecha(LocalDate.now().plusDays(7))
                .horaInicio(LocalTime.of(14, 0))
                .duracionHoras(2)
                .cantidadPersonas(5)
                .sala(new Sala("B", "Sala B", 4))
                .build();
            System.out.println("ERROR: se acepto una sala con capacidad insuficiente.");
        } catch (IllegalStateException ex) {
            System.out.println("Rechazada: " + ex.getMessage());
        }
    }
}
