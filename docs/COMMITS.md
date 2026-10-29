# Desarrollo y commits

## Regla de trabajo

Guardar cada avance coherente cuando se haya comprobado. Un avance puede ser una pantalla, una regla del juego, una corrección o documentación. No esperar al final del proyecto ni crear commits vacíos para aparentar progreso.

Antes de comenzar una sesión, usar Fetch origin y, si hay cambios remotos, Pull origin. Antes de cada commit, revisar los archivos seleccionados y sus diferencias en Changes. Después de guardar el commit, usar Push origin y comprobar el historial en GitHub.

Un commit guarda una versión en la computadora. Push origin envía los commits a GitHub para que el profesor pueda revisarlos.

## Primeros registros

1. Al crear el repositorio en GitHub Desktop con README, Desktop genera `Initial commit`. Ese registro acredita la creación del repositorio.
2. Después de copiar los archivos iniciales de Lexi y revisar su contenido, usar:

   Summary: `docs: definir alcance y plan de desarrollo de Lexi`

   Description:

   ```text
   Documenta los dos modos de práctica, seis intentos y pistas por letra.
   Registra las reglas de racha y las decisiones pendientes del diseño.
   Añade el plan de commits y exclusiones para archivos locales de Android.

   Verificación: documentación contrastada con las nueve páginas del PDF.
   Todavía no hay un proyecto Android ejecutable.
   ```

## Etapas previstas

Los mensajes siguientes son ejemplos para usar cuando el cambio exista y esté comprobado. Cada etapa puede necesitar varios commits.

| Avance | Comprobación antes del commit | Mensaje orientativo |
| --- | --- | --- |
| Proyecto Android inicial | Sincronizar Gradle, compilar y abrir la app | `chore: crear proyecto Android con Kotlin y Compose` |
| Inicio, tema y navegación | Elegir un modo, transmitirlo a la práctica y navegar conservando la selección | `feat: crear pantalla de inicio y navegación principal` |
| Banco de vocabulario | Comprobar palabras, definiciones y traducciones | `feat: añadir vocabulario inicial bilingüe` |
| Motor y evaluación de letras | Probar letras repetidas, validación, seis intentos, acierto y derrota mediante pruebas unitarias | `feat: evaluar intentos y letras repetidas` |
| Tablero de la partida | Conectar el motor y comprobar en el teléfono la escritura, las pistas, los errores y el final de partida | `feat: implementar partida de vocabulario` |
| Resultado y aprendizaje | Revisar traducción, frase y siguiente palabra | `feat: mostrar resultado y ejemplo de uso` |
| Guardado de progreso | Cerrar y abrir la app conservando datos | `feat: guardar progreso local de práctica` |
| Racha diaria | Probar varios aciertos del mismo día y cambios de fecha | `feat: calcular racha y meta diaria` |
| Repaso | Comprobar selección y práctica según las reglas acordadas | `feat: añadir práctica de repaso` |
| Correcciones | Reproducir el error y comprobar que ya no ocurre | `fix: describir la corrección concreta` |
| Preparación de distribución | Verificar compilación de distribución y requisitos vigentes | `chore: preparar versión para pruebas de distribución` |

Las pruebas de una función pueden ir en el mismo commit que su implementación. No separar artificialmente un cambio que necesita sus pruebas para estar completo.

## Qué revisar en Changes

- Incluir código fuente, recursos, documentación y configuración compartida.
- Cuando exista el proyecto, incluir también Gradle Wrapper: `gradlew`, `gradlew.bat` y `gradle/wrapper/`, incluido `gradle-wrapper.jar`.
- Excluir `local.properties`, cachés, compilaciones y archivos de firma o credenciales. El `.gitignore` inicial cubre estos casos habituales; aun así, revisar siempre las diferencias.
- Registrar lo que realmente se verificó. No escribir «pruebas superadas» sin ejecutarlas.
- Conservar los commits de avance que el profesor necesita revisar; evitar reescribir o combinar el historial ya compartido sin una razón acordada.

## Acceso del profesor

Si el repositorio es privado, invitar al profesor como colaborador y esperar que acepte. Si la materia exige un repositorio público, elegir esa visibilidad de forma consciente. Copiar el enlace del repositorio para la entrega.

## Referencia de la herramienta

[Crear un repositorio, hacer commits y publicarlos con GitHub Desktop](https://docs.github.com/en/desktop/overview/creating-your-first-repository-using-github-desktop).
