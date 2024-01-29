package cellsociety.Model;

import java.util.*;

public abstract class Simulation {

  /**
   * Abstract Class that runs the simulation of a cellular automata. Subclasses will implement
   * transitionFunction() based on the rules of the simulation
   */
  private Neighborhood neighborhood;
  private Grid myGrid;
  private List<Cell> myCells;

  /**
   * Constructs a basic Simulation object
   *
   * @param rows, the number of rows in the 2-dimensional grid
   * @param cols, the number of columns in the 2-dimensional grid
   */
  public Simulation(int rows, int cols) {
    myGrid = new Grid(rows, cols);
  }

  /**
   * This abstract function will assign each cell a new value for their myNextState, based on the
   * current grid configuration and the rules of the simulation
   */
  public abstract void transitionFunction();

  /**
   * Iterates through all available cells and updates the current state based on the results of the
   * transition function
   */
  public void processUpdate() {
    Iterator<Cell> iterator = myGrid.iterator();
    while (iterator.hasNext()) {
      Cell c = iterator.next();
      c.updateStates();
    }
  }
}
