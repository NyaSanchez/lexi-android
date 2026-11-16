# Validación técnica de la base auditada — issue #5

**Fecha:** 16 de noviembre de 2026.  
**Código auditado:** [55407ef](https://github.com/NyaSanchez/lexi-android/commit/55407ef0cfb49071f4456c8f8e1710d65557f26f).  
**Entorno:** Windows, JDK de Android Studio 25, Gradle 9.5.1, compileSdk/targetSdk 36 y minSdk 26.  
**Método:** copia temporal del código original, sin las modificaciones experimentales de la auditoría; dependencias locales con `--offline`.

## Resultado de las tareas

| Tarea | Resultado observado |
| --- | --- |
| `:app:assembleDebug` | APK de desarrollo generado. |
| `:app:assembleDebugAndroidTest` | APK de pruebas generado; no ejecutado en dispositivo. |
| `:app:testDebugUnitTest` | 70 pruebas aprobadas; 0 fallos, 0 errores y 0 omitidas. |
| `:app:lintDebug` | Falló: 2 errores y 10 advertencias. |

**El comando conjunto terminó con error por Lint.** Este resultado es un hallazgo de la auditoría y no se ocultó mediante exclusiones, supresiones ni cambios en el código.

```powershell
.\gradlew.bat :app:assembleDebug :app:assembleDebugAndroidTest :app:testDebugUnitTest :app:lintDebug --continue --no-daemon --console=plain --offline
```

## Pruebas existentes

| Clase | Casos | Fallos | Errores |
| --- | --- | --- | --- |
| SavedGameTest | 7 | 0 | 0 |
| LocalVocabularyRepositoryTest | 6 | 0 | 0 |
| VocabularyCatalogTest | 2 | 0 | 0 |
| VocabularyEntryTest | 6 | 0 | 0 |
| GameRestoreTest | 4 | 0 | 0 |
| LetterEvaluatorTest | 10 | 0 | 0 |
| WordGameTest | 21 | 0 | 0 |
| LevelRulesTest | 8 | 0 | 0 |
| ReviewRulesTest | 6 | 0 | 0 |

## Hallazgos de Lint

| Identificador | Severidad | Cantidad |
| --- | --- | --- |
| GradleDependency | Warning | 1 |
| NewApi | Error | 2 |
| OldTargetApi | Warning | 1 |
| PluralsCandidate | Warning | 6 |
| UnusedResources | Warning | 1 |
| UseKtx | Warning | 1 |

Los dos errores `NewApi` se refieren a `android:windowLightNavigationBar`, que requiere API 27, en los temas generales claro y oscuro de una app con minSdk 26. Se registran como H-08 en el informe; no se corrigieron en este PR.

Las advertencias incluyen actualizaciones de SDK, candidatos a plurales, un recurso sin uso y una sugerencia de KTX. Son observaciones de la herramienta, no evidencia por sí solas de un fallo reproducido en el dispositivo.

## Comprobación estructural y de contraste

Un análisis externo al proyecto examinó las 300 entradas (100 por nivel). No encontró IDs o respuestas duplicados, campos vacíos, espacios exteriores, texto fuera de NFC, ejemplos sin su palabra completa ni definiciones que incluyeran su propia respuesta como palabra completa. Esta comprobación no sustituye la revisión del significado ni certifica la clasificación MCER.

Se verificó que los colores medidos en las 22 combinaciones de `contraste.csv` coinciden con los del código original. No fue necesario modificar la paleta para elaborar la auditoría.

## Límites y trazabilidad

Los resultados anteriores de 71 pruebas y Lint sin errores correspondían a una compilación experimental que incluía cambios y una prueba unitaria adicional. No describen la base auditada; este registro los sustituye para el PR documental.

Las observaciones manuales del tutorial y la edición con TalkBack también se obtuvieron de una compilación experimental. Se conservan en el apartado 5.1 del informe, diferenciadas de los casos pendientes sobre la base.

La compilación de los APK de pruebas no implica que los casos instrumentados hayan sido ejecutados. No se publicó una versión de Google Play como parte de esta auditoría.
