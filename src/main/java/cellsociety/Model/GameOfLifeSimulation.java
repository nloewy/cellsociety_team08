package cellsociety.Model;

import java.util.*;

public class GameOfLifeSimulation extends SimpleCellSimulation  {

  /**
   * This cellular automata simulation represents Conway's Game of Life. author @noah loewy
   */
  public static final int DEAD = 0;

  public static final int ALIVE = 1;

  private int aliveToAliveMin;
  private int aliveToAliveMax;
  private int deadToAliveMin;
  private int deadToAliveMax;

  public GameOfLifeSimulation(int row, int col, Neighborhood neighborhoodType,
      List<Integer> stateList) {

    super(row, col, neighborhoodType, stateList);

    //these will be parameters, as opposed to hardcoded
    aliveToAliveMin = 2;
    aliveToAliveMax = 3;
    deadToAliveMin = 3;
    deadToAliveMax = 3;
  }

  /**
   * Transition function for gameOfLife. Alive cells remain alive if their number of alive neighbors
   * is between aliveToAliveMin and aliveToAliveMax. Dead cells become alive if their number of
   * alive neighbors is between deadToAliveMax and deadToAliveMin
   */
  @Override
  public void transitionFunction() {

    Iterator<Cell> gridIterator = getIterator();
    while (gridIterator.hasNext()) {
      Cell currentCell = gridIterator.next();
      List<Cell> neighbors = getNeighbors(currentCell);
      int aliveNeighbors = countNeighborsInState(neighbors, ALIVE);
      //can definitely use some refactoring here
      if (currentCell.getCurrentState() == ALIVE) {
        if (aliveNeighbors >= aliveToAliveMin && aliveNeighbors <= aliveToAliveMax) {
          currentCell.setNextState(ALIVE);
        } else {
          currentCell.setNextState(DEAD);
        }
      }
      if (currentCell.getCurrentState() == DEAD) {
        if (aliveNeighbors >= deadToAliveMin && aliveNeighbors <= deadToAliveMax) {
          currentCell.setNextState(ALIVE);
        } else {
          currentCell.setNextState(DEAD);
        }
      }
    }
  }
}
