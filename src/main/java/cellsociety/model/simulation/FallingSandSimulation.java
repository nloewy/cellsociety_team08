package cellsociety.model.simulation;

import cellsociety.model.core.cell.Cell;
import cellsociety.model.core.cell.FallingSandCell;
import cellsociety.model.core.shape.Shape;
import cellsociety.model.neighborhood.Neighborhood;
import cellsociety.model.simulation.Records.FallingSandRecord;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * This cellular automata simulation represents the Predator-Prey simulation developed by Alexander
 * K Dewdney
 * <p>
 * author @noah loewy
 */

public class FallingSandSimulation extends Simulation<FallingSandCell> {

  public static final int EMPTY = 0;
  public static final int SAND = 1;
  public static final int WATER = 2;
  public static final int METAL = 3;
  public static final int[] UPDATE_ORDER = {METAL, SAND, WATER, EMPTY};

  /**
   * Initializes a FallingSandSimulation object
   *
   * @param row,       the number of rows in the 2-dimensional grid
   * @param col,       the number of columns in the 2-dimensional grid
   * @param hoodType,  the definition of neighbors
   * @param stateList, a list of the integer representation of each cells state in row major order
   * @param r,         a record of all parameters needed for Falling Sand Simulation. Description of
   *                   parameters can be found in the FallingSand Cell class
   */

  public FallingSandSimulation(int row, int col, Neighborhood hoodType, List<Integer> stateList,
      FallingSandRecord r) {
    super(hoodType, r.gridType());
    createCellsAndGrid(row, col, stateList, getCellShape(r.cellShape()), hoodType);
  }

  /**
   * Creates list of FallingSandCell objects to be passed into grid
   *
   * @param col       number of columns in grid for simulation
   * @param stateList list of all cell's states in row major order
   * @param shape     Shape object representing the shape of the cell as represented on 2d plane
   * @return list of initialized FallingSandCell
   */

  public List<FallingSandCell> cellMaker(int col, List<Integer> stateList,
      Shape shape) {
    List<FallingSandCell> cellList = new ArrayList<>();
    for (int i = 0; i < stateList.size(); i++) {
      cellList.add(new FallingSandCell(stateList.get(i), i / col, i % col, shape));
    }
    return cellList;
  }


  /**
   * Transition function for Falling Sand. Iterates through each cell, starting with sand then
   * water, then other, and calls the cell's transition function.
   */
  @Override
  public void transitionFunction() {
    for (int cellToUpdate : UPDATE_ORDER) {
      List<FallingSandCell> cellsToUpdate = new ArrayList<>();

      // Collect cells to update in reverse order
      Iterator<FallingSandCell> gridIterator = getIterator();
      while (gridIterator.hasNext()) {
        FallingSandCell currentCell = gridIterator.next();
        if (currentCell.getNextState() == Cell.PLACEHOLDER &&
            currentCell.getCurrentState() == cellToUpdate) {
          cellsToUpdate.add(currentCell);
        }
      }

      // Update cells in reverse order
      for (int i = cellsToUpdate.size() - 1; i >= 0; i--) {
        FallingSandCell currentCell = cellsToUpdate.get(i);
        currentCell.transition();
      }
    }
  }

}