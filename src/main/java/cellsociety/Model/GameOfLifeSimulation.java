package cellsociety.Model;

import java.util.*;

public class GameOfLifeSimulation extends Simulation{

  public final int ALIVE = 1;
  public final int DEAD = 0;

  private int alive_to_alive_min;
  private int alive_to_alive_max;
  private int dead_to_alive_min;
  private int dead_to_alive_max;

  public GameOfLifeSimulation(int row, int col, Neighborhood neighborhoodType, List<Cell> gridList){

    super(row,col,neighborhoodType, gridList);

    //the below values will become parameterized later on
    alive_to_alive_min = 2;
    alive_to_alive_max = 3;
    dead_to_alive_min = 3;
    dead_to_alive_max = 3;
  }
  public int countAliveNeighbors(List<Cell> neighbors){
    int count = 0;
    for(Cell c : neighbors){
      if (c.getCurrentState()==ALIVE){
        count++;
      }
    }
    return count;
  }
  @Override
  public void transitionFunction() {
    Iterator gridIterator = myGrid.iterator();
    while (gridIterator.hasNext()) {
      Cell currentCell = (Cell) gridIterator.next();
      List<Cell> neighbors = getNeighbors(currentCell);
      int aliveNeighbors = countAliveNeighbors(neighbors);

      //can definitely use some refactoring here
      if (currentCell.getCurrentState() == ALIVE) {
        if (aliveNeighbors >= alive_to_alive_min && aliveNeighbors <= alive_to_alive_max) {
          currentCell.setNextState(ALIVE);
        } else {
          currentCell.setNextState(DEAD);
        }
      }
      if (currentCell.getCurrentState() == DEAD) {
        if (aliveNeighbors >= dead_to_alive_min && aliveNeighbors <= dead_to_alive_max) {
          currentCell.setNextState(ALIVE);
        } else {
          currentCell.setNextState(DEAD);
        }
      }
    }
  }
}
