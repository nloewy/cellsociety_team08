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

1. Simulations
2. Neighborhood Types
3. Cell Shapes
4. Languages
5. Grid Type (edge policy)
6. New Cell Colors


## High-level Design

#### Core Classes

Our project has the following Core Classes:

**Cell**:

**Grid**

**Shape**

**Simulation**
**Cell View**
**Controller**
**SimulationPage**
**XmlParser**


## Assumptions that Affect the Design

#### Features Affected by Assumptions


## Significant differences from Original Plan


## New Features HowTo

#### Easy to Add Features

#### Other Features not yet Done

