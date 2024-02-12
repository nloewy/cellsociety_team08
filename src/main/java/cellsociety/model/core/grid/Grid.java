package cellsociety.model.core.grid;

import cellsociety.Point;
import cellsociety.model.core.cell.Cell;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * The Grid represents the Collection of Cells in our simulation, where each element in the grid
 * represents some point on the xy plane. We try to abstract away the size and shape of the grid.
 * The grid takes in a generic kind of Cell. This general Grid is finite, so cells on the edge will
 * have fewer neighbors than cells in the center of the grid.
 *
 * @author Noah Loewy
 */

public class Grid<T extends Cell> {

  private final int myNumRows;
  private final int myNumCols;
  private final List<T> myGrid;

  /**
   * Constructs a cell object
   *
   * @param rows     is the number of rows in the grid
   * @param cols     is the number of columns in the grid
   * @param cellList a list of the cells in row-major order
   */
  public Grid(int rows, int cols, List<T> cellList) {
    myNumRows = rows;
    myNumCols = cols;
    myGrid = new ArrayList<>(cellList);

  }

  public int getNumRows() {
    return myNumRows;
  }

  public int getNumCols() {
    return myNumCols;
  }

  /**
   * Retrieves Iterator object that can access the grid of cells while hiding the Data Structure
   * used to implement it.
   *
   * @return Iterator object that can iterate through my grid
   */
  public Iterator<T> iterator() {
    return myGrid.iterator();
  }

  public boolean containsVertex(Point p, List<Point> vertices) {
    return vertices.contains(p);
  }
}
