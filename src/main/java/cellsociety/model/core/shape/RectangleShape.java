package cellsociety.model.core.shape;

import cellsociety.Point;
import java.util.ArrayList;
import java.util.List;

/**
 * Represents the location of a Rectangular Shaped Cell on a 2D Grid
 *
 * @author Noah Loewy
 */
public class RectangleShape implements Shape {

  /**
   * Returns a list of vertices representing the corners of a square on a grid. Each cell on the
   * grid is assumed to take up 1 square unit. The vertices are calculated based on the provided row
   * and column indices,representing the top-left corner of the square.
   *
   * @param row the row index of the top-left corner of the square
   * @param col the column index of the top-left corner of the square
   * @return a list of Point objects representing the vertices of the square
   */
  public List<Point> getVertices(int row, int col) {
    final double[][] offsets = {{0, 0}, {1, 0}, {1, 1}, {0, 1}};
    List<Point> vertices = new ArrayList<>();
    for (double[] offset : offsets) {
      vertices.add(new Point(row + offset[0], col + offset[1]));
    }
    return vertices;
  }

}
