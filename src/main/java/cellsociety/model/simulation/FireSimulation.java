package cellsociety.model.simulation;

import cellsociety.model.core.cell.FireCell;
import cellsociety.model.core.shape.Shape;
import cellsociety.model.neighborhood.Neighborhood;
import cellsociety.model.simulation.Records.FireRecord;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Represents the spreading of a wildfire in a forest
 *
 * @author Noah Loewy
 */

public class FireSimulation extends Simulation<FireCell> {

  public static final int EMPTY = 0;
  public static final int TREE = 1;
  public static final int BURNING = 2;


  private final double probTreeIgnites;
  private final double probTreeCreated;
  private final int neighborsToIgnite;


  /**
   * Initializes a FireSimulation object
   *
   * @param row,       the number of rows in the 2-dimensional grid
   * @param col,       the number of columns in the 2-dimensional grid
   * @param hoodType,  the definition of neighbors
   * @param stateList, a list of the integer representation of each cells state in row major order
   * @param r,         a record of all parameters needed for Fire Simulation. Description of
   *                   parameters can be found in the FireCell class
   */
  public FireSimulation(int row, int col, Neighborhood hoodType, List<Integer> stateList,
      FireRecord r) {
    super(hoodType, r.gridType());
    this.neighborsToIgnite = r.neighborsToIgnite();
    this.probTreeIgnites = r.probTreeIgnites();
    this.probTreeCreated = r.probTreeCreated();
    createCellsAndGrid(row, col, stateList, getCellShape(r.cellShape()), hoodType);
  }


  /**
   * Creates list of FireCell objects to be passed into grid
   *
   * @param col       number of columns in grid for simulation
   * @param stateList list of all cell's states in row major order
   * @param shape     Shape object representing the shape of the cell as represented on 2d plane
   * @return list of initialized FireCells
   */
  public List<FireCell> cellMaker(int col, List<Integer> stateList, Shape shape) {
    List<FireCell> cellList = new ArrayList<>();
    Map<String, Double> params = new HashMap<>();
    params.put("neighborsToIgnite", (double) neighborsToIgnite);
    params.put("probTreeIgnites", probTreeIgnites);
    params.put("probTreeCreated", probTreeCreated);
    for (int i = 0; i < stateList.size(); i++) {
      cellList.add(new FireCell(stateList.get(i), i / col, i % col, shape, params));
    }
    return cellList;
  }
}
