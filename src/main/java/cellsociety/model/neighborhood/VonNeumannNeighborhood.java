package cellsociety.model.neighborhood;

import cellsociety.Point;
import cellsociety.model.core.cell.Cell;
import cellsociety.model.core.grid.Grid;


public class VonNeumannNeighborhood extends Neighborhood {


  @Override
  public boolean isValidNeighbor(Cell cell1, Cell cell2, Grid grid) {
    for (Point vtx : cell1.getVertices()) {
      if (grid.containsVertex(vtx, cell2.getVertices()) &&
          (cell2.getCentroid().getCol() == cell1.getCentroid().getCol()
              || cell2.getCentroid().getRow() == cell1.getCentroid().getRow())) {
        return true;
      }
    }
    return false;
  }
}
