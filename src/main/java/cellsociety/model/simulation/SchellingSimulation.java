package cellsociety.model.simulation;

import cellsociety.model.core.cell.SchellingCell;
import cellsociety.model.core.shape.Shape;
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

public class SchellingSimulation extends Simulation<SchellingCell> {

  public static final int EMPTY = 2;
  public static final int TEMP_EMPTY = 3;
  public static final int TEMP_TO_MOVE = 4;

  private List<SchellingCell> myCellsToMove;
  private List<SchellingCell> myEmptyCells;
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

  public List<SchellingCell> cellMaker(int col, List<Integer> stateList, Shape cellShape) {
    List<SchellingCell> cellList = new ArrayList<>();
    Map<String, Double> params = new HashMap<>();
    params.put("proportionNeededToStay", proportionNeededToStay);
    for (int i = 0; i < stateList.size(); i++) {
      cellList.add(new SchellingCell(stateList.get(i), i / col, i % col, cellShape, params));
    }
    return cellList;
  }

  private void moveCells() {
    Collections.shuffle(myEmptyCells);
    Collections.shuffle(myCellsToMove);
    int shorterListLength = Math.min(myCellsToMove.size(), myEmptyCells.size());
    List<SchellingCell> remainingList =
        (myCellsToMove.size() < myEmptyCells.size()) ? myEmptyCells : myCellsToMove;
    for (int i = 0; i < shorterListLength; i++) {
      myEmptyCells.get(i).setNextState(myCellsToMove.get(i).getCurrentState());
      myCellsToMove.get(i).setNextState(EMPTY);
    }
    for (int i = shorterListLength; i < remainingList.size(); i++) {
      remainingList.get(i).setNextState(remainingList.get(i).getCurrentState());
    }
  }

  /**
   * Transition function for Segregation Model. Cells that are "satisfied" with current location
   * (meaning that the proportion of non-empty neighbors that are the same group of them exceeds
   * proportionNeededToStay) remain in place, whereas others move to a random vacant cell.
   */
  @Override
  public void transitionFunction() {
    Iterator<SchellingCell> gridIterator = getIterator();
    myCellsToMove.clear();
    myEmptyCells.clear();
    while (gridIterator.hasNext()) {
      SchellingCell currentCell = gridIterator.next();
      currentCell.transition();
      if (currentCell.getNextState() == TEMP_EMPTY) {
        myEmptyCells.add(currentCell);
      }
      if (currentCell.getNextState() == TEMP_TO_MOVE) {
        myCellsToMove.add(currentCell);
      }
    }
    moveCells();
  }
}
