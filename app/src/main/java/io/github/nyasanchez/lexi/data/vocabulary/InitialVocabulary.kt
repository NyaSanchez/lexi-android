package io.github.nyasanchez.lexi.data.vocabulary

/**
 * Banco inicial de 100 conceptos. Definiciones y ejemplos redactados para Lexi.
 * Los primeros 30 son de dificultad básica y los últimos 70 de dificultad media.
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

        // ---- Ampliación: 70 palabras de dificultad media ----
        VocabularyEntry(
            id = "bridge",
            spanish = VocabularyText(
                word = "puente",
                definition = "Construcción que permite cruzar un río o una carretera.",
                example = "Cruzamos el puente a pie.",
            ),
            english = VocabularyText(
                word = "bridge",
                definition = "A structure that lets people cross a river or a road.",
                example = "We crossed the bridge on foot.",
            ),
        ),
        VocabularyEntry(
            id = "window",
            spanish = VocabularyText(
                word = "ventana",
                definition = "Abertura en una pared, cubierta con cristal, que deja pasar la luz.",
                example = "Abro la ventana por la mañana.",
            ),
            english = VocabularyText(
                word = "window",
                definition = "An opening in a wall, covered with glass, that lets light in.",
                example = "I open the window in the morning.",
            ),
        ),
        VocabularyEntry(
            id = "garden",
            spanish = VocabularyText(
                word = "jardín",
                definition = "Terreno donde se cultivan flores, plantas y césped.",
                example = "Mi abuela cuida el jardín.",
            ),
            english = VocabularyText(
                word = "garden",
                definition = "A piece of land where flowers, plants and grass are grown.",
                example = "My grandmother takes care of the garden.",
            ),
        ),
        VocabularyEntry(
            id = "market",
            spanish = VocabularyText(
                word = "mercado",
                definition = "Lugar público donde se compran y se venden alimentos y otros productos.",
                example = "Compramos fruta en el mercado.",
            ),
            english = VocabularyText(
                word = "market",
                definition = "A public place where food and other goods are bought and sold.",
                example = "We buy fruit at the market.",
            ),
        ),
        VocabularyEntry(
            id = "school",
            spanish = VocabularyText(
                word = "escuela",
                definition = "Lugar donde los niños reciben clases.",
                example = "Mi hermana llega temprano a la escuela.",
            ),
            english = VocabularyText(
                word = "school",
                definition = "A place where children go to learn.",
                example = "My sister arrives early at school.",
            ),
        ),
        VocabularyEntry(
            id = "kitchen",
            spanish = VocabularyText(
                word = "cocina",
                definition = "Habitación de una casa donde se preparan los alimentos.",
                example = "Mamá prepara la cena en la cocina.",
            ),
            english = VocabularyText(
                word = "kitchen",
                definition = "The room in a house where food is prepared.",
                example = "Mom is making dinner in the kitchen.",
            ),
        ),
        VocabularyEntry(
            id = "mirror",
            spanish = VocabularyText(
                word = "espejo",
                definition = "Superficie de cristal en la que se refleja la imagen de quien se mira.",
                example = "Me miro en el espejo.",
            ),
            english = VocabularyText(
                word = "mirror",
                definition = "A glass surface in which you can see your own reflection.",
                example = "I look at myself in the mirror.",
            ),
        ),
        VocabularyEntry(
            id = "blanket",
            spanish = VocabularyText(
                word = "manta",
                definition = "Pieza gruesa de tela con la que nos cubrimos para tener calor.",
                example = "Puse una manta sobre la cama.",
            ),
            english = VocabularyText(
                word = "blanket",
                definition = "A thick piece of cloth used to keep warm.",
                example = "She put a blanket on the bed.",
            ),
        ),
        VocabularyEntry(
            id = "pillow",
            spanish = VocabularyText(
                word = "almohada",
                definition = "Cojín blando sobre el que apoyamos la cabeza al dormir.",
                example = "Mi almohada es muy suave.",
            ),
            english = VocabularyText(
                word = "pillow",
                definition = "A soft cushion on which we rest our head to sleep.",
                example = "My pillow is very soft.",
            ),
        ),
        VocabularyEntry(
            id = "bottle",
            spanish = VocabularyText(
                word = "botella",
                definition = "Recipiente de cuello estrecho que sirve para guardar líquidos.",
                example = "La botella está llena de jugo.",
            ),
            english = VocabularyText(
                word = "bottle",
                definition = "A container with a narrow neck used to hold liquids.",
                example = "The bottle is full of juice.",
            ),
        ),
        VocabularyEntry(
            id = "button",
            spanish = VocabularyText(
                word = "botón",
                definition = "Pieza pequeña y redonda que sirve para cerrar una prenda de ropa.",
                example = "Se me cayó un botón del abrigo.",
            ),
            english = VocabularyText(
                word = "button",
                definition = "A small round piece used to fasten a piece of clothing.",
                example = "A button fell off my coat.",
            ),
        ),
        VocabularyEntry(
            id = "carpet",
            spanish = VocabularyText(
                word = "alfombra",
                definition = "Tejido grueso que cubre el suelo de una habitación.",
                example = "La alfombra es muy suave.",
            ),
            english = VocabularyText(
                word = "carpet",
                definition = "A thick woven covering for the floor of a room.",
                example = "The carpet is very soft.",
            ),
        ),
        VocabularyEntry(
            id = "curtain",
            spanish = VocabularyText(
                word = "cortina",
                definition = "Tela que se cuelga para tapar una ventana.",
                example = "Cierra la cortina, por favor.",
            ),
            english = VocabularyText(
                word = "curtain",
                definition = "A piece of cloth hung to cover a window.",
                example = "Please close the curtain.",
            ),
        ),
        VocabularyEntry(
            id = "drawer",
            spanish = VocabularyText(
                word = "cajón",
                definition = "Parte de un mueble que se desliza hacia afuera para guardar cosas.",
                example = "Guardé las llaves en el cajón.",
            ),
            english = VocabularyText(
                word = "drawer",
                definition = "A part of a piece of furniture that slides out to store things.",
                example = "I put the keys in the drawer.",
            ),
        ),
        VocabularyEntry(
            id = "umbrella",
            spanish = VocabularyText(
                word = "paraguas",
                definition = "Objeto plegable que usamos para protegernos de la lluvia.",
                example = "Llévate el paraguas, va a llover.",
            ),
            english = VocabularyText(
                word = "umbrella",
                definition = "A folding object that protects us from the rain.",
                example = "Take your umbrella, it is going to rain.",
            ),
        ),
        VocabularyEntry(
            id = "scissors",
            spanish = VocabularyText(
                word = "tijeras",
                definition = "Herramienta con dos hojas afiladas que sirve para cortar papel o tela.",
                example = "Necesito unas tijeras para cortar el papel.",
            ),
            english = VocabularyText(
                word = "scissors",
                definition = "A tool with two sharp blades used to cut paper or cloth.",
                example = "I need scissors to cut the paper.",
            ),
        ),
        VocabularyEntry(
            id = "hammer",
            spanish = VocabularyText(
                word = "martillo",
                definition = "Herramienta con una cabeza pesada que sirve para golpear clavos.",
                example = "Golpea el clavo con el martillo.",
            ),
            english = VocabularyText(
                word = "hammer",
                definition = "A tool with a heavy head used to hit nails.",
                example = "He hit the nail with the hammer.",
            ),
        ),
        VocabularyEntry(
            id = "needle",
            spanish = VocabularyText(
                word = "aguja",
                definition = "Objeto delgado y puntiagudo con un ojo por el que pasa el hilo al coser.",
                example = "Pasa el hilo por la aguja.",
            ),
            english = VocabularyText(
                word = "needle",
                definition = "A thin, pointed tool with a small hole for thread, used for sewing.",
                example = "She put the thread through the needle.",
            ),
        ),
        VocabularyEntry(
            id = "thread",
            spanish = VocabularyText(
                word = "hilo",
                definition = "Hebra fina y larga de algodón que se usa para coser.",
                example = "Necesito hilo negro para coser el botón.",
            ),
            english = VocabularyText(
                word = "thread",
                definition = "A thin, long strand of cotton used for sewing.",
                example = "I need black thread to sew on the button.",
            ),
        ),
        VocabularyEntry(
            id = "wheel",
            spanish = VocabularyText(
                word = "rueda",
                definition = "Pieza circular que gira y permite que un vehículo se mueva.",
                example = "La rueda del carrito está rota.",
            ),
            english = VocabularyText(
                word = "wheel",
                definition = "A circular part that turns and lets a vehicle move.",
                example = "The wheel of the cart is broken.",
            ),
        ),
        VocabularyEntry(
            id = "engine",
            spanish = VocabularyText(
                word = "motor",
                definition = "Máquina que produce el movimiento de un vehículo.",
                example = "El motor hace mucho ruido.",
            ),
            english = VocabularyText(
                word = "engine",
                definition = "A machine that makes a vehicle move.",
                example = "The engine makes a lot of noise.",
            ),
        ),
        VocabularyEntry(
            id = "chimney",
            spanish = VocabularyText(
                word = "chimenea",
                definition = "Conducto por el que sale el humo de un fuego dentro de una casa.",
                example = "Sale humo de la chimenea.",
            ),
            english = VocabularyText(
                word = "chimney",
                definition = "A pipe through which smoke leaves a building.",
                example = "Smoke is coming out of the chimney.",
            ),
        ),
        VocabularyEntry(
            id = "ladder",
            spanish = VocabularyText(
                word = "escalera",
                definition = "Objeto con peldaños que se apoya en una pared para subir o bajar.",
                example = "Subió al techo con una escalera.",
            ),
            english = VocabularyText(
                word = "ladder",
                definition = "An object with rungs that you lean against a wall to climb up or down.",
                example = "He climbed onto the roof with a ladder.",
            ),
        ),
        VocabularyEntry(
            id = "candle",
            spanish = VocabularyText(
                word = "vela",
                definition = "Cilindro de cera con una mecha que se enciende para dar luz.",
                example = "Encendimos una vela durante el apagón.",
            ),
            english = VocabularyText(
                word = "candle",
                definition = "A cylinder of wax with a wick that is lit to give light.",
                example = "We lit a candle during the blackout.",
            ),
        ),
        VocabularyEntry(
            id = "feather",
            spanish = VocabularyText(
                word = "pluma",
                definition = "Cada una de las piezas ligeras que cubren el cuerpo de un ave.",
                example = "Encontré una pluma blanca en el patio.",
            ),
            english = VocabularyText(
                word = "feather",
                definition = "One of the light parts that cover the body of a bird.",
                example = "I found a white feather in the yard.",
            ),
        ),
        VocabularyEntry(
            id = "turtle",
            spanish = VocabularyText(
                word = "tortuga",
                definition = "Reptil de caparazón duro que camina muy despacio.",
                example = "La tortuga esconde la cabeza en su caparazón.",
            ),
            english = VocabularyText(
                word = "turtle",
                definition = "A slow-moving reptile with a hard shell on its back.",
                example = "The turtle hides its head in its shell.",
            ),
        ),
        VocabularyEntry(
            id = "rabbit",
            spanish = VocabularyText(
                word = "conejo",
                definition = "Animal de orejas largas que se mueve dando saltos.",
                example = "El conejo come una zanahoria.",
            ),
            english = VocabularyText(
                word = "rabbit",
                definition = "A small animal with long ears that moves by jumping.",
                example = "The rabbit eats a carrot.",
            ),
        ),
        VocabularyEntry(
            id = "spider",
            spanish = VocabularyText(
                word = "araña",
                definition = "Animal de ocho patas que teje una tela para atrapar insectos.",
                example = "Una araña teje su tela en el rincón.",
            ),
            english = VocabularyText(
                word = "spider",
                definition = "An animal with eight legs that spins a web to catch insects.",
                example = "A spider is spinning its web in the corner.",
            ),
        ),
        VocabularyEntry(
            id = "eagle",
            spanish = VocabularyText(
                word = "águila",
                definition = "Ave grande de vista aguda que caza otros animales.",
                example = "El águila vuela sobre las montañas.",
            ),
            english = VocabularyText(
                word = "eagle",
                definition = "A large bird with sharp eyesight that hunts other animals.",
                example = "The eagle flies over the mountains.",
            ),
        ),
        VocabularyEntry(
            id = "dolphin",
            spanish = VocabularyText(
                word = "delfín",
                definition = "Mamífero marino muy inteligente que salta sobre las olas.",
                example = "El delfín salta sobre las olas.",
            ),
            english = VocabularyText(
                word = "dolphin",
                definition = "A very intelligent sea mammal that jumps out of the water.",
                example = "The dolphin jumps over the waves.",
            ),
        ),
        VocabularyEntry(
            id = "whale",
            spanish = VocabularyText(
                word = "ballena",
                definition = "Enorme mamífero marino, el animal más grande del planeta.",
                example = "Vimos una ballena desde el barco.",
            ),
            english = VocabularyText(
                word = "whale",
                definition = "A huge sea mammal and the largest animal on Earth.",
                example = "We saw a whale from the boat.",
            ),
        ),
        VocabularyEntry(
            id = "elbow",
            spanish = VocabularyText(
                word = "codo",
                definition = "Parte del brazo donde se dobla por la mitad.",
                example = "Me golpeé el codo con la puerta.",
            ),
            english = VocabularyText(
                word = "elbow",
                definition = "The part in the middle of the arm where it bends.",
                example = "I hit my elbow on the door.",
            ),
        ),
        VocabularyEntry(
            id = "shoulder",
            spanish = VocabularyText(
                word = "hombro",
                definition = "Parte del cuerpo donde el brazo se une con el tronco.",
                example = "Me duele el hombro derecho.",
            ),
            english = VocabularyText(
                word = "shoulder",
                definition = "The part of the body where the arm joins the trunk.",
                example = "My right shoulder hurts.",
            ),
        ),
        VocabularyEntry(
            id = "ankle",
            spanish = VocabularyText(
                word = "tobillo",
                definition = "Articulación que une el pie con la pierna.",
                example = "Me torcí el tobillo jugando.",
            ),
            english = VocabularyText(
                word = "ankle",
                definition = "The joint that connects the foot to the leg.",
                example = "I twisted my ankle while playing.",
            ),
        ),
        VocabularyEntry(
            id = "wrist",
            spanish = VocabularyText(
                word = "muñeca",
                definition = "Articulación que une la mano con el antebrazo.",
                example = "El reloj está en mi muñeca.",
            ),
            english = VocabularyText(
                word = "wrist",
                definition = "The joint that connects the hand to the forearm.",
                example = "My watch is on my wrist.",
            ),
        ),
        VocabularyEntry(
            id = "thumb",
            spanish = VocabularyText(
                word = "pulgar",
                definition = "El dedo más corto y grueso de la mano.",
                example = "Levantó el pulgar en señal de aprobación.",
            ),
            english = VocabularyText(
                word = "thumb",
                definition = "The shortest and thickest finger of the hand.",
                example = "He raised his thumb to show approval.",
            ),
        ),
        VocabularyEntry(
            id = "eyebrow",
            spanish = VocabularyText(
                word = "ceja",
                definition = "Línea de pelo que está sobre cada ojo.",
                example = "Levantó una ceja con sorpresa.",
            ),
            english = VocabularyText(
                word = "eyebrow",
                definition = "The line of hair above each eye.",
                example = "She raised an eyebrow in surprise.",
            ),
        ),
        VocabularyEntry(
            id = "throat",
            spanish = VocabularyText(
                word = "garganta",
                definition = "Parte interior del cuello por donde pasan el aire y los alimentos.",
                example = "Me duele la garganta al tragar.",
            ),
            english = VocabularyText(
                word = "throat",
                definition = "The inner part of the neck through which air and food pass.",
                example = "My throat hurts when I swallow.",
            ),
        ),
        VocabularyEntry(
            id = "island",
            spanish = VocabularyText(
                word = "isla",
                definition = "Terreno rodeado de agua por todas partes.",
                example = "Llegamos a la isla en barco.",
            ),
            english = VocabularyText(
                word = "island",
                definition = "A piece of land completely surrounded by water.",
                example = "We reached the island by boat.",
            ),
        ),
        VocabularyEntry(
            id = "desert",
            spanish = VocabularyText(
                word = "desierto",
                definition = "Región muy seca, con poca lluvia y casi sin plantas.",
                example = "En el desierto hace mucho calor de día.",
            ),
            english = VocabularyText(
                word = "desert",
                definition = "A very dry region with little rain and almost no plants.",
                example = "It is very hot in the desert during the day.",
            ),
        ),
        VocabularyEntry(
            id = "valley",
            spanish = VocabularyText(
                word = "valle",
                definition = "Terreno bajo y llano entre dos montañas.",
                example = "El pueblo está en un valle verde.",
            ),
            english = VocabularyText(
                word = "valley",
                definition = "A low, flat area of land between two mountains.",
                example = "The village is in a green valley.",
            ),
        ),
        VocabularyEntry(
            id = "river",
            spanish = VocabularyText(
                word = "río",
                definition = "Corriente de agua que fluye hacia el mar o hacia un lago.",
                example = "El río baja de la montaña.",
            ),
            english = VocabularyText(
                word = "river",
                definition = "A stream of water that flows toward the sea or a lake.",
                example = "The river flows down from the mountain.",
            ),
        ),
        VocabularyEntry(
            id = "ocean",
            spanish = VocabularyText(
                word = "océano",
                definition = "Enorme extensión de agua salada que cubre gran parte de la Tierra.",
                example = "El barco cruzó el océano.",
            ),
            english = VocabularyText(
                word = "ocean",
                definition = "A huge body of salt water that covers much of the Earth.",
                example = "The ship crossed the ocean.",
            ),
        ),
        VocabularyEntry(
            id = "storm",
            spanish = VocabularyText(
                word = "tormenta",
                definition = "Fenómeno del clima con fuerte lluvia, viento y rayos.",
                example = "La tormenta llegó por la noche.",
            ),
            english = VocabularyText(
                word = "storm",
                definition = "Bad weather with heavy rain, strong wind and lightning.",
                example = "The storm arrived at night.",
            ),
        ),
        VocabularyEntry(
            id = "thunder",
            spanish = VocabularyText(
                word = "trueno",
                definition = "Ruido fuerte que se oye en el cielo después de un rayo.",
                example = "El trueno asustó al perro.",
            ),
            english = VocabularyText(
                word = "thunder",
                definition = "The loud noise heard in the sky after a flash of lightning.",
                example = "The thunder scared the dog.",
            ),
        ),
        VocabularyEntry(
            id = "rainbow",
            spanish = VocabularyText(
                word = "arcoíris",
                definition = "Arco de colores que aparece en el cielo cuando hay sol y lluvia a la vez.",
                example = "Vimos un arcoíris después de la lluvia.",
            ),
            english = VocabularyText(
                word = "rainbow",
                definition = "An arch of colors in the sky when there is sun and rain at the same time.",
                example = "We saw a rainbow after the rain.",
            ),
        ),
        VocabularyEntry(
            id = "shadow",
            spanish = VocabularyText(
                word = "sombra",
                definition = "Zona oscura que deja un cuerpo cuando tapa la luz.",
                example = "Me siento a la sombra del árbol.",
            ),
            english = VocabularyText(
                word = "shadow",
                definition = "The dark shape a body makes when it blocks the light.",
                example = "I sit in the shadow of the tree.",
            ),
        ),
        VocabularyEntry(
            id = "anchor",
            spanish = VocabularyText(
                word = "ancla",
                definition = "Pieza pesada de metal que sujeta un barco al fondo del agua.",
                example = "El capitán echó el ancla.",
            ),
            english = VocabularyText(
                word = "anchor",
                definition = "A heavy piece of metal that holds a ship in place on the sea floor.",
                example = "The captain dropped the anchor.",
            ),
        ),
        VocabularyEntry(
            id = "compass",
            spanish = VocabularyText(
                word = "brújula",
                definition = "Instrumento con una aguja imantada que señala el norte.",
                example = "La brújula nos indicó el camino.",
            ),
            english = VocabularyText(
                word = "compass",
                definition = "An instrument with a magnetic needle that points north.",
                example = "The compass showed us the way.",
            ),
        ),
        VocabularyEntry(
            id = "treasure",
            spanish = VocabularyText(
                word = "tesoro",
                definition = "Conjunto de objetos de gran valor, como monedas y joyas, que alguien esconde.",
                example = "Los piratas escondieron el tesoro.",
            ),
            english = VocabularyText(
                word = "treasure",
                definition = "A collection of valuable things, like coins and jewels, that someone hides.",
                example = "The pirates hid the treasure.",
            ),
        ),
        VocabularyEntry(
            id = "castle",
            spanish = VocabularyText(
                word = "castillo",
                definition = "Edificio grande con torres y muros altos, construido para defenderse.",
                example = "El castillo está sobre una colina.",
            ),
            english = VocabularyText(
                word = "castle",
                definition = "A large building with towers and high walls, built for defense.",
                example = "The castle is on top of a hill.",
            ),
        ),
        VocabularyEntry(
            id = "sword",
            spanish = VocabularyText(
                word = "espada",
                definition = "Arma de metal con una hoja larga y afilada.",
                example = "El caballero sacó su espada.",
            ),
            english = VocabularyText(
                word = "sword",
                definition = "A metal weapon with a long, sharp blade.",
                example = "The knight drew his sword.",
            ),
        ),
        VocabularyEntry(
            id = "crown",
            spanish = VocabularyText(
                word = "corona",
                definition = "Aro de metal precioso que llevan en la cabeza los reyes.",
                example = "La reina llevaba una corona de oro.",
            ),
            english = VocabularyText(
                word = "crown",
                definition = "A ring of precious metal that kings and queens wear on the head.",
                example = "The queen wore a golden crown.",
            ),
        ),
        VocabularyEntry(
            id = "dragon",
            spanish = VocabularyText(
                word = "dragón",
                definition = "Animal fantástico con alas y garras que echa fuego por la boca.",
                example = "El dragón del cuento vivía en una cueva.",
            ),
            english = VocabularyText(
                word = "dragon",
                definition = "A fantasy animal with wings and claws that breathes fire.",
                example = "The dragon in the story lived in a cave.",
            ),
        ),
        VocabularyEntry(
            id = "pirate",
            spanish = VocabularyText(
                word = "pirata",
                definition = "Persona que roba barcos en el mar.",
                example = "El pirata tenía un parche en el ojo.",
            ),
            english = VocabularyText(
                word = "pirate",
                definition = "A person who robs ships at sea.",
                example = "The pirate had a patch over his eye.",
            ),
        ),
        VocabularyEntry(
            id = "harvest",
            spanish = VocabularyText(
                word = "cosecha",
                definition = "Recolección de los frutos que ha dado la tierra.",
                example = "La cosecha de maíz fue muy buena.",
            ),
            english = VocabularyText(
                word = "harvest",
                definition = "The gathering of crops when they are ripe.",
                example = "The corn harvest was very good.",
            ),
        ),
        VocabularyEntry(
            id = "farmer",
            spanish = VocabularyText(
                word = "granjero",
                definition = "Persona que cuida cultivos y animales en el campo.",
                example = "El granjero se levanta antes del amanecer.",
            ),
            english = VocabularyText(
                word = "farmer",
                definition = "A person who looks after crops and animals in the countryside.",
                example = "The farmer gets up before dawn.",
            ),
        ),
        VocabularyEntry(
            id = "luggage",
            spanish = VocabularyText(
                word = "equipaje",
                definition = "Conjunto de maletas y bolsas que llevamos cuando viajamos.",
                example = "Perdieron mi equipaje en el aeropuerto.",
            ),
            english = VocabularyText(
                word = "luggage",
                definition = "The suitcases and bags that we take with us when we travel.",
                example = "They lost my luggage at the airport.",
            ),
        ),
        VocabularyEntry(
            id = "judge",
            spanish = VocabularyText(
                word = "juez",
                definition = "Persona que decide quién tiene la razón en un juicio.",
                example = "El juez escuchó a las dos partes.",
            ),
            english = VocabularyText(
                word = "judge",
                definition = "A person who decides who is right in a trial.",
                example = "The judge listened to both sides.",
            ),
        ),
        VocabularyEntry(
            id = "lawyer",
            spanish = VocabularyText(
                word = "abogado",
                definition = "Persona que defiende los derechos de otras ante la ley.",
                example = "Mi abogado revisó el contrato.",
            ),
            english = VocabularyText(
                word = "lawyer",
                definition = "A person who defends other people's rights under the law.",
                example = "My lawyer checked the contract.",
            ),
        ),
        VocabularyEntry(
            id = "hunger",
            spanish = VocabularyText(
                word = "hambre",
                definition = "Sensación de necesitar comer.",
                example = "Tengo mucha hambre después de correr.",
            ),
            english = VocabularyText(
                word = "hunger",
                definition = "The feeling of needing to eat.",
                example = "I feel very hungry after running.",
            ),
        ),
        VocabularyEntry(
            id = "courage",
            spanish = VocabularyText(
                word = "valentía",
                definition = "Cualidad de quien enfrenta el miedo o el peligro sin rendirse.",
                example = "Salvó al niño con gran valentía.",
            ),
            english = VocabularyText(
                word = "courage",
                definition = "The quality of facing fear or danger without giving up.",
                example = "He saved the boy with great courage.",
            ),
        ),
        VocabularyEntry(
            id = "silence",
            spanish = VocabularyText(
                word = "silencio",
                definition = "Ausencia total de ruido o de voces.",
                example = "En la biblioteca hay que guardar silencio.",
            ),
            english = VocabularyText(
                word = "silence",
                definition = "The complete absence of noise or voices.",
                example = "You must keep silence in the library.",
            ),
        ),
        VocabularyEntry(
            id = "memory",
            spanish = VocabularyText(
                word = "memoria",
                definition = "Capacidad de recordar lo que hemos vivido o aprendido.",
                example = "Tiene muy buena memoria para los nombres.",
            ),
            english = VocabularyText(
                word = "memory",
                definition = "The ability to remember what we have lived or learned.",
                example = "She has a very good memory for names.",
            ),
        ),
        VocabularyEntry(
            id = "dream",
            spanish = VocabularyText(
                word = "sueño",
                definition = "Imágenes e historias que vemos mientras dormimos.",
                example = "Anoche tuve un sueño muy extraño.",
            ),
            english = VocabularyText(
                word = "dream",
                definition = "Images and stories that we see while we sleep.",
                example = "Last night I had a very strange dream.",
            ),
        ),
        VocabularyEntry(
            id = "truth",
            spanish = VocabularyText(
                word = "verdad",
                definition = "Lo que es cierto y corresponde con los hechos.",
                example = "Dime la verdad, por favor.",
            ),
            english = VocabularyText(
                word = "truth",
                definition = "What is real and matches the facts.",
                example = "Please tell me the truth.",
            ),
        ),
        VocabularyEntry(
            id = "secret",
            spanish = VocabularyText(
                word = "secreto",
                definition = "Cosa que se mantiene oculta y que solo conocen unas pocas personas.",
                example = "Prometí guardar el secreto.",
            ),
            english = VocabularyText(
                word = "secret",
                definition = "Something kept hidden that only a few people know.",
                example = "I promised to keep the secret.",
            ),
        ),
        VocabularyEntry(
            id = "danger",
            spanish = VocabularyText(
                word = "peligro",
                definition = "Situación en la que alguien puede sufrir un daño.",
                example = "El cartel advierte del peligro.",
            ),
            english = VocabularyText(
                word = "danger",
                definition = "A situation in which someone might be harmed.",
                example = "The sign warns of the danger.",
            ),
        ),
        VocabularyEntry(
            id = "neighbor",
            spanish = VocabularyText(
                word = "vecino",
                definition = "Persona que vive muy cerca de nuestra casa.",
                example = "Mi vecino riega las plantas todos los días.",
            ),
            english = VocabularyText(
                word = "neighbor",
                definition = "A person who lives very close to our home.",
                example = "My neighbor waters the plants every day.",
            ),
        ),
        VocabularyEntry(
            id = "freedom",
            spanish = VocabularyText(
                word = "libertad",
                definition = "Derecho de actuar y decidir sin que nadie nos obligue.",
                example = "Todos merecemos vivir con libertad.",
            ),
            english = VocabularyText(
                word = "freedom",
                definition = "The right to act and decide without being forced.",
                example = "We all deserve to live in freedom.",
            ),
        ),
    )
}