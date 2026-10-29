# Inicio y navegación

Este documento conserva el registro de la etapa de interfaz. El siguiente avance añade el [banco de vocabulario](VOCABULARIO_INICIAL.md), cuya conexión con la partida sigue pendiente.

## Alcance del avance

- Inicio con la identidad violeta de Lexi, una tarjeta de meta diaria y selección mediante dos opciones de radio.
- Definición en español para responder en inglés; definición en inglés para responder en español.
- Nivel inicial fijo. El catálogo de niveles aún no está definido y por eso el campo no simula un desplegable.
- Navegación inferior entre Inicio, Repaso y Progreso con Navigation Compose.
- «Empezar» abre una ruta de práctica que recibe el modo elegido. Todavía no hay vocabulario; se muestra el estado vacío y se puede regresar.
- Repaso y Progreso tienen estados vacíos que corresponden a una persona sin práctica registrada. No se muestran días de racha ni aciertos ficticios.
- Selección del modo conservada con `rememberSaveable` al navegar, girar el dispositivo o recrearse la actividad por el sistema. No es todavía una preferencia persistente entre cierres voluntarios de la app.
- Diseño desplazable para pantallas pequeñas, colores claros y oscuros, controles con etiquetas y opciones de radio accesibles.

## Comprobaciones

1. Abrir Lexi y comprobar la tarjeta de meta, las dos opciones y las tres pestañas.
2. En el modo «Definición en español», pulsar «Empezar»: debe aparecer «Escribe en inglés».
3. Volver y elegir «Definition in English». Al empezar debe aparecer «Escribe en español».
4. Regresar con la flecha, con el botón «Volver al inicio» y con el botón Atrás de Android. Confirmar que la selección anterior se conserva.
5. Abrir Repaso y Progreso y volver a Inicio. Confirmar que la selección se mantiene.
6. Pulsar varias veces las pestañas. Desde Repaso o Progreso, Atrás debe regresar a Inicio sin recorrer pantallas duplicadas.
7. Con el segundo modo seleccionado, girar el dispositivo y comprobar que sigue seleccionado; si el contenido no cabe, comprobar el desplazamiento.
8. Comprobar también el modo claro y oscuro, y un tamaño de fuente mayor.

Para compilar y revisar Android:

```powershell
.\gradlew.bat :app:assembleDebug :app:lintDebug
```

## Límites de esta entrega

«No hay palabras disponibles» es el estado esperado de la práctica en este avance. El banco de vocabulario y la partida se añadirán en los próximos commits. Los accesos a ajustes y pronunciación, las estadísticas y la persistencia quedan pendientes. No es todavía una versión lista para Google Play.

## Resultado de la revisión del 29 de octubre de 2026

- `assembleDebug`: correcto. La nueva versión se instaló y abrió en el teléfono conectado.
- `lintDebug`: terminó con cero errores y nueve avisos sobre versiones más recientes de SDK, herramientas y dependencias.
- Revisión visual: Inicio en modo oscuro, sin textos cortados ni controles fuera de la pantalla del teléfono utilizado.
- Prueba manual confirmada por la persona autora: el modo «Definition in English» abre «Escribe en español»; Repaso y Progreso abren correctamente; al volver, el modo elegido se conserva.
- Pendiente de comprobar manualmente: rotación, texto grande, modo claro y el conjunto completo de casos anteriores. No se afirma que haya pruebas automatizadas de interacción.

La compilación se verificó en una copia temporal con los mismos archivos de código y configuración, debido a la limitación de permisos de Java del entorno automatizado. Este entorno emitió avisos sobre cachés de lint y conservó los símbolos de una biblioteca nativa de terceros en el APK de depuración. El teléfono permitió instalar y consultar la interfaz, pero bloqueó la inyección de pulsaciones por USB; las interacciones anteriores fueron comprobadas por la persona autora.

## Commit propuesto

Summary:

```text
feat: crear pantalla de inicio y navegación principal
```

Description:

```text
Añade la meta diaria y la selección del modo de práctica.
Implementa Inicio, Repaso y Progreso con navegación inferior.
Conserva la selección del modo y la transmite a la práctica.
Añade estados vacíos y adapta los colores al tema claro y oscuro.
```

Añadir a la descripción únicamente las comprobaciones realmente realizadas. Después del commit, usar Push origin y revisar el registro en GitHub.

## Referencias de implementación

- [Navigation de Android](https://developer.android.com/guide/navigation).
- [Conservar estado de la interfaz en Compose](https://developer.android.com/develop/ui/compose/state-saving).
