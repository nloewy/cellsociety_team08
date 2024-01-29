package cellsociety.Model;

import java.util.*;

public class SchellingSimulation extends Simulation {

  /**
   * This cellular automata simulation represents the Schelling Segregation Model.
   * <p>
   * author @noah loewy
   */
  public final int GROUPA = 0;
  public final int GROUPB = 1;
  public final int EMPTY = 2;

  private double proportionNeededToStay;

  public SchellingSimulation(int row, int col, Neighborhood neighborhoodType,
      List<Cell> gridList) {
    super(row, col, neighborhoodType, gridList);

    //these will be parameters, as opposed to hardcoded
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
    Iterator<Cell> gridIterator = myGrid.iterator();
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
      if (c.getNextState() == -1) {
        c.setNextState(c.getCurrentState());
      }
    }
    for (Cell c : toLeave) {
      if (c.getNextState() == -1) {
        c.setNextState(c.getCurrentState());
      }
    }
  }
}
