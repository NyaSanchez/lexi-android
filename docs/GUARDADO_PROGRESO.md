# Guardado local del progreso

## Alcance

Las partidas terminadas se guardan en `lexi-progress.db`, dentro del almacenamiento privado de la aplicación. No se necesita una cuenta ni conexión a Internet para guardar o consultar resultados. No se añadieron permisos de Android.

Una partida produce un resultado de victoria o derrota. No se guarda como resultado una partida abandonada, un intento inválido ni un borrador. El historial comienza con las partidas terminadas en esta versión: las pruebas de versiones anteriores no tenían registros que recuperar.

## Qué contiene cada registro

| Campo | Propósito |
| --- | --- |
| `sessionId` | Identifica una partida concreta; es la clave primaria |
| `entryId` | ID estable de la palabra del catálogo |
| `answerLanguage` | Idioma de respuesta de esa partida |
| `answer`, `translation` | Copia de los textos al terminar, para conservar el resultado histórico |
| `won`, `attempts` | Resultado e intentos válidos utilizados |
| `completedAt` | Instante de finalización en milisegundos desde epoch |
| `localDate`, `zoneId` | Fecha local y zona horaria capturadas al terminar, para el futuro cálculo de la racha |

El identificador, el instante y la zona se conservan al recrearse la pantalla. Si se intenta guardar de nuevo el mismo registro, Room mantiene una sola fila. Si el mismo identificador trae otro contenido, se rechaza la escritura para no sobrescribir un resultado anterior. Otra partida de la misma palabra tiene otro identificador y sí cuenta como otra partida.

## Interfaz

Al terminar aparece **Guardando resultado…**. **Resultado guardado** solo se muestra cuando la escritura ha terminado. Hasta entonces se espera antes de pasar a otra palabra o salir de la pantalla. Si falla el guardado, aparece **Reintentar guardado**; salir en ese caso requiere confirmar que el resultado no quedó guardado.

Progreso distingue carga, ausencia de resultados y error de lectura. Un error no se presenta como si el historial estuviera vacío, y no se elimina la base de datos para recuperarse.

Los contadores son acumulados:

- **Partidas terminadas:** victorias y derrotas guardadas.
- **Aciertos:** partidas ganadas, incluso si se repite una palabra en otra partida.
- **Sin acierto:** partidas perdidas tras seis intentos.
- **Palabras distintas acertadas:** IDs del catálogo con al menos una victoria. HOUSE y CASA comparten un concepto y cuentan una sola vez aquí.

Se muestran los últimos veinte registros, ordenados por instante de finalización. Los totales incluyen todos los registros. Las fechas visibles se presentan en la zona actual del teléfono; la fecha y zona originales permanecen en la base de datos.

El calendario y Repaso siguen pendientes. La racha diaria se calcula de forma dinámica extrayendo fechas únicas del historial. Exige victorias en días consecutivos para mantenerse activa y notifica visualmente en la pantalla de inicio si la práctica del día actual ya se completó o está en riesgo.

## Organización técnica

- `data/progress/SavedGame.kt`: registro y validación de una partida terminada.
- `data/progress/ProgressDao.kt`: inserción sin duplicados y consultas transaccionales.
- `data/progress/ProgressDatabase.kt`: base de datos Room, versión 1, una instancia por proceso.
- `data/progress/ProgressRepository.kt`: acceso al guardado y observación de cambios.
- `ui/ProgressViewModel.kt`: estados de carga, guardado, error, reintento y cálculo reactivo de la racha (StreakState) filtrando fechas anómalas; sobrevive a la rotación.
- `ui/ProgressScreen.kt`: contadores y últimas partidas.
- `ui/HomeScreen.kt`: consume el estado de la racha para mostrar el indicador de meta diaria.
- `app/schemas/`: esquema exportado por Room para revisar futuras migraciones.

Las rutas de código son relativas a `app/src/main/java/io/github/nyasanchez/lexi/`. Se añadieron Room 2.8.5, KSP 2.3.10 y Lifecycle 2.10.0. Las consultas se ejecutan mediante APIs suspendidas de Room; no se habilita el acceso a la base de datos en el hilo principal ni la recreación destructiva como sustituto de una migración.

## Comprobación manual

1. Instalar la versión actual y abrir Progreso. Si no hay partidas guardadas, debe aparecer el historial vacío.
2. Ganar con HOUSE y esperar **Resultado guardado**. Girar el teléfono en el resultado. En Progreso deben verse una partida, un acierto, cero derrotas y una palabra distinta.
3. Cerrar y volver a abrir Lexi. Los mismos contadores y la fila de HOUSE deben seguir presentes, sin duplicarse.
4. Perder otra partida enviando SHARE seis veces. Al guardarse, debe sumar una partida y una derrota, sin aumentar los aciertos.
5. Ganar con CASA en el modo inverso. Debe sumar una partida y un acierto, pero el número de palabras distintas debe seguir contando casa/house una sola vez.
6. Empezar una palabra y abandonarla tras un intento. Los contadores deben permanecer iguales.

Los valores concretos del segundo paso suponen un historial inicial vacío. Si ya hay registros, comparar los incrementos correspondientes.

## Pruebas automatizadas

```powershell
.\gradlew.bat :app:assembleDebug :app:testDebugUnitTest :app:lintDebug
```

Las pruebas locales validan la representación del resultado, el idioma inverso, la fecha local alrededor de medianoche, los estados alterados y la reconstrucción de un registro idéntico.

`ProgressDatabaseTest` comprueba duplicados, conflicto de contenido, reapertura del archivo, contadores y límite de veinte filas. Usa archivos temporales propios y no borra `lexi-progress.db`. Puede ejecutarse específicamente en un dispositivo o emulador preparado para pruebas:

```powershell
.\gradlew.bat :app:connectedDebugAndroidTest -Pandroid.testInstrumentationRunnerArguments.class=io.github.nyasanchez.lexi.ProgressDatabaseTest
```

Las pruebas generales de interfaz `PracticeFlowTest` realizan partidas en la app de depuración y pueden añadir resultados de prueba a su historial, como las comprobaciones manuales. En el Xiaomi se mantiene la elección de comprobación manual de la persona autora.

## Verificación del 30 de octubre de 2026

- APK de la app y APK de pruebas Android compilados; versión de depuración instalada correctamente en el Xiaomi.
- 54 pruebas unitarias aprobadas con JUnitCore, incluidas las siete nuevas del registro de resultados.
- Lint: cero errores y once advertencias sobre versiones de herramientas, dependencias y SDK objetivo.
- Las ocho pruebas instrumentadas se compilaron, pero no se ejecutaron en el Xiaomi; se respeta la elección de prueba manual.
- Comprobación inicial en el Xiaomi: HOUSE guardado, rotación sin duplicados y contadores 1/1/0/1 confirmados por la persona autora. Tras detener el proceso, la base de datos conservó exactamente ese registro; integridad SQLite correcta y reapertura en arranque en frío confirmada.
- Comprobación final en el Xiaomi: reapertura conservando el resultado, derrota con seis intentos, victoria en español y abandono confirmados por la persona autora. La pantalla y el archivo SQLite coinciden: 3 partidas, 2 aciertos, 1 derrota y 1 palabra distinta. Los registros son HOUSE ganado en un intento, HOUSE perdido en seis y CASA ganado en un intento; el abandono no añadió una fila. Integridad del archivo correcta después de cerrar el proceso.
- El esquema JSON exportado coincide con el incluido en el proyecto. Los archivos fuente utilizados para compilar coinciden con los del repositorio.

El comando conjunto de Gradle termina con error porque el entorno restringido impide renombrar el informe binario de `testDebugUnitTest` (`AccessDeniedException`). La compilación y lint terminaron; las mismas 54 pruebas se ejecutaron directamente con JUnitCore y aprobaron. Esto no se registra como un `BUILD SUCCESSFUL` global.

## Commit propuesto

Summary:

```text
feat: guardar progreso local de práctica
```

Description:

```text
Guarda partidas terminadas con Room y evita registros duplicados.
Muestra totales e historial reciente en Progreso.
Añade confirmación de guardado y reintento ante errores.
Conserva la fecha y el idioma originales de cada resultado.
Incluye el esquema inicial y pruebas del registro y la base de datos.
```

Revisar **Changes**, hacer **Commit to main** y después **Push origin**. Incluir el esquema JSON y los cambios de Gradle; excluir la base de datos del teléfono, compilaciones y archivos locales.

## Referencias

La implementación utiliza [Room](https://developer.android.com/training/data-storage/room), sus [consultas asíncronas](https://developer.android.com/training/data-storage/room/async-queries) y el [esquema exportado del plugin](https://developer.android.com/jetpack/androidx/releases/room). Las dependencias de la rama Room 2.8 se fijan en el catálogo de versiones del proyecto.
