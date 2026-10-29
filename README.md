# Lexi

Aplicación nativa para Android que permite practicar vocabulario y escritura en español e inglés mediante definiciones, intentos y pistas por letra.

## Estado actual

Proyecto Android inicial con Kotlin y Jetpack Compose. Incluye una pantalla de bienvenida, recursos de texto, un icono provisional y una base de tema claro y oscuro.

Los modos de práctica, el tablero, la navegación y el guardado de progreso se implementarán en los siguientes avances.

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
| Android mínimo | Android 8.0, API 26 |
| compileSdk / targetSdk | 36 / 36 |
| Identificador de la app | `io.github.nyasanchez.lexi` |

AGP 9 incorpora el soporte de Kotlin. Las dependencias se centralizan en `gradle/libs.versions.toml`. La persistencia se concretará al implementar el progreso.

## Documentación

- [Reglas y alcance](docs/REQUISITOS.md): requisitos extraídos del PDF de referencia y decisiones pendientes.
- [Plan y rutina de commits](docs/COMMITS.md): avances previstos, validación y mensajes de ejemplo.
- [Primer arranque](docs/PRIMER_ARRANQUE.md): apertura en Android Studio, ejecución y commit del proyecto inicial.

## Referencia de diseño

La especificación inicial procede de las nueve páginas de «Idea de App.pdf», proporcionado por la persona autora del proyecto. Los diseños muestran el nombre Lexi, una paleta violeta, tarjetas claras y navegación inferior. Los elementos propios del dispositivo de las maquetas se adaptarán a Android.

## Seguimiento académico

Cada avance funcional o documental se guardará en un commit descriptivo y se enviará a GitHub. El historial debe reflejar el trabajo real y permitir revisar qué cambió y cómo se comprobó.

## Ejecución

1. En Android Studio, seleccionar **Open** y abrir la carpeta raíz `lexi-android`, la que contiene `settings.gradle.kts`.
2. Usar el JDK integrado de Android Studio como Gradle JDK y tener instalado Android SDK Platform 36.
3. Esperar a que termine la sincronización de Gradle.
4. Elegir un emulador o dispositivo Android con API 26 o superior, seleccionar la configuración `app` y pulsar **Run**.
5. Comprobar que aparece Lexi con el mensaje «Una palabra al día. Un nuevo aprendizaje.».

Desde PowerShell en la raíz, con `JAVA_HOME` apuntando a un JDK compatible con Gradle 9.5.1 (se recomienda el JDK integrado de Android Studio instalado):

```powershell
.\gradlew.bat :app:assembleDebug :app:lintDebug
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
      ui/LexiApp.kt
      ui/theme/Theme.kt
    res/
docs/
gradle/
  libs.versions.toml
  wrapper/
gradlew
gradlew.bat
settings.gradle.kts
```

`MainActivity` abre la interfaz; `LexiApp` contiene la bienvenida; `Theme` define los colores. Los textos están en `res/values/strings.xml`.
