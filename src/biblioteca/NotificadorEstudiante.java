package biblioteca;

public class NotificadorEstudiante implements ObservadorReserva {
@Override 
public void actualizar (Reserva reserva, String anterior, String nuevo){
    System.out.println("Notificacion a "
        + reserva.getEstudiante().getNombre()
        + ": su reserva" + reserva.getCodigo()
        + " cambio de " + anterior + " a " + nuevo + "."
    );
}
}

