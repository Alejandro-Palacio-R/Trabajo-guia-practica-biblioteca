package biblioteca;

import java.util.List;

public class MainEstacion4 {
    public static void main(String[] args) {
        System.out.println("=== ESTACION 4: SELECCIONAR SALA ===");
        List<Sala> salas = List.of(
            new Sala("A", "Sala A", 2),
            new Sala("B", "Sala B", 4),
            new Sala("C", "Sala C", 6),
            new Sala("D", "Sala D", 8)
        );

        System.out.println("\nPor capacidad para 4 personas. Esperado: sala B.");
        System.out.println(new SeleccionPorCapacidad().seleccionar(salas, 4).orElse(null));

        System.out.println("\nPreferida C para 4 personas. Esperado: sala C.");
        System.out.println(new SeleccionSalaPreferida("C").seleccionar(salas, 4).orElse(null));

        System.out.println("\nPreferida B para 7 personas. Esperado: sin sala.");
        if (new SeleccionSalaPreferida("B").seleccionar(salas, 7).isEmpty()) {
            System.out.println("Sin sala: la capacidad no es suficiente.");
        } else {
            System.out.println("ERROR: se selecciono una sala con capacidad insuficiente.");
        }
    }
}
