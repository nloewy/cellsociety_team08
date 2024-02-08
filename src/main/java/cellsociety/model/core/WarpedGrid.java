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
  public boolean containsVertex(Point vtx, List<Point> vertices) {
    if (super.containsVertex(vtx, vertices)){
      return true;
    }

    //if(vtx.getX()==3 && vtx.getY()==3){
     // for(Point vtx2 : vertices){
      //  System.out.print(vtx2.getX()+","+vtx2.getY() +  "   : ");
      ///}
      //System.out.println();
     // System.out.println(super.containsVertex(new Point(vtx.getX()+getNumCols(), vtx.getY()),vertices));
    //  System.out.println(super.containsVertex(new Point(vtx.getX()-getNumCols(), vtx.getY()),vertices));
    //  System.out.println(super.containsVertex(new Point(vtx.getX(), vtx.getY()+getNumRows()),vertices));
    //  System.out.println(super.containsVertex(new Point(vtx.getX(), vtx.getY()-getNumRows()),vertices));
   // }
    return super.containsVertex(new Point(vtx.getX()+getNumCols(), vtx.getY()),vertices) ||
        super.containsVertex(new Point(vtx.getX()-getNumCols(), vtx.getY()),vertices) ||
        super.containsVertex(new Point(vtx.getX(), vtx.getY()+getNumRows()),vertices) ||
        super.containsVertex(new Point(vtx.getX(), vtx.getY()-getNumRows()),vertices) ;
  }
}
