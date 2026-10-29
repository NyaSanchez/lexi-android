# Lexi

Aplicación nativa para Android que permite practicar vocabulario y escritura en español e inglés mediante definiciones, intentos y pistas por letra.

## Estado actual

Etapa inicial: documentación de la idea y preparación del control de versiones. Todavía no existe una aplicación ejecutable ni se ha generado el proyecto de Android Studio.

El objetivo es desarrollar y probar la aplicación por etapas hasta preparar su publicación en Google Play.

## Cómo funcionará

- Una definición en español pide una palabra en inglés; una definición en inglés pide una palabra en español.
- Cada palabra permite hasta seis intentos válidos.
- Las pistas distinguen letras correctas, letras en otra posición y letras sin coincidencia disponible.
- El primer acierto de cada día completa la meta y actualiza la racha una sola vez.
- Al acertar, se muestran la traducción y una frase de ejemplo.
- Las secciones principales serán Inicio, Repaso y Progreso.

## Tecnología elegida

- Android Studio.
- Kotlin y Jetpack Compose para la aplicación nativa.
- Git, GitHub y GitHub Desktop para el control de versiones.

La persistencia y las dependencias se concretarán al implementar cada etapa. Las versiones de las herramientas se registrarán cuando se genere el proyecto.

## Documentación

- [Reglas y alcance](docs/REQUISITOS.md): requisitos extraídos del PDF de referencia y decisiones pendientes.
- [Plan y rutina de commits](docs/COMMITS.md): avances previstos, validación y mensajes de ejemplo.

## Referencia de diseño

La especificación inicial procede de las nueve páginas de «Idea de App.pdf», proporcionado por la persona autora del proyecto. Los diseños muestran el nombre Lexi, una paleta violeta, tarjetas claras y navegación inferior. Los elementos propios del dispositivo de las maquetas se adaptarán a Android.

## Seguimiento académico

Cada avance funcional o documental se guardará en un commit descriptivo y se enviará a GitHub. El historial debe reflejar el trabajo real y permitir revisar qué cambió y cómo se comprobó.

## Ejecución

Pendiente de crear el proyecto en Android Studio. Esta sección se actualizará con los pasos de compilación y ejecución cuando exista el primer proyecto ejecutable.
