# Lobby System

## Funktionen

### Erstellung von Welten und Karten

### Hologramme

### Blockparticle

### Duell-System

### Spielzeit-System

### Level-System

### Custom Tabliste

### Custom Scoreboard

### Custom Join Items

### Serverauswahl

### Statistiken

## Befehle

- invsee (player)
- hub
- status (threads|database|tps|ram)
- teleport (player, world)
- <details> <summary>hologram (create|remove|list) || Mehr zum Hologram</summary>
  <details> <summary>"Commands"/Formatierung</summary>

  - `|` = neue Zeile (nicht vom text getrennt also z.b. "Hello|World")
  - `&0` - Schwarz
  - `&1` - Dunkelblau
  - `&2` - Dunkelgrün
  - `&3` - Dunkelaqua
  - `&4` - Dunkelrot
  - `&5` - Dunkellila
  - `&6` - Gold
  - `&7` - Grau
  - `&8` - Dunkelgrau
  - `&9` - Blau
  - `&a` - Grün
  - `&b` - Aqua
  - `&c` - Rot
  - `&d` - Helllila
  - `&e` - Gelb
  - `&f` - Weiß
  - `&l` - Fett
  - `&m` - Durchgestrichen
  - `&n` - Unterstrichen
  - `&o` - Kursiv
  </details>
  <details><summary>Infos</summary>
  
  - Radius von der remove Funktion sind 2 blöcke
  - -> nah an das Hologramm gehen. Im falle das es nicht gefunden wird kann es immernoch mit folgendem Befehl entfernt werden:
  - `/kill @e[type=minecraft:armor_stand, limit=1, distance= ..2]`
  </details>
  </details>

- blockparticle (create|remove|list)
- duel (player)
- world (create map|world || delete)
- setup (pvp kit|map)

## Permissions

<details> <summary>Events</summary>
  
  - lobbySystem.hubprotection*
  - lobbySystem.hubprotection.placeBlock
  - lobbySystem.hubprotection.breakBlock
  - lobbySystem.hubprotection.dropItem
  - lobbySystem.hubprotection.pickUpItem
  - lobbySystem.hubprotection.interact

</details>

<details> <summary>Commands</summary>
  
  - lobbySystem.command*
  - lobbySystem.command.status
  - lobbySystem.command.hub
  - lobbySystem.command.invsee
  - lobbySystem.command.hologram
  - lobbySystem.command.blockparticle
  - lobbySystem.command.duel
  - lobbySystem.command.teleport
  - lobbySystem.command.world 
  - lobbySystem.command.setup
  - lobbySystem.command.blockparticle

</details>

> Last edit: 21.03.24 | 08:40
> 
> Version: 1.0.1
> 
> Written by: Rollocraft