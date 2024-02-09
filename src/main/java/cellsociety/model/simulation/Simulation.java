package cellsociety.model.simulation;

import cellsociety.exception.InvalidValueException;
import cellsociety.model.core.Cell;
import cellsociety.model.core.CellShape;
import cellsociety.model.core.Grid;
import cellsociety.model.core.HexagonShape;
import cellsociety.model.core.RectangleShape;
import cellsociety.model.core.WarpedGrid;
import cellsociety.model.neighborhood.Neighborhood;
import java.util.Iterator;
import java.util.List;

/**
 * Abstract Class that runs the simulation of a cellular automata. Subclasses will implement
 * transitionFunction() based on the rules of the simulation
 *
 * @author Noah Loewy
 */

public abstract class Simulation {

  protected Neighborhood myNeighborhood;
  protected Grid myGrid;
  private String myGridType;

  public Simulation() {
  }

  /**
   * Constructs a basic Simulation object
   *
   * @param row,       the number of rows in the 2-dimensional grid
   * @param col,       the number of columns in the 2-dimensional grid
   * @param hoodType,  the definition of neighbors
   * @param stateList, a list of the integer representation of each cells state, by rows, then cols
   * @param gridType   type of grid used in simulation
   */

  public Simulation(Neighborhood hoodType, String gridType) {
    myNeighborhood = hoodType;
    myGridType = gridType;
  }

  public void initializeMyGrid(int row, int col, List<Cell> cellList) {
    System.out.println(myGridType);
    myGrid = switch (myGridType) {
      case "Normal" -> new Grid(row, col, cellList);
      case "Warped" -> new WarpedGrid(row, col, cellList);
      default -> throw new InvalidValueException("Edge Type Does Not Exist");
    };
    Iterator<Cell> iterator = myGrid.iterator();
    while (iterator.hasNext()) {
      Cell cell = iterator.next();
      cell.setNeighborhood(myNeighborhood.getNeighbors(myGrid, cell));
    }
  }

  /**
   * This abstract function will assign each cell a new value for their myNextState, based on the
   * current grid configuration and the rules of the simulation
   */
  public abstract void transitionFunction();

  /**
   * Iterates through all available cells and updates the current state based on the results of the
   * transition function
   */
  public void processUpdate() {
    Iterator<Cell> iterator = myGrid.iterator();
    while (iterator.hasNext()) {
      Cell cell = iterator.next();
      cell.updateStates();
    }
  }

  public void createCellsAndGrid(int row, int col, List<Integer> stateList,
      CellShape shape, Neighborhood hoodType) {
    List<Cell> cellList = cellMaker(col, stateList, shape);
    initializeMyGrid(row, col, cellList);
    for (Cell cell : cellList) {
      cell.initializeNeighbors(hoodType, myGrid);
    }
  }

  public abstract List<Cell> cellMaker(int col, List<Integer> stateList, CellShape cellShape);


  public CellShape getCellShape(String shapeStr) {
    return switch (shapeStr) {
      case "square" -> new RectangleShape();
      case "hexagon" -> new HexagonShape();
      default -> throw new InvalidValueException("Cell Shape Does Not Exist");
    };
  }

  public Neighborhood getNeighborhood() {
    return myNeighborhood;
  }

  public Grid getGrid() {
    return myGrid;
  }

  /**
   * Retrieves the Grid's object that can access the grid of cells while hiding the Data Structure
   * used to implement it.
   *
   * @return Iterator object that can iterate through my grid
   */
  public Iterator<Cell> getIterator() {
    return myGrid.iterator();
  }

}
