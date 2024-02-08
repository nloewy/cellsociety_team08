package cellsociety.model.simulation;

import cellsociety.model.core.Cell;
import cellsociety.model.neighborhood.Neighborhood;
import java.util.Iterator;
import java.util.List;
import cellsociety.model.simulation.Records.PercolationRecord;

/**
 * This cellular automata simulation represents the CS201 Percolation Assignment
 * <p>
 * author @noah loewy
 */

public class PercolationSimulation extends SimpleCellSimulation {

  public static final int OPEN = 0;
  public static final int PERCOLATED = 1;
  public static final int BLOCKED = 2;

  private final int percolatedNeighbors;


  /**
   * Initializes a PercolationSimulation object
   *
   * @param row,                the number of rows in the 2-dimensional grid
   * @param col,                the number of columns in the 2-dimensional grid
   * @param hoodType,           the definition of neighbors
   * @param stateList,          a list of the integer representation of each cells state, by rows,
   *                            then cols
   * @param percolatedNeighbors minimum number of percolated neighbors an open cell must have for it
   *                            to percolate
   * @param gridType            type of grid used in simulation
   */
  public PercolationSimulation(int row, int col, Neighborhood hoodType, List<Integer> stateList,
      PercolationRecord r) {
    super(row, col, hoodType, stateList, r.gridType(), r.cellShape());
    this.percolatedNeighbors = r.percolatedNeighbors();
  }

  /**
   * Handles transition of open cell in PercolationSimulation. Open cells with at least
   * neighersPercolatedRequired will become percolated, and otherwise will remain open.
   *
   * @param currentCell the transitioning cell object
   * @param neighbors   all cells in the neighborhood of the transitioning cell, under the current
   *                    definition of neighborhood
   */
  private void handleOpenCell(Cell currentCell, List<Cell> neighbors) {
    int numPercolatedNeighbors = countNeighborsInState(neighbors, PERCOLATED);
    if (numPercolatedNeighbors >= percolatedNeighbors) {
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
      List<Cell> neighbors = getNeighborhood().getNeighbors(getGrid(), currentCell);
      if (currentCell.getState().getCurrentStatus() == OPEN) {
        handleOpenCell(currentCell, neighbors);
      } else {
        currentCell.setNextState(currentCell.getState().getCurrentStatus());
      }
    }
  }
}
