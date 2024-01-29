package cellsociety.Model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Runner {

  public static void main(String[] args) {
    /**  Integer[] arr = {0,0,0,0,0,0,0,0,0,0,0,0,
                     0,0,1,0,0,0,0,0,0,0,0,0,
                     1,0,1,0,0,0,0,0,0,0,0,0,
                     0,1,1,0,0,0,0,0,0,0,0,0,
                     0,0,0,0,0,0,0,0,0,0,0,0,
                     0,0,0,0,0,0,0,0,0,0,0,0,
                     0,0,0,0,0,0,0,0,0,0,0,0,
                     0,0,0,0,0,0,0,0,0,0,0,0,
                     0,0,0,0,0,0,0,0,0,0,0,0,
                     0,0,0,0,0,0,0,0,0,0,0,0,
                     0,0,0,0,0,0,0,0,0,0,0,0};

     Glider Test
    */

    /**

      Integer[] arr = {0,0,0,0,1,1,1,1,1,1,1,1,
                       0,0,0,0,1,1,1,1,1,1,1,1,
                       0,0,0,0,1,1,1,1,1,1,1,1,
                       0,0,0,0,1,1,1,1,1,1,1,1,
                       1,1,1,1,1,1,1,1,1,1,1,2,
                       1,1,1,1,1,1,1,1,1,1,1,1,
                       1,1,1,1,1,1,1,1,1,1,1,1,
                       1,1,1,1,1,1,1,1,1,1,1,1,
                       1,1,1,1,1,1,1,1,1,1,1,1,
                       1,1,0,0,1,1,1,1,1,1,0,0,
                       1,1,0,0,1,1,1,1,1,1,0,0,
                       1,1,1,1,1,1,1,1,1,1,0,0};

     fire test
    */
    List<Integer> CURRENT_CONFIG_TO_START = Arrays.asList(arr);
    List<Cell> cellList = new ArrayList<>();
    int x = 0;
    int y = 0;
    int NUMROWS = 12;
    int NUMCOLS = 12;
    for(Integer i: CURRENT_CONFIG_TO_START){
      cellList.add(new Cell(i, x/NUMROWS, y%NUMCOLS));
      if(i==1){
        System.out.println("" + x/NUMROWS + " " + y%NUMCOLS);
      }
      x++;
      y++;
    }
    Simulation s = new FireSimulation(NUMROWS, NUMCOLS, new AdjacentNeighborhood(), cellList);
    for(int i = 0; i< 50; i++){
      s.transitionFunction();
      s.processUpdate();
      System.out.print("\n\n\n\n");
    }
  }

}
