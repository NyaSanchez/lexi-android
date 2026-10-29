# Lexi

Aplicación nativa para Android que permite practicar vocabulario y escritura en español e inglés mediante definiciones, intentos y pistas por letra.

## Estado actual

Aplicación Android con Kotlin y Jetpack Compose. Incluye Inicio, selección de los dos modos de práctica y navegación entre Inicio, Repaso y Progreso, con tema claro y oscuro.

La selección del modo se conserva al navegar y al recrearse la actividad. «Empezar» abre la práctica con el idioma seleccionado. Repaso y Progreso muestran sus estados iniciales, sin resultados simulados.

El banco local contiene 30 pares de palabras con definiciones y ejemplos en ambos idiomas. Puede preparar 60 ejercicios contando los dos sentidos. Los datos y sus pruebas ya forman parte del proyecto, pero la pantalla de práctica todavía no consume el repositorio: mantiene su estado vacío hasta implementar la partida.

El tablero, la evaluación de intentos, el cálculo de racha y el guardado de progreso se implementarán en los siguientes avances.

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
| Android mínimo | Android 8.0, API 26 |
| compileSdk / targetSdk | 36 / 36 |
| Identificador de la app | `io.github.nyasanchez.lexi` |

AGP 9 incorpora el soporte de Kotlin. Las dependencias se centralizan en `gradle/libs.versions.toml`. La persistencia se concretará al implementar el progreso.

## Documentación

- [Reglas y alcance](docs/REQUISITOS.md): requisitos extraídos del PDF de referencia y decisiones pendientes.
- [Plan y rutina de commits](docs/COMMITS.md): avances previstos, validación y mensajes de ejemplo.
- [Primer arranque](docs/PRIMER_ARRANQUE.md): apertura en Android Studio, ejecución y commit del proyecto inicial.
- [Inicio y navegación](docs/INICIO_NAVEGACION.md): alcance y comprobación de las pantallas actuales.
- [Vocabulario inicial](docs/VOCABULARIO_INICIAL.md): catálogo, reglas de edición y pruebas del avance actual.

## Referencia de diseño

La especificación inicial procede de las nueve páginas de «Idea de App.pdf», proporcionado por la persona autora del proyecto. Los diseños muestran el nombre Lexi, una paleta violeta, tarjetas claras y navegación inferior. Los elementos propios del dispositivo de las maquetas se adaptarán a Android.

## Seguimiento académico

Cada avance funcional o documental se guardará en un commit descriptivo y se enviará a GitHub. El historial debe reflejar el trabajo real y permitir revisar qué cambió y cómo se comprobó.

## Ejecución

1. En Android Studio, seleccionar **Open** y abrir la carpeta raíz `lexi-android`, la que contiene `settings.gradle.kts`.
2. Usar el JDK integrado de Android Studio como Gradle JDK y tener instalado Android SDK Platform 36.
3. Esperar a que termine la sincronización de Gradle.
4. Elegir un emulador o dispositivo Android con API 26 o superior, seleccionar la configuración `app` y pulsar **Run**.
5. Comprobar que aparece «Tu práctica de hoy», los dos modos de práctica y las pestañas Inicio, Repaso y Progreso.

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
      ui/LexiApp.kt
      ui/HomeScreen.kt
      ui/PracticeMode.kt
      ui/PracticeEmptyScreen.kt
      ui/ReviewScreen.kt
      ui/ProgressScreen.kt
      ui/ScreenComponents.kt
      ui/theme/Theme.kt
    res/
  src/test/java/io/github/nyasanchez/lexi/data/vocabulary/
docs/
gradle/
  libs.versions.toml
  wrapper/
gradlew
gradlew.bat
settings.gradle.kts
```

`MainActivity` abre la interfaz; `LexiApp` gestiona la navegación y la selección del modo; `HomeScreen` muestra el inicio; `Theme` define los colores. Los textos están en `res/values/strings.xml`.

El vocabulario está separado de la interfaz y no depende de un teléfono o emulador para ejecutar sus pruebas. Los informes se generan en `app/build/reports/tests/testDebugUnitTest/index.html`.
