# Breakout Abstractions Lab Discussion
#### Noah Loewy, Alisha Zhang, Judy He


### Block

This superclass's purpose as an abstraction: represents a template for a general block object, that more specific different types of blocks can implement
```java
 public class Block {
     public Block (){}
     // initializing instance variables
    
    public void hitBlock(){}
    // decrease health of block
    
    public abstract void destroyBlock(){}
    // remove block from interface and do special actions
    
    //getters and setters for instance variables
    
    
 }
```

#### Subclasses

Each subclass's high-level behavorial differences from the superclass

* regular block
* exploding block
    * distroys blocks on the same row and column when it's destroyed
* powerup block
    * drops a powerup when destroyed

#### Affect on Game/Level class

Which methods are simplified by using this abstraction and why

* constructor
    * doesn't have to specify which blocks are powerup blocks/exploding blocks in the constructor
* generatePowerupType
    * doesn't have to be in all block instances, only needs to be in the powerup blocks


### Power-up

This superclass's purpose as an abstraction:
```java
 public class PowerUp {
     public Powerup(){}
     // no implementation, just a comment about its purpose in the abstraction 
    public void update(){}
    // update how much time the powerup is active for
    public abstract void activate(){}
    //activate powerup
    public abstract void deactivate(){}
    //deactivate powerup when time is up
    
    //getters and setters
 }
```

#### Subclasses

Each subclass's high-level behavorial differences from the superclass

* increase paddle length
* speed up ball
* add 1 ball

activates the corresponding powerup behavior when activate is called


#### Affect on Game/Level class

Which methods are simplified by using this abstraction and why

* removeBlock in levelcontrol
    * doesn't have to check block/powerup type


### Others?
