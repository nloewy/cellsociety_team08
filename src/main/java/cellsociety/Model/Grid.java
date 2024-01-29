package cellsociety.Model;

import java.util.*;

public class Grid {

  /**
   * The Grid represents a 2-dimensional list for our simulation, where each x,y pairing contains a
   * singular cell. We try to abstract away the size and shape of the grid. atomic unit of our
   * simulation.
   *
   * @author Noah Loewy
   */
  private int myNumRows;
  private int myNumCols;
  private List<Cell> myGrid;

  /**
   * Constructs a cell object
   *
   * @param rows is the number of rows in the grid
   * @param cols is the number of columns in the grid
   */
  public Grid(int rows, int cols) {
    myNumRows = rows;
    myNumCols = cols;
    myGrid = new ArrayList<>();
  }

  /**
   * Determines if (x,y) is a legal cell in the grid.
   *
   * @param x, the x coordinate of a potential grid cell
   * @param y, the y coordinate of a potential grid cell
   * @return boolean, highlighting if x,y is a legal cell in the grid
   */
  public boolean inBounds(int x, int y) {
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
   * Retrieves Iterator object that can access the grid of cells while hiding the Data Structure
   * used to implement it.
   *
   * @return Iterator<Cell>
   */
  public Iterator<Cell> iterator() {
    return myGrid.iterator();
  }
}
