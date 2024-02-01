package cellsociety.model.core;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class Grid<T extends Cell> {

  /**
   * The Grid represents a list for our simulation, where each element in the grid represents a cell
   * located at some point on the xy plane. We try to abstract away the size and shape of the grid.
   * The grid takes in a generic kind of Cell.
   *
   * @author Noah Loewy
   */
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

  /**
   * Determines if (x,y) is a legal cell in the grid.
   *
   * @param p, the coordinates of a potential grid cell on a 2d-plane
   * @return boolean, highlighting if p is a legal cell in the grid
   */
  private boolean inBounds(Point p) {
    int x = p.getX();
    int y = p.getY();
    return inBoundsX(x) && inBoundsY(y);
  }

  /**
   * Determines if the x coordinate of a potential cell is in bounds
   *
   * @param x, the x coordinate of a potential grid cell
   * @return boolean, highlighting if 0<=x<myNumRows
   */
  private boolean inBoundsX(int x) {
    return x >= 0 && x < myNumRows;
  }

  /**
   * Determines if the y coordinate of a potential cell is in bounds
   *
   * @param y, the y coordinate of a potential grid cell
   * @return boolean, highlighting if 0<=y<myNumCols
   */
  private boolean inBoundsY(int y) {
    return y >= 0 && y < myNumCols;
  }


  /**
   * Returns the cell located at a given Point on the 2 dimensional grid
   *
   * @param p, an (x,y) pairing of a potential cell location on the grid
   * @return T, the cell located at Point p, or null, if p is not a valid location
   */

  public T getCellAtLocation(Point p) {
    if (!inBounds(p)) {
      throw new IndexOutOfBoundsException();
    }
    return myGrid.get(myNumRows * p.getX() + p.getY());
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
}
