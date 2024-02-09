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

public class Grid {


  private int myNumRows;
  private int myNumCols;
  private List<Cell> myGrid;

  /**
   * Constructs a cell object
   *
   * @param rows      is the number of rows in the grid
   * @param cols      is the number of columns in the grid
   * @param cellList, a list of the cells, by rows, then cols
   */
  public Grid(int rows, int cols, List<Cell> cellList) {
    myNumRows = rows;
    myNumCols = cols;
    myGrid = new ArrayList<>(cellList);

  }

  /**
   * Determines if (x,y) is a legal cell in the grid.
   *
   * @param p, the coordinates of a potential grid cell on a 2d-plane
   * @return boolean, highlighting if p is a legal cell in the grid
   */
  private boolean inBounds(Point p) {
    double x = p.getRow();
    double y = p.getCol();
    return inBoundsX(x) && inBoundsY(y);
  }

  /**
   * Determines if the x coordinate of a potential cell is in bounds
   *
   * @param x, the x coordinate of a potential grid cell
   * @return boolean, highlighting if 0<=x<myNumRows
   */
  private boolean inBoundsX(double x) {
    return x >= 0 && x < myNumRows;
  }

  /**
   * Determines if the y coordinate of a potential cell is in bounds
   *
   * @param y, the y coordinate of a potential grid cell
   * @return boolean, highlighting if 0<=y<myNumCols
   */
  private boolean inBoundsY(double y) {
    return y >= 0 && y < myNumCols;
  }


  public int getNumRows() {
    return myNumRows;
  }

  public int getNumCols() {
    return myNumCols;
  }

  public Cell getCellAtLocation(Point p) {
    if (!inBounds(p)) {
      throw new IndexOutOfBoundsException();
    }
    return myGrid.get((myNumCols * (int) p.getRow() + (int) p.getCol()));
  }

  /**
   * Retrieves Iterator object that can access the grid of cells while hiding the Data Structure
   * used to implement it.
   *
   * @return Iterator object that can iterate through my grid
   */
  public Iterator iterator() {
    return myGrid.iterator();
  }

  public boolean containsVertex(Point p, List<Point> vertices) {
    return vertices.contains(p);
  }
}
