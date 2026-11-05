# Lexi: guía de incorporación y contexto del equipo

Actualizada el **31 de octubre de 2026**. Estado funcional de referencia: commit **11f5416**, `feat: guardar progreso local de práctica`.

**Actualización del 4 de noviembre de 2026:** `main` en `e11abf9` incorpora racha/meta diaria, 100 palabras y continuación de la sesión, además de documentación de privacidad y pruebas manuales. La rama `feat/respuesta-en-cuadros` añade la [entrada directamente en el tablero](ENTRADA_EN_TABLERO.md). Las tablas anteriores a esta actualización conservan contexto histórico; consultar el README y esa documentación para el estado de esta rama.

Repositorio compartido: [NyaSanchez/lexi-android](https://github.com/NyaSanchez/lexi-android). Rama principal: `main`. Esta guía es el punto de entrada para continuar sin necesitar el historial de conversaciones con el asistente.

## 1. Qué estamos construyendo

Lexi es una app nativa de Android para practicar vocabulario y escritura en español e inglés. Muestra una definición; la persona escribe la palabra en el otro idioma y recibe pistas por letra. El objetivo final es publicarla en Google Play. **Todavía está en desarrollo y no está preparada para publicación.**

El diseño original procede de «Idea de App.pdf». Sus reglas se transcribieron en [REQUISITOS.md](REQUISITOS.md), que también distingue decisiones adoptadas y preguntas pendientes. El PDF no está versionado en el repositorio: para consultar sus maquetas, pedirlo a la responsable del proyecto.

El profesor necesita ver avances reales en el historial. Cada integrante debe usar su cuenta, registrar cambios coherentes mediante commits y enviarlos a GitHub durante el desarrollo.

## 2. Incorporar a los dos colaboradores

Estos pasos los realiza la propietaria, NyaSanchez:

1. Pedir a cada integrante su nombre de usuario de GitHub.
2. Abrir el repositorio y entrar en **Settings → Collaborators**, dentro de **Access**.
3. Pulsar **Add people**, seleccionar la cuenta correcta y confirmar la invitación.
4. Repetir con el segundo integrante. Cada persona debe aceptar su invitación para obtener acceso.
5. Comprobar que ambos pueden abrir y clonar el repositorio con sus propias cuentas.

No es necesario convertir el repositorio en público. En un repositorio personal, los colaboradores pueden leer y enviar cambios; la propietaria conserva la administración. Consultar las [instrucciones oficiales de invitación](https://docs.github.com/en/repositories/managing-your-repositorys-settings-and-features/repository-access-and-collaboration/inviting-collaborators-to-a-personal-repository) y los [permisos de colaboradores](https://docs.github.com/en/repositories/managing-your-repositorys-settings-and-features/repository-access-and-collaboration/permission-levels-for-a-personal-account-repository).

## 3. Primera instalación en la computadora de cada integrante

### Clonar con GitHub Desktop

1. Instalar GitHub Desktop y Android Studio. Iniciar sesión en Desktop con la cuenta que aceptó la invitación.
2. En Desktop: **File → Clone repository → URL**.
3. Pegar `https://github.com/NyaSanchez/lexi-android` y elegir una carpeta propia, por ejemplo `C:\Proyectos\lexi-android`. Es preferible una carpeta que no sincronice OneDrive.
4. Pulsar **Clone** y seleccionar `main` en **Current Branch**.
5. Revisar **History**: debe contener `727afe3` y cualquier avance integrado después. Configurar el nombre y correo de autor propios en las opciones de Git de Desktop.

Cada persona trabaja en su copia local; GitHub intercambia los cambios. No hace falta crear otro repositorio ni compartir una misma carpeta de Windows. [Guía oficial de clonación](https://docs.github.com/en/desktop/adding-and-cloning-repositories/cloning-and-forking-repositories-from-github-desktop).

### Abrir y ejecutar en Android Studio

1. Seleccionar **Open** y abrir la raíz `lexi-android`, donde está `settings.gradle.kts`.
2. Dejar que Android Studio configure la ruta de su SDK en `local.properties`. Ese archivo es local y está excluido de Git.
3. Instalar Android SDK Platform **36** y Platform-Tools desde SDK Manager. Aceptar las licencias que solicite el IDE.
4. Esperar a que termine **Gradle Sync**. La primera vez se necesita Internet para descargar dependencias.
5. Elegir un emulador Android API 26 o superior, o un teléfono con depuración USB autorizada.
6. Seleccionar la configuración **app** y pulsar **Run**. Si aparece **Sync Now** tras descargar cambios de Gradle, sincronizar antes de ejecutar.

Usar el Gradle Wrapper incluido, sin instalar otra versión de Gradle por separado. La configuración versionada `gradle/gradle-daemon-jvm.properties` solicita **Java 25** para el daemon. La máquina de referencia compila con el JBR 25.0.2 de Android Studio. Si el IDE no encuentra ese JDK o no puede descargarlo, configurar uno compatible con ese criterio; no modificar las versiones compartidas para resolver una ruta local. El código de la app genera bytecode **JVM 17**: es un ajuste diferente del JDK que ejecuta Gradle.

| Configuración verificada en el proyecto | Valor |
| --- | --- |
| Kotlin / Android Gradle Plugin | 2.4.20 / 9.3.3 |
| Gradle Wrapper / Compose BOM | 9.5.1 / 2026.06.01 |
| Navigation Compose | 2.9.8 |
| Room / KSP / Lifecycle | 2.8.5 / 2.3.10 / 2.10.0 |
| Android mínimo / compileSdk / targetSdk | 26 / 36 / 36 |
| Paquete e identificador de aplicación | `io.github.nyasanchez.lexi` |
| Versión de desarrollo | `0.1.0`, código 1 |

Las versiones están en `gradle/libs.versions.toml`, los archivos Gradle y el Wrapper. Mantenerlas mientras se incorpora el equipo; una actualización de herramientas debe ser un cambio acordado y comprobado.

## 4. Qué funciona hoy

| Área | Implementado | Límite actual |
| --- | --- | --- |
| Inicio | Tema claro/oscuro, selección de modo y navegación | No calcula racha ni meta diaria |
| Vocabulario | 30 pares con definiciones y ejemplos bilingües | Un único nivel inicial |
| Partida | Tablero de seis intentos, teclado y pistas por letra | Las palabras siguen el orden del catálogo |
| Resultado | Victoria/derrota, traducción, ejemplos e intentos desplegables | Sin pronunciación |
| Estado de pantalla | Conserva borrador e intentos al girar | No se garantiza continuar una partida tras cerrarla explícitamente |
| Guardado | Resultados terminados en Room, confirmación y reintento | Datos locales, sin sincronización entre dispositivos |
| Progreso | Totales, racha actual e historial de hasta 20 partidas recientes | Sin calendario mensual |
| Repaso | Pestaña y estado inicial | La práctica de repaso aún no está implementada |

La app puede practicar y guardar sin una cuenta de usuario ni un servicio remoto. El historial pertenece a cada instalación; no viaja con los commits de Git.

## 5. Reglas que hay que conservar

- **Definición en español → respuesta en inglés. Definition in English → respuesta en español.** Los dos modos comparten progreso.
- Hay seis intentos válidos por palabra. Una entrada vacía, de longitud incorrecta o con caracteres no admitidos muestra error sin consumir intento.
- Se aceptan mayúsculas y minúsculas y se recortan espacios exteriores. Se normalizan formas Unicode equivalentes. En español las tildes, la diéresis y la ñ sí distinguen respuestas: ARBOL no acierta ÁRBOL.
- No se consulta un diccionario para validar intentos. Una secuencia de letras admitidas con la longitud correcta consume intento aunque no sea una palabra real. Repetir una respuesta también consume intento.
- Primero se asignan las letras verdes; después, las amarillas usando las coincidencias restantes. Una letra repetida de más no obtiene otra coincidencia.
- Acertar en el sexto intento es victoria. Perder muestra la solución y los ejemplos; no convierte el resultado en acierto.
- Una sesión nueva desde Inicio empieza por HOUSE/CASA; después siguen APPLE/MANZANA y TREE/ÁRBOL. Tras las 30 entradas vuelve al principio.
- Solo se guardan partidas terminadas. Abandonar una partida no suma victoria ni derrota. Repetir una palabra en otra partida sí suma un nuevo resultado.
- Cada partida tiene un `sessionId` único; recrear la pantalla no debe insertar otro resultado. **Resultado guardado** aparece después de terminar la escritura.
- **Palabras distintas acertadas** cuenta conceptos por `entryId`: ganar HOUSE y CASA suma dos aciertos, pero una sola palabra distinta. Los totales incluyen todo el historial aunque solo se muestren 20 filas.

| Solución | Intento | Pistas esperadas |
| --- | --- | --- |
| HOUSE | SHARE | Amarillo, amarillo, rojo, rojo, verde |
| CASA | COSA | Verde, rojo, verde, verde |
| APPLE | PAPAL | Amarillo, amarillo, verde, rojo, amarillo |

La meta diaria se completa con el primer acierto del día; múltiples aciertos el mismo día no inflan la racha. El cálculo extrae fechas únicas de la base de datos (ignorando horas y partidas futuras anómalas) para hacer la racha resistente a la manipulación del reloj del dispositivo. Los huecos de inactividad de más de un día reinician el contador a cero.

## 6. Dónde está cada parte

Las rutas de esta tabla parten de `app/src/main/java/io/github/nyasanchez/lexi/`:

| Ruta | Responsabilidad |
| --- | --- |
| `MainActivity.kt` | Entrada de la actividad y tema |
| `ui/LexiApp.kt` | Navegación, selección de modo y conexión con el ViewModel |
| `ui/HomeScreen.kt`, `PracticeMode.kt` | Inicio y relación entre idiomas |
| `ui/PracticeScreen.kt` | Entrada, estado recuperable, motor y envío del resultado al guardado |
| `ui/GuessBoard.kt`, `PracticeResultScreen.kt` | Pistas del tablero y resultado con ejemplos |
| `domain/game/` | Validación, evaluación de letras, seis intentos y reconstrucción de una partida |
| `data/vocabulary/` | Modelos, catálogo y búsqueda por ID |
| `data/progress/` | Entidad Room, DAO, base de datos y repositorio |
| `ui/ProgressViewModel.kt`, `ProgressScreen.kt` | Carga, errores, guardado, contadores e historial |
| `ui/ReviewScreen.kt` | Punto de partida para implementar Repaso |
| `ui/theme/` | Tema visual |

Los textos de interfaz están en `app/src/main/res/values/strings.xml`. Las pruebas locales están en `app/src/test/`; las pruebas que necesitan Android están en `app/src/androidTest/`.

Flujo principal:

    Inicio → modo → ejercicio del catálogo → WordGame → resultado
      → ProgressViewModel → ProgressRepository → Room
      → consulta observada → pantalla Progreso

La base privada se llama `lexi-progress.db`, versión **1**, tabla `completed_games`. Guarda ID de sesión, ID de palabra, idioma, textos de solución/traducción, resultado, intentos e instante de finalización. También conserva `localDate` y `zoneId` originales para el futuro cálculo diario. Las fechas del historial se muestran en la zona actual del dispositivo.

El esquema exportado está en `app/schemas/io.github.nyasanchez.lexi.data.progress.ProgressDatabase/1.json`. Si se cambia la estructura, planificar versión y migración y probar datos existentes. No resolver una migración borrando el historial. No renombrar IDs del catálogo sin revisar su relación con los registros guardados.

## 7. Cómo comprobar una copia recién instalada

En la terminal de Android Studio, situada en la raíz del proyecto, ejecutar en Windows:

```powershell
.\gradlew.bat :app:assembleDebug :app:testDebugUnitTest :app:lintDebug
```

Para las pruebas de Android, con emulador o dispositivo preparado:

```powershell
.\gradlew.bat :app:connectedDebugAndroidTest
```

Las pruebas generales de interfaz realizan partidas y pueden añadir resultados al historial de la app de depuración. `ProgressDatabaseTest` utiliza archivos temporales propios. No confundir una compilación del APK de pruebas con una ejecución de esas pruebas.

### Verificación registrada en 727afe3

- App y APK de pruebas Android compilados.
- **54 pruebas unitarias** aprobadas mediante JUnitCore.
- Lint: **0 errores y 11 advertencias** sobre versiones de herramientas, dependencias y SDK objetivo.
- **8 pruebas instrumentadas** compiladas, sin ejecutarlas en el Xiaomi: la persona autora eligió comprobación manual tras la restricción de instalación del paquete de pruebas.
- En el Xiaomi: giro sin duplicados, persistencia tras detener/reabrir el proceso, victoria en ambos idiomas, derrota y abandono comprobados. La pantalla y SQLite coincidieron en 3 partidas, 2 aciertos, 1 derrota y 1 palabra distinta; integridad del archivo correcta.

El entorno restringido del asistente impidió a Gradle renombrar su informe de pruebas (`AccessDeniedException`); por eso se ejecutaron las pruebas con JUnitCore. No se afirmó que el comando global de Gradle hubiera terminado correctamente. En cada computadora del equipo se debe ejecutar el comando normal y registrar su resultado real. Los detalles están en [GUARDADO_PROGRESO.md](GUARDADO_PROGRESO.md).

### Recorrido manual breve

1. En «Definición en español», iniciar HOUSE. Enviar HO: error y cero intentos utilizados. Enviar SHARE: comprobar las cinco pistas de la tabla anterior.
2. Escribir HOU sin enviarlo y girar: deben conservarse el borrador y el intento. Enviar HOUSE: victoria, ejemplos bilingües y **Resultado guardado**.
3. Consultar Progreso y cerrar/reabrir Lexi. Ese resultado debe seguir presente una sola vez.
4. Desde Inicio, comenzar otra partida y enviar SHARE seis veces: debe sumar una derrota. En otra partida del modo inverso, acertar CASA: suma un acierto y mantiene el mismo concepto único casa/house.
5. Iniciar otra partida, enviar un intento válido incorrecto y abandonarla confirmando la salida: los contadores no cambian.

Comparar incrementos cuando ya haya historial. No borrar datos personales para conseguir contadores iniciales; se puede usar un emulador dedicado a pruebas.

## 8. Trabajo pendiente y orden sugerido

| Prioridad      | Trabajo | Qué decidir o comprobar antes de darlo por terminado |
|---| --- | --- |
| Completado     | Racha y meta diaria | Primer acierto del día; varios aciertos no suman días; derrotas no cuentan; definir huecos entre días, medianoche, zona horaria y cambios del reloj |
| Después        | Progreso diario/calendario | Definir qué indicadores se muestran y calcularlos con resultados reales |
| Después        | Repaso | Acordar qué palabras entran, orden, selección y efecto de sus partidas sobre progreso/meta |
| Por acordar    | Selección y niveles | Sustituir el recorrido fijo si se decide; definir niveles y criterios |
| Antes de publicar | Vocabulario y accesibilidad | Revisar definiciones, ejemplos, tildes, contraste, tamaños de texto, TalkBack y pantallas pequeñas |
| Por acordar    | Audio y Ajustes | Determinar alcance y comportamiento; están sugeridos en las maquetas |
| Por acordar    | Recuperar partida cerrada | Definir si se guarda una partida incompleta y cómo se reanuda sin duplicar resultados |
| Al final       | Distribución en Google Play | Pruebas de distribución, firma, paquete de publicación, ficha y requisitos vigentes según las funciones reales |

Propuesta de reparto para tres personas, **pendiente de acordarla**: la responsable continúa racha/meta; un integrante prepara Repaso después de definir sus reglas; otro revisa vocabulario y pruebas. Acordar un responsable por tarea y registrar archivos afectados antes de programar. No trabajar simultáneamente en el mismo bloque de navegación, guardado o Gradle sin coordinarse.

Una tarea debe indicar objetivo, responsable, archivos probables y cómo se demostrará que funciona. Puede anotarse como Issue en GitHub. Esta guía no crea ni asigna Issues automáticamente.

## 9. Rutina del equipo con GitHub Desktop

**Propuesta de trabajo:** usar una rama por tarea y revisar mediante pull request antes de integrar en `main`. Un pull request (PR) es una solicitud para revisar y unir esos cambios. Esta es una convención del equipo; no implica que existan protecciones configuradas en GitHub.

1. Con cambios previos guardados, seleccionar `main`; pulsar **Fetch origin** y **Pull origin** si hay novedades.
2. En **Current Branch → New Branch**, crear una rama desde `main`, por ejemplo `feat/racha-diaria`, `feat/repaso` o `docs/revision-vocabulario`.
3. Hacer un avance coherente, comprobarlo y revisar **Changes**. Escribir un Summary concreto; en Description, indicar el resultado y cómo se probó. Hacer **Commit to [rama]**.
4. La primera vez pulsar **Publish branch**; después, **Push origin**. Repetir con cada avance real, sin esperar a terminar toda la función.
5. Cuando la tarea esté lista, usar **Preview Pull Request → Create Pull Request**, con destino `main`. Explicar comportamiento, pruebas y pendientes, y pedir revisión a otro integrante.
6. Tras revisar y probar, integrar con **Create a merge commit / Merge pull request**, si está habilitado. Así se conservan los commits individuales que acreditan el avance. Evitar **Squash and merge** para esta entrega académica, porque los combina en uno.
7. Después de integrar, todos actualizan su `main` con Fetch/Pull antes de iniciar otra tarea. Una rama que siga abierta también debe incorporar los cambios de `main` antes de su integración.

Las ramas separan el trabajo, pero no evitan todos los conflictos. Si Desktop indica un conflicto, revisar con el autor del otro cambio qué debe conservarse, resolver los archivos y volver a probar antes de completar la integración. No usar **Discard changes** o un push forzado como solución automática.

Para que cada contribución quede atribuida correctamente, cada persona usa su cuenta y su identidad de Git. Conservar el historial ya compartido. En commits y PR se pueden incluir pruebas y documentación de la misma función; no crear commits vacíos para aparentar avance.

Versionar código, recursos, documentación, Gradle Wrapper, catálogo de versiones y esquemas de Room. Mantener fuera `local.properties`, `.idea`, cachés, compilaciones, APK/AAB, bases de datos del teléfono y claves de firma. Revisar `.gitignore` y los archivos seleccionados antes de cada commit.

Procedimientos oficiales: [ramas en Desktop](https://docs.github.com/en/desktop/making-changes-in-a-branch/managing-branches-in-github-desktop), [crear un PR](https://docs.github.com/en/desktop/working-with-your-remote-repository-on-github-or-github-enterprise/creating-an-issue-or-pull-request-from-github-desktop) y [métodos de integración](https://docs.github.com/en/repositories/configuring-branches-and-merges-in-your-repository/configuring-pull-request-merges/about-merge-methods-on-github).

## 10. Problemas habituales al incorporarse

| Síntoma | Primer paso |
| --- | --- |
| No aparece el repositorio o no se puede clonar | Confirmar cuenta e invitación aceptada; probar la URL en el navegador con esa cuenta |
| No aparece Settings | La invitación la debe gestionar la propietaria desde su cuenta |
| SDK no encontrado | Configurar el SDK propio en Android Studio; no copiar `local.properties` de otro integrante |
| Error de Java/Gradle al sincronizar | Revisar el JDK 25 solicitado por el archivo del daemon y la descarga de dependencias |
| El teléfono no aparece | Revisar conexión de datos USB, depuración USB y autorización del teléfono |
| Android rechaza una instalación | Leer el error y revisar la autorización de instalación; usar un emulador o pruebas manuales si el dispositivo restringe pruebas instrumentadas |
| El historial del compañero no aparece | Es local a su instalación; clonar Git solo descarga código y documentación |
| Push rechazado | Comprobar acceso y novedades remotas; Fetch y revisar los cambios pendientes antes de integrarlos |

Al reportar un fallo, incluir rama/commit, pasos para reproducir, resultado esperado, resultado real y mensaje de error. Si se usa otro teléfono, anotar modelo y versión de Android.

## 11. Historial y lecturas para continuar

| Commit | Avance funcional o documental |
| --- | --- |
| `8f5b717` | Alcance y plan de desarrollo |
| `11cbd2f` | Proyecto Android Kotlin/Compose |
| `7c9d426` | Inicio y navegación |
| `f0935e9` | Catálogo bilingüe |
| `296e51a` | Motor y letras repetidas |
| `d8cf126` | Partida interactiva |
| `e24e142` | Resultado y ejemplos de uso |
| `727afe3` | Persistencia y Progreso real |

Leer primero esta guía y [README](../README.md). Consultar después [REQUISITOS](REQUISITOS.md) y la documentación del área que se vaya a modificar: [vocabulario](VOCABULARIO_INICIAL.md), [motor](MOTOR_PARTIDA.md), [partida](PARTIDA.md), [resultado](RESULTADO_APRENDIZAJE.md) o [guardado](GUARDADO_PROGRESO.md). La [guía de commits](COMMITS.md) conserva el plan académico.

Al terminar cada tarea, actualizar el estado y los pendientes de esta guía si cambiaron. Registrar en el PR qué se comprobó y qué sigue pendiente; una propuesta del PDF o del asistente no se considera implementada hasta existir en el código y estar verificada.

### Contexto breve para iniciar una sesión de trabajo

> Trabajo en Lexi, repositorio NyaSanchez/lexi-android, Android nativo con Kotlin y Jetpack Compose. Lee docs/GUIA_EQUIPO.md, README y las instrucciones del repositorio antes de editar. La referencia funcional 727afe3 incluye vocabulario, partida, resultado y guardado local con Room; la racha y Repaso siguen pendientes en esa referencia. Verifica el estado actual y los cambios de otros integrantes. Mantén los commits de avance académico y no hagas commit ni push por mí. Mi tarea acordada y mi rama son: [completar].

## 12. Guardar esta documentación

Este avance solo cambia documentación. En GitHub Desktop, revisar `docs/GUIA_EQUIPO.md`, `README.md` y `docs/COMMITS.md` y usar:

```text
docs: añadir guía de incorporación y contexto del equipo
```

Descripción sugerida:

```text
Documenta instalación, estado actual, arquitectura y pruebas de Lexi.
Enumera pendientes y decisiones por acordar.
Explica acceso, ramas, revisión y commits para el trabajo en equipo.
```

Con la copia actual en `main`, registrar este commit documental y hacer **Push origin** antes de pedir a los compañeros que lean la guía en GitHub. A partir de la siguiente tarea compartida, utilizar el flujo de ramas y revisión acordado.
