package io.github.nyasanchez.lexi.data.vocabulary

/**
 * Banco inicial de 30 conceptos. Definiciones y ejemplos redactados para Lexi.
 * Mantener los IDs al corregir contenido: podrán referenciarse desde progreso y repaso.
 */
internal object InitialVocabulary {
    val entries: List<VocabularyEntry> = listOf(
        VocabularyEntry(
            id = "house",
            spanish = VocabularyText(
                word = "casa",
                definition = "Edificio donde vive una persona o una familia.",
                example = "Esta es mi casa.",
            ),
            english = VocabularyText(
                word = "house",
                definition = "A building where a person or a family lives.",
                example = "This is my house.",
            ),
        ),
        VocabularyEntry(
            id = "apple",
            spanish = VocabularyText(
                word = "manzana",
                definition = "Fruta redonda, de pulpa firme, que suele ser roja, verde o amarilla.",
                example = "La manzana está sobre la mesa.",
            ),
            english = VocabularyText(
                word = "apple",
                definition = "A round fruit with firm flesh that is often red, green or yellow.",
                example = "The apple is on the table.",
            ),
        ),
        VocabularyEntry(
            id = "tree",
            spanish = VocabularyText(
                word = "árbol",
                definition = "Planta con un tronco leñoso del que salen ramas.",
                example = "El árbol da sombra.",
            ),
            english = VocabularyText(
                word = "tree",
                definition = "A plant with a woody trunk from which branches grow.",
                example = "The tree gives shade.",
            ),
        ),
        VocabularyEntry(
            id = "book",
            spanish = VocabularyText(
                word = "libro",
                definition = "Conjunto de páginas unidas que contienen un texto para leer.",
                example = "Leo un libro antes de dormir.",
            ),
            english = VocabularyText(
                word = "book",
                definition = "A set of pages joined together containing text to read.",
                example = "I read a book before bed.",
            ),
        ),
        VocabularyEntry(
            id = "sun",
            spanish = VocabularyText(
                word = "sol",
                definition = "Estrella que proporciona luz y calor a la Tierra.",
                example = "El sol ilumina el jardín.",
            ),
            english = VocabularyText(
                word = "sun",
                definition = "The star that gives Earth light and heat.",
                example = "The sun lights up the garden.",
            ),
        ),
        VocabularyEntry(
            id = "moon",
            spanish = VocabularyText(
                word = "luna",
                definition = "Satélite natural que gira alrededor de la Tierra.",
                example = "La luna brilla esta noche.",
            ),
            english = VocabularyText(
                word = "moon",
                definition = "The natural satellite that travels around Earth.",
                example = "The moon is shining tonight.",
            ),
        ),
        VocabularyEntry(
            id = "water",
            spanish = VocabularyText(
                word = "agua",
                definition = "Líquido transparente que bebemos para hidratarnos.",
                example = "Hay agua en el vaso.",
            ),
            english = VocabularyText(
                word = "water",
                definition = "A clear liquid that people drink to stay hydrated.",
                example = "There is water in the glass.",
            ),
        ),
        VocabularyEntry(
            id = "bread",
            spanish = VocabularyText(
                word = "pan",
                definition = "Alimento preparado con masa de harina y agua, normalmente horneado.",
                example = "Compro pan por la mañana.",
            ),
            english = VocabularyText(
                word = "bread",
                definition = "Food made from a mixture of flour and water that is usually baked.",
                example = "I buy bread in the morning.",
            ),
        ),
        VocabularyEntry(
            id = "milk",
            spanish = VocabularyText(
                word = "leche",
                definition = "Líquido blanco que producen las vacas y otros mamíferos para alimentar a sus crías.",
                example = "La leche está en el refrigerador.",
            ),
            english = VocabularyText(
                word = "milk",
                definition = "A white liquid produced by cows and other mammals to feed their young.",
                example = "The milk is in the refrigerator.",
            ),
        ),
        VocabularyEntry(
            id = "cat",
            spanish = VocabularyText(
                word = "gato",
                definition = "Animal doméstico que maúlla y suele cazar ratones.",
                example = "El gato duerme en el sofá.",
            ),
            english = VocabularyText(
                word = "cat",
                definition = "A domestic animal that meows and often hunts mice.",
                example = "The cat sleeps on the sofa.",
            ),
        ),
        VocabularyEntry(
            id = "dog",
            spanish = VocabularyText(
                word = "perro",
                definition = "Animal doméstico que ladra y suele acompañar a las personas.",
                example = "Mi perro juega en el parque.",
            ),
            english = VocabularyText(
                word = "dog",
                definition = "A domestic animal that barks and often lives as a companion to people.",
                example = "My dog plays in the park.",
            ),
        ),
        VocabularyEntry(
            id = "bird",
            spanish = VocabularyText(
                word = "pájaro",
                definition = "Animal con plumas, pico y dos alas.",
                example = "El pájaro canta por la mañana.",
            ),
            english = VocabularyText(
                word = "bird",
                definition = "An animal with feathers, a beak and two wings.",
                example = "The bird sings in the morning.",
            ),
        ),
        VocabularyEntry(
            id = "fish",
            spanish = VocabularyText(
                word = "pez",
                definition = "Animal acuático que respira por branquias y se mueve con aletas.",
                example = "El pez nada en el lago.",
            ),
            english = VocabularyText(
                word = "fish",
                definition = "An aquatic animal that breathes through gills and moves using fins.",
                example = "The fish swims in the lake.",
            ),
        ),
        VocabularyEntry(
            id = "hand",
            spanish = VocabularyText(
                word = "mano",
                definition = "Parte del cuerpo al final del brazo, con la que sujetamos objetos.",
                example = "Levanto la mano para preguntar.",
            ),
            english = VocabularyText(
                word = "hand",
                definition = "The body part at the end of the arm that we use to hold things.",
                example = "I raise my hand to ask a question.",
            ),
        ),
        VocabularyEntry(
            id = "eye",
            spanish = VocabularyText(
                word = "ojo",
                definition = "Órgano del cuerpo que usamos para ver.",
                example = "Cierro un ojo para mirar por el telescopio.",
            ),
            english = VocabularyText(
                word = "eye",
                definition = "A body organ that we use to see.",
                example = "I close one eye to look through the telescope.",
            ),
        ),
        VocabularyEntry(
            id = "door",
            spanish = VocabularyText(
                word = "puerta",
                definition = "Pieza que se abre y se cierra para permitir el paso a una habitación o edificio.",
                example = "La puerta está abierta.",
            ),
            english = VocabularyText(
                word = "door",
                definition = "A panel that opens and closes to let people enter a room or building.",
                example = "The door is open.",
            ),
        ),
        VocabularyEntry(
            id = "table",
            spanish = VocabularyText(
                word = "mesa",
                definition = "Mueble con una superficie plana sobre la que comemos o trabajamos.",
                example = "La mesa está junto a la ventana.",
            ),
            english = VocabularyText(
                word = "table",
                definition = "A piece of furniture with a flat surface used for eating or working.",
                example = "The table is next to the window.",
            ),
        ),
        VocabularyEntry(
            id = "chair",
            spanish = VocabularyText(
                word = "silla",
                definition = "Asiento para una persona, normalmente con respaldo y cuatro patas.",
                example = "La silla es de madera.",
            ),
            english = VocabularyText(
                word = "chair",
                definition = "A seat for one person, usually with a back and four legs.",
                example = "The chair is made of wood.",
            ),
        ),
        VocabularyEntry(
            id = "flower",
            spanish = VocabularyText(
                word = "flor",
                definition = "Parte de una planta que suele tener pétalos de colores.",
                example = "Esta flor es amarilla.",
            ),
            english = VocabularyText(
                word = "flower",
                definition = "The part of a plant that often has colorful petals.",
                example = "This flower is yellow.",
            ),
        ),
        VocabularyEntry(
            id = "cloud",
            spanish = VocabularyText(
                word = "nube",
                definition = "Masa de pequeñas gotas de agua o cristales de hielo que vemos en el cielo.",
                example = "Una nube cubre el sol.",
            ),
            english = VocabularyText(
                word = "cloud",
                definition = "A mass of tiny water droplets or ice crystals that we see in the sky.",
                example = "A cloud covers the sun.",
            ),
        ),
        VocabularyEntry(
            id = "fire",
            spanish = VocabularyText(
                word = "fuego",
                definition = "Fenómeno que produce llamas, luz y calor cuando algo arde.",
                example = "Vemos el fuego desde lejos.",
            ),
            english = VocabularyText(
                word = "fire",
                definition = "The flames, light and heat produced when something burns.",
                example = "We watch the fire from a distance.",
            ),
        ),
        VocabularyEntry(
            id = "forest",
            spanish = VocabularyText(
                word = "bosque",
                definition = "Lugar extenso con muchos árboles que crecen juntos.",
                example = "Caminamos por el bosque.",
            ),
            english = VocabularyText(
                word = "forest",
                definition = "A large area with many trees growing together.",
                example = "We walk through the forest.",
            ),
        ),
        VocabularyEntry(
            id = "shoe",
            spanish = VocabularyText(
                word = "zapato",
                definition = "Calzado que cubre el pie y normalmente deja libre el tobillo.",
                example = "El zapato está debajo de la cama.",
            ),
            english = VocabularyText(
                word = "shoe",
                definition = "Footwear that covers the foot and usually leaves the ankle uncovered.",
                example = "The shoe is under the bed.",
            ),
        ),
        VocabularyEntry(
            id = "shirt",
            spanish = VocabularyText(
                word = "camisa",
                definition = "Prenda que cubre el torso y que suele tener cuello, mangas y botones.",
                example = "Mi camisa es azul.",
            ),
            english = VocabularyText(
                word = "shirt",
                definition = "A garment worn on the upper body that usually has a collar, sleeves and buttons.",
                example = "My shirt is blue.",
            ),
        ),
        VocabularyEntry(
            id = "key",
            spanish = VocabularyText(
                word = "llave",
                definition = "Objeto pequeño que se introduce en una cerradura para abrirla o cerrarla.",
                example = "La llave está en mi bolsillo.",
            ),
            english = VocabularyText(
                word = "key",
                definition = "A small object inserted into a lock to open or close it.",
                example = "The key is in my pocket.",
            ),
        ),
        VocabularyEntry(
            id = "pencil",
            spanish = VocabularyText(
                word = "lápiz",
                definition = "Instrumento de escritura que tiene una mina de grafito.",
                example = "Escribo con un lápiz.",
            ),
            english = VocabularyText(
                word = "pencil",
                definition = "A writing instrument with a graphite core.",
                example = "I write with a pencil.",
            ),
        ),
        VocabularyEntry(
            id = "egg",
            spanish = VocabularyText(
                word = "huevo",
                definition = "Alimento con clara y yema dentro de una cáscara, como el que pone una gallina.",
                example = "Cocino un huevo para desayunar.",
            ),
            english = VocabularyText(
                word = "egg",
                definition = "Food with a white and a yolk inside a shell, such as one laid by a hen.",
                example = "I cook an egg for breakfast.",
            ),
        ),
        VocabularyEntry(
            id = "cheese",
            spanish = VocabularyText(
                word = "queso",
                definition = "Alimento sólido o cremoso elaborado al cuajar la leche.",
                example = "Pongo queso en el pan.",
            ),
            english = VocabularyText(
                word = "cheese",
                definition = "A solid or creamy food made by curdling milk.",
                example = "I put cheese on the bread.",
            ),
        ),
        VocabularyEntry(
            id = "mountain",
            spanish = VocabularyText(
                word = "montaña",
                definition = "Elevación natural del terreno de gran altura.",
                example = "La montaña está cubierta de nieve.",
            ),
            english = VocabularyText(
                word = "mountain",
                definition = "A natural elevation of the land that rises to a great height.",
                example = "The mountain is covered in snow.",
            ),
        ),
        VocabularyEntry(
            id = "year",
            spanish = VocabularyText(
                word = "año",
                definition = "Periodo de doce meses.",
                example = "Un año tiene doce meses.",
            ),
            english = VocabularyText(
                word = "year",
                definition = "A period of twelve months.",
                example = "A year has twelve months.",
            ),
        ),
    )
}
