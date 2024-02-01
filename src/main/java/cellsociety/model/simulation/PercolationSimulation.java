package cellsociety.model.simulation;

import cellsociety.model.core.Cell;
import cellsociety.model.neighborhood.Neighborhood;
import java.util.Iterator;
import java.util.List;

public class PercolationSimulation extends SimpleCellSimulation {

  /**
   * This cellular automata simulation represents the CS201 Percolation Assignment.
   *
   * author @noah loewy
   */
  public static final int OPEN = 0;
  public static final int PERCOLATED = 1;
  public static final int BLOCKED = 2;

  private int neighborsPercolatedRequired;


  /**
   * Initializes a PercolationSimulation object
   *
   * @param row,              the number of rows in the 2-dimensional grid
   * @param col,              the number of columns in the 2-dimensional grid
   * @param neighborhoodType, the definition of neighbors
   * @param stateList,        a list of the integer representation of each cells state, by rows,
   *                          then cols
   */
  public PercolationSimulation(int row, int col, Neighborhood neighborhoodType,
      List<Integer> stateList) {
    super(row, col, neighborhoodType, stateList);

    //these will be parameters, as opposed to hardcoded
    neighborsPercolatedRequired = 1;
  }

  /**
   * Handles transition of open cell in PercolationSimulation. Open cells with at least
   * neighersPercolatedRequired will become percolated, and otherwise will remain open.
   * @param currentCell the transitioning cell object
   * @param neighbors all cells in the neighborhood of the transitioning cell, under the current
   *                 definition of neighborhood
   */
  private void handleOpenCell(Cell currentCell, List<Cell> neighbors){
    int percolatedNeighbors = countNeighborsInState(neighbors, PERCOLATED);
    if (percolatedNeighbors >= neighborsPercolatedRequired) {
      currentCell.setNextState(PERCOLATED);
    } else {
      currentCell.setNextState(OPEN);
    }
  }

  /**
   * Transition function for Percolation. All cells remain in their state, unless the cell is open,
   * in which the cell is passed into the helper function handleOpenCell for transitioning
   */
  @Override
  public void transitionFunction() {
    Iterator<Cell> gridIterator = getIterator();
    while (gridIterator.hasNext()) {
      Cell currentCell = gridIterator.next();
      List<Cell> neighbors = getNeighbors(currentCell);
      if (currentCell.getCurrentState() == OPEN) {
        handleOpenCell(currentCell, neighbors);
      } else {
        currentCell.setNextState(currentCell.getCurrentState());
      }
    }
  }
}
