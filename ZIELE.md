#Java Real Time Game (Facharbeit Informatik)

Echtzeit Jump'n'Run in Anlehnung an Super Mario

#Ziele:

Start Menü:

- min. 2 wählbare Themes, a min. 2 wählbare Characters & min. 2 wählbare Levels
  - Levels: gespeichert in Textdokument und atumomatisch eingelesen -> Veränderung des Textdokuments = Veränderung der Map
- Start-Button
- Highscore-Anzeige
  - siehe Game

Game:

- Player
  - kann laufen, springen, schlagen; alles eigene Animationen
- zurück-Button 
  - führt zu start-Menü
- min. zwei verschiedene Block-Arten
  - standart (keine Funktion)
  - Aktionsblock (mit Funktion -> z.B. Ausgabe von Münzen)
  - Design hängt von Theme ab
- zu erreichendes Ziel
- Münzen zum Sammeln
  - mit counter, Highscore, etc.
- Zeitmesser (zu Highscore)
- Lebensanzeige (bestimmte Anzahl an Leben),
- min. ein Typ von Gegner
  - NPC
  - verfolgt Spieler
  - kann Blöcke hoch & runter laufen/springen
  - erkennt void und vermeidet es
  - fügt Schaden hinzu -> zieht Leben ab
  - kann Schaden nehmen wenn geschlagen
  - Design hängt von Theme ab
- verschiedene Animationen für verschiedene Schaden-Stadien des Spielers
