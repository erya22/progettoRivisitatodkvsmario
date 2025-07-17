package model;

/**
 * Enum con i vari stati delle entità.
 */
public enum ActionState {
    IDLE,        // fermo
    WALKING,     // cammina (Mario)
    JUMPING,	 // sta salytando(Mario)
    ROLLING, 	 // sta rotolando (barrel)
    FALLING,     // sta cadendo (Mario)
    CLIMBING,    // su scala (Mario)
    THROWING,    // DK lancia un barile
    ROARING,     // DK fa animazione di ruggito
    HIT,         // stato quando è colpito (Mario)
    VICTORY,     // Peach alla fine
    STATIC       // usato per chi è fermo ma ha una posa animata (es. DK)
}
