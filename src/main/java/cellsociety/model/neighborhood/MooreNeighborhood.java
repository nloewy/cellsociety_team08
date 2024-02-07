package cellsociety.model.neighborhood;

import cellsociety.Point;
import java.util.ArrayList;
import java.util.List;

/**
 * Represents a neighborhood where neighbors are characterized as points that share a common vertex
 * or side on the coordinate grid
 *
 * @author Noah Loewy
 */

public class MooreNeighborhood implements Neighborhood {

  private static final int[] DELTA_X = {-1, 0, 1};
  private static final int[] DELTA_Y = {-1, 0, 1};

  /**
   * Retrieves all points that either shares an edge or a vertex with the central point
   *
   * @param p, a central point that we are aiming to get the neighbors of
   * @return List<Point>, a list of all points that could potentially represent cells that share a
   * an edge or a vertex with central point p, should those points be valid indices in the grid.
   */
  @Override
  public List<Point> getNeighborCoordinates(Point p) {
    List<Point> neighbors = new ArrayList<>();
    for (int dx : DELTA_X) {
      for (int dy : DELTA_Y) {
        Point neighbor = p.translate(dx, dy);
        if (!neighbor.equals(p)) {
          neighbors.add(neighbor);
        }
      }
    }
    return neighbors;
  }
}
