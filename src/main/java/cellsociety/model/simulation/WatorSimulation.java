package cellsociety.model.simulation;

import cellsociety.exception.InvalidValueException;
import cellsociety.model.core.Cell;
import cellsociety.model.core.CellShape;
import cellsociety.model.core.FireCell;
import cellsociety.model.core.HexagonShape;
import cellsociety.model.core.RectangleShape;
import cellsociety.model.core.WatorCell;
import cellsociety.model.neighborhood.Neighborhood;
import cellsociety.model.simulation.Records.FireRecord;
import cellsociety.model.simulation.Records.WatorRecord;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * This cellular automata simulation represents the Predator-Prey simulaton developed by Alexander K
 * Dewdney
 * <p>
 * author @noah loewy
 */

public class WatorSimulation extends Simulation {

  public static final int EMPTY = 0;
  public static final int FISH = 1;
  public static final int SHARK = 2;
  public static final int[] UPDATE_ORDER = {SHARK, FISH, EMPTY};
  private final int fishAgeOfReproduction;
  private final int sharkAgeOfReproduction;
  private final int energyBoost;
  private final int initialEnergy;

  public WatorSimulation(int row, int col, Neighborhood hoodType, List<Integer> stateList,
      WatorRecord r) {
    super(hoodType, r.gridType());
    this.fishAgeOfReproduction = r.fishAgeOfReproduction();
    this.sharkAgeOfReproduction = r.sharkAgeOfReproduction();
    this.initialEnergy = r.initialEnergy();
    this.energyBoost = r.energyBoost();
    createCellsAndGrid(row, col, stateList, r.cellShape(), hoodType);
  }

  public void createCellsAndGrid(int row, int col, List<Integer> stateList,
      String cellShape, Neighborhood hoodType) {
    List<Cell> cellList = cellMaker(row, col, stateList, hoodType, cellShape);
    initializeMyGrid(row, col, cellList);
    for (Cell cell : cellList) {
      cell.initializeNeighbors(hoodType, myGrid);
    }
  }

  public List<Cell> cellMaker(int row, int col, List<Integer> stateList, Neighborhood hoodType,
      String cellShape) {
    List<Cell> cellList = new ArrayList<>();
    for (int i = 0; i < stateList.size(); i++) {
      CellShape shape = switch (cellShape) {
        case "square" -> new RectangleShape();
        case "hexagon" -> new HexagonShape();
        default -> throw new InvalidValueException("Cell Shape Does Not Exist");
      };
      Map<String, Integer> params = new HashMap<>();
      params.put("fishAgeOfReproduction", fishAgeOfReproduction);
      params.put("sharkAgeOfReproduction", sharkAgeOfReproduction);
      params.put("initialEnergy", initialEnergy);
      params.put("energyBoost", energyBoost);

      cellList.add(new WatorCell(stateList.get(i), i / col, i % col, shape, params));
    }
    return cellList;
  }


  /**
   * Transition function for Wator World. Iterates through each cell and calls proper cell-specific
   * transition function based on the state of the particular cell
   */
  @Override
  public void transitionFunction() {
    for (int cellToUpdate : UPDATE_ORDER) {
      Iterator<Cell> gridIterator = getIterator();
      while (gridIterator.hasNext()) {
        Cell currentCell = gridIterator.next();
        if (currentCell.getNextState() == Cell.PLACEHOLDER &&
            currentCell.getCurrentState() == cellToUpdate) {
          currentCell.transition();
        }
      }
    }
  }
}







