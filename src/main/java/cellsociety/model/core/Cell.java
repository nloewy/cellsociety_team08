package cellsociety.model.core;

import cellsociety.Point;
import java.util.ArrayList;
import java.util.List;

/**
 * The Cell is the atomic unit of our simulation. Each individual cell represents a location on a
 * grid, and has some sort of state.
 *
 * @author Noah Loewy
 */

public class Cell { //<T extends State> {

  public static final int PLACEHOLDER = -1;
  private State myState; //T myState;
  private Point myLocation;
  private List<Point> myVertices;
  private Point myCentroid;

  /**
   * Constructs a cell object
   *
   * @param initialState is the original state of the cell, either randomly set or determined from a
   *                     configuration file
   * @param row          is the row-coordinate of the cell on the 2-dimensional grid
   * @param col          is the col-coordinate of the cell on the 2-dimensional grid
   */
  public Cell(int initialState, int row, int col) {
    myState = new State(initialState);
    myLocation = new Point(row, col);
    myVertices = new ArrayList<>();
  }

  /**
   * This function updates the state of the cell after calling the transition function. The new
   * currentState takes the value of the nextState placeholder, and nextState is set to placeholder
   * value.
   */

  public void updateStates() {
    myState.updateStatus();
  }

  public State getState() {
    return myState;
  }

  /**
   * Retrieves myLocation instance variable
   *
   * @return myLocation, the current x,y position of the cell object on the 2-dimensional grid
   */
  public Point getLocation() {
    return myLocation;
  }

  /**
   * Updates myNextState instance variable
   *
   * @param nextState, new value of myNextState, calculated by transition function
   */
  public void setNextState(int nextState) {
    myState.setNextStatus(nextState);
  }

  public List<Point> getVertices() {
    return myVertices;
  }

  public void addVertex(Point p) {
    myVertices.add(p);
  }

  public Point getCentroid() {
    double rowSum = 0;
    double colSum = 0;
    for (Point p : myVertices) {
      rowSum += p.getRow();
      colSum += p.getCol();
    }
    myCentroid = new Point(rowSum, colSum);
    return myCentroid;
  }
}
