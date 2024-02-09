package cellsociety.model.simulation;

import cellsociety.model.core.cell.Cell;
import cellsociety.model.core.cell.SchellingCell;
import cellsociety.model.core.shape.CellShape;
import cellsociety.model.neighborhood.Neighborhood;
import cellsociety.model.simulation.Records.SchellingRecord;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * This cellular automata simulation represents the Schelling Segregation Model.
 * <p>
 * author @noah loewy
 */

public class SchellingSimulation extends Simulation {

  public static final int EMPTY = 2;
  private List<Cell> myCellsToMove;
  private List<Cell> myEmptyCells;
  private double proportionNeededToStay;

  /**
   * Initializes a SchellingSimulation object
   *
   * @param row,       the number of rows in the 2-dimensional grid
   * @param col,       the number of columns in the 2-dimensional grid
   * @param hoodType,  the definition of neighbors
   * @param stateList, a list of the integer representation of each cells state, by rows, then cols
   */
  public SchellingSimulation(int row, int col, Neighborhood hoodType, List<Integer> stateList,
      SchellingRecord r) {
    super(hoodType, r.gridType());
    myCellsToMove = new ArrayList<>();
    myEmptyCells = new ArrayList<>();
    proportionNeededToStay = r.proportionNeededToStay();
    createCellsAndGrid(row, col, stateList, getCellShape(r.cellShape()), hoodType);
  }

  public List<Cell> cellMaker(int col, List<Integer> stateList, CellShape cellShape) {
    List<Cell> cellList = new ArrayList<>();
    Map<String, Double> params = new HashMap<>();
    params.put("proportionNeededToStay", proportionNeededToStay);
    for (int i = 0; i < stateList.size(); i++) {
      cellList.add(new SchellingCell(stateList.get(i), i / col, i % col, cellShape, params));
    }
    return cellList;
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
      myEmptyCells.get(i).setNextState(myCellsToMove.get(i).getCurrentState());
      myCellsToMove.get(i).setNextState(EMPTY);
    }
    for (int i = shorterListLength; i < longerListLength; i++) {
      if (myCellsToMove.size() < myEmptyCells.size()) {
        myEmptyCells.get(i).setNextState(myEmptyCells.get(i).getCurrentState());
      } else {
        myCellsToMove.get(i).setNextState(myCellsToMove.get(i).getCurrentState());
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
      currentCell.transition();
    }
    Iterator<Cell> gridIterator2 = getIterator();
    while (gridIterator2.hasNext()) {
      SchellingCell currentCell = (SchellingCell) gridIterator2.next();
      if (currentCell.getToEmptyList()) {
        myEmptyCells.add(currentCell);
      }
      if (currentCell.getToMove()) {
        myCellsToMove.add(currentCell);
      }
    }
    Collections.shuffle(myCellsToMove);
    moveCells();
  }
}
