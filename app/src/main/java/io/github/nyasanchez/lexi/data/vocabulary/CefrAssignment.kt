package io.github.nyasanchez.lexi.data.vocabulary

/**
 * Reparto editorial de cada nivel en dos subniveles CEFR de 50 palabras.
 *
 * Esta lista contiene el primer subnivel de cada nivel (A1, B1 y C1). Todas las demás
 * palabras del nivel pertenecen al segundo subnivel (A2, B2 y C2).
 *
 * Es una propuesta del equipo: conviene validarla con el English Vocabulary Profile.
 * Para mover una palabra de subnivel basta con añadir o quitar su ID de esta lista;
 * las pruebas verifican que cada subnivel conserve exactamente 50 palabras.
 */
internal object CefrAssignment {
    val firstSublevelIds: Set<String> = setOf(
        // A1 (nivel Básico)
        "house", "apple", "tree", "book", "sun", "moon", "water", "bread", "milk", "cat",
        "dog", "bird", "fish", "hand", "eye", "door", "table", "chair", "flower", "cloud",
        "fire", "forest", "shoe", "shirt", "key", "pencil", "egg", "cheese", "mountain", "year",
        "bridge", "window", "garden", "market", "school", "kitchen", "mirror", "bottle", "button", "curtain",
        "umbrella", "scissors", "rabbit", "whale", "island", "river", "ocean", "rainbow", "shadow", "castle",

        // B1 (nivel Intermedio)
        "anger", "pride", "shame", "relief", "trust", "doubt", "hope", "sadness", "shy", "brave",
        "lazy", "polite", "salary", "boss", "deadline", "homework", "meeting", "ticket", "suitcase", "journey",
        "border", "schedule", "delay", "flight", "fever", "cough", "wound", "nurse", "screen", "keyboard",
        "charger", "password", "download", "search", "link", "law", "tax", "wave", "fog", "wallet",
        "soap", "towel", "shelf", "roof", "wall", "flour", "sugar", "honey", "onion", "oven",

        // C1 (nivel Avanzado)
        "gloomy", "reluctant", "thrifty", "sturdy", "witty", "thrive", "wander", "foster", "scold", "mock",
        "crawl", "stumble", "shiver", "sigh", "yawn", "blink", "whisper", "boast", "inherit", "gossip",
        "witness", "mortgage", "quarrel", "prank", "feast", "famine", "heir", "ancestor", "orphan", "widow",
        "bachelor", "cradle", "coffin", "scar", "limp", "wrinkle", "freckle", "dimple", "eyelash", "knuckle",
        "skull", "rib", "tide", "reef", "lighthouse", "blizzard", "drizzle", "thorn", "moss", "swan",
    )
}
