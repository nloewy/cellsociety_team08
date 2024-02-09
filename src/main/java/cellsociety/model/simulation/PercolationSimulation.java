package cellsociety.model.simulation;

import cellsociety.model.core.cell.PercolationCell;
import cellsociety.model.core.shape.CellShape;
import cellsociety.model.neighborhood.Neighborhood;
import cellsociety.model.simulation.Records.PercolationRecord;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * This cellular automata simulation represents the CS201 Percolation Assignment
 * <p>
 * author @noah loewy
 */

public class PercolationSimulation extends Simulation<PercolationCell> {

  public static final int OPEN = 0;
  public static final int PERCOLATED = 1;
  //public static final int BLOCKED = 2;

  private final int percolatedNeighbors;


  /**
   * Initializes a PercolationSimulation object
   *
   * @param row,       the number of rows in the 2-dimensional grid
   * @param col,       the number of columns in the 2-dimensional grid
   * @param hoodType,  the definition of neighbors
   * @param stateList, a list of the integer representation of each cells state, by rows, then cols
   */
  public PercolationSimulation(int row, int col, Neighborhood hoodType, List<Integer> stateList,
      PercolationRecord r) {
    super(hoodType, r.gridType());
    this.percolatedNeighbors = r.percolatedNeighbors();
    createCellsAndGrid(row, col, stateList, getCellShape(r.cellShape()), hoodType);
  }

  public List<PercolationCell> cellMaker(int col, List<Integer> stateList, CellShape shape) {
    List<PercolationCell> cellList = new ArrayList<>();
    Map<String, Integer> params = new HashMap<>();
    params.put("percolatedNeighbors", percolatedNeighbors);
    for (int i = 0; i < stateList.size(); i++) {
      cellList.add(new PercolationCell(stateList.get(i), i / col, i % col, shape, params));
    }
    return cellList;
  }
}
