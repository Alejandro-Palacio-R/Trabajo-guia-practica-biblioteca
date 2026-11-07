# Responsabilidades del sistema

1. Main configura dependencias y presenta los escenarios.
2. Strategy selecciona una sala adecuada.
3. Builder valida los datos y construye la reserva.
4. Los repositorios consultan salas y registran reservas.
5. Facade coordina el caso de uso.
6. State controla las operaciones permitidas por estado.
7. Observer comunica los cambios a los suscriptores.

Una clase que consultara, seleccionara, construyera, validara, notificara y controlara estados tendría baja cohesión: cambiaría por motivos diferentes y sería difícil probarla y mantenerla.
