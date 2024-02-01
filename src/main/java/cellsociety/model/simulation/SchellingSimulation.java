package cellsociety.model.simulation;

import cellsociety.model.core.Cell;
import cellsociety.model.neighborhood.Neighborhood;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

public class SchellingSimulation extends SimpleCellSimulation {

  /**
   * This cellular automata simulation represents the Schelling Segregation Model.
   * <p>
   * author @noah loewy
   */
  public static final int PLACEHOLDER = -1;
  public static final int GROUPA = 0;
  public static final int GROUPB = 1;
  public static final int EMPTY = 2;

  private double proportionNeededToStay;

  /**
   * Initializes a SchellingSimulation object
   *
   * @param row,              the number of rows in the 2-dimensional grid
   * @param col,              the number of columns in the 2-dimensional grid
   * @param neighborhoodType, the definition of neighbors
   * @param stateList,        a list of the integer representation of each cells state, by rows,
   *                          then cols
   */
  public SchellingSimulation(int row, int col, Neighborhood neighborhoodType,
      List<Integer> stateList) {
    super(row, col, neighborhoodType, stateList);
    proportionNeededToStay = .5;
  }

  /**
   * Transition function for Segregation Model. Cells that are "satisfied" with current location
   * (meaning that the proportion of non-empty neighbors that are the same group of them exceeds
   * proportionNeededToStay) remain in place, whereas others move to a random vacant cell.
   */
  @Override
  //MAJOR REFACTORING NEEDED
  public void transitionFunction() {
    Iterator<Cell> gridIterator = getIterator();
    List<Cell> emptyCells = new ArrayList<>();
    List<Cell> toLeave = new ArrayList<>();
    while (gridIterator.hasNext()) {
      Cell currentCell = gridIterator.next();
      if (currentCell.getCurrentState() == EMPTY) {
        emptyCells.add(currentCell);
      }
      List<Cell> neighbors = getNeighbors(currentCell);
      int totalNeighbors = neighbors.size();
      int numEmptyNeighbors = countNeighborsInState(neighbors, EMPTY);
      int numNeighborsSameState = countNeighborsInState(neighbors, currentCell.getCurrentState());
      if (currentCell.getCurrentState() != EMPTY) {
        if (totalNeighbors != numEmptyNeighbors
            && (double) numNeighborsSameState / (totalNeighbors - numEmptyNeighbors)
            < proportionNeededToStay) {
          toLeave.add(currentCell);
        } else {
          currentCell.setNextState(currentCell.getCurrentState());
        }
      }
    }
    Collections.shuffle(toLeave);
    int index = 0;
    while (index < Math.min(toLeave.size(), emptyCells.size())) {
      emptyCells.get(index).setNextState(toLeave.get(index).getCurrentState());
      toLeave.get(index).setNextState(EMPTY);
      index++;
    }
    for (Cell c : emptyCells) {
      if (c.getNextState() == PLACEHOLDER) {
        c.setNextState(c.getCurrentState());
      }
    }
    for (Cell c : toLeave) {
      if (c.getNextState() == PLACEHOLDER) {
        c.setNextState(c.getCurrentState());
      }
    }
  }
}
