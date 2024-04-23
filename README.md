
### Running the Program

* Main class:
    * The main class creates a new instance of the Controller class. In the Controller class'
      constructor, a new window is created prompting the user to choose a XML configuration file for
      a simulation they wish to play. After a file is chosen, the constructor creates a new instance
      of the XmlParser and calls its readXml method to parse the XML configuration file. This
      successfully loads all data necessary for running the simulation.

* Data files needed:
    * Test XML configuration files: data folder
    * GUI images: src/main/java/doc/images folder
    * GUI labels and language property files: src/main/resources folder

* Interesting data files:
    * Test XML configuration files:
        * [Catching Fire - Inside-Out Burn](data/fire/FireCenterBurning.xml)
        * [Game of Life - Copperhead Spaceship](data/gameoflife/GameOfLifeCopperhead.xml)
        * [Game of Life - Gosper Glider Gun](data/gameoflife/GameOfLifeGosperGliderGun.xml)
        * [Percolation - We Love CS308](data/percolation/PercolationCS308.xml)
        * [Sugar Scape - 2 Mounds](data/sugar/SugarTestMounds.xml)
        * [Falling Sand - Water Absorbs Sand](data/falling/FallingTestWaterAbsorbsSand.xml)
        * [Schelling - 1 Free Cell](data/schelling/SchellingOneFree.xml)

* Key/Mouse inputs:
    * Mouse click - interact with the GUI to generate and visualize simulations

