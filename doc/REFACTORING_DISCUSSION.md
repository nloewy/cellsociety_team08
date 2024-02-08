# Cell Society Design Plan

### Team 8

### Judy He, Noah Loewy, Alisha Zhang

### Open-Closed Principle

The open-closed principle is a guideline for creating classes that are *closed for modificaiton* and
*open for extension*. Essentially, this means that we do not want to update our classes, as that can
potentially cause a problem with the abstraction, but rather we want to extend them by adding new
subclasses / children.

### Liskov Substitution Principle

The Liskov Substitution Model also focuses on abstraction, and essentially states that replacing a
reference to an abstract object (superclass) with any of its concrete children (subclasses) should
not break the code/model. For example, replacing List with ArrayList should not break the code.

### Our Abstractions
1. Simulations ==> all of the simulations extend a general simulation class. It does not matter what
   type of simulation object you use, as they all have the same public methods. This highlights
   open-closed principle, as we would not have to modify existing code to create a new type of
   Simulation, and adheres to Liskov as well because any reference to a simulation can also refer to
   a specific simulation object.
2. Cells (Shapes) ==> although this abstraction is still in progress, we are trying to abstract away
   the shape of a cell. Essentially, as long as you add the proper code for a new shape, it would
   not be difficult to add any other cell shapes to the model. All of the specific shape classes are
   the same as the general shape class, except for the constructor that creates the vertices.
3. Neighborhoods ==> we abstract the neighborhood by allowing cells to get their nearby cells in
   different ways. This is particularly easy to add new neighborhoods, as only one method from the
   abstract class Neighborhood actually needs to be overriden. Otherwise, you can just use the
   general neighborhood class to loop through the grid (using an iterator for encapsulation, of
   course!) and check on a pairwise level to see if two cells are neighbors. Shapes are also
   abstracted away using this neighbor design.  