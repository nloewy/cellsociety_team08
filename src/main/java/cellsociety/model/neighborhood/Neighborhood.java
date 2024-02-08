package cellsociety.model.neighborhood;

import cellsociety.model.core.Cell;
import cellsociety.model.core.Grid;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;



public abstract class Neighborhood {
  /**
   * Class that represents a cell's neighborhood, which is a collection of points surrounding a
   * center point.
   *
   * @author Noah Loewy
   */

  public List<Cell> getNeighbors(Grid grid, Cell cell) {
    List<Cell> neighbors = new ArrayList<>();
    Iterator<Cell> iter = grid.iterator();
    while (iter.hasNext()) {
      Cell otherCell = iter.next();
      if (cell.equals(otherCell)) {
        continue;
      }
      if (isValidNeighbor(cell, otherCell, grid)) {
        neighbors.add(otherCell);
      }
    }
    return neighbors;
  }

  public abstract boolean isValidNeighbor(Cell cell1, Cell cell2, Grid grid);
}
