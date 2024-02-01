package cellsociety.model.neighborhood;

import cellsociety.model.core.Point;
import java.util.ArrayList;
import java.util.List;

public class CardinalNeighborhood implements Neighborhood {

  /**
   * Represents a neighborhood where neighbors are characterized as points that share a common side
   * on the coordinate grid
   *
   * @author Noah Loewy
   */

  public static final int[] ROW_DELTAS = {1, -1, 0, 0};
  public static final int[] COL_DELTAS = {0, 0, 1, -1};

  /**
   * Retrieves all points that either shares an edge with the central point
   *
   * @param p, a central point that we are aiming to get the neighbors of
   * @return List<Point>, a list of all points that could potentially represent cells that share a
   * an edge with central point p, should those points be valid indices in the grid.
   */
  @Override
  public List<Point> getNeighborCoordinates(Point p) {
    List<Point> neighbors = new ArrayList<>();
    for (int i = 0; i < ROW_DELTAS.length; i++) {
      Point neighbor = p.translate(ROW_DELTAS[i], COL_DELTAS[i]);
      if (!neighbor.equals(p)) {
        neighbors.add(neighbor);
      }
    }
    return neighbors;
  }
}
