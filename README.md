# Lexi

Aplicación nativa para Android que permite practicar vocabulario y escritura en español e inglés mediante definiciones, intentos y pistas por letra.

**¿Te incorporas al equipo?** Empieza por la [guía de incorporación y contexto](docs/GUIA_EQUIPO.md): acceso, instalación, estado actual, arquitectura, pendientes, pruebas y trabajo con ramas y commits.

## Documentación Segunda Entrega:

[Link directo a la documentación](docs/evidencia/entrega-2/README.md)

## Estado actual

Aplicación Android con Kotlin y Jetpack Compose. Incluye Inicio, selección de los dos modos de práctica y navegación entre Inicio, Repaso y Progreso, con tema claro y oscuro.

La selección del modo se conserva al navegar y al recrearse la actividad. "Empezar" abre la práctica con el idioma seleccionado, respetando el nivel actual de la persona. Progreso muestra los resultados guardados; Repaso reúne las palabras perdidas y permite volver a intentarlas.

El banco local contiene el vocabulario clasificado en niveles de dificultad (A1, A2, B1, etc.). La pantalla de práctica ya utiliza ese catálogo y el motor de partida: muestra la definición, un tablero visual interactivo y un campo nativo adaptado para ingresar letras mediante el teclado del dispositivo, bloqueando selectores de texto no deseados.

Cada envío válido revela pistas con colores, símbolos y descripciones para lectores de pantalla. Los errores de formato no consumen intentos. Al acertar o agotar las oportunidades, se abre una vista de resultado con la solución, su traducción y una frase de ejemplo en ambos idiomas. Se pueden revisar los intentos y avanzar al siguiente nivel al cumplir los requisitos. El estado de la partida y el borrador se conservan al recrearse la actividad.

Las partidas terminadas se guardan en una base de datos local de Room. Progreso muestra el total de partidas, aciertos, derrotas, palabras distintas acertadas y los últimos 20 resultados. Un identificador único impide duplicar una partida al recuperar la pantalla. La vista de resultado confirma cuándo quedó guardada y permite reintentar si falla.

La selección de palabras sigue el orden del catálogo correspondiente al nivel. La práctica conserva la continuación por modo y nivel. La racha se calcula a partir de las partidas ganadas. En Repaso aparecen las palabras en las que se agotaron los intentos y que aún no se han acertado en ese modo; al acertarlas en un reintento cuentan para el nivel que les corresponde en Inicio. El historial guardado permanece al cerrar la app.

La aplicación cuenta con una publicación inicial y una actualización en prueba cerrada de Google Play. El expediente de la Entrega 2 documenta sus versiones y la evidencia disponible.

## Cómo funcionará

- Una definición en español pide una palabra en inglés; una definición en inglés pide una palabra en español.
- Las palabras están divididas en niveles progresivos de dificultad (A1 a C2).
- Cada palabra permite hasta seis intentos válidos.
- Las pistas distinguen letras correctas, letras en otra posición y letras sin coincidencia disponible.
- El primer acierto de cada día completa la meta y actualiza la racha una sola vez.
- Al acertar, se muestran la traducción y una frase de ejemplo.
- Las secciones principales serán Inicio, Repaso y Progreso.

## Prueba cerrada en Google Play

Lexi se distribuye en el canal **Prueba cerrada - Entrega 2 - Curso**, con el paquete `io.github.nyasanchez.lexi`. El acceso requiere una cuenta autorizada por el equipo.

### Instrucciones para testers

1. Iniciar sesión en Google Play con la cuenta autorizada y abrir el [enlace de incorporación a la prueba](https://play.google.com/apps/testing/io.github.nyasanchez.lexi).
2. Aceptar la participación y abrir la [ficha de Lexi en Google Play](https://play.google.com/store/apps/details?id=io.github.nyasanchez.lexi).
3. Pulsar **Instalar** si la app aún no está instalada. Si ya existe una versión anterior y aparece **Actualizar**, utilizar esa opción conservando la instalación y sus datos.
4. Abrir Lexi, recorrer el tutorial si aparece y elegir un modo y nivel disponibles. Escribir la respuesta en las casillas y pulsar **Comprobar** para recibir las pistas.
5. Al completar la partida, abrir **Ver resultado** y comprobar el resultado guardado en **Progreso**.
6. Ejecutar los casos de la [matriz de QA](docs/pruebas.md) y registrar fecha, dispositivo/API, versión, pasos, resultado observado y evidencia. Los defectos se documentan en [Issues](https://github.com/NyaSanchez/lexi-android/issues), con título y descripción en inglés.

La ejecución desde Android Studio corresponde al flujo de desarrollo. Las evidencias de distribución y actualización de la Entrega 2 se obtienen desde la instalación de Google Play.

### Versiones publicadas

| Lanzamiento | versionName | versionCode | Fecha de publicación | Alcance |
| --- | --- | --- | --- | --- |
| Inicial | 0.1.0 | 1 | 02/11/2026 | Primera publicación en prueba cerrada. |
| Actualización | 0.2.0 | 2 | 07/11/2026 | Corrección de los tiradores del selector, tutorial y niveles Básico, Intermedio y Avanzado, con 100 palabras por nivel. |

El [expediente de la Entrega 2](docs/entrega-2.md) enlaza los lanzamientos, PRs, issues, revisiones y evidencias. La [matriz de QA](docs/pruebas.md) registra la ejecución manual y las comprobaciones pendientes. Los valores de la tabla proceden de las capturas de Play Console. Zyanya Sanchez identifica [9a73231](https://github.com/NyaSanchez/lexi-android/commit/9a73231c19a2fa2b6a4a3627a741a85ea3718ad1) como base del AAB actualizado; queda por documentar el ajuste del código de versión 1 del repositorio al código 2 publicado.

## Tecnología elegida

- Android Studio.
- Kotlin y Jetpack Compose para la aplicación nativa.
- Git, GitHub y GitHub Desktop para el control de versiones.

Configuración de esta primera versión:

| Elemento | Versión o valor |
| --- | --- |
| Android Gradle Plugin | 9.3.3 |
| Gradle Wrapper | 9.5.1 |
| Kotlin y compilador de Compose | 2.4.20 |
| Compose BOM | 2026.06.01 |
| Navigation Compose | 2.9.8 |
| Room / KSP | 2.8.5 / 2.3.10 |
| Lifecycle | 2.10.0 |
| Android mínimo | Android 8.0, API 26 |
| compileSdk / targetSdk | 36 / 36 |
| Identificador de la app | `io.github.nyasanchez.lexi` |

AGP 9 incorpora el soporte de Kotlin. Las dependencias se centralizan en `gradle/libs.versions.toml`. Room usa KSP para generar el acceso a SQLite; el esquema inicial se versiona en `app/schemas/`.

## Novedades recientes

*   **Niveles de Vocabulario:** Nuevo sistema de progresión y jerarquía que clasifica las palabras por nivel del Marco Común Europeo (A1 a C2). La interfaz muestra tres niveles de 100 palabras y habilita el siguiente al acertar el 70 % del anterior, con progreso independiente por modo.
*   **Tutorial Interactivo (Onboarding):** Implementación de un carrusel de bienvenida nativo utilizando HorizontalPager de Jetpack Compose Foundation.
    *   Construido íntegramente con componentes Composable (Surface, Icon, Text), eliminando la dependencia de imágenes estáticas y reduciendo el peso final.
    *   Diseño adaptable con los tokens de color de MaterialTheme.
    *   Integrado mediante estado temporal condicionado a los valores persistentes locales (racha de 0 días).
*   **Tablero Interactivo (GuessBoard):** Nuevo diseño estilo Wordle para el ingreso visual de la palabra.
    *   Integración transparente de componentes para recibir la entrada del teclado ocultando el cursor y el selector del sistema.
    *   Manejo de estados con TextFieldState para la distribución fluida de las letras por casilla.
*   **Gestión de Notificaciones de Actualización:** Incorporación de diálogos nativos (AlertDialog) en pantalla principal controlados por estado para notificar sobre los cambios del sistema de niveles y evitar la sobrecarga visual del tutorial base.

## Documentación

- [Entrega 2](docs/entrega-2.md): publicaciones, sprint y trazabilidad de los cambios.
- [Matriz de QA](docs/pruebas.md): casos manuales, resultados y videos del dispositivo.
- [Inventario de evidencias](docs/evidencia/entrega-2/README.md): archivos disponibles y su alcance.
- [Reglas y alcance](docs/REQUISITOS.md): requisitos extraídos del PDF de referencia y decisiones pendientes.
- [Plan y rutina de commits](docs/COMMITS.md): avances previstos, validación y mensajes de ejemplo.
- [Primer arranque](docs/PRIMER_ARRANQUE.md): apertura en Android Studio, ejecución y commit del proyecto inicial.
- [Inicio y navegación](docs/INICIO_NAVEGACION.md): alcance y comprobación de las pantallas actuales.
- [Vocabulario inicial](docs/VOCABULARIO_INICIAL.md): catálogo, reglas de edición y pruebas de los datos.
- [Motor de partida](docs/MOTOR_PARTIDA.md): validación, seis intentos y letras repetidas.
- [Partida en el teléfono](docs/PARTIDA.md): tablero, teclado y recuperación del estado.
- [Resultado y aprendizaje](docs/RESULTADO_APRENDIZAJE.md): ejemplos bilingües y revisión de intentos.
- [Guardado de progreso](docs/GUARDADO_PROGRESO.md): persistencia, contadores, errores y comprobación del avance actual.
- [Repaso](docs/REPASO.md): palabras perdidas, reintento y cómo cuentan para el nivel.

## Referencia de diseño

La especificación inicial procede de las nueve páginas de "Idea de App.pdf", proporcionado por la persona autora del proyecto. Los diseños muestran el nombre Lexi, una paleta violeta, tarjetas claras y navegación inferior. Los elementos propios del dispositivo de las maquetas se adaptarán a Android.

## Seguimiento académico

Se utilizó Codex como apoyo en la implementación incremental, la preparación de pruebas y la redacción y revisión de documentación. Las publicaciones y operaciones de GitHub se realizaron por integrantes del equipo; las pruebas manuales del dispositivo fueron ejecutadas por la persona probadora.

Cada avance funcional o documental se guardará en un commit descriptivo y se enviará a GitHub. El historial debe reflejar el trabajo real y permitir revisar qué cambió y cómo se comprobó.

## Ejecución

1. En Android Studio, seleccionar **Open** y abrir la carpeta raíz `lexi-android`, la que contiene `settings.gradle.kts`.
2. Usar el JDK integrado de Android Studio como Gradle JDK y tener instalado Android SDK Platform 36.
3. Esperar a que termine la sincronización de Gradle.
4. Elegir un emulador o dispositivo Android con API 26 o superior, seleccionar la configuración `app` y pulsar **Run**.
5. Elegir un modo y pulsar "Empezar". Comprobar la definición, enviar una respuesta y revisar las pistas del tablero.

Desde PowerShell en la raíz, con `JAVA_HOME` apuntando a un JDK compatible con Gradle 9.5.1 (se recomienda el JDK integrado de Android Studio instalado):

```powershell
.\gradlew.bat :app:assembleDebug :app:testDebugUnitTest :app:lintDebug
```

`local.properties` contiene la ruta al SDK de cada computadora y está excluido de Git. Android Studio puede crearlo al configurar el proyecto. La primera sincronización necesita acceso a Internet para descargar las herramientas y dependencias que falten.

## Estructura inicial

```text
app/
  build.gradle.kts
  src/main/
    AndroidManifest.xml
    java/io/github/nyasanchez/lexi/
      MainActivity.kt
      data/vocabulary/InitialVocabulary.kt
      data/vocabulary/VocabularyEntry.kt
      data/vocabulary/LocalVocabularyRepository.kt
      data/progress/SavedGame.kt
      data/progress/ProgressDao.kt
      data/progress/ProgressDatabase.kt
      data/progress/ProgressRepository.kt
      domain/game/WordGame.kt
      domain/game/GameState.kt
      domain/game/GuessInput.kt
      domain/game/LetterEvaluator.kt
      domain/level/LevelRules.kt
      domain/level/LevelStatus.kt
      ui/LexiApp.kt
      ui/HomeScreen.kt
      ui/PracticeMode.kt
      ui/PracticeScreen.kt
      ui/PracticeResultScreen.kt
      ui/GuessBoard.kt
      ui/GuessRowEditor.kt
      ui/TutorialCarousel.kt
      ui/PracticeEmptyScreen.kt
      ui/ReviewScreen.kt
      ui/ProgressScreen.kt
      ui/ProgressViewModel.kt
      ui/ScreenComponents.kt
      ui/theme/Theme.kt
    res/
      drawable/
        ic_warning_update.xml
  src/test/java/io/github/nyasanchez/lexi/data/vocabulary/
  src/test/java/io/github/nyasanchez/lexi/data/progress/
  src/test/java/io/github/nyasanchez/lexi/domain/game/
  src/androidTest/java/io/github/nyasanchez/lexi/PracticeFlowTest.kt
  src/androidTest/java/io/github/nyasanchez/lexi/ProgressDatabaseTest.kt
  schemas/
docs/
gradle/
  libs.versions.toml
  wrapper/
gradlew
gradlew.bat
settings.gradle.kts
```

`MainActivity` abre la interfaz; `LexiApp` gestiona la navegación y la selección del modo; `HomeScreen` muestra el inicio; `Theme` define los colores. Los textos están en `res/values/strings.xml`.

El vocabulario y el motor de partida están separados de la interfaz y no dependen de un teléfono o emulador para ejecutar sus pruebas. Los informes se generan en `app/build/reports/tests/testDebugUnitTest/index.html`.

Las pruebas de pantalla sí necesitan un dispositivo desbloqueado o un emulador. Se ejecutan con `./gradlew.bat :app:connectedDebugAndroidTest`; realizan partidas de prueba y recrean la actividad.