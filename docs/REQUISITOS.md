# Requisitos iniciales de Lexi

Fuente: «Idea de App.pdf», nueve páginas suministradas por la persona autora del proyecto. Esta transcripción separa las reglas del documento de las decisiones que todavía faltan por tomar. Ninguna función se considera implementada por aparecer aquí.

## Objetivo (página 1)

Practicar vocabulario y escritura con una meta diaria sencilla: acertar una palabra completa. La aplicación muestra una definición y la persona escribe la palabra en el otro idioma.

## Inicio y modos (páginas 2 y 3)

- Mostrar la racha y la meta del día.
- Elegir el idioma antes de comenzar la partida.
- Modo habitual: definición en español y respuesta en inglés. Ejemplo: «Edificio donde vive una persona» → HOUSE.
- Modo inverso: definición en inglés y respuesta en español. Ejemplo: «A building where someone lives» → CASA.
- Navegación principal: Inicio, Repaso y Progreso.
- El diseño incluye un selector de nivel inicial; el catálogo y comportamiento de los niveles quedan pendientes.

## Partida y evaluación (páginas 4, 5, 6 y 8)

- Indicar en qué idioma se debe responder.
- Mostrar la longitud de la palabra y permitir hasta seis intentos válidos.
- Aplicar la evaluación cuando se envíe el intento.
- Verde y símbolo de acierto: la letra coincide en esa posición.
- Amarillo y símbolo de cambio de posición: existe una coincidencia disponible en otra posición.
- Rojo y símbolo de error: no hay una coincidencia disponible para esa letra, incluida una repetición excedente.
- Acompañar los colores con símbolos o etiquetas.
- Asignar primero los verdes y después los amarillos usando solo las apariciones restantes de cada letra objetivo.
- Aceptar mayúsculas y minúsculas; permitir tildes y ñ en español.
- Consumir un intento solo cuando el envío sea válido.
- Mantener un idioma por partida. Cambiar de modo inicia un reto nuevo, conservando el progreso y la racha compartidos.
- Al agotar intentos, mostrar la solución y permitir practicar otra palabra. Revelar la solución no completa la meta ni suma racha.

### Ejemplos que deberán comprobar las pruebas

| Objetivo | Intento | Resultado de izquierda a derecha |
| --- | --- | --- |
| HOUSE | SHARE | amarillo, amarillo, rojo, rojo, verde |
| CASA | COSA | verde, rojo, verde, verde |
| APPLE | PAPAL | amarillo, amarillo, verde, rojo, amarillo |
| HOUSE | HOUSE | verde, verde, verde, verde, verde |

## Acierto y progreso (páginas 7 y 9)

- Confirmar el acierto cuando la palabra completa sea correcta.
- Mostrar la traducción y una frase de ejemplo.
- El primer acierto del día completa la meta y actualiza la racha; los siguientes aciertos del mismo día no vuelven a sumar un día.
- Permitir seguir practicando y consultar el progreso.
- El diseño de Progreso muestra días completados y el número de palabras acertadas hoy.

## Diseño visual

- Nombre visible en las maquetas: Lexi.
- Color principal violeta, fondos claros y tarjetas redondeadas.
- Tablero de letras con colores, símbolos y una leyenda.
- Navegación inferior con Inicio, Repaso y Progreso.
- Usar controles, teclado, barras del sistema y comportamiento adecuados para Android.
- La maqueta muestra accesos a Ajustes y audio, pero no desarrolla todas sus reglas.

## Decisiones pendientes antes de implementar cada función

- Qué constituye un envío válido: longitud, caracteres permitidos y necesidad o no de pertenecer a un diccionario. El ejemplo PAPAL permite probar el algoritmo; no define por sí mismo la validación del vocabulario.
- Si se exige la tilde exacta para acertar y cómo se normaliza el texto; admitir tildes no equivale a ignorarlas.
- Fuente, revisión y permiso de uso del banco de palabras, definiciones y frases.
- Cómo se seleccionan palabras, se distribuyen niveles y se evitan repeticiones.
- Qué palabras se incluyen en Repaso y cómo se practica con ellas.
- Regla de reinicio de racha tras un día sin aciertos, zona horaria y cambios de fecha.
- Recuperación de una partida al cerrar o girar la aplicación.
- Comportamiento de Ajustes y del botón de pronunciación.

## Propuesta técnica inicial

Kotlin y Jetpack Compose, elegidos por la persona autora del proyecto. Como propuesta para la primera versión, usar un banco de palabras y progreso locales, sin requerir una cuenta de usuario. Esta propuesta no procede del PDF y podrá ajustarse antes de implementar los datos.

## Preparación futura para Google Play

Al completar y probar la aplicación se revisarán los requisitos vigentes de publicación, el identificador de la app, la firma, la ficha, las pruebas de distribución y las declaraciones que correspondan a las funciones y datos reales de la app. La preparación de este repositorio no implica que la app esté publicada ni aprobada.
