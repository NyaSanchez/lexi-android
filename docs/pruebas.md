# Matriz de QA - Entrega 2 de Lexi

## Alcance y estado del registro

Esta matriz reúne las pruebas de instalación inicial, tarea principal, entrada inválida, actualización desde Google Play, corrección del selector y conservación de datos. Incluye comprobaciones adicionales del tutorial, los niveles de vocabulario y la persistencia al volver a abrir la aplicación.

**Responsable del registro:** Sanchez Valadez Zyanya Maxi. **Alias de tester:** T03.

La persona probadora confirmó que el Xiaomi tiene instalada la versión 0.2.0 desde Google Play. Las capturas de Play Console acreditan las publicaciones 0.1.0 (código 1) y 0.2.0 (código 2). Los resultados manuales registrados por T03 se acompañan de videos y capturas. La revisión de los niveles Intermedio y Avanzado permanece pendiente. 

### Entorno y versiones

| Referencia | Datos registrados | Evidencia o dato pendiente |
| --- | --- | --- |
| D01 | Xiaomi POCO; Android 14; API 34. Datos declarados en el expediente. | [COMPLETAR: confirmar el modelo técnico exacto en el teléfono; el expediente indica `20021211RG`. Enlazar captura de modelo y versión de Android]. |
| V1 | `versionName: 0.1.0`; `versionCode: 1`. Publicación inicial del 02/11/2026. | [Historial de Play Console](evidencia/entrega-2/play-cerrada-v1.jpeg). |
| V2 | `versionName: 0.2.0`; `versionCode: 2`. Actualización del 07/11/2026. | [Publicación de v2](evidencia/entrega-2/play-actualizacion-v2.jpeg) y [paquete publicado](evidencia/entrega-2/play-notas-v2.jpeg). |
| Canal | Prueba cerrada - Entrega 2 - Curso. Paquete `io.github.nyasanchez.lexi`. | [Historial del canal](evidencia/entrega-2/play-historial-v1-v2.jpeg). |



### Estados

- **Pendiente:** ejecución o evidencia necesaria aún no registrada.
- **Parcial:** existe evidencia o ejecución de una parte del caso; deben identificarse los pasos pendientes.
- **Aprobado:** se ejecutaron los pasos, se cumplió el resultado esperado y se enlazó la evidencia.
- **Fallido:** se observó una diferencia respecto del resultado esperado; debe enlazarse el issue del defecto.

## Resumen de casos

| Caso | Comprobación | Dispositivo / API | Versión | Estado |
| --- | --- | --- | --- | --- |
| [QA-01](#qa-01-instalacion-inicial) | Instalación inicial desde Google Play | D01 / 34; confirmar dispositivo del registro recuperado | 0.1.0 (1) | Aprobado |
| [QA-02](#qa-02-partida-completa) | Tarea principal: partida y resultado guardado | D01 / 34 | 0.2.0 (2) | Aprobado |
| [QA-03](#qa-03-entrada-invalida) | Respuesta incompleta sin consumir intentos | D01 / 34 | 0.2.0 (2) | Aprobado |
| [QA-04](#qa-04-actualizacion-desde-google-play) | Actualización sin desinstalar | D01 / 34; confirmar dispositivo de la evidencia | 0.1.0 (1) → 0.2.0 (2) |Aprobado|
| [QA-05](#qa-05-correccion-del-selector) | Edición de letras sin tiradores superpuestos | D01 / 34 | 0.2.0 (2) | Aprobado |
| [QA-06](#qa-06-tutorial-interactivo) | Tutorial, navegación y cierre | D01 / 34 | 0.2.0 (2) | Aprobado |
| [QA-07](#qa-07-niveles-y-vocabulario) | Niveles, vocabulario y desbloqueo | D01 / 34 | 0.2.0 (2) | Pendiente |
| [QA-08](#qa-08-persistencia-al-reabrir) | Conservación de resultados al reabrir V2 | D01 / 34 | 0.2.0 (2) | Aprobado |

## QA-01. Instalacion inicial

| Campo | Registro |
| --- | --- |
| Dispositivo / API | D01 / Android 14 / API 34 |
| Versión | V1: 0.1.0, código 1. |
| Fecha / tester | El expediente declara 05/11/2026, T03. |
| Precondición | Cuenta autorizada en la prueba cerrada y dispositivo sin Lexi instalada antes del proceso original. |
| Pasos | 1. Acceder al programa de testers. 2. Abrir la ficha de Lexi en Google Play. 3. Instalar V1. 4. Abrir la app y mostrar su versión. 5. Acceder a una partida. |
| Resultado esperado | La instalación de V1 se completa desde Google Play; Lexi abre y permite acceder a la tarea principal. |
| Resultado observado | El expediente registra V1 instalada en D01.|
| Evidencia | [Manual de incorporación](Manual.pdf), antecedente parcial sin identificación de versión. |

## QA-02. Partida completa

| Campo | Registro |
| --- | --- |
| Dispositivo / API | D01 / Android 14 / API 34. |
| Versión | V2: 0.2.0, código 2; procedencia Google Play declarada por T03. |
| Fecha / tester | 7 de noviembre de 2026 |
| Precondición | Registrar los contadores de Progreso antes de la partida; acceder a un nivel disponible. |
| Pasos | 1. Seleccionar un modo y comenzar o continuar la práctica. 2. Mostrar la definición y el idioma solicitado. 3. Escribir una respuesta en las casillas y pulsar Comprobar. 4. Completar la partida por acierto o al agotar seis intentos válidos. 5. Abrir Ver resultado, mostrar solución, traducción y ejemplos, y esperar Resultado guardado. 6. Revisar el nuevo resultado en Progreso. |
| Resultado esperado | Las letras se evalúan en la fila enviada. La partida termina con el resultado correspondiente; la solución y los ejemplos son legibles. El historial incorpora una sola partida y los contadores cambian según victoria o derrota. |
| Resultado observado | Las letras se evalúan en la fila enviada. La partida termina con el resultado correspondiente; la solución y los ejemplos son legibles. El historial incorpora una sola partida y los contadores cambian según victoria o derrota.  |
| Evidencia | [Video de la partida: respuesta APPLE y acierto](evidencia/entrega-2/Evidencias%20QA/qa-02-partida-v2.mp4). |
| Estado / incidencia | Aprobado |

## QA-03. Entrada invalida

| Campo | Registro |
| --- | --- |
| Dispositivo / API | D01 / Android 14 / API 34. |
| Versión | V2: 0.2.0, código 2. |
| Fecha / tester | 7 de noviembre de 2026 |
| Precondición | Fila activa con al menos una casilla vacía; anotar el contador de intentos. |
| Pasos | 1. Escribir una respuesta incompleta. 2. Pulsar Comprobar. 3. Mostrar el mensaje, el borrador y el contador. 4. Completar la respuesta y comprobar que puede enviarse normalmente. |
| Resultado esperado | La respuesta incompleta muestra un error de longitud; conserva las letras y el número de intentos. Tras corregir la longitud, el envío válido se evalúa y consume un intento. |
| Resultado observado | La respuesta incompleta muestra un error de longitud; conserva las letras y el número de intentos. Tras corregir la longitud, el envío válido se evalúa y consume un intento. |
| Evidencia | [Video de entrada inválida: TRE incompleta y corrección a TREE](evidencia/entrega-2/Evidencias%20QA/qa-03-entrada-invalida-v2.mp4). |
| Estado / incidencia | Aprobado |

## QA-04. Actualizacion desde Google Play

| Campo | Registro |
| --- | --- |
| Dispositivo / API | D01 / Android 14 / API 34; sustituir por los datos reales si la evidencia corresponde a otro dispositivo. |
| Versión | V1: 0.1.0 (1) → V2: 0.2.0 (2). |
| Fecha / tester | 07/11/2026 |
| Precondición | V1 instalada desde Google Play, con la misma cuenta autorizada y datos locales conservados. |
| Pasos | 1. Mostrar V1 instalada y el estado de Progreso. 2. Abrir la ficha de Lexi en Google Play. 3. Pulsar Actualizar y esperar a que termine. 4. Abrir Lexi, mostrar V2 instalada y ejecutar la tarea principal. Mantener la instalación y sus datos durante todo el proceso. |
| Resultado esperado | Google Play actualiza el mismo paquete de código 1 a código 2 sin desinstalar; V2 abre y permite jugar. |
| Resultado observado | T03 confirma que actualmente usa V2 desde Google Play. Las capturas muestran la ficha con Actualizar y posteriormente Jugar; en ellas no aparecen los números de versión ni una comparación de los datos antes y después de actualizar. |
| Evidencia | [Captura 1: opción Actualizar en Google Play](evidencia/entrega-2/Evidencias%20QA/qa-04-actualizacion-play1.jpeg) y [captura 2: opción Jugar](evidencia/entrega-2/Evidencias%20QA/qa-04-actualizacion-play2.jpeg). [Historial de publicaciones](evidencia/entrega-2/play-historial-v1-v2.jpeg). |
| Estado / incidencia | Aprobado |

**Capturas de Google Play**

| Opción Actualizar | Opción Jugar |
| --- | --- |
| ![Lexi en Google Play con la opción Actualizar](evidencia/entrega-2/Evidencias%20QA/qa-04-actualizacion-play1.jpeg) | ![Lexi en Google Play con la opción Jugar](evidencia/entrega-2/Evidencias%20QA/qa-04-actualizacion-play2.jpeg) |

## QA-05. Correccion del selector

| Campo | Registro |
| --- | --- |
| Dispositivo / API | D01 / Android 14 / API 34. |
| Versión | V2: 0.2.0, código 2. |
| Fecha / tester | 7 de noviembre de 2026 |
| Trazabilidad | [Issue #11](https://github.com/NyaSanchez/lexi-android/issues/11), [PR #10](https://github.com/NyaSanchez/lexi-android/pull/10) y [captura del defecto original](evidencia/entrega-2/pr-01-selector-defecto.png). |
| Precondición | Fila activa de una palabra de al menos tres letras, con respuesta aún sin comprobar. |
| Pasos | 1. Escribir en las casillas. 2. Tocar una letra intermedia y sustituirla. 3. Tocar la última letra escrita y sustituirla. 4. Mostrar la primera casilla, el indicador de selección y el contador. 5. Completar la fila y pulsar Comprobar. 6. Repetir la edición en el otro modo de práctica. |
| Resultado esperado | Solo cambia la letra elegida. La selección se distingue en su casilla, sin tiradores flotantes que tapen la primera letra. Editar no consume intentos. Al comprobar, esa misma fila muestra las pistas de colores. |
| Resultado observado | Solo cambia la letra elegida. La selección se distingue en su casilla, sin tiradores flotantes que tapen la primera letra. Editar no consume intentos. Al comprobar, esa misma fila muestra las pistas de colores.  |
| Evidencia | [Video de edición en inglés: BOOK](evidencia/entrega-2/Evidencias%20QA/qa-05-selector-ingles-v2.mp4.mp4) y [video de edición en español: CASA](evidencia/entrega-2/Evidencias%20QA/qa-05-selector-espanol-v2.mp4.mp4). |
| Estado / incidencia | Aprobado |

## QA-06. Tutorial interactivo

| Campo | Registro |
| --- | --- |
| Dispositivo / API | D01 / Android 14 / API 34; registrar otro dispositivo si se necesita para la precondición. |
| Versión | V2: 0.2.0, código 2. |
| Fecha / tester | 7 de noviembre de 2026 |
| Trazabilidad | [Issue #12](https://github.com/NyaSanchez/lexi-android/issues/12) y [PR #14](https://github.com/NyaSanchez/lexi-android/pull/14). |
| Precondición | Racha de cero días para la aparición automática. Registrar también una ejecución con racha mayor que cero; no borrar datos para provocar la condición. |
| Pasos | 1. Abrir Lexi con racha 0. 2. Recorrer todas las páginas y observar los indicadores. 3. Finalizar con ¡Empezar a jugar! y acceder a una partida. 4. En la misma sesión, volver a Inicio. 5. En otra ejecución con racha 0, comprobar Omitir tutorial. 6. Con racha mayor que 0, comprobar que no se abre automáticamente. |
| Resultado esperado | El tutorial se muestra con racha 0, cambia de página e indicador y permite finalizar u omitir. No reaparece tras cerrarlo durante la misma sesión ni se abre automáticamente con racha mayor que 0. |
| Resultado observado | El tutorial se muestra al iniciar una sesion con racha de 0. |
| Evidencia | [Video del recorrido por las páginas del tutorial](evidencia/entrega-2/Evidencias%20QA/qa-06-tutorial-v2.mp4). |
| Estado / incidencia | Aprobada |

## QA-07. Niveles y vocabulario

| Campo | Registro |
| --- | --- |
| Dispositivo / API | D01 / Android 14 / API 34. |
| Versión | V2: 0.2.0, código 2. |
| Fecha / tester | [COMPLETAR: fecha real de ejecución], T03. |
| Trazabilidad | [Issue #4](https://github.com/NyaSanchez/lexi-android/issues/4), [PR #13](https://github.com/NyaSanchez/lexi-android/pull/13) y [reglas de niveles](NIVELES.md). |
| Precondición | Registrar el progreso existente de cada modo. Para comprobar los umbrales se necesita alcanzar los estados de 69 y 70 palabras distintas acertadas del nivel correspondiente. |
| Pasos | 1. Mostrar las tarjetas Básico, Intermedio y Avanzado y su progreso. 2. Seleccionar un nivel disponible y comprobar su identificación y contador de 100 palabras. 3. Mostrar un nivel bloqueado y el requisito pendiente. 4. Cambiar de modo y comprobar su progreso independiente. 5. Verificar el desbloqueo de Intermedio al pasar de 69 a 70 aciertos distintos en Básico. 6. Verificar Avanzado con 70 aciertos distintos en Intermedio y el requisito de Básico cumplido. |
| Resultado esperado | Hay tres niveles de 100 palabras, divididos en dos subniveles de 50. Básico está disponible; Intermedio y Avanzado respetan la regla del 70 %. La práctica usa el nivel elegido. Progreso y desbloqueos corresponden al modo seleccionado. |
| Resultado observado | Niveles visibles, progreso por modo. |
| Evidencia | [Captura de Básico, Intermedio y Avanzado: progreso y requisitos de desbloqueo](evidencia/entrega-2/Evidencias%20QA/qa-08-niveles-v2.jpeg). |
| Estado / incidencia | Pendiente de probar los demas niveles. |

**Captura de niveles y progreso**

![Nivel Básico con 1 de 100 palabras acertadas; Intermedio y Avanzado bloqueados con sus requisitos visibles](evidencia/entrega-2/Evidencias%20QA/qa-08-niveles-v2.jpeg)

## QA-08. Persistencia al reabrir

| Campo | Registro |
| --- | --- |
| Dispositivo / API | D01 / Android 14 / API 34. |
| Versión | V2: 0.2.0, código 2, antes y después de reabrir. |
| Fecha / tester | 8 de noviembre de 2026 |
| Precondición | Al menos una partida terminada con confirmación de Resultado guardado. |
| Pasos | 1. Mostrar Progreso, sus contadores y una entrada identificable del historial. 2. Cerrar Lexi desde las aplicaciones recientes. 3. Abrirla de nuevo. 4. Volver a Progreso y comparar antes de jugar otra partida. |
| Resultado esperado | Los contadores y las entradas guardadas permanecen iguales; la reapertura no añade resultados duplicados. |
| Resultado observado | Los contadores y las entradas guardadas permanecen iguales; la reapertura no añade resultados duplicados. |
| Evidencia | [Video de Progreso antes y después de reabrir Lexi](evidencia/entrega-2/Evidencias%20QA/qa-09-reapertura-v2.mp4). |
| Estado / incidencia | Aprobado |

