package cellsociety.model.core;

import cellsociety.Point;
import cellsociety.model.neighborhood.Neighborhood;
import java.util.ArrayList;
import java.util.List;

public class WarpedGrid<T extends Cell> extends Grid<T> {

  public WarpedGrid(int rows, int cols, List<T> cellList) {
    super(rows,cols,cellList);
  }

  @Override
  public List<T> getNeighbors(Point p, Neighborhood neighborhood) {
    List<T> neighboringCells = new ArrayList<>();
    List<Point> neighboringCoordinates = neighborhood.getNeighborCoordinates(p);
    for (Point pNew : neighboringCoordinates) {
      try {
        T neighbor = getCellAtLocation(pNew);
        neighboringCells.add(neighbor);
      } catch (IndexOutOfBoundsException e) {
        Point pNew2 = new Point((pNew.getX()+getNumCols())% getNumCols(),
            (pNew.getY()+getNumRows())%getNumRows());
        T neighbor = getCellAtLocation(pNew2);
        neighboringCells.add(neighbor);
      }
    }
    return neighboringCells;
  }
}
