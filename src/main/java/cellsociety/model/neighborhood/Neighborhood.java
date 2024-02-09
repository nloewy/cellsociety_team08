package cellsociety.model.neighborhood;

import cellsociety.model.core.cell.Cell;
import cellsociety.model.core.grid.Grid;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;



public abstract class Neighborhood<T extends Cell> {
  /**
   * Class that represents a cell's neighborhood, which is a collection of points surrounding a
   * center point.
   *
   * @author Noah Loewy
   */

  /**
   * Retrieves all neighbors of a cell object using the 2D grid provided
   * @param grid  the representation of the cells on the 2D plane
   * @param cell  the central cell whose neighbors we are aiming to get
   * @return a list of cells, each cell representing a neighbor of the given cell
   */
  public List<T> getNeighbors(Grid grid, T cell) {
    List<T> neighbors = new ArrayList<>();
    Iterator<T> iter = grid.iterator();
    while (iter.hasNext()) {
      T otherCell = iter.next();
      if (cell.equals(otherCell)) {
        continue;
      }
      if (isValidNeighbor(cell, otherCell, grid)) {
        neighbors.add(otherCell);
      }
    }
    return neighbors;
  }

  /**
   * Abstract method that determines if two cells are neighbors under a certain grid. The
   * implementation of this method is dependent on the type of neighborhood being used.
   * @param cell1  potential neighbor of cell 2
   * @param cell2  potential neighbor of cell 1
   * @param grid   grid object for the simulation
   * @return true if and only if cell1 and cell2 are neighbors
   */
  public abstract boolean isValidNeighbor(T cell1, T cell2, Grid grid);
}
