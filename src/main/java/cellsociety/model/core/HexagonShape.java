package cellsociety.model.core;

import cellsociety.Point;
import java.util.ArrayList;
import java.util.List;

public class HexagonShape implements CellShape {

  /**
   * Constructs a cell object
   *
   * @param row is the x-coordinate of the cell on the 2-dimensional grid
   * @param col is the y-coordinate of the cell on the 2-dimensional grid
   */
  public List<Point> getVertices(int row, int col) {
    double shapeOffset = .5;
    double currOffset;
    if (row % 2 == 1) {
      currOffset = .5;
    } else {
      currOffset = 0.0;
    }
    final double[][] offsets = {{.25, 0}, {1, 0}, {1.25, .5}, {1, 1}, {.25, 1}, {1, .5}};
    List<Point> vertices = new ArrayList<>();
    for (double[] offset : offsets) {
      vertices.add(new Point(row + offset[0], col + offset[1] + currOffset, shapeOffset));
    }
    return vertices;
  }
}
