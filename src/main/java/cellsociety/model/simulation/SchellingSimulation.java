package cellsociety.model.simulation;

import cellsociety.model.core.Cell;
import cellsociety.model.neighborhood.Neighborhood;
import cellsociety.model.simulation.Records.SchellingRecord;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

/**
 * This cellular automata simulation represents the Schelling Segregation Model.
 * <p>
 * author @noah loewy
 */

public class SchellingSimulation extends SimpleCellSimulation {

  public static final int PLACEHOLDER = -1;
  public static final int GROUPA = 0;
  public static final int GROUPB = 1;
  public static final int EMPTY = 2;

  private List<Cell> myCellsToMove;
  private List<Cell> myEmptyCells;
  private double proportionNeededToStay;

  /**
   * Initializes a SchellingSimulation object
   *
   * @param row,                   the number of rows in the 2-dimensional grid
   * @param col,                   the number of columns in the 2-dimensional grid
   * @param hoodType,              the definition of neighbors
   * @param stateList,             a list of the integer representation of each cells state, by
   *                               rows, then cols
   * @param proportionNeededToStay the minimum proportion of neighboring cells (excluding empty
   *                               cells) that are of the same state as the given cell, for a cell
   *                               to remain in their current state
   * @param gridType               type of grid used in simulation
   */
  public SchellingSimulation(int row, int col, Neighborhood hoodType, List<Integer> stateList,
      SchellingRecord r) {
    super(row, col, hoodType, stateList, r.gridType(), r.cellShape());
    myCellsToMove = new ArrayList<>();
    myEmptyCells = new ArrayList<>();
    this.proportionNeededToStay = r.proportionNeededToStay();
  }

  /**
   * Given a cell in either group A or group B, places the cell in either myCellsToMove, or updates
   * its future state to its current-state, depending on the state-makeup of its neighbors
   *
   * @param currentCell a cell in group A or B preparing to transition
   */
  private void handleDemographicCell(Cell currentCell) {
    List<Cell> neighbors = getNeighborhood().getNeighbors(getGrid(), currentCell);
    int totalNeighbors = neighbors.size();
    int numEmptyNeighbors = countNeighborsInState(neighbors, EMPTY);
    int numNeighborsSameState = countNeighborsInState(neighbors,
        currentCell.getState().getCurrentStatus());
    if (currentCell.getState().getCurrentStatus() != EMPTY) {
      if (totalNeighbors != numEmptyNeighbors
          && (double) numNeighborsSameState / (totalNeighbors - numEmptyNeighbors)
          < proportionNeededToStay) {
        myCellsToMove.add(currentCell);
      } else {
        currentCell.setNextState(currentCell.getState().getCurrentStatus());
      }
    }
  }

  /**
   * Sets the state of all cells that contain an agent that is moving to empty, and set their new
   * cells to occupied. Also handles case when the amount of agents wanting to leave exceeds the
   * number of available spots.
   */
  private void moveCells() {
    int shorterListLength = Math.min(myCellsToMove.size(), myEmptyCells.size());
    int longerListLength = Math.max(myCellsToMove.size(), myEmptyCells.size());
    for (int i = 0; i < shorterListLength; i++) {
      myEmptyCells.get(i).setNextState(myCellsToMove.get(i).getState().getCurrentStatus());
      myCellsToMove.get(i).setNextState(EMPTY);
    }
    for (int i = shorterListLength; i < longerListLength; i++) {
      if (myCellsToMove.size() < myEmptyCells.size()) {
        myEmptyCells.get(i).setNextState(myEmptyCells.get(i).getState().getCurrentStatus());
      } else {
        myCellsToMove.get(i).setNextState(myCellsToMove.get(i).getState().getCurrentStatus());
      }
    }

  }

  /**
   * Transition function for Segregation Model. Cells that are "satisfied" with current location
   * (meaning that the proportion of non-empty neighbors that are the same group of them exceeds
   * proportionNeededToStay) remain in place, whereas others move to a random vacant cell.
   */
  @Override
  public void transitionFunction() {
    Iterator<Cell> gridIterator = getIterator();
    myCellsToMove.clear();
    myEmptyCells.clear();

    while (gridIterator.hasNext()) {
      Cell currentCell = gridIterator.next();
      if (currentCell.getState().getCurrentStatus() == EMPTY) {
        myEmptyCells.add(currentCell);
      }
      handleDemographicCell(currentCell);
    }
    Collections.shuffle(myCellsToMove);
    moveCells();
  }
}
