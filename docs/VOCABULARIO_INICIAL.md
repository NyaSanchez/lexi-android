# Banco inicial de vocabulario

## Alcance

Se incluyen 30 conceptos cotidianos. Cada uno tiene una palabra, una definición y una frase de ejemplo en español y en inglés. Las frases de cada par expresan la misma idea en ambos idiomas.

Los textos se redactaron específicamente para Lexi durante este desarrollo. No se importó un diccionario ni una API de traducción. El contenido está disponible dentro de la aplicación sin Internet.

La etiqueta de nivel `INITIAL` es una clasificación interna del proyecto; no representa una certificación de nivel lingüístico.

## Cómo se usa en los dos modos

El parámetro `answerLanguage` siempre indica el idioma en el que la persona debe responder:

| Idioma de respuesta | Definición mostrada | Respuesta de ejemplo | Longitud |
| --- | --- | --- | --- |
| `ENGLISH` | Edificio donde vive una persona o una familia. | house | 5 |
| `SPANISH` | A building where a person or a family lives. | casa | 4 |

La traducción y los ejemplos también cambian de dirección. La futura pantalla de juego deberá mostrar inicialmente solo la definición y la longitud. Reservará la respuesta y los ejemplos para la pantalla de resultado, según las reglas de acierto o intentos agotados.

Treinta conceptos permiten preparar sesenta ejercicios, contando los dos sentidos. No son sesenta palabras diferentes por idioma.

## Archivos

Todos están dentro de `app/src/main/java/io/github/nyasanchez/lexi/data/vocabulary/`:

- `InitialVocabulary.kt`: contenido de las treinta entradas.
- `VocabularyEntry.kt`: modelos del concepto bilingüe y del ejercicio, idiomas y nivel.
- `LocalVocabularyRepository.kt`: consulta por ID, listado por nivel y generación de ejercicios según el idioma.

Las pruebas están en `app/src/test/java/io/github/nyasanchez/lexi/data/vocabulary/`.

El repositorio toma una copia del catálogo. Rechaza IDs duplicados para evitar que una entrada sobrescriba otra y devuelve `null` cuando un ID no existe. Un catálogo vacío devuelve listas vacías.

## Reglas para añadir o corregir palabras

1. Crear un ID único, estable y sin espacios. Conservar ese ID cuando se corrija un texto: servirá para enlazar el progreso y el repaso.
2. Escribir una sola palabra en minúsculas por idioma. Este banco inicial todavía no admite expresiones de varias palabras ni guiones.
3. Conservar las tildes, la diéresis y la ñ del español. Usar caracteres Unicode precompuestos: por ejemplo, `á` en lugar de `a` seguida de una marca combinante.
4. Añadir ambas definiciones y ambas frases. Los ejemplos deben contener la palabra y expresar la misma idea.
5. Revisar la ortografía y la correspondencia de las traducciones. Las pruebas automáticas detectan fallos estructurales, pero no sustituyen la revisión del significado.
6. Ejecutar las pruebas después del cambio. Si se amplía deliberadamente el catálogo, actualizar el tamaño esperado y la documentación.

Estas restricciones describen el contenido editorial almacenado. No son todavía las reglas para validar lo que escriba la persona: la aceptación de mayúsculas, normalización del teclado y política de tildes de los intentos se implementarán con el juego.

## Catálogo

| ID | Español | Inglés |
| --- | --- | --- |
| `house` | casa | house |
| `apple` | manzana | apple |
| `tree` | árbol | tree |
| `book` | libro | book |
| `sun` | sol | sun |
| `moon` | luna | moon |
| `water` | agua | water |
| `bread` | pan | bread |
| `milk` | leche | milk |
| `cat` | gato | cat |
| `dog` | perro | dog |
| `bird` | pájaro | bird |
| `fish` | pez | fish |
| `hand` | mano | hand |
| `eye` | ojo | eye |
| `door` | puerta | door |
| `table` | mesa | table |
| `chair` | silla | chair |
| `flower` | flor | flower |
| `cloud` | nube | cloud |
| `fire` | fuego | fire |
| `forest` | bosque | forest |
| `shoe` | zapato | shoe |
| `shirt` | camisa | shirt |
| `key` | llave | key |
| `pencil` | lápiz | pencil |
| `egg` | huevo | egg |
| `cheese` | queso | cheese |
| `mountain` | montaña | mountain |
| `year` | año | year |

## Comprobación en Android Studio

Sincronizar Gradle después de incorporar la dependencia de JUnit. Se pueden ejecutar las clases de prueba con el botón de ejecución que aparece junto a cada clase en el editor.

También se puede usar la terminal de Android Studio desde la raíz:

```powershell
.\gradlew.bat :app:testDebugUnitTest
```

Para comprobar además la compilación y Android lint:

```powershell
.\gradlew.bat :app:assembleDebug :app:testDebugUnitTest :app:lintDebug
```

El informe de pruebas se genera en `app/build/reports/tests/testDebugUnitTest/index.html`.

## Verificación del 29 de octubre de 2026

- El APK de depuración compiló correctamente con `assembleDebug`.
- `lintDebug` terminó con cero errores. Permanecen nueve avisos sobre versiones más recientes del SDK, herramientas y dependencias.
- Las doce pruebas unitarias pasaron al ejecutarse directamente con JUnitCore: `OK (12 tests)`.
- Cubren la correspondencia de los idiomas, los ejemplos, treinta IDs y pares distintos, sesenta ejercicios, tildes y ñ, letras repetidas, campos incompletos, IDs duplicados, catálogo vacío y aislamiento frente a cambios en listas externas.
- Se compararon los archivos de código y configuración del repositorio con los de la copia compilada y probada; coinciden.

La tarea `testDebugUnitTest` de Gradle encontró `AccessDeniedException` al renombrar su informe binario dentro del entorno automatizado. Por eso no se registra esa tarea como exitosa: se ejecutaron las mismas clases compiladas mediante JUnitCore para comprobar sus doce resultados. Conviene ejecutar el comando de pruebas anterior desde Android Studio y confirmar que también se genera el informe local antes del commit.

La comprobación se hizo en una copia temporal. Como en los avances anteriores, el entorno emitió avisos sobre la caché de lint y no pudo quitar símbolos de una biblioteca nativa de terceros, que se conservó en el APK de depuración. No se cambió la interfaz ni se instaló una nueva versión en el teléfono durante esta etapa de datos.

## Qué cambia en la app visible

Este commit incorpora y prueba los datos internos. La interfaz conserva las pantallas del avance anterior; la ruta de práctica todavía no consume el repositorio. El mensaje de práctica sin palabras se sustituirá cuando se conecten los datos y el estado de la partida.

El banco no elige una palabra al azar, no calcula aciertos, no valida intentos, no asigna colores y no actualiza rachas. Son las siguientes responsabilidades del desarrollo.

## Commit propuesto

Summary:

```text
feat: añadir vocabulario inicial bilingüe
```

Description:

```text
Añade 30 pares de palabras con definiciones y ejemplos originales.
Prepara ejercicios en ambos idiomas mediante un repositorio local.
Valida el contenido editorial y los identificadores del catálogo.
Incluye pruebas para traducciones, tildes, ñ y datos incompletos.
```

Después del commit, usar Push origin.

## Referencia técnica

[Pruebas unitarias locales de Android](https://developer.android.com/training/testing/local-tests).
