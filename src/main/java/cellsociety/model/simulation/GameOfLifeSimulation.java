package cellsociety.model.simulation;

import cellsociety.model.core.cell.LifeCell;
import cellsociety.model.core.shape.Shape;
import cellsociety.model.neighborhood.Neighborhood;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * This cellular automata simulation represents Conway's Game of Life.
 * <p>
 * author @Noah Loewy
 */

public class GameOfLifeSimulation extends Simulation<LifeCell> {

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
   * @param stateList, a list of the integer representation of each cells state in row major order
   * @param r,         a record of all parameters needed for Game of Life Simulation. Description of
   *                   parameters can be found in the LifeCell class
   */
  public GameOfLifeSimulation(int row, int col, Neighborhood hoodType, List<Integer> stateList,
      SimulationRecord r) {
    super(hoodType, r.gridType());
    r.params().get("probTreeIgnites");
    this.aliveToAliveMin = (int) Math.floor(r.params().get("aliveToAliveMin"));
    this.aliveToAliveMax = (int) Math.floor(r.params().get("aliveToAliveMax"));
    this.deadToAliveMin = (int) Math.floor(r.params().get("deadToAliveMin"));
    this.deadToAliveMax = (int) Math.floor(r.params().get("deadToAliveMax"));
    createCellsAndGrid(row, col, stateList, getCellShape(r.cellShape()), hoodType);
  }

  /**
   * Creates list of LifeCell objects to be passed into grid
   *
   * @param col       number of columns in grid for simulation
   * @param stateList list of all cell's states in row major order
   * @param shape     Shape object representing the shape of the cell as represented on 2d plane
   * @return list of initialized LifeCells
   */

  @Override
  public List<LifeCell> cellMaker(int col, List<Integer> stateList, Shape shape) {
    List<LifeCell> cellList = new ArrayList<>();
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



