package cellsociety.model.core;

import cellsociety.Point;
import cellsociety.model.neighborhood.Neighborhood;
import java.util.ArrayList;
import java.util.List;

public class WarpedGrid extends Grid {

  public WarpedGrid(int rows, int cols, List<Cell> cellList) {
    super(rows,cols,cellList);
  }

  @Override
  public List<Cell> getNeighbors(Point p, Neighborhood neighborhood) {
    List<Cell> neighboringCells = new ArrayList<>();
    List<Point> neighboringCoordinates = neighborhood.getNeighborCoordinates(p);
    for (Point pNew : neighboringCoordinates) {
      try {
        Cell neighbor = getCellAtLocation(pNew);
        neighboringCells.add(neighbor);
      } catch (IndexOutOfBoundsException e) {
        Point pNew2 = new Point((pNew.getX()+getNumCols())% getNumCols(),
            (pNew.getY()+getNumRows())%getNumRows());
        Cell neighbor = getCellAtLocation(pNew2);
        neighboringCells.add(neighbor);
      }
    }
    return neighboringCells;
  }
}
