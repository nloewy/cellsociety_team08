package cellsociety.model.simulation;

import cellsociety.model.core.cell.Cell;
import cellsociety.model.core.cell.WatorCell;
import cellsociety.model.core.shape.Shape;
import cellsociety.model.neighborhood.Neighborhood;
import cellsociety.model.simulation.SimulationRecord;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * This cellular automata simulation represents the Predator-Prey simulation developed by Alexander
 * K Dewdney
 * <p>
 * author @noah loewy
 */

public class WatorSimulation extends Simulation<WatorCell> {

  public static final int EMPTY = 0;
  public static final int FISH = 1;
  public static final int SHARK = 2;
  public static final int[] UPDATE_ORDER = {SHARK, FISH, EMPTY};
  private final int fishAgeOfReproduction;
  private final int sharkAgeOfReproduction;
  private final int energyBoost;
  private final int initialEnergy;

  /**
   * Initializes a WatorSimulation object
   *
   * @param row,       the number of rows in the 2-dimensional grid
   * @param col,       the number of columns in the 2-dimensional grid
   * @param hoodType,  the definition of neighbors
   * @param stateList, a list of the integer representation of each cells state in row major order
   * @param r,         a record of all parameters needed for Wator Simulation. Description of
   *                   parameters can be found in the WatorCell class
   */

  public WatorSimulation(int row, int col, Neighborhood hoodType, List<Integer> stateList,
      SimulationRecord r) {
    super(hoodType, r.gridType());
    this.fishAgeOfReproduction = (int) Math.floor(r.params().get("fishAgeOfReproduction"));
    this.sharkAgeOfReproduction = (int) Math.floor(r.params().get("sharkAgeOfReproduction"));
    this.initialEnergy = (int) Math.floor(r.params().get("initialEnergy"));
    this.energyBoost = (int) Math.floor(r.params().get("energyBoost"));
    createCellsAndGrid(row, col, stateList, getCellShape(r.cellShape()), hoodType);
  }


  /**
   * Creates list of WatorCells objects to be passed into grid
   *
   * @param col       number of columns in grid for simulation
   * @param stateList list of all cell's states in row major order
   * @param shape     Shape object representing the shape of the cell as represented on 2d plane
   * @return list of initialized WatorCells
   */

  @Override
  public List<WatorCell> cellMaker(int col, List<Integer> stateList,
      Shape shape) {
    List<WatorCell> cellList = new ArrayList<>();
    Map<String, Integer> params = new HashMap<>();
    params.put("fishAgeOfReproduction", fishAgeOfReproduction);
    params.put("sharkAgeOfReproduction", sharkAgeOfReproduction);
    params.put("initialEnergy", initialEnergy);
    params.put("energyBoost", energyBoost);
    for (int i = 0; i < stateList.size(); i++) {
      cellList.add(new WatorCell(stateList.get(i), i / col, i % col, shape, params));
    }
    return cellList;
  }


  /**
   * Transition function for Wator World. Iterates through each cell, starting with all the sharks,
   * then fish, then empty, and calls the cell's transition function.
   */
  @Override
  public void transitionFunction() {
    for (int cellToUpdate : UPDATE_ORDER) {
      Iterator<WatorCell> gridIterator = getIterator();
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