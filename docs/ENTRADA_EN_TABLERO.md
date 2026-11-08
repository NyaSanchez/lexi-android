# Responder directamente en el tablero

Rama: `feat/respuesta-en-cuadros`. Base: `e11abf9`, versión de `main` descargada el 4 de noviembre de 2026 después de los cambios del equipo.

## Comportamiento

La respuesta se escribe en la fila activa del tablero. Desaparece la barra «Tu respuesta» y se conserva **Comprobar**. Al tocar una casilla aparece el teclado; una casilla ocupada selecciona esa letra para sustituirla y una vacía sitúa la escritura al final del borrador.

Las letras no reciben pistas mientras se escribe. Al comprobar un intento válido, esa misma fila muestra verde (posición correcta), amarillo (otra posición) o rojo (sin coincidencia disponible), además de los símbolos existentes. Si la partida continúa, se habilita la siguiente fila vacía. Las filas comprobadas no se editan.

La fila admite tantas letras como casillas tenga la palabra. Un pegado demasiado largo se rechaza completo, conservando el texto anterior; no se comprueba automáticamente ni se recorta para enviar una palabra distinta. Los caracteres Unicode equivalentes se normalizan antes de contar: una vocal más su acento combinante ocupa una casilla. Las tildes y la ñ siguen siendo necesarias. Los errores de longitud o caracteres no consumen intentos y mantienen el borrador para corregirlo.

Al acertar o agotar los seis intentos, el tablero permanece visible con sus colores. **Ver resultado** abre la pantalla existente de solución, traducción y ejemplos. El resultado se guarda al terminar, independientemente de abrir esa pantalla.

El borrador y la selección se conservan al recrear la actividad. El punto de continuación y los intentos aceptados siguen usando `PracticeSessionStore`, añadido por el equipo. El borrador sin enviar no se incorpora a ese almacenamiento persistente.

## Integración

Se parte de la versión con 100 palabras, racha/meta diaria y recuperación de la sesión. Se reutilizan `WordGame`, la asignación de letras repetidas, Room y la lógica de racha existentes.

Archivos relativos a `app/src/main/java/io/github/nyasanchez/lexi/ui/`:

- `GuessRowEditor.kt`: un editor de Compose con teclado, selección y presentación mediante casillas; admite borrado, pegado y normalización NFC.
- `GuessBoard.kt`: conecta el editor a la fila activa y comparte el dibujo de casillas con las filas evaluadas.
- `PracticeScreen.kt`: sustituye el campo separado, mantiene la validación y permite ver los colores finales antes de abrir el resultado.

El editor mantiene una única semántica de entrada de texto para accesibilidad. En palabras largas o con letra grande, la fila puede desplazarse horizontalmente y la casilla seleccionada se lleva a la zona visible.

## Comprobación manual

La práctica continúa desde la palabra recordada. Usar la definición y el idioma visibles; no asumir que siempre abrirá HOUSE/CASA. Los siguientes ejemplos sirven cuando se llega a esas entradas.

1. Tocar la fila activa, escribir dos letras y pulsar Comprobar. Debe indicar longitud incorrecta sin consumir un intento y conservar las letras.
2. Para HOUSE, escribir SHARE. Antes de comprobar, las casillas siguen sin pistas. Después muestran amarillo, amarillo, rojo, rojo y verde en esa misma fila.
3. En la siguiente fila escribir HAXSE; tocar A y escribir O, después tocar X y escribir U. Debe quedar HOUSE. Girar antes de comprobar y verificar el borrador.
4. Comprobar HOUSE: la fila queda verde. Pulsar Ver resultado para consultar ejemplos y seguir con una palabra nueva, una fila vacía e intentos en cero.
5. En modo español, probar una palabra con tilde o ñ; para ÁRBOL, ARBOL falla y ÁRBOL acierta. Revisar borrar y volver a escribir una letra.
6. Enviar seis respuestas válidas incorrectas: comprobar la sexta fila coloreada, Intentos agotados y Ver resultado. Volver a Inicio y entrar otra vez para comprobar la continuación de la sesión.

## Pruebas

`BoardInputTest` cubre escritura sin evaluación anticipada, comprobación en la fila original, rotación, sustitución de una letra tocada, borrado de selección, rechazo de un pegado largo, errores sin consumir intentos, acentos combinantes y ñ. `PracticeFlowTest` se adapta a Ver resultado y a la continuación de sesión incorporada en `main`.

Las pruebas de pantalla aíslan las preferencias de sesión y las restauran al terminar; sus partidas pueden añadir resultados a la base de datos de depuración. Ejecutarlas en un emulador de pruebas:

```powershell
.\gradlew.bat :app:connectedDebugAndroidTest
```

Compilación y análisis:

```powershell
.\gradlew.bat :app:assembleDebug :app:assembleDebugAndroidTest :app:testDebugUnitTest :app:lintDebug
```

## Resultados de validación

### Validación realizada el 4 de noviembre de 2026

- APK de la app y APK de pruebas compilados. Lint: 0 errores y 15 advertencias.
- JUnitCore: 54 pruebas ejecutadas, 50 aprobadas y los cuatro fallos del catálogo descritos abajo.
- Las 12 pruebas instrumentadas compilan; su ejecución automatizada en emulador está pendiente.
- La persona responsable confirmó en su Xiaomi que desapareció «Tu respuesta», HO conserva el borrador sin consumir intentos, SHARE colorea la misma fila correctamente y se habilita la siguiente.
- También confirmó la sustitución de A/X en HAXSE para formar HOUSE, la conservación del borrador al girar, la fila verde al acertar y el acceso a los ejemplos mediante Ver resultado.
- En modo español confirmó CASA, MANZANA y la distinción entre ARBOL (incorrecto) y ÁRBOL (correcto), escribiendo directamente en las casillas.

### Fallos existentes en la base del PR y limitaciones

La batería local contiene 54 pruebas. Cuatro pruebas de `LocalVocabularyRepositoryTest` fallan con los datos heredados de `main`: tres aún esperan 30 pares o 60 ejercicios, y una exige que el ejemplo de `hunger` incluya literalmente esa palabra. El catálogo ya tiene 100 pares / 200 ejercicios y el ejemplo de esa entrada usa otra forma. El catálogo, el motor y sus pruebas locales no se modificaron en esta rama; estos fallos deben comunicarse al equipo, sin presentarlos como pruebas aprobadas.

El entorno del asistente también restringe el renombrado del informe binario de Gradle. El resultado directo de JUnitCore permite distinguir los fallos de aserción de esa limitación del entorno.

## Commit y pull request

Summary sugerido:

```text
feat: escribir respuestas directamente en el tablero
```

Revisar los cambios, hacer Commit to feat/respuesta-en-cuadros y Publish branch (o Push origin). Crear el PR con base `main` y compare `feat/respuesta-en-cuadros`. Incluir los resultados reales de pruebas y los fallos previos del catálogo. La integración queda para la revisión del equipo.
