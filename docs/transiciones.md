# Ciclo de vida de Reserva

Las clases de estado se incorporaron como dependencias en la estación 3; esta estación las valida explícitamente.

| Estado | confirmar | cancelar | finalizar |
|---|---|---|---|
| Pendiente | Confirmada | Cancelada | Rechazo |
| Confirmada | Rechazo | Cancelada | Finalizada |
| Cancelada | Rechazo | Rechazo | Rechazo |
| Finalizada | Rechazo | Rechazo | Rechazo |

Una operación rechazada no llama a cambiarEstado y no genera evento. Las pruebas de notificación se completan en la batería P01-P11 de la estación 8. El Main de esta rama es auxiliar de pruebas; la estación 8 restaura el Main final del taller.
