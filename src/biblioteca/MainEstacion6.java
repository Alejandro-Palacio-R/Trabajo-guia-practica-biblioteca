package biblioteca;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

public class MainEstacion6 {
    private static int comprobaciones;

    public static void main(String[] args) {
        estacion6();
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

    public static void estacion6() {
        System.out.println("\n=== ESTACION 6: OBSERVER ===");
        Reserva r = datos().build();
        int[] avisos = {0, 0};
        ObservadorReserva primero = (reserva, anterior, nuevo) -> {
            avisos[0]++;
            System.out.println("Observador 1: " + anterior + " -> " + nuevo);
        };
        ObservadorReserva segundo = (reserva, anterior, nuevo) -> {
            avisos[1]++;
            System.out.println("Observador 2: " + anterior + " -> " + nuevo);
        };
        r.agregarObservador(new NotificadorEstudiante());
        r.agregarObservador(new RegistroEventos());
        r.agregarObservador(primero);
        r.agregarObservador(segundo);
        r.confirmar();
        comprobar(avisos[0] == 1 && avisos[1] == 1,
            "P07: ambos suscriptores reciben la confirmacion");
        r.cancelar();
        comprobar(r.getNombreEstado().equals("Cancelada")
            && avisos[0] == 2 && avisos[1] == 2,
            "P08: cancelar confirmada cambia estado y notifica");
        rechaza(r::confirmar, "P09: confirmar cancelada se rechaza");
        comprobar(avisos[0] == 2 && avisos[1] == 2,
            "P09: rechazo no genera notificaciones");

        Reserva otra = datos().build();
        otra.agregarObservador(primero);
        otra.retirarObservador(primero);
        otra.confirmar();
        comprobar(avisos[0] == 2, "Observador retirado no recibe avisos");
    }
}
