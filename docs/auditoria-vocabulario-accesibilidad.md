# Auditoría de vocabulario y accesibilidad de Lexi

**Seguimiento:** [Issue #5 — Vocabulary audit and UI accessibility](https://github.com/NyaSanchez/lexi-android/issues/5).  
**Responsable de integración:** Zyanya Sanchez.  
**Fecha de revisión técnica:** 16 de noviembre de 2026.  
**Versión de código auditada:** [55407ef](https://github.com/NyaSanchez/lexi-android/commit/55407ef0cfb49071f4456c8f8e1710d65557f26f).  
**Rama de documentación:** `fix/auditoria-vocabulario-accesibilidad`.  
**Estado:** revisión técnica y resultados manuales documentados; pendiente revisión del equipo. La cobertura manual corresponde a las muestras descritas en cada caso y conserva explícitamente sus limitaciones.

## 1. Objetivo y alcance

Evaluar definiciones, ejemplos, tildes, contraste, tamaño de texto, compatibilidad con TalkBack y presentación en pantallas pequeñas, conforme al issue #5. El entregable registra hallazgos, recomendaciones, evidencias y límites de la revisión. **Este PR es exclusivamente documental: no incluye cambios de funcionamiento, interfaz, vocabulario ni pruebas de la aplicación.**

La revisión editorial comprende las 300 entradas del catálogo: 100 de Básico, 100 de Intermedio y 100 de Avanzado. La inspección del código abarca Inicio, tutorial, práctica, resultado, Repaso, Progreso y Equipo. La organización A1–C2 se mantiene como clasificación del proyecto; esta auditoría no certifica la asignación de cada palabra a un nivel del MCER.

Se distinguen revisión estática, pruebas automáticas y ejecución manual. Un hallazgo de código señala una condición verificable o un riesgo; no acredita por sí solo que un fallo visual haya sido reproducido en un dispositivo.

## 2. Vocabulario

| Aspecto | Resultado |
| --- | --- |
| Cobertura editorial | Se revisaron definiciones y ejemplos de las 300 entradas en español e inglés. |
| Integridad estructural | Sin IDs o palabras duplicadas, campos vacíos, espacios exteriores ni texto fuera de NFC en el catálogo inspeccionado. |
| Tildes y ñ | Las respuestas conservan estos caracteres; existen pruebas de dominio para su tratamiento. No se alteraron las respuestas. |
| Definiciones y ejemplos | Las pruebas existentes verifican que la definición no incluya su respuesta como palabra completa y que el ejemplo sí contenga la palabra practicada. |
| Correspondencia bilingüe | Algunos ejemplos cambian sujeto, contexto o acciones respecto de su traducción; se recomienda revisar esos pares. |
| Precisión | Se detectaron formulaciones restrictivas o generalizaciones, por ejemplo hilo limitado al algodón y marea con una frecuencia universal. |

El [registro de hallazgos editoriales](evidencia/auditoria-5/hallazgos-vocabulario.md) contiene **41 propuestas sobre 33 entradas**, con texto original, redacción sugerida y motivo. Las propuestas deben evaluarse en una tarea posterior. No se modificaron IDs, respuestas, ejemplos, niveles, orden del catálogo ni esquema de datos.

Las observaciones sobre tiburones y mareas se contrastaron con NOAA: [características de los tiburones](https://www.fisheries.noaa.gov/feature-story/12-shark-facts-may-surprise-you) y [variación de las mareas](https://oceanservice.noaa.gov/facts/high-tide.html). Las propuestas son redacción editorial, no citas textuales.

## 3. Hallazgos de accesibilidad

Los identificadores H-01…H-08 son referencias internas del informe, no números de issues creados en GitHub. Las prioridades propuestas orientan el seguimiento del equipo.

| ID | Prioridad propuesta | Evidencia en la base auditada | Recomendación, no aplicada |
| --- | --- | --- | --- |
| H-01 | Alta | **Fallo reportado en A-02 por Zyanya Sanchez:** TalkBack selecciona toda la fila y no permite editar una casilla individual. La inspección de `GuessRowEditor.kt` identifica `clearAndSetSemantics` en la fila y eventos de puntero para elegir letras. Evidencia: declaración escrita de la ejecutora; sin grabación adicional. | Ofrecer un mecanismo accesible para seleccionar y editar una letra individual. Corrección pendiente para una tarea posterior. |
| H-02 | Media | `GuessBoard.kt` permite un ancho mínimo de 36 dp por casilla; cada casilla recibe toques individuales. | Reservar objetivos táctiles de al menos 48 dp y conservar acceso a todas las letras en palabras largas. |
| H-03 | Alta | **Reproducido en A-01.** Con el tutorial visible, TalkBack alcanza controles de Inicio situados detrás. Zyanya Sanchez reporta el menú inferior, los modos de definición y el aviso de actualización; el video muestra el foco sobre este último aproximadamente en 00:02. [Evidencia](evidencia/auditoria-5/tutorial-talkback-original.mp4). | Aislar el foco de accesibilidad dentro del tutorial mientras esté abierto y devolverlo a Inicio al cerrarlo. Corrección pendiente para una tarea posterior. |
| H-04 | Media | El tutorial cambia de página por gesto horizontal; el indicador usa puntos decorativos, sin texto de página ni botones de avance o retroceso. | Evaluar alternativas de navegación y un indicador anunciable para lectores de pantalla. |
| H-05 | Media (riesgo) | Equipo no tiene desplazamiento vertical; los controles generales del tutorial incluyen alturas y espacios fijos. El contenido de cada página del tutorial sí admite desplazamiento. En A-03, Zyanya Sanchez no reportó problemas en Equipo con letra al máximo y ambas orientaciones en su Xiaomi. | No se reprodujo un recorte en Equipo bajo la configuración informada. Revisar el tutorial y otros tamaños antes de concluir que existe un fallo de presentación; proponer una corrección solo si se confirma. |
| H-06 | Media (riesgo) | Nombre y subnivel comparten una fila rígida en las tarjetas de Inicio. En A-03, Zyanya Sanchez informó funcionamiento normal de Inicio con letra al máximo, en vertical y horizontal. | No se reprodujo un problema en la configuración informada. Mantener la observación como riesgo de adaptación a otros tamaños, sin presentarla como defecto visual confirmado. |
| H-07 | Media | El texto de racha utiliza «días» también para el valor 1. | Emplear concordancia de singular/plural en una corrección posterior. |
| H-08 | Media | Los temas declaran `android:windowLightNavigationBar`, de API 27, en recursos generales, con minSdk 26. | Revisar recursos específicos por versión. Conservar el fallo de Lint como hallazgo; no ocultarlo ni corregirlo en este PR. |

Como aspectos favorables, el tablero evaluado incluye colores, símbolos y descripciones por letra; los errores de entrada tienen anuncio semántico; varios encabezados están identificados y las pantallas principales permiten desplazamiento. Su experiencia real debe verificarse con TalkBack y la configuración del dispositivo utilizada para la entrega.

### 3.1. Contraste

Se calcularon **22 combinaciones explícitas** de los temas claro y oscuro y las pistas del tablero. Todas superan el umbral aplicado: 4.5:1 para texto y símbolos y 3:1 para el borde activo. El [archivo de mediciones](evidencia/auditoria-5/contraste.csv) contiene colores, razones y criterios. Los colores medidos coinciden con la base auditada.

Método: convertir sRGB a valores lineales, calcular `L = 0.2126 R + 0.7152 G + 0.0722 B` y aplicar `(L_mayor + 0.05) / (L_menor + 0.05)`. La comparación utiliza los valores sin redondear; el CSV presenta dos decimales. No se midieron colores heredados de Material, transparencias, estados deshabilitados ni elementos del sistema. La medición tampoco determina si el texto queda recortado.

Los criterios proceden de las [recomendaciones de accesibilidad de Compose](https://developer.android.com/develop/ui/compose/accessibility/api-defaults) y la [guía de accesibilidad de Android](https://developer.android.com/guide/topics/ui/accessibility/apps).

## 4. Comprobaciones automáticas

El [registro técnico](evidencia/auditoria-5/validacion-automatica.md) identifica la versión comprobada, los resultados y sus limitaciones. Se ejecutan las pruebas existentes sin modificar su contenido ni agregar pruebas al proyecto.

Comando de referencia desde la raíz del proyecto en PowerShell:

```powershell
.\gradlew.bat :app:testDebugUnitTest :app:assembleDebug :app:assembleDebugAndroidTest :app:lintDebug
```

Una auditoría conserva y explica los resultados fallidos de las herramientas. No requiere modificar la aplicación para obtener un informe sin errores.

## 5. Validación manual de la base auditada

**Configuración informada por Zyanya Sanchez:** Xiaomi **22021211RG**, **Android 14 (API 34)**, compilación del sistema **UKQ1.231207.002**, **TalkBack 17.1** e instalación de desarrollo de Lexi. Datos recibidos el 16 de noviembre de 2026. El identificador UKQ1.231207.002 corresponde al sistema Android, no a la versión de Lexi.  
**Por registrar en cada prueba:** ancho disponible, escala de fuente, fecha de ejecución y commit de la compilación probada. No se deben borrar los datos del dispositivo para ejecutar estas pruebas.

**Método de registro manual:** se aceptan los resultados escritos que comunica la persona ejecutora. Cada fila identifica su fuente: observación reportada, análisis técnico o archivo audiovisual disponible. No se exige una grabación por caso; se conserva el video de A-01 como evidencia complementaria. Un reporte escrito no se presenta como una observación audiovisual independiente.

| ID | Caso | Criterio de revisión | Estado y evidencia |
| --- | --- | --- | --- |
| A-01 | Recorrer el tutorial con TalkBack, cambiar de página por los mecanismos disponibles y girar. | Describir qué anuncia el lector, cómo se cambia de página y si el foco alcanza Inicio detrás del tutorial. | **No cumple el aislamiento del foco (H-03).** Zyanya Sanchez reporta acceso al menú inferior, los modos de definición y el aviso de actualización detrás del tutorial. [Video](evidencia/auditoria-5/tutorial-talkback-original.mp4). Registro: 16/11/2026, Xiaomi, instalación de desarrollo. Se observan cambios de página; el recorrido completo de las seis páginas y el giro no quedan acreditados en esta grabación. Detalle en 5.1. |
| A-02 | Escribir una respuesta y tratar de sustituir una letra con TalkBack, en ambos idiomas. | Registrar controles disponibles, selección de letra, escritura y anuncio de pistas. Si no se puede editar, documentar el bloqueo. | **Fallo de edición individual reportado (H-01).** Zyanya Sanchez comunica el 16/11/2026 que se selecciona toda la fila y no se pueden editar las casillas una por una. Evidencia: reporte escrito de la ejecutora, sin video adicional. El resultado no se desglosó por idioma; no se presume una ejecución independiente en ambos modos. |
| A-03 | Aumentar la fuente y recorrer Inicio, tutorial, práctica, resultado, Repaso, Progreso y Equipo, en vertical y horizontal. | Registrar escala y tamaño de pantalla; identificar textos superpuestos, contenido recortado y controles que no puedan alcanzarse. | **Satisfactorio en el alcance probado, según la ejecutora.** Zyanya Sanchez informa el 16/11/2026 que Inicio, Equipo y la pantalla de práctica funcionan con normalidad con el tamaño de letra al máximo, en vertical y horizontal. No reporta recortes, superposiciones ni controles inaccesibles. No se registró el factor numérico de escala; este resultado no acredita las otras pantallas del caso ni otros tamaños de dispositivo. Evidencia: reporte escrito, sin grabación adicional. |
| A-04 | Comparar los temas claro y oscuro en una práctica con los tres tipos de pistas. | Revisar lectura de texto, símbolos y descripciones; identificar información que dependa solo del color. | **Satisfactorio en el alcance informado.** Zyanya Sanchez comunica el 16/11/2026 que en Inicio y la pantalla de práctica se distinguen correctamente los textos y símbolos en los temas claro y oscuro, con el tamaño de letra habitual. No reporta dificultades para leer la definición, las letras, los botones ni la leyenda de pistas. Evidencia: reporte escrito de la ejecutora y medición estática de 22 pares. Esta respuesta no acredita una prueba adicional de lectura de las pistas con TalkBack ni resultados en otras pantallas. |
| A-05 | Probar una respuesta con tilde y una entrada incompleta o inválida. | Registrar distinción de tildes, mensaje anunciado y contador de intentos. | **Satisfactorio para validación de entrada y distinción de tilde, según Zyanya Sanchez (16/11/2026).** Una respuesta de una sola letra y una respuesta numérica de la longitud solicitada fueron rechazadas sin consumir intentos. En English definition, entrada `bird`, `PAJARO` fue incorrecta y consumió un intento; `PÁJARO` acertó. Evidencia: reporte escrito de la ejecutora. No se verificó la lectura de los mensajes mediante TalkBack en este caso. |
| A-06 | Abrir resultados de palabras elegidas y revisar definición, respuesta y ejemplos en ambos modos. | Comparar el contenido mostrado con el catálogo de la base y con los hallazgos editoriales. | **Satisfactorio para la muestra `bird` en English definition, según Zyanya Sanchez (16/11/2026).** En Ver resultado se mostró `PÁJARO · bird`, primero «Español: El pájaro canta por la mañana.» y después «Inglés: The bird sings in the morning.», de acuerdo con el catálogo auditado. Evidencia: reporte escrito de la ejecutora y comparación con `BasicVocabulary.kt`. No se presume cobertura manual de otras palabras ni del modo inverso. |

**Alcance de las conclusiones:** los resultados se limitan al dispositivo informado y a los recorridos descritos. Los fallos H-01 y H-03 requieren seguimiento; los resultados satisfactorios de otras pruebas no los resuelven. No se acredita una comprobación manual completa de todas las pantallas, tamaños, idiomas o configuraciones de TalkBack. El informe permite revisar los hallazgos y decidir su seguimiento sin modificar la aplicación.

### 5.1. Evidencia A-01: foco fuera del tutorial

- **Persona ejecutora:** Zyanya Sanchez.
- **Fecha de registro:** 16 de noviembre de 2026.
- **Versión de referencia:** código de la base `55407ef`; el commit documental [6df365c](https://github.com/NyaSanchez/lexi-android/commit/6df365c49353eaca65b364381ecc31f0ffc78d92) conserva ese código sin cambios de aplicación.
- **Entorno informado:** Xiaomi 22021211RG, Android 14 (API 34), compilación del sistema UKQ1.231207.002, TalkBack 17.1 e instalación de desarrollo. La grabación muestra orientación vertical y tema oscuro. Ancho disponible en dp y escala de fuente pendientes de registrar.
- **Pasos reproducidos:** abrir Lexi con el tutorial visible y recorrer elementos mediante TalkBack sin cerrar el tutorial.
- **Resultado esperado:** el foco accesible permanece en el contenido y controles del tutorial hasta su cierre.
- **Resultado observado:** el foco alcanza controles de la pantalla Inicio situada detrás. Zyanya Sanchez identifica el menú inferior, los modos de definición y el aviso de actualización.
- **Comprobación visual:** aproximadamente en `00:02`, el marco verde de foco rodea el icono del aviso de actualización mientras sigue visible la página «Zona De Repaso». Esto respalda H-03. No se afirma que todos los controles reportados se hayan identificado de forma independiente en los fotogramas revisados.
- **Archivo original:** [tutorial-talkback-original.mp4](evidencia/auditoria-5/tutorial-talkback-original.mp4), duración 21.71 s, tamaño 7,555,000 bytes y video 1080 × 2400 px. Contiene una pista de audio; la comprobación técnica de su presencia no equivale a una transcripción de los anuncios.
- **Conclusión:** defecto de aislamiento del foco reproducido. La auditoría lo documenta; la corrección permanece pendiente. La grabación no acredita una prueba de giro ni el recorrido completo de las seis páginas.

### 5.2. Observaciones previas en una compilación modificada

El 16/11/2026, Zyanya Sanchez informó desde el Xiaomi que funcionaban Siguiente, Anterior y el giro del tutorial; TalkBack anunciaba páginas y botones y mantenía el foco dentro del tutorial. También informó que al intentar editar una letra se enfocaba la fila completa y no aparecía «Acciones» o «Editar casilla 2».

Estas observaciones proceden de una compilación de desarrollo con cambios experimentales, retirados del alcance de este PR. Se conservan para mantener trazabilidad, **pero no se atribuyen al commit 55407ef ni permiten aprobar sus casos manuales**. La configuración exacta y la evidencia audiovisual aún no se han incorporado. Restaurar los archivos del repositorio no reemplaza automáticamente la app instalada en el teléfono.

