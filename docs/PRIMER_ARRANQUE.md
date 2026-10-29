# Primer arranque y commit del proyecto Android

## Qué contiene este avance

Una aplicación Android inicial escrita en Kotlin y Jetpack Compose, con una pantalla de bienvenida, tema claro y oscuro y un icono provisional con la letra L. El juego y la navegación corresponden a avances posteriores.

El identificador inicial es `io.github.nyasanchez.lexi`. Se usará para instalar la aplicación; se revisará antes de publicar en Google Play.

## Abrir el proyecto existente

1. Abrir Android Studio y elegir **Open** (o **File > Open** si hay otro proyecto abierto).
2. Seleccionar la carpeta `lexi-android`, donde están el README y `settings.gradle.kts`.
3. Esperar a que termine **Gradle Sync**.
4. Si se solicita el SDK, seleccionar Android SDK Platform 36. Usar el JDK integrado de Android Studio como **Gradle JDK**, en **Settings > Build, Execution, Deployment > Build Tools > Gradle**.
5. Seleccionar la configuración `app` y un emulador o dispositivo con Android 8.0 (API 26) o posterior.
6. Pulsar **Run** y esperar a que la instalación termine.

## Comprobación manual

- La app abre y muestra «Lexi».
- Aparece «Una palabra al día. Un nuevo aprendizaje.» y la descripción en español.
- No hay cierres inesperados.
- Al girar el dispositivo, el contenido sigue siendo legible.
- Al cambiar entre modo claro y oscuro, los textos mantienen buen contraste.

Todavía no hay botones de juego, estadísticas ni partidas: en este avance se comprueba la base ejecutable.

## Verificación realizada el 29 de octubre de 2026

- `:app:assembleDebug`: terminó correctamente y generó el APK de depuración.
- `:app:lintDebug`: terminó sin errores. Señaló versiones más recientes de herramientas, dependencias y SDK. La observación sobre una carpeta de recursos vacía se resolvió retirando esa carpeta.
- Se comprobó que los archivos del repositorio coincidían con los de la copia compilada y que Git excluye la configuración local y los binarios generados.
- El primer arranque y la revisión visual en emulador o teléfono siguen pendientes.

La compilación se realizó sobre una copia temporal porque el entorno automatizado informa incorrectamente a Java de que las carpetas de trabajo no son escribibles. Ese entorno también emitió avisos sobre cachés de lint y no pudo quitar símbolos de una biblioteca nativa de terceros; el APK de depuración se generó conservando esa biblioteca. Conviene repetir la compilación y lint desde Android Studio en la computadora antes de cerrar este avance. Esta comprobación todavía no valida una versión de distribución.

## Comprobaciones con Gradle

Desde PowerShell en la raíz del repositorio, con el JDK y SDK configurados:

```powershell
.\gradlew.bat :app:assembleDebug :app:lintDebug
```

La primera tarea compila un APK de depuración; la segunda revisa problemas estáticos de Android. Estas comprobaciones no sustituyen el arranque en un emulador o dispositivo. El reporte de lint queda en `app/build/reports/lint-results-debug.html`.

## Guardar el avance en GitHub Desktop

Después de comprobar el arranque:

1. Abrir el repositorio `lexi-android` en GitHub Desktop.
2. En **Changes**, revisar `app/`, `gradle/`, los scripts `gradlew` y `gradlew.bat`, los archivos de configuración, el README y esta guía.
3. Comprobar que no se incluyen `local.properties`, carpetas de caché o compilación, APK ni claves de firma. `gradle-wrapper.jar` sí se incluye: permite usar la misma versión de Gradle al clonar el proyecto.
4. En **Summary**, escribir:

   ```text
   chore: crear proyecto Android con Kotlin y Compose
   ```

5. En **Description**, describir lo realizado y únicamente las comprobaciones que hayan terminado correctamente. Por ejemplo, después de verificar compilación, lint y arranque:

   ```text
   Configura la aplicación Android y fija versiones con Gradle Wrapper.
   Añade bienvenida de Lexi, recursos y tema claro y oscuro.
   Documenta los pasos de apertura y ejecución.

   Verificación: assembleDebug, lintDebug y arranque en emulador.
   ```

   Si se usó un teléfono, sustituir «emulador» por «dispositivo».

6. Pulsar **Commit to main** y después **Push origin**.
7. Revisar en GitHub que aparece el nuevo commit junto a los dos anteriores.

## Si hay un error

Guardar el texto completo del error mostrado en **Build** o **Sync**. Resolverlo antes de registrar como correcto el primer arranque. No cambiar versiones al azar: las herramientas del proyecto se han elegido para funcionar juntas.

## Referencias

- [Configurar Jetpack Compose](https://developer.android.com/develop/ui/compose/setup).
- [Compatibilidad de Android Gradle Plugin 9.3](https://developer.android.com/build/releases/agp-9-3-0-release-notes).
- [Soporte de Kotlin integrado en AGP](https://developer.android.com/build/migrate-to-built-in-kotlin).
