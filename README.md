# Lexi

Aplicación nativa para Android que permite practicar vocabulario y escritura en español e inglés mediante definiciones, intentos y pistas por letra.

**¿Te incorporas al equipo?** Empieza por la [guía de incorporación y contexto](docs/GUIA_EQUIPO.md): acceso, instalación, estado actual, arquitectura, pendientes, pruebas y trabajo con ramas y commits.

## Estado actual

Aplicación Android con Kotlin y Jetpack Compose. Incluye Inicio, selección de los dos modos de práctica y navegación entre Inicio, Repaso y Progreso, con tema claro y oscuro.

La selección del modo se conserva al navegar y al recrearse la actividad. «Empezar» abre la práctica con el idioma seleccionado. Progreso muestra los resultados guardados; Repaso todavía conserva su estado inicial.

El banco local contiene 30 pares de palabras con definiciones y ejemplos en ambos idiomas. La pantalla de práctica ya utiliza ese catálogo y el motor de partida: muestra la definición, un tablero de seis filas y un campo para responder con el teclado del teléfono.

Cada envío válido revela pistas con colores, símbolos y descripciones para lectores de pantalla. Los errores de formato no consumen intentos. Al acertar o agotar las oportunidades, se abre una vista de resultado con la solución, su traducción y una frase de ejemplo en ambos idiomas. Se pueden revisar los intentos y pasar a la siguiente palabra. El estado de la partida y el borrador se conservan al recrearse la actividad.

Las partidas terminadas se guardan en una base de datos local de Room. Progreso muestra el total de partidas, aciertos, derrotas, palabras distintas acertadas y los últimos 20 resultados. Un identificador único impide duplicar una partida al recuperar la pantalla. La vista de resultado confirma cuándo quedó guardada y permite reintentar si falla.

La selección de palabras sigue el orden del catálogo y vuelve al principio después de las treinta. Salir al inicio termina esa sesión; el cálculo de racha y el repaso se implementarán en los siguientes avances. El historial guardado permanece al cerrar la app; una partida sin terminar sigue siendo transitoria. Las partidas de versiones anteriores, que no se almacenaban, no se inventan ni reconstruyen.

El objetivo es desarrollar y probar la aplicación por etapas hasta preparar su publicación en Google Play.

## Cómo funcionará

- Una definición en español pide una palabra en inglés; una definición en inglés pide una palabra en español.
- Cada palabra permite hasta seis intentos válidos.
- Las pistas distinguen letras correctas, letras en otra posición y letras sin coincidencia disponible.
- El primer acierto de cada día completa la meta y actualiza la racha una sola vez.
- Al acertar, se muestran la traducción y una frase de ejemplo.
- Las secciones principales serán Inicio, Repaso y Progreso.

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

## Documentación

- [Reglas y alcance](docs/REQUISITOS.md): requisitos extraídos del PDF de referencia y decisiones pendientes.
- [Plan y rutina de commits](docs/COMMITS.md): avances previstos, validación y mensajes de ejemplo.
- [Primer arranque](docs/PRIMER_ARRANQUE.md): apertura en Android Studio, ejecución y commit del proyecto inicial.
- [Inicio y navegación](docs/INICIO_NAVEGACION.md): alcance y comprobación de las pantallas actuales.
- [Vocabulario inicial](docs/VOCABULARIO_INICIAL.md): catálogo, reglas de edición y pruebas de los datos.
- [Motor de partida](docs/MOTOR_PARTIDA.md): validación, seis intentos y letras repetidas.
- [Partida en el teléfono](docs/PARTIDA.md): tablero, teclado y recuperación del estado.
- [Resultado y aprendizaje](docs/RESULTADO_APRENDIZAJE.md): ejemplos bilingües y revisión de intentos.
- [Guardado de progreso](docs/GUARDADO_PROGRESO.md): persistencia, contadores, errores y comprobación del avance actual.

## Referencia de diseño

La especificación inicial procede de las nueve páginas de «Idea de App.pdf», proporcionado por la persona autora del proyecto. Los diseños muestran el nombre Lexi, una paleta violeta, tarjetas claras y navegación inferior. Los elementos propios del dispositivo de las maquetas se adaptarán a Android.

## Seguimiento académico

Cada avance funcional o documental se guardará en un commit descriptivo y se enviará a GitHub. El historial debe reflejar el trabajo real y permitir revisar qué cambió y cómo se comprobó.

## Ejecución

1. En Android Studio, seleccionar **Open** y abrir la carpeta raíz `lexi-android`, la que contiene `settings.gradle.kts`.
2. Usar el JDK integrado de Android Studio como Gradle JDK y tener instalado Android SDK Platform 36.
3. Esperar a que termine la sincronización de Gradle.
4. Elegir un emulador o dispositivo Android con API 26 o superior, seleccionar la configuración `app` y pulsar **Run**.
5. Elegir un modo y pulsar «Empezar». Comprobar la definición, enviar una respuesta y revisar las pistas del tablero.

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
      ui/LexiApp.kt
      ui/HomeScreen.kt
      ui/PracticeMode.kt
      ui/PracticeScreen.kt
      ui/PracticeResultScreen.kt
      ui/GuessBoard.kt
      ui/PracticeEmptyScreen.kt
      ui/ReviewScreen.kt
      ui/ProgressScreen.kt
      ui/ProgressViewModel.kt
      ui/ScreenComponents.kt
      ui/theme/Theme.kt
    res/
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
