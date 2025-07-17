# 🎮 Donkey Kong vs Mario

Un videogioco multiplayer ispirato alla versione arcade originale di **Donkey Kong (1981)**, sviluppato in **Java** seguendo l'architettura **MVC (Model-View-Controller)**. Il gioco permette a più giocatori di sfidarsi nei panni di **Mario** attraverso una rete **Server-Client**, con mappa JSON in stile pixel art e grafica fedele allo spirito retrò.

---

## 🗂️ Struttura del Progetto

Il progetto è suddiviso nei seguenti **package**:

- `audio`  
- `controller`  
- `defaultmain`  
- `menu`  
- `model`  
- `utils`  
- `view`

---

## 🔧 Componenti Principali

### 🔊 Audio
- **`AudioManager`**: gestisce tutti gli effetti sonori e la musica del gioco.

---

### 🎮 Controller
- **`PlayerController`**: gestisce il movimento del personaggio controllato dal giocatore (Mario), compresi salto e camminata.

---

### 🧩 Default Main / ServerClient
- **`ClientManager`**: gestisce la connessione del client al server, il nickname dell'utente e la ricezione dei dati.
- **Server**: mantiene due `ConcurrentHashMap` per distinguere tra client attivi e disconnessi.
- Gestisce anche una **tabella degli score** con i nickname dei giocatori.

---

### 🔁 Game Loop
- **`GameEngine`**: contiene il **game loop principale**, aggiornando lo stato del gioco e gestendone l’arresto.
- **`GameLauncher`**: entry point iniziale, collega le componenti di `View` e `Model`.
- **`GameSetter`**: inizializza gli oggetti di gioco e le componenti necessarie.

---

### 🧭 Menu
- Sistema grafico di menu con diversi dialoghi:
  - Vittoria
  - Sconfitta
  - Modalità multiplayer
- Possibilità di inserire **nickname** e **numero del server** per il matchmaking.

---

### 🧱 Model

Include le entità, stati, mappa e logica di gioco.

#### ✅ Classi principali:
- **`Entity`**: classe base per tutte le entità (player, NPC, nemici).
- **`GameItem`**: classe base per gli oggetti interagibili.
- **`DonkeyKong`**, **`Player`**, **`Barrel`**, **`Peach`**

#### ⚙️ Enums & Logica:
- **`PlayerState`**, **`ActionState`**, **`Direction`**, **`BarrelState`**
- **`PlayerListener`**: interfaccia per comunicazione tra il player e altre componenti senza dipendenze circolari.

#### 🗺️ Mappa e Collisioni:
- **`Layer`**, **`Ladder`**, **`TriggerZone`**, **`TileMap`**, **`World`**
- Mappa caricata da file **JSON** con libreria di Google.

---

### 🔌 Utils

Utilità generiche e gestione collisioni:

- **`CollisionManager`**: gestisce collisioni via `Rectangle`.
- **`LadderManager`**: identifica la posizione delle scale.
- **`TiledMapLoader`** / **`TiledUtils`**: caricano mappa e tile come immagini `BufferedImage`.
- **`NumSprites`**: gestisce i riferimenti statici agli sprite.
- **`Constants`**: variabili globali di configurazione.
- **`GUIUtils`**: funzioni grafiche di supporto.

---

### 🖼️ View

- Visualizzazione degli oggetti di gioco.
- **`SideMenuView`**: gestisce la parte grafica laterale dell’interfaccia.

---

## 🌐 Multiplayer (Server-Client)

- Modalità multiplayer gestita tramite **socket TCP/IP**.
- Il server tiene traccia di:
  - Giocatori attivi
  - Score
- Il client invia nickname e riceve aggiornamenti in tempo reale.

---

## 🕹️ Obiettivi di Gioco

- Il giocatore che controlla **Mario** deve raggiungere **Peach** evitando barili e ostacoli.
- I barili si muovono lungo le travi e cambiano direzione quando non trovano una piattaforma vicina.
- Il gioco termina quando Mario raggiunge Peach o esce dalla partita.

---

## 🧱 Tecnologie Utilizzate

- **Java 21+**
- **Swing/AWT**
- **BufferedImage / Graphics2D**
- **Sockets (TCP/IP)**
- **JSON Parser (Google Gson)**
- **Tiled Map Editor**
- **PiskelApp Editor Grafico**

---

## ✅ Stato del Progetto

- [x] Modalità giocatore singolo
- [x] Modalità multiplayer via rete
- [x] Sistema punteggio e ranking
- [x] Mappa caricata da JSON
- [x] Menu dinamico con input
- [x] Animazioni sprite e audio

---

## 📂 Come eseguire il progetto

1. Nel progetto c'è una cartella dkserver, che dovrà essere tagliata e runnata separatamente.
2. Si runna la classe **`Server`** in dkserver
3. Inizializza il menu runnando **`MenuStart`** nel package menu di progettoRivisto
4. Enjoy :3
