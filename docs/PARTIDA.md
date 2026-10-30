# Partida en el teléfono

Este documento registra la etapa del tablero. El resultado básico ya se amplió con una vista de aprendizaje y ejemplos: [Resultado y aprendizaje](RESULTADO_APRENDIZAJE.md).

## Qué incorpora este avance

«Empezar» abre una partida real en el modo elegido. La pantalla muestra la definición, la cantidad de letras, seis filas, los intentos usados y un campo para responder. Se puede enviar con **Comprobar** o con la acción de finalizar del teclado.

Las pistas combinan color y símbolo: verde/✓ para posición correcta, amarillo/↔ para otra posición y rojo/× cuando no queda una coincidencia disponible. Cada fila tiene una descripción de sus letras y resultados para lectores de pantalla. El tablero permite desplazamiento horizontal si el tamaño del texto impide que todas las columnas quepan; la pantalla también permite desplazamiento vertical y reserva espacio para el teclado.

Las entradas inválidas muestran el motivo y mantienen el contador. Al acertar o agotar los intentos desaparece el campo de escritura, se revela la solución con su traducción y aparece **Siguiente palabra**. Las frases de ejemplo se incorporarán al ampliar la presentación del resultado.

## Sesiones y selección de palabras

- La primera palabra es HOUSE si se responde en inglés y CASA si se responde en español.
- Se recorre el catálogo en su orden: casa/house, manzana/apple, árbol/tree, etc. Después de las treinta entradas se vuelve al principio.
- La siguiente palabra comienza con seis oportunidades y sin el texto ni los errores de la anterior.
- Se conservan la palabra actual, el historial aceptado y el borrador al recrearse la actividad, incluida la rotación. Se guarda un máximo de seis respuestas y un borrador de hasta 128 caracteres; el motor reconstruye las pistas.
- Salir al inicio termina la sesión. Si hay texto o intentos en una partida activa, se pide confirmación. «Seguir jugando» conserva el estado.
- Este guardado es transitorio: no sustituye una base de datos. El progreso, la racha y Repaso todavía no registran los resultados.

## Archivos principales

- `ui/PracticeScreen.kt`: estado de pantalla, teclado, mensajes, salida y resultado básico.
- `ui/GuessBoard.kt`: tablero, símbolos, leyenda y accesibilidad.
- `ui/PracticeMode.kt`: correspondencia del modo con el idioma del catálogo.
- `domain/game/WordGame.kt`: reconstrucción del motor a partir del historial aceptado.
- `src/androidTest/java/io/github/nyasanchez/lexi/PracticeFlowTest.kt`: pruebas que recorren la aplicación en un dispositivo.

Las rutas `ui/` y `domain/` son relativas a `app/src/main/java/io/github/nyasanchez/lexi/`. Los textos visibles están en `app/src/main/res/values/strings.xml`.

## Prueba manual antes del commit

1. Sincronizar Gradle si Android Studio muestra **Sync Now**. Elegir el Xiaomi o un emulador y pulsar **Run app** para instalar el código actual.
2. Elegir **Definición en español** y pulsar **Empezar**. Debe pedir una respuesta en inglés de cinco letras.
3. Escribir HO y comprobar: debe mostrar un error y seguir en cero intentos. Después enviar SHARE: debe mostrar amarillo, amarillo, rojo, rojo y verde.
4. Escribir un borrador y girar el teléfono con la rotación automática activada. Deben conservarse ese texto y el intento anterior.
5. Enviar HOUSE: debe aparecer **¡Acertaste!**, la traducción y **Siguiente palabra**. La segunda palabra es APPLE; PAPAL debe producir amarillo, amarillo, verde, rojo y amarillo.
6. Volver al inicio y elegir **Definition in English**. La respuesta de la primera partida es CASA. Tras CASA y MANZANA, la tercera es ÁRBOL: ARBOL consume una oportunidad sin ganar; ÁRBOL acierta.
7. En una nueva sesión en inglés, enviar SHARE seis veces. Debe aparecer **Intentos agotados** y HOUSE · casa, sin permitir más envíos. Pasar a la siguiente palabra debe limpiar el tablero y el contador.

También conviene comprobar el modo oscuro, el teclado español y el tamaño grande de texto. En una pantalla pequeña, desplazar verticalmente para llegar al campo de respuesta y a los controles.

## Comandos de comprobación

Desde la raíz, en la terminal de Android Studio:

```powershell
.\gradlew.bat :app:assembleDebug :app:testDebugUnitTest :app:lintDebug
```

Las 47 pruebas locales cubren el catálogo, las reglas y la reconstrucción del historial. Con el teléfono desbloqueado y conectado, las cuatro pruebas de pantalla se ejecutan con:

```powershell
.\gradlew.bat :app:connectedDebugAndroidTest
```

Estas últimas comprueban el envío de respuestas en ambos idiomas, los mensajes, las pistas, el siguiente reto, victoria, derrota, cancelación de salida y recreación de la actividad con historial y borrador. Usan AndroidX Test y Compose Test; no forman parte de la aplicación de distribución.

## Verificación del 29 de octubre de 2026

- Compilaron el APK de depuración y el APK de pruebas instrumentadas.
- Las 47 pruebas locales pasaron al ejecutarse directamente con JUnitCore (`OK (47 tests)`).
- Android lint terminó sin errores. Permanecen los nueve avisos de versiones del SDK, herramientas y dependencias que ya tenía el proyecto; se corrigieron los nuevos avisos sobre colecciones mutables y plurales.
- Los archivos de código y configuración del repositorio coinciden con la copia compilada.
- La versión actual se instaló en el Xiaomi 22021211RG y Android confirmó un arranque correcto de `MainActivity`.
- La persona autora confirmó manualmente HO sin consumir un intento, las cinco pistas de SHARE, la conservación del borrador HOU y el historial al girar el teléfono, y la victoria al enviar HOUSE.
- También confirmó el modo español con CASA y MANZANA, ARBOL como respuesta incorrecta y ÁRBOL como acierto, la derrota tras seis envíos de SHARE y el siguiente reto con el contador en cero.
- Las cuatro pruebas instrumentadas compilaron, pero no se ejecutaron: Android canceló la instalación de su paquete con `INSTALL_FAILED_USER_RESTRICTED`. Se eligió comprobar la interfaz manualmente, sin modificar los ajustes de seguridad del teléfono.

En el entorno automatizado, Gradle no pudo renombrar el informe binario de `testDebugUnitTest` por un problema de permisos. Por eso no se registra la tarea completa como exitosa; se verificaron las mismas clases compiladas con JUnitCore. También hubo avisos de caché de lint y se conservaron los símbolos de una biblioteca nativa de terceros en el APK de depuración. La compilación se hizo en una copia temporal.

Se completaron las comprobaciones manuales principales en el teléfono. El modo oscuro, el tamaño grande de texto y TalkBack tienen soporte en la interfaz, pero no se registra una prueba manual específica de esos casos en este avance. Las pruebas instrumentadas quedan disponibles para ejecutarse después en un dispositivo o emulador que permita instalar su paquete.

## Commit propuesto

Después de comprobar el funcionamiento, en GitHub Desktop:

Summary:

```text
feat: implementar partida de vocabulario
```

Description:

```text
Conecta el catálogo y el motor con un tablero de seis intentos.
Añade escritura, validación y pistas con colores y símbolos.
Muestra victoria o derrota y permite practicar la siguiente palabra.
Conserva historial y borrador al recrearse la actividad.
Añade pruebas de recuperación y del flujo en el dispositivo.
```

Revisar **Changes**, pulsar **Commit to main** y después **Push origin**.

## Referencias técnicas

Se utilizan las APIs de [estado guardado en Compose](https://developer.android.com/develop/ui/compose/state-saving) para conservar datos transitorios y de [márgenes del sistema](https://developer.android.com/develop/ui/compose/system/insets-ui) para el teclado. Las pruebas de pantalla siguen la [documentación de pruebas de Compose](https://developer.android.com/develop/ui/compose/testing).
