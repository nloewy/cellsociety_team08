package cellsociety.model.simulation;

import cellsociety.model.core.Cell;
import cellsociety.model.neighborhood.Neighborhood;
import java.util.Iterator;
import java.util.List;

public class PercolationSimulation extends SimpleCellSimulation {

  /**
   * This cellular automata simulation represents the CS201 Percolation Assignment.
   * <p>
   * author @noah loewy
   */
  public static final int OPEN = 0;
  public static final int PERCOLATED = 1;
  public static final int BLOCKED = 2;

  private int neighborsPercolatedRequired;


  public PercolationSimulation(int row, int col, Neighborhood neighborhoodType,
      List<Integer> stateList) {
    super(row, col, neighborhoodType, stateList);

    //these will be parameters, as opposed to hardcoded
    neighborsPercolatedRequired = 1;
  }

  /**
   * Transition function for Percolation. All cells remain in their state, unless the cell is open,
   * AND the open cell at least neighborsPercolatedRequired of the cells neighbors are percolated.
   */
  @Override
  public void transitionFunction() {
    Iterator<Cell> gridIterator = getIterator();
    while (gridIterator.hasNext()) {
      Cell currentCell = gridIterator.next();
      List<Cell> neighbors = getNeighbors(currentCell);
      if (currentCell.getCurrentState() == OPEN) {
        int percolatedNeighbors = countNeighborsInState(neighbors, PERCOLATED);
        if (percolatedNeighbors >= neighborsPercolatedRequired) {
          currentCell.setNextState(PERCOLATED);
        } else {
          currentCell.setNextState(OPEN);
        }
      } else {
        currentCell.setNextState(currentCell.getCurrentState());
      }
    }
  }
}