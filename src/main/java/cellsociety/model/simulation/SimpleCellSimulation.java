package cellsociety.model.simulation;

import cellsociety.model.core.Cell;
import cellsociety.model.neighborhood.Neighborhood;
import java.util.List;

/**
 * Abstract Class that represents the subset of simulations that utilize normal Cells
 *
 * @author Noah Loewy
 */

public abstract class SimpleCellSimulation extends Simulation<Cell> {

  /**
   * Subclass of Simulation, and SuperClass of all simulations that use simple Cell objects in their
   * implementation. Constructor passes in a lambda function to Simulation constructor that allows
   * for the creation of new cell objects.
   *
   * @param row,       the number of rows in the 2-dimensional grid
   * @param col,       the number of columns in the 2-dimensional grid
   * @param hoodType,  the definition of neighbors
   * @param stateList, a list of the integer representation of each cells state, by rows, then cols
   */
  public SimpleCellSimulation(int row, int col, Neighborhood hoodType,
      List<Integer> stateList) {
    super(row, col, hoodType, stateList,
        (ind -> new Cell(stateList.get(ind), ind / col, ind % col)));
  }
}
