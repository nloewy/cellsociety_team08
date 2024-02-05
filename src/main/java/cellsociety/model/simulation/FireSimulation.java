package cellsociety.model.simulation;

import static java.lang.Math.random;

import cellsociety.model.core.Cell;
import cellsociety.model.neighborhood.Neighborhood;
import java.util.Iterator;
import java.util.List;


/**
 * Represents the spreading of a wild fire in a forest
 *
 * @author Noah Loewy
 */

public class FireSimulation extends SimpleCellSimulation {

  public static final int EMPTY = 0;
  public static final int TREE = 1;
  public static final int BURNING = 2;


  private final double probTreeIgnites;
  private final double probTreeCreated;
  private final int neighborsToIgnite;

  /**
   * Initializes a FireSimulation object
   *
   * @param row,              the number of rows in the 2-dimensional grid
   * @param col,              the number of columns in the 2-dimensional grid
   * @param hoodType,         the definition of neighbors
   * @param stateList,        a list of the integer representation of each cells state, by rows,
   *                          then cols
   * @param neighborsToIgnite the number of burning neighbors needed for a tree to catch fire
   * @param probTreeIgnites   probability a tree (not meeting the required number of burning
   *                          neighbors) catches fire
   * @param probTreeCreated   probability an empty cell becomes a tree
   */
  public FireSimulation(int row, int col, Neighborhood hoodType, List<Integer> stateList,
      int neighborsToIgnite, double probTreeIgnites, double probTreeCreated) {
    super(row, col, hoodType, stateList);
    this.neighborsToIgnite = neighborsToIgnite;
    this.probTreeIgnites = probTreeIgnites;
    this.probTreeCreated = probTreeCreated;
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
          break;
        }
        case BURNING: {
          currentCell.setNextState(EMPTY);
          break;
        }
        case TREE: {
          handleTreeCell(currentCell);
          break;
        }
      }
    }
  }
}
