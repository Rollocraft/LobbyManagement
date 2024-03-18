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
- hologram (create|remove|list)
- <details> <summary>Mehr zum Hologram</summary>
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
- world (create map|world **||** delete)
- setup (pvp kit|map)

## Permissions

- <details> <summary>Events</summary>
  - pridetuve.lobby.hubprotection*
  - pridetuve.lobby.hubprotection.placeBlock
  - pridetuve.lobby.hubprotection.breakBlock
  - pridetuve.lobby.hubprotection.dropItem
  - pridetuve.lobby.hubprotection.pickUpItem
  - pridetuve.lobby.hubprotection.interact

</details>

- <details> <summary>Commands</summary>
  - pridetuve.lobby.command*
  - pridetuve.lobby.command.status
  - pridetuve.lobby.command.hub
  - pridetuve.lobby.command.invsee
  - pridetuve.lobby.command.hologram
  - pridetuve.lobby.command.blockparticle
  - pridetuve.lobby.command.duel
  - pridetuve.lobby.command.teleport
  - pridetuve.lobby.command.world 
  - pridetuve.lobby.command.setup

</details>

> Last edit: 19.03.24 | 20:00
> 
> Version: 1.0.0
> 
> Written by: Rollocraft