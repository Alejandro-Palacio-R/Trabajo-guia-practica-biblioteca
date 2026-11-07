package biblioteca;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

/** Pruebas auxiliares: no reemplaza el Main original ni modifica el dominio. */
public class MainPruebas {
    private static int comprobaciones;

    public static void main(String[] args) {
        estacion2();
        estacion3();
        estacion4();
        estacion5();
        estacion6();
        estacion7();
        estacion8();
        System.out.println("TOTAL: " + comprobaciones + " comprobaciones correctas.");
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

    public static void estacion3() {
        System.out.println("\n=== ESTACION 3: BUILDER ===");
        Reserva r = datos().build();
        System.out.println(r);
        comprobar(r.getNombreEstado().equals("Pendiente")
            && r.getCantidadPersonas() == 4 && r.getSala().getCodigo().equals("B"),
            "P01: construccion valida, estado inicial Pendiente");
        rechaza(() -> datos().estudiante(null).build(), "P02: sin estudiante");
        rechaza(() -> datos().cantidadPersonas(5).build(), "Capacidad insuficiente");
        rechaza(() -> datos().fecha(LocalDate.now().minusDays(1)).build(), "Fecha pasada");
        rechaza(() -> datos().duracionHoras(0).build(), "Duracion cero");
        rechaza(() -> datos().cantidadPersonas(0).build(), "Personas cero");
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

    public static void estacion7() {
        System.out.println("\n=== ESTACION 7: STATE ===");
        Reserva r = datos().build();
        rechaza(r::finalizar, "No se finaliza una Pendiente");
        comprobar(r.getNombreEstado().equals("Pendiente"), "Rechazo conserva estado");
        r.confirmar();
        rechaza(r::confirmar, "No se confirma dos veces");
        int[] eventos = {0};
        r.agregarObservador((reserva, anterior, nuevo) -> eventos[0]++);
        r.finalizar();
        comprobar(r.getNombreEstado().equals("Finalizada") && eventos[0] == 1,
            "P11: finalizar confirmada cambia estado y notifica");
        rechaza(r::cancelar, "No se cancela una Finalizada");
        rechaza(r::confirmar, "No se confirma una Finalizada");
        rechaza(r::finalizar, "No se finaliza dos veces");
        comprobar(eventos[0] == 1 && r.getNombreEstado().equals("Finalizada"),
            "Estado terminal conserva estado y no notifica rechazos");
        Reserva pendiente = datos().build();
        pendiente.cancelar();
        comprobar(pendiente.getNombreEstado().equals("Cancelada"), "Pendiente -> Cancelada");
        rechaza(pendiente::cancelar, "No se cancela dos veces");
        rechaza(pendiente::finalizar, "No se finaliza una Cancelada");
    }

    public static void estacion8() {
        System.out.println("\n=== ESTACION 8: MAIN ORIGINAL ===");
        Main.main(new String[0]);
        comprobar(true, "Main original termina sin excepciones no controladas");
    }
}
