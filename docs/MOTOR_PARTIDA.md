# Motor de partida

## Alcance de este avance

Se incorpora la lógica de una partida para una palabra y un idioma. Es Kotlin independiente de la interfaz: recibe un ejercicio del banco local y devuelve el estado y la evaluación de cada intento.

La pantalla del teléfono todavía conserva el estado vacío del avance de navegación. El siguiente commit conectará este motor al tablero, la escritura y los mensajes. La persistencia, la selección de la siguiente palabra y la racha se implementarán después.

## Reglas implementadas

1. Una partida comienza con seis oportunidades y sin mostrar la solución.
2. Se aceptan mayúsculas y minúsculas y se quitan los espacios exteriores. Se normaliza el texto a Unicode NFC: una vocal seguida de una marca combinante equivale a la vocal acentuada precompuesta.
3. En inglés se admiten A–Z. En español se añaden Á, É, Í, Ó, Ú, Ü y Ñ. Se comprueban los caracteres antes de convertir a mayúsculas, para evitar aceptar letras ajenas al idioma por una conversión que las transforme.
4. Un envío vacío, con caracteres no permitidos o de longitud incorrecta se rechaza sin cambiar el historial ni las oportunidades.
5. Una combinación de letras de la longitud correcta consume un intento. No se consulta un diccionario. Repetir una respuesta válida también consume otra oportunidad.
6. Las tildes y la ñ forman parte de la respuesta: ARBOL no acierta ÁRBOL, ni ANO acierta AÑO. Estos envíos sí consumen un intento porque contienen letras válidas y tienen la longitud correcta.
7. Acertar termina la partida, incluso en el sexto intento. Seis respuestas incorrectas producen una derrota.
8. Tras victoria o derrota se rechazan nuevos envíos. El resultado permite consultar la solución, traducción y ejemplos. Una derrota continúa siendo una derrota aunque se revele la solución.

El motor no actualiza la racha. La integración posterior deberá registrar el acierto únicamente cuando un envío aceptado termine en `WON`, y aplicar por separado la regla de un día de racha por fecha.

## Pistas y letras repetidas

Primero se reservan todas las coincidencias en la posición exacta. Después se recorren las demás letras del intento de izquierda a derecha, utilizando solo las apariciones que quedan disponibles en la solución.

| Estado | Representación prevista en el tablero | Significado |
| --- | --- | --- |
| `CORRECT` | Verde, con símbolo o etiqueta | Letra y posición correctas |
| `PRESENT` | Amarillo, con símbolo o etiqueta | Letra disponible en otra posición |
| `ABSENT` | Rojo, con símbolo o etiqueta | Sin coincidencia disponible, incluidas repeticiones excedentes |

| Solución | Intento | Evaluación de izquierda a derecha |
| --- | --- | --- |
| HOUSE | SHARE | amarillo, amarillo, rojo, rojo, verde |
| CASA | COSA | verde, rojo, verde, verde |
| APPLE | PAPAL | amarillo, amarillo, verde, rojo, amarillo |
| ARRAY | AAAAA | verde, rojo, rojo, verde, rojo |

En APPLE/PAPAL, la segunda A del intento queda roja porque la única A de la solución ya se asignó. En ARRAY/AAAAA, los dos verdes tienen prioridad sobre las otras A.

## Archivos y uso

Dentro de `app/src/main/java/io/github/nyasanchez/lexi/domain/game/`:

- `WordGame.kt`: sesión, seis intentos y transición a victoria o derrota.
- `GameState.kt`: estado observable, historial, errores y resultado de un envío.
- `GuessInput.kt`: normalización y validación por idioma.
- `LetterEvaluator.kt`: comparación de posiciones y cantidades disponibles.

Ejemplo desde Kotlin, con los imports correspondientes:

```kotlin
val entry = requireNotNull(LocalVocabularyRepository().findById("house"))
val game = WordGame(entry.exerciseFor(VocabularyLanguage.ENGLISH))
val first = game.submit("SHARE") // Aceptado, quedan cinco oportunidades.
val second = game.submit("HOUSE") // Aceptado, estado WON.
val solution = game.state.solution // Disponible al terminar.
```

`Accepted` significa que el intento se evaluó; no implica por sí solo un acierto. La interfaz consultará `state.status`. `Rejected` incluye `EMPTY`, `INVALID_CHARACTERS`, `WRONG_LENGTH` o `GAME_FINISHED` para mostrar el mensaje adecuado.

Cada consulta devuelve una copia del historial, incluidas las listas de letras. Modificar una lista devuelta no altera la sesión interna. Una nueva instancia representa otra partida; el guardado y la recuperación no forman parte de este avance.

## Comprobación en Android Studio

En la terminal, desde la raíz del proyecto:

```powershell
.\gradlew.bat :app:testDebugUnitTest
```

La comprobación completa también puede ejecutarse con:

```powershell
.\gradlew.bat :app:assembleDebug :app:testDebugUnitTest :app:lintDebug
```

El informe local de pruebas queda en `app/build/reports/tests/testDebugUnitTest/index.html`. Las nuevas clases son `LetterEvaluatorTest` y `WordGameTest`; se ejecutan junto a las pruebas del vocabulario.

## Verificación del 29 de octubre de 2026

- El APK de depuración compiló con `assembleDebug`.
- Las 43 pruebas unitarias pasaron mediante JUnitCore: las 12 del catálogo y las 31 nuevas del motor.
- Una prueba comprueba 6,561 pares de palabras de cuatro letras sobre el alfabeto A/B/C, verificando posiciones exactas y cantidades de letras disponibles. Otra recorre los 60 ejercicios reales del catálogo en ambos idiomas.
- Se comprobaron los ejemplos del PDF, entradas inválidas, mayúsculas, tildes combinantes, ñ, diéresis, sexto intento ganador, derrota, bloqueo posterior y aislamiento del historial.
- `lintDebug` terminó sin errores y con nueve avisos sobre versiones del SDK, herramientas y dependencias. Permanecen pendientes de la revisión de distribución.
- El código y la configuración del repositorio coinciden con la copia que se compiló y probó.

La tarea `testDebugUnitTest` no terminó correctamente en el entorno automatizado: encontró `AccessDeniedException` al renombrar su informe binario. Se ejecutaron directamente con JUnitCore las mismas clases compiladas, y las 43 pasaron. Antes del commit, ejecutar el comando de pruebas desde Android Studio para verificar también la generación del informe en el entorno local.

La comprobación se hizo en una copia temporal. El entorno también emitió avisos sobre la caché de lint y conservó los símbolos de una biblioteca nativa de terceros en el APK de depuración por un problema de permisos. No se cambió la interfaz ni se instaló esta compilación en el teléfono.

## Commit propuesto

Después de comprobar el resultado local, revisar los cambios en GitHub Desktop y usar:

Summary:

```text
feat: evaluar intentos y letras repetidas
```

Description:

```text
Implementa el motor de partida con seis intentos válidos.
Evalúa posiciones y letras repetidas con prioridad a los aciertos exactos.
Valida longitud, idioma, tildes y ñ sin consumir intentos inválidos.
Distingue victoria y derrota y bloquea envíos después del final.
Añade pruebas del algoritmo, validación y estados de la partida.
```

Pulsar **Commit to main** y después **Push origin**. Este commit acredita la lógica; la prueba del tablero en el celular corresponde al siguiente avance.
