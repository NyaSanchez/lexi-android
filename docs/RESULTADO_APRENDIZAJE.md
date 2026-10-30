# Resultado y aprendizaje

## Alcance

Este avance desarrolla la parte de aprendizaje de la página 7 del PDF. Al terminar una palabra, la aplicación presenta una vista dedicada al resultado:

- Acierto y número de intentos utilizados, o derrota claramente identificada.
- Solución y traducción, con sus idiomas indicados.
- Una frase de ejemplo en el idioma de la respuesta y su traducción debajo.
- **Ver mis intentos** para desplegar las seis filas con sus pistas. **Ocultar mis intentos** vuelve a recoger el tablero.
- **Siguiente palabra** para continuar y **Volver al inicio** para terminar la sesión.

Los ejemplos también aparecen al perder, para poder aprender de esa palabra. Revelarlos no convierte una derrota en victoria. La racha, la meta y el progreso permanente siguen pendientes de su propia implementación; no se muestran aciertos ni días inventados.

## Correspondencia de idiomas

| Modo de respuesta | Solución y traducción | Primer ejemplo | Traducción del ejemplo |
| --- | --- | --- | --- |
| Inglés | HOUSE · casa | This is my house. | Esta es mi casa. |
| Español | CASA · house | Esta es mi casa. | This is my house. |

Las frases salen del mismo `VocabularyExercise` utilizado durante la partida. No se consulta otro servicio ni se añade contenido a una palabra por su posición en la lista. Los ejemplos de la siguiente palabra no se presentan durante el reto.

## Interfaz y estado

`PracticeResultScreen.kt` contiene la vista de aprendizaje. `PracticeScreen.kt` la presenta únicamente cuando el motor entrega un estado `WON` o `LOST`. La partida terminada sigue siendo la misma: consultar el tablero y girar el dispositivo no registra otro acierto ni consume intentos.

La vista es desplazable, se adapta a los temas claro y oscuro y tiene vistas previas con texto grande. Los títulos usan semántica de encabezado y cada ejemplo agrupa su idioma y frase para su lectura. La preferencia de mostrar los intentos y la posición de desplazamiento usan el estado transitorio de Compose.

No se añadieron bibliotecas ni permisos de Android. La pronunciación de audio que aparece en la maqueta sigue pendiente de definir.

## Comprobación manual

1. Abrir una partida con **Definición en español** y enviar HOUSE. Debe aparecer **¡Acertaste!**, «Lo resolviste en 1 intento», HOUSE · casa y las frases «This is my house.» / «Esta es mi casa.», con sus idiomas.
2. Abrir **Ver mis intentos**: debe verse HOUSE en verde. Girar el teléfono y comprobar que se conservan el resultado y el tablero desplegado; después ocultarlo.
3. Pulsar **Siguiente palabra**. Debe abrirse APPLE con cero intentos, sin mostrar su ejemplo todavía. Al acertar APPLE, debe aparecer «The apple is on the table.» / «La manzana está sobre la mesa.».
4. Volver al inicio y elegir **Definition in English**. Al acertar CASA, las frases deben aparecer en el orden español → inglés.
5. En una nueva partida con respuesta en inglés, enviar SHARE seis veces. El resultado debe seguir diciendo **Intentos agotados**, con la solución y los ejemplos, y permitir continuar.

## Comandos

En la terminal de Android Studio, desde la raíz del proyecto:

```powershell
.\gradlew.bat :app:assembleDebug :app:testDebugUnitTest :app:lintDebug
```

Para compilar las pruebas de pantalla sin instalarlas:

```powershell
.\gradlew.bat :app:assembleDebugAndroidTest
```

`PracticeFlowTest` amplía sus casos de victoria, derrota y español para comprobar los ejemplos. También incluye un caso de recuperación del resultado, revisión del tablero y cambio a la siguiente palabra sin revelar sus ejemplos antes de tiempo. Las pruebas instrumentadas pueden ejecutarse con `:app:connectedDebugAndroidTest` en un dispositivo o emulador que permita instalar el paquete de pruebas; en este teléfono se mantiene la comprobación manual elegida por la persona autora.

## Verificación del 29 de octubre de 2026

- Compilaron el APK de la aplicación y el APK de pruebas instrumentadas.
- Pasaron las 47 pruebas locales mediante JUnitCore; el catálogo y las reglas del motor conservan sus resultados.
- Android lint terminó con cero errores y los mismos nueve avisos de versiones del SDK, herramientas y dependencias. No aparecieron avisos nuevos.
- El código y la configuración coinciden con la copia compilada.
- El APK actualizado se instaló en el Xiaomi 22021211RG y Android confirmó la apertura de `MainActivity`.
- La persona autora confirmó manualmente el resultado de HOUSE, ambos ejemplos, el tablero desplegable y su conservación al girar, y el cambio a los ejemplos de APPLE al continuar.
- Se revisó una captura real del resultado de APPLE en tema claro: las frases y los controles se muestran completos.
- La persona autora confirmó que CASA presenta primero el ejemplo en español y después en inglés, y que seis envíos de SHARE mantienen el resultado de derrota, muestran ambos ejemplos de HOUSE y permiten continuar.
- Las cinco pruebas de pantalla compilaron. No se ejecutaron en el teléfono: se conserva la elección de comprobación manual tras el bloqueo de instalación del paquete de pruebas en la etapa anterior.

La tarea de Gradle `testDebugUnitTest` encontró el mismo error de permisos al renombrar su informe binario en el entorno automatizado. Por eso se comprobaron sus clases compiladas directamente con JUnitCore y no se registra toda la ejecución de Gradle como exitosa. La compilación se realizó en una copia temporal; también hubo avisos de caché de lint y se conservaron los símbolos de una biblioteca nativa de terceros en el APK de depuración.

El tema oscuro, el texto grande y la lectura con TalkBack tienen soporte en la vista, pero no se registra una comprobación manual específica de esos casos en esta etapa.

## Commit propuesto

Después de comprobar el resultado, revisar **Changes** en GitHub Desktop.

Summary:

```text
feat: mostrar resultado y ejemplo de uso
```

Description:

```text
Añade una vista de resultado con traducción y ejemplos bilingües.
Muestra los intentos utilizados al acertar y distingue la derrota.
Permite consultar el tablero y continuar con la siguiente palabra.
Amplía las pruebas de pantalla para el aprendizaje y su recuperación.
```

Pulsar **Commit to main** y después **Push origin**. El siguiente avance previsto es guardar el progreso localmente.
