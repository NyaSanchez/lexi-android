# Evidencias de la Entrega 2

Inventario del **8 de noviembre de 2026**. Se incluyen capturas de GitHub y Play Console y grabaciones del dispositivo proporcionadas por el equipo. La descripción identifica lo visible en cada archivo y conserva la diferencia entre publicación, ejecución y cobertura pendiente.

## Gestión del proyecto y revisión

| Archivo | Referencia |
| --- | --- |
| [Tablero Scrum](scrum-tablero.png) | Vista del tablero del proyecto. El historial de cambios de estado debe consultarse en las tarjetas e issues. |
| [Revisión y merge del PR #10](pr-01-revision-merge.png) | Entrada en casillas y corrección del selector. |
| [Revisión y merge del PR #13](pr-02-revision-merge.png) | Niveles y ampliación del vocabulario. |
| [Revisión y merge del PR #14](pr-03-revision-merge.png) | Tutorial interactivo. |

## Antecedentes de los PRs

| Archivo | Procedencia | Contenido y alcance |
| --- | --- | --- |
| [Defecto del selector](pr-01-selector-defecto.png) | [Issue #11, Zyanya Sanchez](https://github.com/NyaSanchez/lexi-android/issues/11) | Tiradores sobre la primera casilla con otra casilla seleccionada. La imagen no identifica la versión instalada. |
| [Selector corregido](pr-01-selector-corregido.png) | [QA de Angel Orozco en #10](https://github.com/NyaSanchez/lexi-android/pull/10#pullrequestreview-5815640228) | PENCIL, C resaltada y tablero legible. La revisión corresponde a un APK de depuración local en Oppo A58. |
| [Niveles](pr-02-niveles-qa.png) | [PR #13, Angel Tellez](https://github.com/NyaSanchez/lexi-android/pull/13) | Básico 17/100 y niveles posteriores bloqueados. |
| [Tutorial](pr-03-tutorial-qa.png) | [PR #14, Angel Orozco](https://github.com/NyaSanchez/lexi-android/pull/14) | Página del tutorial, indicador y botón Omitir. |
| [Manual de incorporación](../../Manual.pdf) | Manual del equipo incorporado mediante [PR #9](https://github.com/NyaSanchez/lexi-android/pull/9) | Acceso al programa de testers y ficha con Lexi instalada; no identifica el número de versión ni muestra una partida. |

Originales publicados en GitHub: [defecto](https://github.com/user-attachments/assets/1fc025a3-846e-4edf-99e0-f9143d0a26ca), [corrección](https://github.com/user-attachments/assets/460e1963-fcb4-42f5-9251-939d759b7f6b), [niveles](https://github.com/user-attachments/assets/dff274fd-b05a-480d-8254-93d04d3e44a0) y [tutorial](https://github.com/user-attachments/assets/7936174d-e608-4382-9119-2c3bc817588b).

## Publicaciones en Play Console

| Archivo | Contenido visible |
| --- | --- |
| [Primera versión](play-cerrada-v1.jpeg) | 0.1.0, código 1, fecha de lanzamiento y fecha de reemplazo. |
| [Historial de v1 y v2](play-historial-v1-v2.jpeg) | Mismo canal cerrado; versiones 0.1.0 (1) y 0.2.0 (2), con sus fechas. |
| [Estado de v2](play-actualizacion-v2.jpeg) | 0.2.0 disponible para verificadores específicos en el canal cerrado. |
| [Paquete de v2](play-notas-v2.jpeg) | Datos del app bundle 2 (0.2.0). Aunque su nombre contiene «notas», esta captura no muestra las notas de versión publicadas. |

## Pruebas en el dispositivo

La numeración de esta tabla corresponde a las secciones actuales de [pruebas.md](../../pruebas.md). Se conservan los nombres originales de los archivos, incluso cuando su número difiere del caso o contienen la extensión `.mp4` repetida.

| Caso | Archivo | Contenido visible |
| --- | --- | --- |
| QA-02 | [Partida](Evidencias%20QA/qa-02-partida-v2.mp4) | Respuesta APPLE, fila verde y acierto. |
| QA-03 | [Entrada inválida](Evidencias%20QA/qa-03-entrada-invalida-v2.mp4) | TRE incompleta, error sin consumir intento y corrección a TREE. |
| QA-04 | [Antes](Evidencias%20QA/qa-04-actualizacion-play1.jpeg) y [después](Evidencias%20QA/qa-04-actualizacion-play2.jpeg) | Ficha de Google Play con Actualizar y posteriormente Jugar. Los números de versión no son visibles en estas dos capturas. |
| QA-05 | [Edición en inglés](Evidencias%20QA/qa-05-selector-ingles-v2.mp4.mp4) y [edición en español](Evidencias%20QA/qa-05-selector-espanol-v2.mp4.mp4) | Sustitución de letras para formar BOOK y CASA sin tiradores sobre la primera casilla, seguida del acierto. |
| QA-06 | [Tutorial](Evidencias%20QA/qa-06-tutorial-v2.mp4) | Recorrido por las páginas e indicadores del tutorial. |
| QA-07 | [Niveles](Evidencias%20QA/qa-08-niveles-v2.jpeg) | Básico con 1/100, Intermedio con 1/70 para desbloquear y Avanzado bloqueado. Los umbrales de desbloqueo siguen pendientes de prueba. |
| QA-08 | [Reapertura](Evidencias%20QA/qa-09-reapertura-v2.mp4) | Antes y después de reabrir: 5 partidas, 5 aciertos, 0 sin acierto y 4 palabras distintas; la entrada CASA permanece en el historial. |

## Evidencia por completar

- Historial del sprint con los movimientos reales de las tarjetas.
- Instalación inicial de V1 identificable desde Google Play y observación de la prueba inicial.
- Notas de versión publicadas de 0.2.0 en Play Console.
- Versión instalada antes y después de actualizar y comparación de los datos conservados durante ese proceso. El video de reapertura acredita persistencia dentro de V2, sin cambio de versión.
- Registro del ajuste aplicado al generar el AAB 0.2.0 con código 2 desde el commit [9a73231](https://github.com/NyaSanchez/lexi-android/commit/9a73231c19a2fa2b6a4a3627a741a85ea3718ad1), identificado por Zyanya Sanchez; la configuración de ese commit declara código 1.
- Identificación del modelo técnico del dispositivo y resultados de las comprobaciones de niveles aún pendientes.

Los archivos se enlazan desde [el expediente](../../entrega-2.md) y [la matriz de QA](../../pruebas.md). Las evidencias deben conservar las versiones, fechas y resultados relevantes sin divulgar credenciales, claves de firma ni correos de testers.
