package cellsociety.model.neighborhood;

import cellsociety.Point;
import cellsociety.model.core.Cell;
import cellsociety.model.core.Grid;
/**
 * Represents a neighborhood where neighbors are characterized as points that share a common vertex
 * or side on the coordinate grid
 *
 * @author Noah Loewy
 */

public class MooreNeighborhood extends Neighborhood {
  @Override
  public boolean isValidNeighbor(Cell cell1, Cell cell2, Grid grid) {
    for (Point vtx : cell1.getVertices()) {
      if (grid.containsVertex(vtx, cell2.getVertices())) {
        return true;
      }
    }
    return false;
  }
}
