package cellsociety.model.simulation;

import static java.lang.Math.random;

import cellsociety.model.core.Cell;
import cellsociety.model.neighborhood.Neighborhood;
import java.util.Iterator;
import java.util.List;


public class FireSimulation extends SimpleCellSimulation {

  /**
   * Represents the spreading of a wild fire in a forest
   *
   * @author Noah Loewy
   */
  public static final int EMPTY = 0;
  public static final int TREE = 1;
  public static final int BURNING = 2;


  private double probTreeIgnites;
  private double probTreeCreated;
  private int neighborsToIgnite;

  /**
   * Initializes a FireSimulation object
   *
   * @param row,              the number of rows in the 2-dimensional grid
   * @param col,              the number of columns in the 2-dimensional grid
   * @param neighborhoodType, the definition of neighbors
   * @param stateList,        a list of the integer representation of each cells state, by rows,
   *                          then cols
   */
  public FireSimulation(int row, int col, Neighborhood neighborhoodType, List<Integer> stateList) {

    super(row, col, neighborhoodType, stateList);

    //these will be parameters, as opposed to hardcoded
    neighborsToIgnite = 1;
    probTreeIgnites = 0;
    probTreeCreated = 0;
  }

  /**
   * Handles transition of empty cell in FireSimulation. Empty cells transition to trees with
   * probability probTreeCreated, and remain empty with probability 1 - probTreeCreated
   *
   * @param currentCell the transitioning cell object
   */
  private void handleEmptyCell(Cell currentCell) {
    if (random() <= probTreeCreated) {
      currentCell.setNextState(TREE);
    } else {
      currentCell.setNextState(EMPTY);
    }
  }

  /**
   * Handles transition of tree cell in FireSimulation. Tree cells with at least neighborsToIgnite
   * burning neighbors will always burn. Tree cells that do not meet the required amount of burning
   * neighbors will burn with probability probTreeIgnites, and remain trees with probability 1 -
   * probTreeIgnites
   *
   * @param currentCell the transitioning cell object
   */
  private void handleTreeCell(Cell currentCell) {
    List<Cell> neighbors = getNeighbors(currentCell);
    int burningNeighbors = countNeighborsInState(neighbors, BURNING);
    if (burningNeighbors >= neighborsToIgnite || random() <= probTreeIgnites) {
      currentCell.setNextState(BURNING);
    } else {
      currentCell.setNextState(TREE);
    }
  }

  /**
   * Iterates through each cell, and calls the proper helper function for transitioning based on the
   * current state of the cell in the Fire Simulation
   */
  @Override
  public void transitionFunction() {
    Iterator<Cell> gridIterator = getIterator();
    while (gridIterator.hasNext()) {
      Cell currentCell = gridIterator.next();
      switch (currentCell.getCurrentState()) {
        case EMPTY: {
          handleEmptyCell(currentCell);
        }
        case BURNING: {
          currentCell.setNextState(EMPTY);
        }
        case TREE: {
          handleTreeCell(currentCell);
        }
      }
    }
  }
}
