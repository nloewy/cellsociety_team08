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

  public static final int EMPTY = 0;
  public static final int GROUP_A = 1;
  public static final int GROUP_B = 2;
  public static final int TEMP_TO_MOVE = 3;
  public static final int TEMP_EMPTY_A = 4;
  public static final int TEMP_EMPTY_B = 5;



  private List<SchellingCell> myCellsToMoveA;
  private List<SchellingCell> myCellsToMoveB;

  private List<SchellingCell> myEmptyCellsA;
  private List<SchellingCell> myEmptyCellsB;

  private double proportionNeededToStay;

  /**
   * Initializes a SchellingSimulation object
   *
   * @param row,       the number of rows in the 2-dimensional grid
   * @param col,       the number of columns in the 2-dimensional grid
   * @param hoodType,  the definition of neighbors
   * @param stateList, a list of the integer representation of each cells state in row major order
   * @param r,         a record of all parameters needed for Schelling Simulation. Description of
   *                   parameters can be found in the SchellingCell class
   */
  public SchellingSimulation(int row, int col, Neighborhood hoodType, List<Integer> stateList,
      SchellingRecord r) {
    super(hoodType, r.gridType());
    myCellsToMoveA = new ArrayList<>();
    myCellsToMoveB = new ArrayList<>();
    myEmptyCellsA = new ArrayList<>();
    myEmptyCellsB = new ArrayList<>();
    proportionNeededToStay = r.proportionNeededToStay();
    createCellsAndGrid(row, col, stateList, getCellShape(r.cellShape()), hoodType);
  }

  /**
   * Creates list of SchellingCells objects to be passed into grid
   *
   * @param col       number of columns in grid for simulation
   * @param stateList list of all cell's states in row major order
   * @param shape     Shape object representing the shape of the cell as represented on 2d plane
   * @return list of initialized SchellingCells
   */

  public List<SchellingCell> cellMaker(int col, List<Integer> stateList, Shape shape) {
    List<SchellingCell> cellList = new ArrayList<>();
    Map<String, Double> params = new HashMap<>();
    params.put("proportionNeededToStay", proportionNeededToStay);
    for (int i = 0; i < stateList.size(); i++) {
      cellList.add(new SchellingCell(stateList.get(i), i / col, i % col, shape, params));
    }
    return cellList;
  }

  /**
   * Updates next state of cells so that in a random order, cells that are not satisfied can move to
   * empty cells that will satisfy them.
   *
   * @param emptyList, a list of empty cells which would satisfy the agents in toMoveList.
   * @param toMoveList, a list of cells that would be satisfied if they move to a cell in empty list
   */
  private void moveCells(List<SchellingCell> emptyList, List<SchellingCell> toMoveList) {
    Collections.shuffle(emptyList);
    Collections.shuffle(toMoveList);
    int shorterListLength = Math.min(toMoveList.size(), emptyList.size());
    List<SchellingCell> remainingList =
        (toMoveList.size() < emptyList.size()) ? emptyList : toMoveList;
    for (int i = 0; i < shorterListLength; i++) {
      emptyList.get(i).setNextState(toMoveList.get(i).getCurrentState());
      toMoveList.get(i).setNextState(EMPTY);
    }
    for (int i = shorterListLength; i < remainingList.size(); i++) {
      remainingList.get(i).setNextState(remainingList.get(i).getCurrentState());
    }
  }

  /**
   * Transition function for Segregation Model. Iterates through each cell and calls the transition
   * function, which will update the cell's next state to highlight if it would like to move to a
   * vacant location. Then it calls private helper function moveCells which actually update the next
   * states to their true values.
   */
  @Override
  public void transitionFunction() {
    Iterator<SchellingCell> gridIterator = getIterator();
    myCellsToMoveA.clear();
    myCellsToMoveB.clear();
    myEmptyCellsA.clear();
    myEmptyCellsB.clear();
    while (gridIterator.hasNext()) {
      SchellingCell currentCell = gridIterator.next();
      currentCell.transition();
      if (currentCell.getNextState() == TEMP_EMPTY_A) {
        myEmptyCellsA.add(currentCell);
      }
      if (currentCell.getNextState() == TEMP_EMPTY_B) {
        myEmptyCellsB.add(currentCell);
      }
      if (currentCell.getNextState() == TEMP_TO_MOVE && currentCell.getCurrentState()==GROUP_A) {
        myCellsToMoveA.add(currentCell);
      }
      if (currentCell.getNextState() == TEMP_TO_MOVE && currentCell.getCurrentState()==GROUP_B) {
        myCellsToMoveB.add(currentCell);
      }
    }
    moveCells(myEmptyCellsA, myCellsToMoveA);
    moveCells(myEmptyCellsB, myCellsToMoveB);
  }
}
