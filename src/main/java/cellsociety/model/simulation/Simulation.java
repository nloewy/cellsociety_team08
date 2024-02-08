package cellsociety.model.simulation;

import cellsociety.exception.InvalidValueException;
import cellsociety.model.core.Cell;
import cellsociety.model.core.Grid;
import cellsociety.model.core.HexagonCell;
import cellsociety.model.core.RectangleCell;
import cellsociety.model.core.State;
import cellsociety.model.core.WarpedGrid;
import cellsociety.model.neighborhood.Neighborhood;
import com.sun.jdi.InvalidCodeIndexException;
import java.util.ArrayList;
import java.util.InvalidPropertiesFormatException;
import java.util.Iterator;
import java.util.List;

/**
 * Abstract Class that runs the simulation of a cellular automata. Subclasses will implement
 * transitionFunction() based on the rules of the simulation
 *
 * @author Noah Loewy
 */

public abstract class Simulation<T extends Cell> {

  protected Neighborhood myNeighborhood;
  protected Grid myGrid;
  private String myGridType;

  public Simulation() {
  }

  /**
   * Constructs a basic Simulation object
   *
   * @param row,       the number of rows in the 2-dimensional grid
   * @param col,       the number of columns in the 2-dimensional grid
   * @param hoodType,  the definition of neighbors
   * @param stateList, a list of the integer representation of each cells state, by rows, then cols
   * @param gridType   type of grid used in simulation
   */

  public Simulation(int row, int col, Neighborhood hoodType, List<Integer> stateList,
      String gridType, String cellShape) {

    myNeighborhood = hoodType;
    myGridType = gridType;
    initializeMyGrid(row, col, stateList, cellShape);
  }

  public void initializeMyGrid(int row, int col, List<Integer> stateList, String cellShape) {
    List<Cell> cellList = new ArrayList<>();
    for (int i = 0; i < stateList.size(); i++) {
      Cell newCell = switch (cellShape) {
        case "square" -> new RectangleCell(stateList.get(i), i / col, i % col);
        case "hexagon" -> new HexagonCell(stateList.get(i), i / col, i % col);
        default -> throw new InvalidValueException("Cell Shape Does Not Exist");
      };
      cellList.add(newCell);
    }

    myGrid = switch (myGridType) {
      case "Normal" -> new Grid(row, col, cellList);
      case "Warped" -> new WarpedGrid(row, col, cellList);
      default -> throw new InvalidValueException("Edge Type Does Not Exist");
    };
  }

  /**
   * This abstract function will assign each cell a new value for their myNextState, based on the
   * current grid configuration and the rules of the simulation
   */
  public abstract void transitionFunction();

  /**
   * Iterates through all available cells and updates the current state based on the results of the
   * transition function
   */
  public void processUpdate() {
    Iterator<Cell> iterator = myGrid.iterator();
    while (iterator.hasNext()) {
      Cell cell = iterator.next();
      cell.updateStates();
    }
  }


  /**
   * Given a list of cells, and an integer representing a state, determines the number of cells in
   * the list that are currently at that state
   *
   * @param neighbors, a list of cells, representing the neighbors of a central cell
   * @param state,     an integer, representing the state to check for
   * @return count, representing the number of cells in neighbors where myCurrentState == state
   */
  public int countNeighborsInState(List<T> neighbors, int state) {
    int count = 0;
    for (T c : neighbors) {
      if (c.getState().getCurrentStatus() == state) {
        count++;
      }
    }
    return count;
  }

  public Neighborhood getNeighborhood() {
    return myNeighborhood;
  }

  public Grid getGrid() {
    return myGrid;
  }

  /**
   * Retrieves the Grid's object that can access the grid of cells while hiding the Data Structure
   * used to implement it.
   *
   * @return Iterator object that can iterate through my grid
   */
  public Iterator<T> getIterator() {
    return myGrid.iterator();
  }

}
