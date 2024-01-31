package cellsociety.Model;

import java.util.*;

public class Grid<T extends Cell> {

  /**
   * The Grid represents a 2-dimensional list for our simulation, where each x,y pairing contains a
   * singular cell. We try to abstract away the size and shape of the grid. atomic unit of our
   * simulation.
   *
   * @author Noah Loewy
   */
  private int myNumRows;
  private int myNumCols;
  private List<T> myGrid;

  /**
   * Constructs a cell object
   *
   * @param rows is the number of rows in the grid
   * @param cols is the number of columns in the grid
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
  public boolean inBounds(Point p) {
    int x = p.getX();
    int y = p.getY();
    return xInBounds(x) && yInBounds(y);
  }

  /**
   * Determines if the x coordinate of a potential cell is in bounds
   *
   * @param x, the x coordinate of a potential grid cell
   * @return boolean, highlighting if 0<=x<myNumRows
   */
  public boolean xInBounds(int x) {
    return x >= 0 && x < myNumRows;
  }

  /**
   * Determines if the y coordinate of a potential cell is in bounds
   *
   * @param y, the y coordinate of a potential grid cell
   * @return boolean, highlighting if 0<=y<myNumCols
   */
  public boolean yInBounds(int y) {
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
   * @return Iterator<Cell>
   */
  public Iterator<T> iterator() {
    return myGrid.iterator();
  }
}
