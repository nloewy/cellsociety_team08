package cellsociety.model.neighborhood;

import cellsociety.Point;
import cellsociety.model.core.cell.Cell;
import cellsociety.model.core.grid.Grid;
import java.util.List;


/**
 * Represents a neighborhood where neighbors are characterized as points that share a common vertex
 * and are horizontally or vertically adjacent on the coordinate grid
 *
 * @author Noah Loewy
 */

public class VonNeumannNeighborhood extends Neighborhood {


  /**
   * Abstract method that determines if two cells are neighbors under a certain grid. This method
   * uses the Von Neumann criterion for neighbors, which requires that the two cells share a vertex
   * and be directly vertically or horizontally adjacent to each other
   *
   * @param cell1 potential neighbor of cell 2
   * @param cell2 potential neighbor of cell 1
   * @param grid  grid object for the simulation
   * @return true if and only if cell1 and cell2 are Von Neumann Neighbors
   */
  @Override
  public boolean isValidNeighbor(Cell cell1, Cell cell2, Grid grid) {
    List<Point> vertices = cell1.getVertices();
    for (Point vtx : vertices) {
      if (grid.containsVertex(vtx, cell2.getVertices()) &&
          (cell2.getCentroid().getCol() == cell1.getCentroid().getCol()
              || cell2.getCentroid().getRow() == cell1.getCentroid().getRow())) {
        return true;
      }
    }
    return false;
  }
}
