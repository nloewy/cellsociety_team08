package cellsociety.model.simulation;

import cellsociety.model.core.cell.Cell;
import cellsociety.model.core.cell.FireCell;
import cellsociety.model.core.shape.CellShape;
import cellsociety.model.neighborhood.Neighborhood;
import cellsociety.model.simulation.Records.FireRecord;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Represents the spreading of a wild fire in a forest
 *
 * @author Noah Loewy
 */

public class FireSimulation extends Simulation {

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
   * @param stateList, a list of the integer representation of each cells state, by rows, then cols
   */
  public FireSimulation(int row, int col, Neighborhood hoodType, List<Integer> stateList,
      FireRecord r) {
    super(hoodType, r.gridType());
    this.neighborsToIgnite = r.neighborsToIgnite();
    this.probTreeIgnites = r.probTreeIgnites();
    this.probTreeCreated = r.probTreeCreated();
    createCellsAndGrid(row, col, stateList, getCellShape(r.cellShape()), hoodType);
  }


  public List<Cell> cellMaker(int col, List<Integer> stateList, CellShape shape) {
    List<Cell> cellList = new ArrayList<>();
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
