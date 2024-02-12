package cellsociety.model.simulation;

import cellsociety.model.core.cell.PercolationCell;
import cellsociety.model.core.shape.Shape;
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
  private final int percolatedNeighbors;


  /**
   * Initializes a PercolationSimulation object
   *
   * @param row,       the number of rows in the 2-dimensional grid
   * @param col,       the number of columns in the 2-dimensional grid
   * @param hoodType,  the definition of neighbors
   * @param stateList, a list of the integer representation of each cells state in row major order
   * @param r,         a record of all parameters needed for Percolation Simulation. Description of
   *                   parameters can be found in the PercolationCell class
   */
  public PercolationSimulation(int row, int col, Neighborhood hoodType, List<Integer> stateList,
      PercolationRecord r) {
    super(hoodType, r.gridType());
    this.percolatedNeighbors = r.percolatedNeighbors();
    createCellsAndGrid(row, col, stateList, getCellShape(r.cellShape()), hoodType);
  }

  /**
   * Creates list of PercolationCell objects to be passed into grid
   *
   * @param col       number of columns in grid for simulation
   * @param stateList list of all cell's states in row major order
   * @param shape     Shape object representing the shape of the cell as represented on 2d plane
   * @return list of initialized PercolationCells
   */

  @Override
  public List<PercolationCell> cellMaker(int col, List<Integer> stateList, Shape shape) {
    List<PercolationCell> cellList = new ArrayList<>();
    Map<String, Integer> params = new HashMap<>();
    params.put("percolatedNeighbors", percolatedNeighbors);
    for (int i = 0; i < stateList.size(); i++) {
      cellList.add(new PercolationCell(stateList.get(i), i / col, i % col, shape, params));
    }
    return cellList;
  }
}
