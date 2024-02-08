package cellsociety.model.simulation;

import cellsociety.model.core.Cell;
import cellsociety.model.core.WatorCell;
import cellsociety.model.neighborhood.Neighborhood;
import cellsociety.model.simulation.Records.WatorRecord;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

/**
 * This cellular automata simulation represents the Predator-Prey simulaton developed by Alexander K
 * Dewdney
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
   * @param row,                   the number of rows in the 2-dimensional grid
   * @param col,                   the number of columns in the 2-dimensional grid
   * @param hoodType,              the definition of neighbors
   * @param stateList,             a list of the integer representation of each cells state, by
   *                               rows, then cols
   * @param fishAgeOfReproduction  age at which fish cells can reproduce and create new cells
   * @param sharkAgeOfReproduction age at which shark cells can reproduce and create new cells
   * @param initialEnergy          initial energy level for shark cell
   * @param energyBoost            energy gained by a shark for eating a fish cell
   * @param gridType               type of grid used in simulation
   */
  public WatorSimulation(int row, int col, Neighborhood hoodType, List<Integer> stateList,
      WatorRecord r) {
    super(row, col, hoodType, stateList, r.gridType(), r.cellShape(),
        (ind -> (WatorCell) new Cell(stateList.get(ind),
            ind / col, ind % col)));
    this.fishAgeOfReproduction = r.fishAgeOfReproduction();
    this.sharkAgeOfReproduction = r.sharkAgeOfReproduction();
    this.energyBoost = r.energyBoost();
    this.initialEnergy = r.initialEnergy();
  }

  /**
   * Filters a list of WatorCell objects to retreive all Cells of a certain state
   *
   * @param cellList a list of valid WatorCell objects
   * @param state    target state in filtering operation
   * @return a (copy) of a filtered list of all cells in cellList with a currentState equal to state
   */
  private List<WatorCell> getCellsOfState(List<WatorCell> cellList, int state) {
    List<WatorCell> ret = new ArrayList<>();
    for (WatorCell cell : cellList) {
      if (cell.getCurrentState() == state) {
        ret.add(cell);
      }
    }
    return ret;
  }

  /**
   * Updates a cell to put it in an empty state
   *
   * @param cell an arbitrary cell in the simulation that is transitioning to empty
   */
  private void fillEmptyCell(WatorCell cell) {
    cell.updateStateEnergyAge(EMPTY, -1, -1);
  }

  /**
   * Allows for a WatorCell in state fish to reproduce, going back to age 0
   *
   * @param currentCell the cell of the parent fish is originally
   * @param nextCell    the cell where the parent fish moves to after reproduction
   */
  private void reproduceFish(WatorCell currentCell, WatorCell nextCell) {
    currentCell.updateStateEnergyAge(FISH, -1, 0);
    nextCell.updateStateEnergyAge(FISH, -1, 0);
  }

  /**
   * Increases the age of a cell by 1, keeping other parameters constant
   *
   * @param currentCell the fish cell whose age will update by 1
   */
  private void increaseFishAge(WatorCell currentCell) {
    currentCell.updateStateEnergyAge(FISH, currentCell.getAge() + 1, -1);
  }

  /**
   * Handles movement of shark from one cell to another. This assumes the cell the shark is
   * attempting to move to is already determined. Shark will reproduce if necessary.
   *
   * @param currentCell the cell of the shark originally
   * @param nextCell    the cell the shark is attempting to move to
   */
  private void handleSharkMoveToEmptySpace(WatorCell currentCell, WatorCell nextCell) {
    if (nextCell.getNextState() == SHARK) { //another shark is already moving to nextCell
      currentCell.updateStateEnergyAge(SHARK, currentCell.getEnergy() - 1,
          currentCell.getAge() + 1);
    } else {
      nextCell.updateStateEnergyAge(SHARK, currentCell.getEnergy() - 1, currentCell.getAge() + 1);
      if (currentCell.getAge() >= sharkAgeOfReproduction) {
        currentCell.updateStateEnergyAge(SHARK, initialEnergy, 0); //create shark
        nextCell.updateStateEnergyAge(SHARK, currentCell.getEnergy() - 1, 0); //reproducing Shark
      } else {
        fillEmptyCell(currentCell);
      }
    }
  }

  /**
   * Handles shark's hunting of fish. Shark will move to cell with fish and eat the fish if no cell
   * is already planning to occupy that cell. Shark will reproduce if necessary
   *
   * @param currentCell the cell where the predator (shark) is first located
   * @param nextCell    the cell where the prey (fish) is first located, and where the shark will
   *                    move
   */
  private void handleSharkEatFish(WatorCell currentCell, WatorCell nextCell) {
    if (nextCell.getNextState() == SHARK || nextCell.getNextState() == FISH) {
      //another shark or fish is already moving to nextCell
      currentCell.updateStateEnergyAge(SHARK, currentCell.getEnergy() - 1,
          currentCell.getAge() + 1);
    } else {
      if (currentCell.getAge() >= sharkAgeOfReproduction) {
        currentCell.updateStateEnergyAge(SHARK, initialEnergy, 0);
        nextCell.updateStateEnergyAge(SHARK, currentCell.getEnergy() + energyBoost, 0);

      } else {
        fillEmptyCell(currentCell);
        nextCell.updateStateEnergyAge(SHARK, currentCell.getEnergy() + energyBoost,
            currentCell.getAge() + 1);

      }
    }
  }

  /**
   * Handles case where a shark is surrounded by sharks (cannot move). Shark ages and loses energy.
   *
   * @param currentCell the cell holding a shark that is unable to move.
   */
  private void handleSharkCantMove(WatorCell currentCell) {
    if (currentCell.getEnergy() > 1) {
      currentCell.updateStateEnergyAge(SHARK, currentCell.getEnergy() - 1,
          currentCell.getAge() + 1);
    } else {
      fillEmptyCell(currentCell);
    }
  }

  /**
   * Specific transition function for a fish cell in Wator Simulation. Calls the proper helper
   * function for transitioning based on the current state of neighbors.
   *
   * @param currentCell the fish cell trying to transition
   */
  private void updateFish(WatorCell currentCell) {
    List<WatorCell> neighbors = myGrid.getNeighbors(currentCell.getLocation(), myNeighborhood);
    List<WatorCell> emptyNeighbors = getCellsOfState(neighbors, EMPTY);
    Collections.shuffle(emptyNeighbors);
    if (emptyNeighbors.isEmpty()) {
      increaseFishAge(currentCell);
    } else {
      WatorCell nextCell = emptyNeighbors.get(0);
      if (nextCell.getNextState() == SHARK || nextCell.getNextState() == FISH) {
        increaseFishAge(currentCell);
      } else {
        if (currentCell.getAge() < fishAgeOfReproduction) {
          nextCell.updateStateEnergyAge(FISH, -1, currentCell.getAge() + 1);
          fillEmptyCell(currentCell);
        } else {
          reproduceFish(currentCell, nextCell);
        }
      }
    }
  }

  /**
   * Specific transition function for a shark cell in Wator Simulation. Calls the proper helper
   * function for transitioning based on the current state of neighbors.
   *
   * @param currentCell the fish cell trying to transition
   */
  private void updateShark(WatorCell currentCell) {
    List<WatorCell> neighbors = getGrid().getNeighbors(currentCell.getLocation(),
        getNeighborhood());
    List<WatorCell> emptyNeighbors = getCellsOfState(neighbors, EMPTY);
    List<WatorCell> fishNeighbors = getCellsOfState(neighbors, FISH);

    Collections.shuffle(emptyNeighbors);
    Collections.shuffle(fishNeighbors);

    if (fishNeighbors.isEmpty() && emptyNeighbors.isEmpty()) {
      handleSharkCantMove(currentCell);
    } else if (!fishNeighbors.isEmpty()) {
      handleSharkEatFish(currentCell, fishNeighbors.get(0));
    } else {
      if (currentCell.getEnergy() <= 1) {
        fillEmptyCell(currentCell);
      } else {
        handleSharkMoveToEmptySpace(currentCell, emptyNeighbors.get(0));
      }
    }
  }


  /**
   * Transition function for Wator World. Iterates through each cell and calls proper cell-specific
   * transition function based on the state of the particular cell
   */
  @Override
  public void transitionFunction() {
    for (int cellToUpdate : UPDATE_ORDER) {
      Iterator<WatorCell> gridIterator = getIterator();
      while (gridIterator.hasNext()) {
        WatorCell currentCell = gridIterator.next();
        if (currentCell.getNextState() == Cell.PLACEHOLDER &&
            currentCell.getCurrentState() == cellToUpdate) {
          switch (cellToUpdate) {
            case EMPTY: {
              currentCell.setNextState(EMPTY);
              break;
            }
            case FISH: {
              updateFish(currentCell);
              break;
            }
            case SHARK: {
              updateShark(currentCell);
              break;
            }
            default:
              break; //TODO: check exception
          }
        }
      }
    }
  }
}







