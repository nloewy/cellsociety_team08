# Cell Society Design Final
### Team 08
### Noah Loewy, Alisha Zhang, Judy Lee

## Team Roles and Responsibilities

* Team Member #1: Noah Loewy
    * Noah was responsible for the design and implementation of the classes in the model
      package. This includes the classes for Simulation, Grid, Cell, Neighborhood, Shape, and all of
      their children classes. In addition, Noah played a role in fixing bugs that were occuring in
      the View.

* Team Member #2: Judy Lee:
    * Judy was responsible for all of the classes in the configuration package. This includes the
      development of an XML Parser, handling exceptions for invalid input, and allowing users to
      save simulations and write to XML. In addition, Judy wrote more than half of our test cases
      and helped with quality assurance.

* Team Member #3: Alisha Zhang:
    * Alisha was responsible for all of the classes in the view package, including the Controller
      and CellView classes. Alisha also played a role in the initial design of the Model classes,
      which were the first things we implemented.

## Design goals

Our fundamental goal in our design of the Cell Society project was adhering to the open-closed
principle. We believe this principle is especially important in the scope of this project, as users
may want to add new simulations, grid types, cell states, and other features. Consequently, we tried
to employ abstraction whenever possible, so that the project could be extended with minimal work.
With respect to the model, we abstracted away all of the core features of our implementation: namely
Cells, Grids, Shapes, Neighborhoods, and Simulations. Furthermore, we wanted our model to be
completely agnostic to the view, and for the implementation of key data structures, such as the
grid, to be hidden from other classes.

#### What Features are Easy to Add

1. Neighborhood Types
2. Cell Shapes
3. Languages
4. Grid Type (edge policy)
5. New Cell Colors
6. Simulations


## High-level Design

#### Core Classes

Our project has the following Core Classes:

**Cell**:

The `Cell` class represents a singular unit in a Cellular Automata simulation. There are two types of
information encapsulated in the Cell class: the location of the `Cell`, as well as the current state
of the `Cell`. The data pertaining to location is necessary to examine the cell in the context of the
other cells around it, such as its vertices on a unit grid and its neighbors. State data is required
for the transition function. We recognize that it would be optimal (by the Single Responsibility
Principle) to turn state into its own class, and we unfortunately did not have enough time to make
this adjustment. The `Cell` class is an active class, and holds the logic for transitioning from one
generation to the next without other dependencies. The general `Cell` class is an abstraction, and its 
children classes, each with their own `tranasition` function, hold the logic for transitioning based 
on the type of cell.

**Grid**

The `Grid` is an abstraction that represents a Collection of cells on a plane. In our case, the Grid
is represented as a List of Cell objects in row major order. However, we encapsulate the
implementation of the Grid by using an iterator to access the physical grid. We also include
multiple types of Grid, which behave the same except for their edge policy. This edge policy is
defined in the `containsVertex` method, which is called in order to determine if a list of
vertices "contains" a given input vertex. Although this seems rather trivial, the abstraction allows
us to incorporate unique edge policies, such as the "Warped" grid we implement by using modular
arithmetic. Including this method in the `Grid` is necessary for handling edge cases, such as in the
Warped Grid, where the edge policy may cause vertices that are not necessarily "adjacent" to be
treated as such. Every `Simulation` object has a `Grid` instance variable (note: not the actual list
of `Cell` objects, but rather the object as a whole). This allows for the View to access the `Grid`
indirectly, using the iterator.

**Shape**

`Shape` is an interface that handles the way a Cell's vertices are constructed. The role of
the `Shape` interface and its implementations is to aid in constructing the lattice grid of Cells.
Implementations of `Shape` are called during the constrution of these `Cell` objects, as they enable
us to represent each cell's location on the 2d plane as a collection of its vertices. This makes
getting a Cell's `Neighborhood` more general, and not dependent on `Shape`.

**Neighborhood**

`Neighborhood` refers to the mechanism that Cell's use in order to identify what `Cell` objects are
surrounding it. The neighbors of a Cell are only calculated once, during initialization (or if
the `Shape` or `Neighborhood` is changed, requiring new neighbors). A cell's `neighbors` are
calculated by examining the vertices of the given `Cell`, as well as other cells in the grid. To
calculate a cell's neighborhood, we examine all Cell's in the grid (particularly just their biggest
vertices), and then call a different `isValidNeighbor` function depending on the concrete type
of `Neighborhood` being used.

**Simulation**

The `Simulation` class is unique because it interacts with the View (to obtain input parameters),
but also creates the `Cell` and `Grid` objects required for modeling the Cellular Automata. Each
unique `Simulation` implementation has its own subclass of the general `Simulation` class, as well
as a subclass of the `Cell` class. Each Simulation has a unique transition function that will
iterate through the Grid in some special manner, and then call its Cell-specific transition
function.

**Cell View**

The `CellView` class is part of the View, and allows to display a particular `Cell` on the scene.
This class holds the location on the User Interface where a Cell should be displayed, as well as how
to draw it on the User Interface. This involves using the aforementioned `Grid` iterator, so it can
get the `vertices` of each individual `Cell`, and know where to display it. Additionally, the
children of `CellView` (one for each simulation) all inherit the general `CellView` methods, with
the exception of the `getColors` method, which obtains a color from the CSS file based on
the `State` / `Simulation` combination.

**Controller**

The `Controller` class interacts with both the `XmlParser` and the `SimulationPage`, in order to
bridge the gap between the two. `Controller` gets all of the necessary input from the XML files, and
then creates a `SimulationPage` object to represent the automata visually. The `Controller` handles
the loop to regularly call the `transitionFunction` for each simulation, to ensure the model and
view update regularly and simultaneously. Furthermore, the `Controller` also handles user input to
the interface via a series of `EventHandlers`, which will call necessary methods in
the `SimulationPage`, that can update the view, or call subsequent model functions to update the
model, if necessary.

**SimulationPage**

The `SimulationPage` contains all of the `CellView` objects, and deals with the actual design of
the `User Interface`. It creates buttons for User Input and places them in the proper locations
based on the properties file. Furthermore, the `SimulationPage` contains the `updateView` method,
which is what employs the aforementioned iterator of Cells in order to send the updated states to
the `CellView` objects, so the depictions of the states can be updated.

**XmlParser**

The XmlParser handles Xml Configuration files that are uploaded to the model, parses them for their
key parameters, and throws exceptions if they are invalid. These exceptions are later caught by
the `View` and are displayed as message boxes. In addition, the Xml Parser has functions that can
write to Xml, in order to save the states of simulations when prompted and create a new
configuration file.

## Assumptions that Affect the Design

#### Features Affected by Assumptions

## Significant differences from Original Plan

Our group spent a lot of time developing a plan with as many abstractions as possible, so the number
of major changes from our original DESIGN_PLAN.md document is relatively minimal.

As highlighted in our DESIGN_PLAN, we implemented a Neighborhood abstract class, a Simulation
abstract class, an XmlParser, Controller, and more. Similarly, we made the location of Cell's
constant, and instead decided for the states to change with each timestep, as opposed to locations.
We also mostly adhered to the primary and secondary responsibilities highlighted in the planning
document, but all made conscious efforts to help each other when necessary.

The one major deviation from our design document was our decision to structure the Neighborhood
class differently from our original specification. Instead of having a `getNeighbors` function, each
specific concrete implementation of `Neighborhood` has a `isValidNeighbor` function, that depended
on the vertices of the `Cell`, as opposed to differences in the row and column location of
the `Cell`. However, once we saw the `Shape` requirement in the change-week, we realized that a new
approach was necessary. This is because requiring a **different** method of getting a cell's
neighbors for every combination of `Shape` and `Neighborhood` would lead to an exponentially high
and unnecessary number of implementations. Thus, we changed the neighborhood implementations to rely
on the specific vertices, to abstract away `Shape` during the calculation of a `Cell`
object's `neighbors`.

## New Features HowTo

#### Easy to Add Features


**Neighborhood Types**
To create a new neighborhood type, create a class that extends `Neighborhood`, and implement a `isValidNeighbor(Cell cell1, Cell cell2, Grid grid)` function. This function should rely on the vertices of the two cells to determine if they are neighbors. This will involve iterating through the grid and looking for vertices that are "adjacent", which can be checked using the grid's `containsVertex` function.  

**Cell Shapes**
**Languages**
**Grid Type (edge policy)**
**New Cell Colors**
**Simulations**


#### Other Features not yet Done

