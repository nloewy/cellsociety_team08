package cellsociety.Model;

import java.util.*;

public class PercolationSimulation extends Simulation{

  /**
   * This cellular automata simulation represents the CS201 Percolation Assignment.
   *
   * author @noah loewy
   */
  public final int OPEN = 0;
  public final int PERCOLATED = 1;
  public final int BLOCKED = 2;

  private int neighborsPercolatedRequired;


  public PercolationSimulation(int row, int col, Neighborhood neighborhoodType, List<Cell> gridList){
    super(row,col,neighborhoodType, gridList);

    neighborsPercolatedRequired = 1;
  }

  /**
   * Transition function for Percolation. All cells remain in their state, unless the cell is open,
   * AND the open cell at least neighborsPercolatedRequired of the cells neighbors are percolated.
   */
  @Override
  public void transitionFunction() {
    Iterator gridIterator = myGrid.iterator();
    while (gridIterator.hasNext()) {
      Cell currentCell = (Cell) gridIterator.next();
      List<Cell> neighbors = getNeighbors(currentCell);
      if (currentCell.getCurrentState() == OPEN) {
        int percolatedNeighbors = countNeighborsInState(neighbors, PERCOLATED);
        if (percolatedNeighbors >= neighborsPercolatedRequired) {
          currentCell.setNextState(PERCOLATED);
        } else {
          currentCell.setNextState(OPEN);
        }
      }
      else {
        currentCell.setNextState(currentCell.getCurrentState());
      }
    }
  }
}
