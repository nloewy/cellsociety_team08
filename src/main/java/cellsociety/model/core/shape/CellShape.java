package cellsociety.model.core.shape;

import cellsociety.Point;
import java.util.List;


/**
 * Represents a geometric shape associated with a cell on a grid. Implementations of this interface
 * provide methods to retrieve the vertices of the shape based on the provided row and column
 * indices.
 *
 * @author Noah Loewy
 */
public interface CellShape {

  /** Returns a list of vertices representing the corners of the shape associated with a cell on a
   * grid. The vertices are calculated based on the provided row and column indices representing the
   * top-left corner of the cell.
   *
   * @param row  the row index of the top-left corner of the cell
   * @param col  the column index of the top-left corner of the cell
   * @return a list of Point objects representing the vertices of the shape
   */
  public List<Point> getVertices(int row, int col);

}
