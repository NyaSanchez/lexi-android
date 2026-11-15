# Repaso

Repaso reúne las palabras en las que se agotaron los seis intentos y permite volver a intentarlas. Si se aciertan, cuentan para el avance del nivel que les corresponde en Inicio.

## Qué entra en Repaso

Una palabra está pendiente en un modo cuando se perdió al menos una vez en ese modo y **nunca se ha acertado en ese mismo modo**. Los dos modos son independientes, igual que el avance de los niveles: perder CASA (respuesta en español) no afecta a HOUSE (respuesta en inglés).

- Abandonar una partida a medias no cuenta como pérdida: la palabra no entra en Repaso.
- La lista se ordena con las pérdidas más recientes primero.
- Cada tarjeta muestra la definición, el modo, el nivel con su subnivel CEFR y la longitud. **Nunca muestra la respuesta**; se revela al terminar el reintento.

## Cómo sale una palabra de Repaso

Un reintento es una partida normal: seis intentos nuevos y un identificador de sesión propio. Al terminar se guarda en Room como cualquier otra partida.

- **Si se acierta**, se guarda como partida ganada. La palabra sale de Repaso y la lista de palabras acertadas que usa Inicio ya la incluye, de modo que el contador y el desbloqueo del nivel se actualizan sin lógica adicional.
- **Si no se acierta**, se guarda otra derrota y la palabra sigue pendiente.
- **Acertarla en la práctica normal** también la saca de Repaso, porque la condición es no tener ninguna victoria en ese modo.

El historial y los contadores de Progreso incluyen los reintentos: cuentan como partidas terminadas, y un acierto en Repaso cuenta para la meta diaria y la racha como cualquier otro acierto.

## Dónde está en el código

Rutas desde `app/src/main/java/io/github/nyasanchez/lexi/`:

| Ruta | Responsabilidad |
| --- | --- |
| `data/progress/ProgressDao.kt` | Consulta `observeReviewEntries()`: agrupa por palabra y modo y conserva los grupos sin victorias. No cambia el esquema de Room, por lo que no hay migración. |
| `data/progress/SavedGame.kt` | `ReviewEntry`, la fila que devuelve la consulta. |
| `domain/review/ReviewRules.kt` | Convierte las filas en `ReviewItem` con el catálogo, descarta palabras que ya no existen y ordena. Sin dependencias de Android. |
| `ui/ProgressViewModel.kt` | Expone `review`, calculado desde la misma base que `levels`. |
| `ui/ReviewScreen.kt` | Lista de pendientes, estado vacío y de carga. |
| `ui/ReviewPracticeScreen.kt` | Reintento de una palabra; reutiliza `PracticeContent` y la pantalla de resultado. |
| `ui/LexiApp.kt` | Ruta `review/{mode}/{entryId}`, fuera de la barra inferior. |

`PracticeContent` y `PracticeResultScreen` ahora aceptan una posición opcional y un texto configurable para el botón de volver, necesarios porque el reintento no tiene «Palabra n de N» ni vuelve a Inicio.

## Comprobación

Pruebas locales (sin dispositivo):

```powershell
.\gradlew.bat :app:testDebugUnitTest
```

`ReviewRulesTest` cubre el orden, el desempate, los datos que ya no existen en el catálogo y la reapertura en el modo correcto.

Pruebas con dispositivo: `ProgressDatabaseTest` comprueba que una palabra perdida queda pendiente, que acertarla en un modo la saca solo de ese modo, que varias derrotas se listan una vez y que una palabra ya acertada nunca entra.

Recorrido manual:

1. En Básico/inglés, perder una palabra con seis intentos incorrectos. En **Repaso** debe aparecer con su definición y sin la respuesta.
2. Pulsar **Reintentar** y acertar. Debe verse **Resultado guardado**; al volver, la palabra ya no está en Repaso.
3. En **Inicio**, el nivel Básico del mismo modo debe mostrar una palabra acertada más.
4. Perder otra vez un reintento: la palabra debe seguir en Repaso.
5. Salir de un reintento a medias y confirmar: Progreso no cambia y la palabra sigue pendiente.

## Pendiente de decidir

- Si un acierto en Repaso debe contar para la racha y la meta diaria o solo para el nivel. Hoy cuenta para ambos porque es una partida ganada más.
- Si la pantalla de resultado de un reintento debe ofrecer, como la práctica normal, pasar de nivel al alcanzar la meta del 70 %.
- Si la lista debe agruparse por modo o nivel cuando haya muchas palabras pendientes.
