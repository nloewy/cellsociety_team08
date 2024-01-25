# Rock Paper Scissors Lab Discussion
#### Noah Loewy(nl190), Alisha Zhang(xz352), Judy He(yh381)


### High Level Design Goals
Player
* choose weapon

Weapon (abstract)
* (variable) beats 

ScoreKeeper
* (variable) player-score map
* generate leaderboard

GameDriver
* display UI
* game over
* reset game
* add player
* add weapon
* new game

### CRC Card Classes
This class's purpose is to run the game

| Game Driver            |             |
|------------------------|-------------|
| void addPlayer(player) | Player      |
| void addWeapon(weapon) | Weapon      |
| void newGame()         | ScoreKeeper |
| void ResetGame()       | ScoreKeeper |

this class's purpose is to represent a player

```java
public class Player {
     public Weapon chooseWeapon(){}
    // returns which weapon the player chooses
 }
 ```

This class's purpose is to create an abstract class for all weapons, where each weapon can inherit
```java
public abstract class Weapon {
     public boolean Beat(Weapon){}
    //determines whether it's beaten by another weapon
 }
```

This class's purpose is to keep track of each player's score
```java
public abstract class ScoreKeeper {
     public void addPoint(Player){}
    // adds a point for the specified player in the chart
    public Map<Player,Integer> generateLeaderBoard(){}
    //returns the players with the top scores
    public void addEntry(){Player, int}
    //add player-score entry to map
 }
```

### Use Cases

* A new game is started with five players, their scores are reset to 0.
 ```java
 Game newGame = new Game();
 for(int i = 0; i < 5; i ++){
     Player newPlayer = new Player();
     newGame.addPlayer(newPlayer);
     Score.addEntry(newPlayer, 0);
 };
 
 ```

* A player chooses his RPS "weapon" with which he wants to play for this round.
 ```java
 player.choseWeapon(weapon);
 ```

* Given three players' choices, one player wins the round, and their scores are updated.
 ```java
 
 ```

* A new choice is added to an existing game and its relationship to all the other choices is updated.
 ```java
game.addWeapon();
 ```

* A new game is added to the system, with its own relationships for its all its "weapons".
 ```java
Game newGame = new Game();
for (Weapon w : weaponList){
    newGame.addWeapon();
        }
 ```
