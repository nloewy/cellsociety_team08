package cellsociety.Model;

import java.util.*;

public abstract class Simulation {

  /**
   * Abstract Class that runs the simulation of a cellular automata. Subclasses will implement
   * transitionFunction() based on the rules of the simulation
   */
  protected Neighborhood myNeighborhood;
  protected Grid myGrid;


  /**
   * Constructs a basic Simulation object
   *
   * @param rows, the number of rows in the 2-dimensional grid
   * @param cols, the number of columns in the 2-dimensional grid
   */
  public Simulation(int rows, int cols, Neighborhood neighborhoodType, List<Cell> gridList) {
    myGrid = new Grid(rows, cols, gridList);

    myNeighborhood = neighborhoodType;
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
    printForDebugging();

    Iterator<Cell> iterator = myGrid.iterator();
    while (iterator.hasNext()) {
      Cell c = iterator.next();
      c.updateStates();
    }


  }

  /**
   * Returns List of all cells that are considered "neighbors" to the parameter cell, given the
   * definition of a neighborhood provided by instance variable myNeighborhood
   *
   * @param c, a cell object that we are trying to get the neighbors of
   * @return List<Cell>, all neighboring cell objects to c
   */
  public List<Cell> getNeighbors(Cell c) {
    List<Cell> neighboringCells = new ArrayList<>();
    List<Point> neighboringCoordinates = myNeighborhood.getNeighborCoordinates(c.getLocation());
    for (Point p : neighboringCoordinates) {
      try {
        Cell neighbor = myGrid.getCellAtLocation(p);
        neighboringCells.add(neighbor);
      } catch (IndexOutOfBoundsException e) {
        continue;
      }
    }
    return neighboringCells;
  }


  private void printForDebugging() {
    Iterator<Cell> iterator2 = myGrid.iterator();
    int count = 0;
    while (iterator2.hasNext()) {
      if(count%12==0){
        System.out.println();
      }
      Cell c = iterator2.next();
      count++;
      System.out.print(c.getCurrentState() + " ");

    }
  }
}
