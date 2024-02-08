package cellsociety.model.neighborhood;

import cellsociety.model.core.Cell;
import cellsociety.model.core.Grid;
import java.util.List;

/**
 * Interface that represents a cell's neighborhood, which is a collection of points surrounding a
 * center point.
 *
 * @author Noah Loewy
 */

public interface Neighborhood {


  /**
   * Different implementations will provide different ways to get the neighboring points of a given
   * point, based on differing definitions of what constitutes a "neighbor"
   *
   */
  List<Cell> getNeighbors(Grid grid, Cell cell);
}
