package cellsociety.model.core.shape;

import cellsociety.Point;
import java.util.ArrayList;
import java.util.List;

/**
 * Represents the location of a Rectangular Shaped Cell on a 2D Grid
 *
 * @author Noah Loewy
 */

public class HexagonShape implements Shape {

  /**
   * Returns a list of vertices representing the corners of a hexagon shape on a grid. Hexagons are
   * latticed in a staggered pattern, where odd rows are shifted right. Each cell on the grid is
   * assumed to take up 1 square unit. The vertices are calculated based on the provided row and
   * column indices, representing the top-left corner of the hexagon.
   *
   * @param row the row index of the top-left corner of the hexagon
   * @param col the column index of the top-left corner of the hexagon
   * @return a list of Point objects representing the vertices of the hexagon
   */
  public List<Point> getVertices(int row, int col) {
    double currOffset;
    if (row % 2 == 1) {
      currOffset = .5;
    } else {
      currOffset = 0.0;
    }
    final double[][] offsets = {{.25, 0}, {0, .5}, {.25, 1}, {1, 1}, {1.25, .5}, {1, 0}};
    List<Point> vertices = new ArrayList<>();
    for (double[] offset : offsets) {
      double shapeOffset = .5; //hexagons in odd rows are shifted by .5
      vertices.add(new Point(row + offset[0], col + offset[1] + currOffset, shapeOffset));
    }
    return vertices;
  }
}
