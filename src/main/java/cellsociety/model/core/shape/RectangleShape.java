package cellsociety.model.core.shape;

import cellsociety.Point;
import cellsociety.model.core.shape.CellShape;
import java.util.ArrayList;
import java.util.List;

public class RectangleShape implements CellShape {

  /**
   * Constructs a cell object
   *
   * @param row is the x-coordinate of the cell on the 2-dimensional grid
   * @param col is the y-coordinate of the cell on the 2-dimensional grid
   */
  public List<Point> getVertices(int row, int col) {
    final double[][] offsets = {{0, 0}, {1, 0}, {0, 1}, {1, 1}};
    List<Point> vertices = new ArrayList<>();
    for (double[] offset : offsets) {
      vertices.add(new Point(row + offset[0], col + offset[1]));
    }
    return vertices;
  }
}
