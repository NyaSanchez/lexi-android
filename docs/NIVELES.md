# Niveles y subniveles

Lexi tiene tres niveles de 100 palabras cada uno. Cada nivel se divide en dos subniveles CEFR de 50 palabras.

| Nivel      | Subniveles | Palabras |
|------------|-----------|----------|
| Básico     | A1 y A2   | 100      |
| Intermedio | B1 y B2   | 100      |
| Avanzado   | C1 y C2   | 100      |

La práctica recorre cada nivel en orden: primero el subnivel más sencillo (A1, B1 o C1) y después el segundo (A2, B2 o C2).
El reparto de palabras entre subniveles está en `CefrAssignment.kt` y es una propuesta del equipo, pendiente de validar con el English Vocabulary Profile.

## Cómo se desbloquea un nivel

- Básico está siempre disponible.
- Intermedio se abre al acertar el **70 %** de las palabras de Básico (70 de 100).
- Avanzado se abre al acertar el 70 % de Intermedio, y solo si Básico ya llegó a su meta.
- **Cada modo lleva su propio avance.** El progreso y el desbloqueo se cuentan por separado para «definición en español, responde en inglés» y para «definición en inglés, responde en español». Lo que aciertas en un modo no cuenta en el otro.
- El cálculo usa los resultados que ya guarda la app (`completed_games`). No hay cambios en la base de datos.

La regla vive en `domain/level/LevelRules.kt` y tiene pruebas en `LevelRulesTest`.

## Lo que ve la persona

- **Inicio:** tres tarjetas de nivel con su progreso y el avance por subnivel (por ejemplo `A1 · 12/50`).
  Las tarjetas bloqueadas muestran lo que falta del nivel anterior, por ejemplo «42 de 70 para desbloquear», y no se pueden elegir.
- **Práctica:** carga solo las palabras del nivel elegido. El contador es «Palabra X de 100» dentro del nivel y la tarjeta de definición indica el subnivel, por ejemplo «Nivel Básico · A1».
- **Al alcanzar el 70 %:** al acertar una palabra aparece una tarjeta con dos opciones: *Pasar a Intermedio* o *Seguir en Básico* hasta terminar las 100 palabras.
  Al acertar todas, la tarjeta felicita por completar el nivel.
- **Continuar donde te quedaste:** cada combinación de modo y nivel recuerda su propia palabra.
  La clave de guardado del Básico es la misma que antes de los niveles (`english`, `spanish`), así que nadie pierde su avance actual.
  Los otros niveles usan `english.intermediate`, `spanish.advanced`, etc.
