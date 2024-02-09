package cellsociety.model.simulation;

import cellsociety.exception.InvalidValueException;
import cellsociety.model.core.Cell;
import cellsociety.model.core.CellShape;
import cellsociety.model.core.FireCell;
import cellsociety.model.core.HexagonShape;
import cellsociety.model.core.LifeCell;
import cellsociety.model.core.RectangleShape;
import cellsociety.model.neighborhood.Neighborhood;
import cellsociety.model.simulation.Records.GameOfLifeRecord;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * This cellular automata simulation represents Conway's Game of Life.
 * <p>
 * author @Noah Loewy
 */

public class GameOfLifeSimulation extends Simulation {

  public static final int DEAD = 0;
  public static final int ALIVE = 1;

  private final int aliveToAliveMin;
  private final int aliveToAliveMax;
  private final int deadToAliveMin;
  private final int deadToAliveMax;

  /**
   * Initializes a GameOfLifeSimulation object
   *
   * @param row,            the number of rows in the 2-dimensional grid
   * @param col,            the number of columns in the 2-dimensional grid
   * @param hoodType,       the definition of neighbors
   * @param stateList,      a list of the integer representation of each cells state, by rows, then
   *                        cols
   */
  public GameOfLifeSimulation(int row, int col, Neighborhood hoodType, List<Integer> stateList,
      GameOfLifeRecord r) {
    super(hoodType,  r.gridType());
    this.aliveToAliveMin = r.aliveToAliveMin();
    this.aliveToAliveMax = r.aliveToAliveMax();
    this.deadToAliveMin = r.deadToAliveMin();
    this.deadToAliveMax = r.deadToAliveMax();
    createCellsAndGrid(row, col, stateList, r.cellShape(), hoodType);
  }

  public void createCellsAndGrid(int row, int col, List<Integer> stateList,
      String cellShape, Neighborhood hoodType){
    List<Cell> cellList = cellMaker(row, col,stateList,hoodType, cellShape);
    initializeMyGrid(row, col, cellList);
    for(Cell cell : cellList) {
      cell.initializeNeighbors(hoodType, myGrid);
    }
  }
  public List<Cell> cellMaker(int row, int col, List<Integer> stateList, Neighborhood hoodType,
      String cellShape){
    List<Cell> cellList = new ArrayList<>();
    for (int i = 0; i < stateList.size(); i++) {
      CellShape shape = switch (cellShape) {
        case "square" -> new RectangleShape();
        case "hexagon" -> new HexagonShape();
        default -> throw new InvalidValueException("Cell Shape Does Not Exist");
      };

      Map<String, Integer> params = new HashMap<>();
      params.put("aliveToAliveMin", aliveToAliveMin);
      params.put("aliveToAliveMax", aliveToAliveMax);
      params.put("deadToAliveMin",  deadToAliveMin);
      params.put("deadToAliveMax",  deadToAliveMax);

      cellList.add(new LifeCell(stateList.get(i), i / col, i % col, shape, params));
    }
    return cellList;
  }

  /**
   * Iterates through each cell, and calls the proper helper function for transitioning based on the
   * current state of the cell in the Game of Life Simulation
   */
  @Override
  public void transitionFunction() {
    Iterator<Cell> gridIterator = getIterator();
    while (gridIterator.hasNext()) {
      Cell currentCell = gridIterator.next();
      currentCell.transition();
    }
  }
}


