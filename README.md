# cell society
## TEAM 8
## Noah Loewy, Judy He, Alisha Zhang


This project implements a cellular automata simulator.

### Timeline

 * Start Date: January 27, 2024

 * Finish Date: February 12, 2024

 * Hours Spent: 240 (approx 3-6 hrs / day / person)


### Attributions

 * Resources used for learning (including AI assistance)
   * Reading XML file: https://mkyong.com/java/how-to-read-xml-file-in-java-dom-parser/
   * Check for empty/badly formatted XML file: https://www.edankert.com/validate.html
   * Writing XML file: https://mkyong.com/java/how-to-create-xml-file-in-java-dom/
 
 * Resources used directly (including AI assistance): None


### Running the Program

 * Main class:
   * The main class creates a new instance of the Controller class. In the Controller class' constructor, a new window is created prompting the user to choose a XML configuration file for a simulation they wish to play. After a file is chosen, the constructor creates a new instance of the XmlParser and calls its readXml method to parse the XML configuration file. This successfully loads all data necessary for running the simulation.

 * Data files needed: 
   * Test XML configuration files: data folder
   * GUI images: src/main/java/doc/images folder
   * GUI labels and language property files: src/main/resources folder

 * Interesting data files:
   * Test XML configuration files:
     * [Sugar simulation test 1](data/sugar/SugarTestSmallHex.xml) 
     * [Game of Life simulation Glider](data/gameoflife/GameOfLifeGlider.xml)
     * [Game of Life simulation Toad](data/gameoflife/GameOfLifeToad.xml)
     * [Game of Life simulation Blinker](data/gameoflife/GameOfLifeBlinker.xml)
     * [Fire simulation test 1](data/fire/FireSmallSimulation1.xml)
     * [Falling Sand simulation test 1](data/falling/FallingTestWaterAbsorbsSand.xml)
     * [WaTor simulation test 1](data/wator/WaTorTest1.xml)
     * [Schelling simulation test 1](data/schelling/SchellingTest1.xml)
     * [Percolation simulation test 1](data/percolation/PercolationTest1.xml)

 * Key/Mouse inputs:
   * Mouse click - interact with the GUI to generate and visualize simulations

### Notes/Assumptions

 * Assumptions or Simplifications:
   * Simplification 
     * Settings and simulation title are always in English
     * To run multiple simulations at the same time, we approached this feature by simply displaying multiple windows, each running a different simulation, rather than keeping one window that is running multiple simulations simultaneously.
   * Assumptions
     * Grid will always be a lattice grid of polygon-shaped cells. This is a tradeoff
       we deemed necessary, however, as doing this allowed us to use the Vertices to represent the
       location of Cells, and not have the `getNeighbors` method be dependent on the shape of the cell. 
     * Transition functions for Wator Cells: We assume that if a shark is next to a fish, he will always eat the fish. In a
     sense, although everything occurs at the same time, the movement of shark's is prioritized first. 
     * Transition functions for FallingSand Cells: We assume that sand movement is prioritized first.
     Furthermore, if two blocks of sand is falling on top of each other into water, the water will "
     make a wave" and separate the sand.

 * Known Bugs: 
   * None

 * Features implemented:
   * Simulations: Falling Sand, Fire, Game of Life (Blinker, Toad, Glider), Percolation, Schelling, Sugar, WaTor
   * Run multiple simulations at the same time in different windows: Users can move windows for each simulation around as they wish; each simulation is controlled by a separate controller, therefore independent from other simulations running at the same time.
   * Program can be in different languages
   * Option to customize the simulation parameters
   * Option to read about the simulation (purpose, author, parameters)
   * Option to speed up, pause, save the simulation 
   * Option to change cell shape to hexagon
   * Option to display a graph demonstrating the status of each state in the simulation 
 * Features unimplemented:
   * Cell Shape Customization - Allow customization of the shape and appearance of cells based on their state (e.g., circles, rectangles, or images like sharks or fire).
   * Change color of cells
   * Using emoji to represent cells

 * Noteworthy Features:
   * Cell shape may be changed to hexagon 
   * Visualize simulation through graphs
   * Neighbor calculation independent of shape and simulation type

### Assignment Impressions

Although this assignment has been challenging and time-demanding, we as a team agree that it was very helpful for learning how to implement a model-view-controller structure, integrate the data and backend with the frontend. Regarding data, we learned how to create XML configuration files and successfully parse and create XML files. As for the backend (the model), we were able to implement the logic behind each simulation. Lastly, for the frontend, we implemented the GUI from scratch following the view-controller architecture. 
Furthermore, we have also learned the importance of repeatedly refactoring our code for scalability, handling errors through custom exceptions, adding meaningful JavaDoc comments, and using resource property files and named constants. We are particularly proud of how abstract our model classes are. For example, the classes for neighborhood, cell shape and cell type are all independent of each other. We were able to minimize dependencies in our implementation, thereby minimizing chances of errors/bugs.
Regarding teamwork, this assignment has been invaluable in teaching us how to collaborate through git (branching and merging) and how to decompose a large assignment in to smaller, individually manageable modules and distribute and plan tasks accordingly. 
