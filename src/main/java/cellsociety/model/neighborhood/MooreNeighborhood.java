package cellsociety.model.neighborhood;

import cellsociety.Point;
import cellsociety.model.core.Cell;
import cellsociety.model.core.Grid;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Represents a neighborhood where neighbors are characterized as points that share a common vertex
 * or side on the coordinate grid
 *
 * @author Noah Loewy
 */

public class MooreNeighborhood implements Neighborhood {

  /**
   * Retrieves all points that either shares an edge or a vertex with the central point
   *
   * @param p, a central point that we are aiming to get the neighbors of
   * @return List<Point>, a list of all points that could potentially represent cells that share a
   * an edge or a vertex with central point p, should those points be valid indices in the grid.
   */
  public List<Cell> getNeighbors(Grid grid, Cell cell) {
    List<Cell> neighbors = new ArrayList<>();
    Iterator<Cell> iter = grid.iterator();
    while(iter.hasNext()){
      Cell otherCell = iter.next();
      if(cell.equals(otherCell)){
        continue;
      }
      for(Point vtx : cell.getVertices()) {
        if (grid.containsVertex(vtx, otherCell.getVertices())){
          neighbors.add(otherCell);
          break;
        }
      }
    }
    return neighbors;
  }
}
