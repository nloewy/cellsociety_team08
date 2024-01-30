package cellsociety.Model;

import java.util.*;

public abstract class Simulation {

  /**
   * Abstract Class that runs the simulation of a cellular automata. Subclasses will implement
   * transitionFunction() based on the rules of the simulation
   *
   * @author Noah Loewy
   */
  private Neighborhood myNeighborhood;
  private Grid myGrid;


  public Simulation(){}
  /**
   * Constructs a basic Simulation object
   *
   * @param rows, the number of rows in the 2-dimensional grid
   * @param cols, the number of columns in the 2-dimensional grid
   * @param neighborhoodType, the definition of neighbors
   * @param stateList, a list of the integer representation of each cells state, by rows, then cols
   */
  public Simulation(int rows, int cols, Neighborhood neighborhoodType, List<Integer> stateList) {
    myGrid = new Grid(rows, cols, stateList);
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

  /**
   * Given a list of cells, and an integer representing a state, determines the number of cells in
   * the list that are currently at that state
   *
   * @param neighbors, a list of cells, representing the neighbors of a central cell
   * @param state,     an integer, representing the state to check for
   * @return an integer, representing the number of cells in neighbors where myCurrentState == state
   */
  public int countNeighborsInState(List<Cell> neighbors, int state) {
    int count = 0;
    for (Cell c : neighbors) {
      if (c.getCurrentState() == state) {
        count++;
      }
    }
    return count;
  }

  public Iterator<Cell> getIterator(){
    return myGrid.iterator();
  }
  private void printForDebugging() {
    Iterator<Cell> iterator2 = myGrid.iterator();
    int count = 0;
    while (iterator2.hasNext()) {
      if (count % 12 == 0) {
        System.out.println();
      }
      Cell c = iterator2.next();
      count++;
      System.out.print(c.getCurrentState() + " ");

    }

  }
}
