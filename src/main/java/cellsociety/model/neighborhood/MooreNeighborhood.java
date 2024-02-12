package cellsociety.model.neighborhood;

import cellsociety.Point;
import cellsociety.model.core.cell.Cell;
import cellsociety.model.core.grid.Grid;
import java.util.List;

/**
 * Represents a neighborhood where neighbors are characterized as points that share a common vertex
 * on the coordinate grid
 *
 * @author Noah Loewy
 */

public class MooreNeighborhood extends Neighborhood {

  /**
   * Abstract method that determines if two cells are neighbors under a certain grid. This method
   * uses the Moore criterion for neighbors, which requires that the two cells share a vertex
   *
   * @param cell1 potential neighbor of cell 2
   * @param cell2 potential neighbor of cell 1
   * @param grid  grid object for the simulation
   * @return true if and only if cell1 and cell2 are Moore Neighbors
   */
  @Override
  public boolean isValidNeighbor(Cell cell1, Cell cell2, Grid grid) {
    List<Point[]> verticesPairs = getPairwiseVertices(cell1, cell2);
    for (Point[] verticesPair : verticesPairs) {
          if (grid.vertexEqual(verticesPair[0], verticesPair[1])) {
            return true;
          }
        }
    return false;
  }
}
