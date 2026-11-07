# Biblioteca · Guía práctica de patrones de diseño

Prototipo académico de consola en **Java 17 o superior** para reservar salas de biblioteca. Construido por estaciones con **Builder, Strategy, Facade, Observer y State**.

**Estado de esta versión:** Integración final de las ocho estaciones.

## Ramas del taller

Cada rama conserva el avance acumulado hasta su estación. Las ramas se integran en `main` mediante merges explícitos, conservando la separación y el historial del taller.

| Estación | Contenido | Rama |
|---|---|---|
| 01 | [Comprender el problema y modelar el flujo](https://github.com/Alejandro-Palacio-R/Trabajo-guia-practica-biblioteca/tree/estacion-01) | `estacion-01` |
| 02 | [Crear el dominio y ejecutar la primera versión](https://github.com/Alejandro-Palacio-R/Trabajo-guia-practica-biblioteca/tree/estacion-02) | `estacion-02` |
| 03 | [Construir reservas con Builder](https://github.com/Alejandro-Palacio-R/Trabajo-guia-practica-biblioteca/tree/estacion-03) | `estacion-03` |
| 04 | [Seleccionar salas con Strategy](https://github.com/Alejandro-Palacio-R/Trabajo-guia-practica-biblioteca/tree/estacion-04) | `estacion-04` |
| 05 | [Separar acceso a datos y coordinar con Facade](https://github.com/Alejandro-Palacio-R/Trabajo-guia-practica-biblioteca/tree/estacion-05) | `estacion-05` |
| 06 | [Notificar eventos con Observer](https://github.com/Alejandro-Palacio-R/Trabajo-guia-practica-biblioteca/tree/estacion-06) | `estacion-06` |
| 07 | [Controlar el ciclo de vida con State](https://github.com/Alejandro-Palacio-R/Trabajo-guia-practica-biblioteca/tree/estacion-07) | `estacion-07` |
| 08 | [Integrar y validar la aplicación completa](https://github.com/Alejandro-Palacio-R/Trabajo-guia-practica-biblioteca/tree/estacion-08) | `estacion-08` |

## Ejecutar

Requiere JDK 17 o superior. Desde la raíz, en PowerShell:

```powershell
New-Item -ItemType Directory -Force out
javac -encoding UTF-8 -d out src/biblioteca/*.java
java -cp out biblioteca.Main
```

La estación 1 es conceptual y no contiene todavía clases para ejecutar.

## Organización

- `src/biblioteca`: código Java de la estación actual.
- `docs/estaciones`: propósito, cambios y validación de cada estación.
- `docs/diagramas`: flujo y fuentes editables.
- `docs/evidencias`: salida real obtenida al ejecutar cada avance.

## Alcance

La disponibilidad es una lista fija con capacidades 2, 4, 6 y 8. Este prototipo no controla cruces horarios ni persiste entre ejecuciones. Una sala puede aparecer en varias reservas.

## Historial de integración

```powershell
git log --graph --oneline --decorate --all
```

Las clases State se incluyen como dependencias desde la estación 3 para mantener esa versión compilable, conforme a la construcción acumulativa de la guía. Su prueba específica se desarrolla en la estación 7.

## Pruebas y entregables

Las pruebas P01-P11 y las comprobaciones adicionales pasaron: **37 comprobaciones correctas**. También se compiló y ejecutó cada versión de estación con código Java.

Para ejecutar la batería sin reemplazar Main:

```powershell
./entregables/ejecutar_pruebas.ps1
```

- [Guía por estaciones, con código Main y explicaciones](entregables/guia_por_estaciones.md)
- [Informe PDF](entregables/informe_biblioteca.pdf)
- [Matriz de trazabilidad CSV](entregables/matriz_trazabilidad.csv)
- [Resultados reales de P01-P11](entregables/evidencias/resultados.txt)
- [Pruebas auxiliares](entregables/pruebas/MainPruebas.java)
- [Diagrama de flujo](entregables/diagramas/flujo.png)
- [Diagrama de clases](entregables/diagramas/clases.png)

Los Main alternativos se encuentran en `entregables/pruebas/estacion2` a `estacion8`. Utiliza uno a la vez; compilar recursivamente todos esos archivos produciría clases Main duplicadas. El script ya selecciona los archivos correctos.

Las imágenes de evidencia fueron generadas desde la salida de ejecución; están rotuladas como representaciones de texto y no como capturas del IDE. La conclusión del informe es una base que el estudiante debe personalizar.

## Patrones y responsabilidades

| Patrón | Componentes | Responsabilidad |
|---|---|---|
| Builder | `ReservaBuilder` | Construcción y validación de reservas |
| Strategy | `EstrategiaSeleccionSala` y sus implementaciones | Seleccionar una sala adecuada |
| Facade | `ReservaFacade` | Coordinar el caso de uso |
| Observer | `ObservadorReserva`, `NotificadorEstudiante`, `RegistroEventos` | Reaccionar a cambios de estado |
| State | `EstadoReserva`, `Pendiente`, `Confirmada`, `Cancelada`, `Finalizada` | Autorizar o rechazar transiciones |

## Fuente y material auxiliar

Trabajo basado en la guía práctica suministrada en clase. Las clases del dominio se conservaron de la carpeta original. La organización del repositorio, la documentación y las pruebas auxiliares se prepararon con asistencia de Codex.
