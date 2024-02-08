

package cellsociety.model.simulation;

import cellsociety.model.core.Cell;
import cellsociety.model.neighborhood.Neighborhood;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

/**
 * Represents the spreading of a wild fire in a forest
 *
 * @author Noah Loewy
 */


public class SugarSimulation extends SimpleCellSimulation {


  public static final int EMPTY = 0;

  public static final int OCCUPIED = 1;


  public SugarSimulation(int row, int col, Neighborhood hoodType, List<Integer> stateList, String gridType, String cellShape) {
    super(row, col, hoodType, stateList, gridType, cellShape);
  }


  @Override
  public void transitionFunction() {
    Iterator<Cell> gridIterator = getIterator();
    while (gridIterator.hasNext()) {
      Cell currentCell = gridIterator.next();
      List<Cell> neighbors = getNeighborhood().getNeighbors(getGrid(),currentCell);
      String stateStr = Integer.toString(currentCell.getState().getCurrentStatus());
      List<String> neighborStates = new ArrayList<>();
      for (Cell c : neighbors) {
        neighborStates.add(Integer.toString(currentCell.getState().getCurrentStatus()));
      }
      Collections.sort(neighborStates);


    }
  }
}

