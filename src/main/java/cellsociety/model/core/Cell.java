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

public class Cell {

  public static final int PLACEHOLDER = -1;
  private int myCurrentState;
  private int myNextState;
  private Point myLocation;
  private List<Point> myVertices;

  private Point myCentroid;

  /**
   * Constructs a cell object
   *
   * @param initialState is the original state of the cell, either randomly set or determined from a
   *                     configuration file
   * @param x            is the x-coordinate of the cell on the 2-dimensional grid
   * @param y            is the y-coordinate of the cell on the 2-dimensional grid
   */
  public Cell(int initialState, int x, int y) {
    myCurrentState = initialState;
    myNextState = PLACEHOLDER;
    myLocation = new Point(x, y);
    myVertices = new ArrayList<>();
  }

  /**
   * This function updates the state of the cell after calling the transition function. The new
   * currentState takes the value of the nextState placeholder, and nextState is set to placeholder
   * value.
   */

  public void updateStates() {
    myCurrentState = myNextState;
    myNextState = PLACEHOLDER;
  }

  /**
   * Retrieves myCurrentState instance variable
   *
   * @return myCurrentState, the current state of the cell object.
   */
  public int getCurrentState() {
    return myCurrentState;
  }

  /**
   * Retrieves myNextState instance variable
   *
   * @return myNextState, the future state of the cell object.
   */
  public int getNextState() {
    return myNextState;
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
    myNextState = nextState;
  }

  public List<Point> getVertices(){
    return myVertices;
  }

  public Point getCentroid(){
    int xSum = 0;
    int ySum = 0;
    for(Point p : myVertices){
      xSum+=p.getX();
      ySum+=p.getY();
    }
    myCentroid = new Point(xSum, ySum);
    return myCentroid;
  }
}
