package cellsociety.model.simulation;

import cellsociety.model.core.Cell;
import cellsociety.model.core.Grid;
import cellsociety.model.core.Point;
import cellsociety.model.neighborhood.Neighborhood;
import java.util.ArrayList;
import java.util.function.Function;
import java.util.Iterator;
import java.util.List;

public abstract class Simulation<T extends Cell> {

  /**
   * Abstract Class that runs the simulation of a cellular automata. Subclasses will implement
   * transitionFunction() based on the rules of the simulation
   *
   * @author Noah Loewy
   */
  private Neighborhood myNeighborhood;
  private Grid<T> myGrid;


  public Simulation() {
  }

  /**
   * Constructs a basic Simulation object
   *
   * @param rows,             the number of rows in the 2-dimensional grid
   * @param cols,             the number of columns in the 2-dimensional grid
   * @param neighborhoodType, the definition of neighbors
   * @param stateList,        a list of the integer representation of each cells state, by rows,
   *                          then cols
   */

  public Simulation(int rows, int cols, Neighborhood neighborhoodType, List<Integer> stateList,
      Function<Integer, T> cellInitializer) {
    List<T> cellList = new ArrayList<>();
    for (int i = 0; i < stateList.size(); i++) {
      cellList.add(cellInitializer.apply(i));
    }
    myGrid = new Grid<T>(rows, cols, cellList);
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
    Iterator<T> iterator = myGrid.iterator();
    while (iterator.hasNext()) {
      T cell = iterator.next();
      cell.updateStates();
    }
  }

  /**
   * Returns List of all cells that are considered "neighbors" to the parameter cell, given the
   * definition of a neighborhood provided by instance variable myNeighborhood
   *
   * @param cell, a cell object that we are trying to get the neighbors of
   * @return List<T>, all neighboring cell objects to c
   */
  public List<T> getNeighbors(T cell) {
    List<T> neighboringCells = new ArrayList<>();
    List<Point> neighboringCoordinates = myNeighborhood.getNeighborCoordinates(cell.getLocation());
    for (Point p : neighboringCoordinates) {
      try {
        T neighbor = myGrid.getCellAtLocation(p);
        neighboringCells.add(neighbor);
      } catch (IndexOutOfBoundsException e) {}
    }
    return neighboringCells;
  }

  /**
   * Given a list of cells, and an integer representing a state, determines the number of cells in
   * the list that are currently at that state
   *
   * @param neighbors, a list of cells, representing the neighbors of a central cell
   * @param state,     an integer, representing the state to check for
   * @return an integer, representing the number of cells in neighbors where myCurrentState == state
   */
  public int countNeighborsInState(List<T> neighbors, int state) {
    int count = 0;
    for (T c : neighbors) {
      if (c.getCurrentState() == state) {
        count++;
      }
    }
    return count;
  }

  public Iterator<T> getIterator() {
    return myGrid.iterator();
  }

  private void printForDebugging() {
    Iterator<T> iterator2 = myGrid.iterator();
    int count = 0;
    while (iterator2.hasNext()) {
      if (count % 6 == 0) {
        System.out.println();
      }
      T c = iterator2.next();
      count++;
      System.out.print(c.getCurrentState() + " ");

    }

  }
}
