package cellsociety.Model;

import static java.lang.Math.random;

import java.util.*;

public class FireSimulation extends Simulation{

  /**
   * Represents the spreading of a wild fire in a forest
   *
   * @author Noah Loewy
   */
  public final int EMPTY = 0;
  public final int TREE = 1;
  public final int BURNING = 2;


  private double probTreeIgnites;
  private double probTreeCreated;
  private int neighborsToIgnite;

  public FireSimulation(int row, int col, Neighborhood neighborhoodType, List<Integer> stateList){

    super(row,col,neighborhoodType, stateList);


    //these will be parameters, as opposed to hardcoded
    neighborsToIgnite=1;
    probTreeIgnites=.1;
    probTreeCreated=.2;
  }

  /**
   * Transitions follow the following rules:
   *  1) Empty cells become trees with probability probTreeCreated
   *  2) Burning cells become empty
   *  3) Trees with at least neighborsToIgnite burning neighbors burn
   *  4) Trees with no burning neighbors burn with probability probTreeIgnites
   */
  @Override
  public void transitionFunction() {
    Iterator<Cell> gridIterator = getIterator();
    while (gridIterator.hasNext()) {
      Cell currentCell = gridIterator.next();
      List<Cell> neighbors = getNeighbors(currentCell);
      int currentState = currentCell.getCurrentState();
      double randomNumber = random();
      if(currentState == EMPTY){
        if(randomNumber <= probTreeCreated) {
          currentCell.setNextState(TREE);
        }
        else{
          currentCell.setNextState(EMPTY);
        }
      }
      else if(currentState == BURNING){
          currentCell.setNextState(EMPTY);
        }
      else if(currentState == TREE){
          int burningNeighbors = countNeighborsInState(neighbors, BURNING);
          if(burningNeighbors >= neighborsToIgnite || randomNumber <= probTreeIgnites){
            currentCell.setNextState(BURNING);
          }
          else{
            currentCell.setNextState(TREE);
          }
      }

    }
  }
}
