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

  public abstract boolean isValidNeighbor(T cell1, T cell2, Grid grid);
}
