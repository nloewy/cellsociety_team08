package cellsociety.model.simulation;

import cellsociety.model.core.cell.Cell;
import cellsociety.model.core.cell.LifeCell;
import cellsociety.model.core.shape.CellShape;
import cellsociety.model.neighborhood.Neighborhood;
import cellsociety.model.simulation.Records.GameOfLifeRecord;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * This cellular automata simulation represents Conway's Game of Life.
 * <p>
 * author @Noah Loewy
 */

public class GameOfLifeSimulation extends Simulation {

  public static final int DEAD = 0;
  public static final int ALIVE = 1;

  private final int aliveToAliveMin;
  private final int aliveToAliveMax;
  private final int deadToAliveMin;
  private final int deadToAliveMax;

  /**
   * Initializes a GameOfLifeSimulation object
   *
   * @param row,       the number of rows in the 2-dimensional grid
   * @param col,       the number of columns in the 2-dimensional grid
   * @param hoodType,  the definition of neighbors
   * @param stateList, a list of the integer representation of each cells state, by rows, then cols
   */
  public GameOfLifeSimulation(int row, int col, Neighborhood hoodType, List<Integer> stateList,
      GameOfLifeRecord r) {
    super(hoodType, r.gridType());
    this.aliveToAliveMin = r.aliveToAliveMin();
    this.aliveToAliveMax = r.aliveToAliveMax();
    this.deadToAliveMin = r.deadToAliveMin();
    this.deadToAliveMax = r.deadToAliveMax();
    createCellsAndGrid(row, col, stateList, getCellShape(r.cellShape()), hoodType);
  }

  public List<Cell> cellMaker(int col, List<Integer> stateList,
      CellShape shape) {
    List<Cell> cellList = new ArrayList<>();
    Map<String, Integer> params = new HashMap<>();
    params.put("aliveToAliveMin", aliveToAliveMin);
    params.put("aliveToAliveMax", aliveToAliveMax);
    params.put("deadToAliveMin", deadToAliveMin);
    params.put("deadToAliveMax", deadToAliveMax);
    for (int i = 0; i < stateList.size(); i++) {
      cellList.add(new LifeCell(stateList.get(i), i / col, i % col, shape, params));
    }
    return cellList;
  }
}


