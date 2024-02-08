package cellsociety.model.simulation;

import cellsociety.model.core.Cell;
import cellsociety.model.neighborhood.Neighborhood;
import java.util.Iterator;
import java.util.List;

/**
 * This cellular automata simulation represents Conway's Game of Life.
 * <p>
 * author @Noah Loewy
 */

public class GameOfLifeSimulation extends SimpleCellSimulation {

  public static final int DEAD = 0;
  public static final int ALIVE = 1;

  private final int aliveToAliveMin;
  private final int aliveToAliveMax;
  private final int deadToAliveMin;
  private final int deadToAliveMax;

  /**
   * Initializes a GameOfLifeSimulation object
   *
   * @param row,            the number of rows in the 2-dimensional grid
   * @param col,            the number of columns in the 2-dimensional grid
   * @param hoodType,       the definition of neighbors
   * @param stateList,      a list of the integer representation of each cells state, by rows, then
   *                        cols
   * @param aliveToAliveMin minimum number of living cells that can be neighbors of a living cell to
   *                        avoid death by underpopulation
   * @param aliveToAliveMax maximum number of living cells can be neighbors of a living cell to
   *                        avoid death by overpopulation
   * @param deadToAliveMin  minimum number of living cells that can be neighbors of a dead cell to
   *                        allow reproduction
   * @param deadToAliveMax  maximum number of living cells that can be neighbors of a dead cell *
   *                        to allow reproduction
   * @param gridType          type of grid used in simulation
   */
  public GameOfLifeSimulation(int row, int col, Neighborhood hoodType, List<Integer> stateList,
      int aliveToAliveMin, int aliveToAliveMax, int deadToAliveMin, int deadToAliveMax, String gridType, String cellShape) {
    super(row, col, hoodType, stateList, gridType, cellShape);
    this.aliveToAliveMin = aliveToAliveMin;
    this.aliveToAliveMax = aliveToAliveMax;
    this.deadToAliveMin = deadToAliveMin;
    this.deadToAliveMax = deadToAliveMax;
  }

  /**
   * Handles transition of alive cell in GameOfLifeSimulation. Alive cells with no less than
   * aliveToAliveMin and no more than aliveToAliveMax living neighbors will remain alive, whereas
   * all other alive cells will die
   *
   * @param currentCell the transitioning cell object
   */
  private void handleAliveCell(Cell currentCell, int aliveNeighbors) {
    if (aliveNeighbors >= aliveToAliveMin && aliveNeighbors <= aliveToAliveMax) {
      currentCell.setNextState(ALIVE);
    } else {
      currentCell.setNextState(DEAD);
    }
  }

  /**
   * Handles transition of alive cell in GameOfLifeSimulation. Dead cells with no less than
   * deadToAliveMin and no more than deadToAliveMax living neighbors will remain alive, whereas all
   * other alive cells will die
   *
   * @param currentCell the transitioning cell object
   */
  private void handleDeadCell(Cell currentCell, int aliveNeighbors) {
    if (aliveNeighbors >= deadToAliveMin && aliveNeighbors <= deadToAliveMax) {
      currentCell.setNextState(ALIVE);
    } else {
      currentCell.setNextState(DEAD);
    }
  }

  /**
   * Iterates through each cell, and calls the proper helper function for transitioning based on the
   * current state of the cell in the Game of Life Simulation
   */
  @Override
  public void transitionFunction() {
    Iterator<Cell> gridIterator = getIterator();
    while (gridIterator.hasNext()) {
      Cell currentCell = gridIterator.next();
      List<Cell> neighbors = getNeighborhood().getNeighbors(getGrid(),currentCell);
      int aliveNeighbors = countNeighborsInState(neighbors, ALIVE);
      if (currentCell.getCurrentState() == ALIVE) {
        handleAliveCell(currentCell, aliveNeighbors);
      }
      if (currentCell.getCurrentState() == DEAD) {
        handleDeadCell(currentCell, aliveNeighbors);
      }
    }
  }
}
