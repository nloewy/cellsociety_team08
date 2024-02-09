package cellsociety.model.core.shape;

import cellsociety.Point;
import java.util.List;

public interface CellShape {

  public List<Point> getVertices(int row, int col);

}
