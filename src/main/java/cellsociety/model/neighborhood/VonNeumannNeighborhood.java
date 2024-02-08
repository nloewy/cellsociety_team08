package cellsociety.model.neighborhood;

import cellsociety.Point;
import cellsociety.model.core.Cell;
import cellsociety.model.core.Grid;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Represents a neighborhood where neighbors are characterized as points that share a common side on
 * the coordinate grid
 *
 * @author Noah Loewy
 */

public class VonNeumannNeighborhood implements Neighborhood {


  @Override
  public List<Cell> getNeighbors(Grid grid, Cell cell) {
    List<Cell> neighbors = new ArrayList<>();
    Iterator<Cell> iter = grid.iterator();
    while (iter.hasNext()) {
      Cell otherCell = iter.next();
      if (cell.equals(otherCell)) {
        continue;
      }
      for (Point vtx : cell.getVertices()) {
        if (grid.containsVertex(vtx, otherCell.getVertices()) &&
            (otherCell.getCentroid().getCol() == cell.getCentroid().getCol()
                || otherCell.getCentroid().getRow() == cell.getCentroid().getRow())) {
          neighbors.add(otherCell);
          break;
        }
      }
    }
    return neighbors;
  }
}
