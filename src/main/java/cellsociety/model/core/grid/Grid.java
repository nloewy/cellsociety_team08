package cellsociety.model.core.grid;

import cellsociety.Point;
import cellsociety.model.core.cell.Cell;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * The Grid represents a list for our simulation, where each element in the grid represents a cell
 * located at some point on the xy plane. We try to abstract away the size and shape of the grid.
 * The grid takes in a generic kind of Cell.
 *
 * @author Noah Loewy
 */

public class Grid<T extends Cell> {


  private int myNumRows;
  private int myNumCols;
  private List<T> myGrid;

  /**
   * Constructs a cell object
   *
   * @param rows      is the number of rows in the grid
   * @param cols      is the number of columns in the grid
   * @param cellList, a list of the cells, by rows, then cols
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
