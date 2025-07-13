package model;

public enum ActionState {
    IDLE,        // fermo
    WALKING,     // cammina (Mario)
    JUMPING,
    ROLLING, // sta saltando (Mario)
    FALLING,     // sta cadendo (Mario)
    CLIMBING,    // su scala (Mario)
    THROWING,    // DK lancia un barile
    ROARING,     // DK fa animazione di ruggito
    HIT,         // stato quando è colpito (Mario)
    VICTORY,     // Peach alla fine
    STATIC       // usato per chi è fermo ma ha una posa animata (es. DK)
}
