# Auditoría de vocabulario y accesibilidad de Lexi

**Seguimiento:** [Issue #5 — Vocabulary audit and UI accessibility](https://github.com/NyaSanchez/lexi-android/issues/5).  
**Responsable de integración:** Zyanya Sanchez.  
**Fecha de revisión técnica:** 16 de noviembre de 2026.  
**Versión de código auditada:** [55407ef](https://github.com/NyaSanchez/lexi-android/commit/55407ef0cfb49071f4456c8f8e1710d65557f26f).  
**Rama de documentación:** `fix/auditoria-vocabulario-accesibilidad`.  
**Estado:** informe técnico preparado; pendiente completar las pruebas manuales sobre la versión auditada y la revisión del equipo.

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
| H-01 | Alta | `GuessRowEditor.kt` elimina la semántica de la fila de casillas con `clearAndSetSemantics` y utiliza eventos de puntero para elegir una letra. | Verificar la edición individual con TalkBack y ofrecer un mecanismo accesible por casilla. |
| H-02 | Media | `GuessBoard.kt` permite un ancho mínimo de 36 dp por casilla; cada casilla recibe toques individuales. | Reservar objetivos táctiles de al menos 48 dp y conservar acceso a todas las letras en palabras largas. |
| H-03 | Alta | El tutorial se dibuja como una capa en Inicio, sin un diálogo o mecanismo explícito de aislamiento del foco accesible. | Comprobar si TalkBack alcanza controles situados detrás; aislar el foco si se confirma el problema. |
| H-04 | Media | El tutorial cambia de página por gesto horizontal; el indicador usa puntos decorativos, sin texto de página ni botones de avance o retroceso. | Evaluar alternativas de navegación y un indicador anunciable para lectores de pantalla. |
| H-05 | Media | Equipo no tiene desplazamiento vertical; los controles generales del tutorial incluyen alturas y espacios fijos. El contenido de cada página del tutorial sí admite desplazamiento. | Probar pantalla pequeña, fuente grande y orientación horizontal; adaptar los elementos que se recorten. |
| H-06 | Media | Nombre y subnivel comparten una fila rígida en las tarjetas de Inicio. | Revisar saltos de línea y lectura con fuente grande. |
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

**Configuración por registrar:** modelo, Android/API, ancho disponible, escala de fuente, versión de TalkBack, fecha y commit de la compilación. El dispositivo previsto es el Xiaomi de Zyanya Sanchez con instalación de desarrollo. No se deben borrar sus datos para ejecutar estas pruebas.

| ID | Caso | Criterio de revisión | Estado y evidencia |
| --- | --- | --- | --- |
| A-01 | Recorrer el tutorial con TalkBack, cambiar de página por los mecanismos disponibles y girar. | Describir qué anuncia el lector, cómo se cambia de página y si el foco alcanza Inicio detrás del tutorial. | Pendiente sobre 55407ef. La prueba anterior corresponde a una compilación modificada; véase 5.1. |
| A-02 | Escribir una respuesta y tratar de sustituir una letra con TalkBack, en ambos idiomas. | Registrar controles disponibles, selección de letra, escritura y anuncio de pistas. Si no se puede editar, documentar el bloqueo. | Pendiente sobre 55407ef. Existe un fallo informado en una compilación modificada; véase 5.1. |
| A-03 | Aumentar la fuente y recorrer Inicio, tutorial, práctica, resultado, Repaso, Progreso y Equipo, en vertical y horizontal. | Registrar escala y tamaño de pantalla; identificar textos superpuestos, contenido recortado y controles que no puedan alcanzarse. | Pendiente. Adjuntar capturas de las condiciones revisadas. |
| A-04 | Comparar los temas claro y oscuro en una práctica con los tres tipos de pistas. | Revisar lectura de texto, símbolos y descripciones; identificar información que dependa solo del color. | Medición estática de 22 pares disponible; inspección en dispositivo pendiente. |
| A-05 | Probar una respuesta con tilde y una entrada incompleta o inválida. | Registrar distinción de tildes, mensaje anunciado y contador de intentos. | Cobertura de dominio existente; ejecución manual de esta auditoría pendiente. |
| A-06 | Abrir resultados de palabras elegidas y revisar definición, respuesta y ejemplos en ambos modos. | Comparar el contenido mostrado con el catálogo de la base y con los hallazgos editoriales. | Pendiente. Anotar IDs, frases observadas y evidencia. |

### 5.1. Observaciones previas en una compilación modificada

El 16/11/2026, Zyanya Sanchez informó desde el Xiaomi que funcionaban Siguiente, Anterior y el giro del tutorial; TalkBack anunciaba páginas y botones y mantenía el foco dentro del tutorial. También informó que al intentar editar una letra se enfocaba la fila completa y no aparecía «Acciones» o «Editar casilla 2».

Estas observaciones proceden de una compilación de desarrollo con cambios experimentales, retirados del alcance de este PR. Se conservan para mantener trazabilidad, **pero no se atribuyen al commit 55407ef ni permiten aprobar sus casos manuales**. La configuración exacta y la evidencia audiovisual aún no se han incorporado. Restaurar los archivos del repositorio no reemplaza automáticamente la app instalada en el teléfono.

