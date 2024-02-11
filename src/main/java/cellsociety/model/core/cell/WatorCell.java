package cellsociety.model.core.cell;

import cellsociety.model.core.shape.Shape;
import cellsociety.model.simulation.WatorSimulation;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

/**
 * Represents an extension of the Cell, and serves as the atomic unit of the Wator Simulation.
 * Contains extra instance variables that typical cells do not have, referencing energy and age.
 *
 * @author Noah Loewy
 */

public class WatorCell extends Cell<WatorCell> {

  private int myCurrentAge;
  private int myCurrentEnergy;
  private int myNextAge;
  private int myNextEnergy;

  /**
   * Initial amount of energy points that a shark is born with. A shark dies once it runs out of
   * energy.
   */
  private int initialEnergy;
  /**
   * Energy points gained by a shark for eating a fish cell. One unit of energy is lost per
   * timestep
   */
  private int energyBoost;
  /**
   * Number of chronons (timesteps) that must pass since birth of a shark until the shark is
   * fertile
   */
  private int sharkAgeOfReproduction;
  /**
   * Number of chronons (timesteps) that must pass since birth of a fish until the fish is fertile
   */
  private int fishAgeOfReproduction;

  /**
   * Constructs a Percolation Cell object for the Percolation simulation
   *
   * @param initialState the integer representation of the cell's current state
   * @param row          the row the cell is positioned at as represented on a 2D coordinate grid
   * @param col          the column the cell is positioned at as represented on a 2D coordinate
   *                     grid
   * @param shapeType    the shape of a cell, as represented on a 2D coordinate grid
   * @param params       map of string parameter names to their values. Description of parameters
   *                     can be found at the declaration of the instance variables.
   */
  public WatorCell(int initialState, int row, int col, Shape shapeType,
      Map<String, Integer> params) {
    super(initialState, row, col, shapeType);
    myCurrentAge = 0;
    myNextEnergy = -1;
    myNextAge = -1;

    fishAgeOfReproduction = params.get("fishAgeOfReproduction");
    sharkAgeOfReproduction = params.get("sharkAgeOfReproduction");
    initialEnergy = params.get("initialEnergy");
    energyBoost = params.get("energyBoost");
    if (initialState == WatorSimulation.SHARK) {
      myCurrentEnergy = initialEnergy;
    }
  }

  /**
   * Updates the state, age, and energy of the Wator Cell after the conclusion of the transition
   * function. This involves setting the "current" values to the "next" values, and settign the
   * "next" values to placeholder values.
   */
  @Override
  public void updateStates() {
    super.updateStates();
    myCurrentAge = myNextAge;
    myCurrentEnergy = myNextEnergy;
    myNextAge = PLACEHOLDER;
    myNextEnergy = PLACEHOLDER;
  }

  /**
   * Sets the values of a cell's next state, energy, and age.
   *
   * @param state  is the current occupant of the cell (Shark, Fish, or Empty)
   * @param energy is the energy remaining for a shark, or -1
   * @param age    is the number of timesteps since birth of the shark/fish, or -1
   */
  private void updateStateEnergyAge(int state, int energy, int age) {
    setNextState(state);
    setNextEnergy(energy);
    setNextAge(age);
  }

  /**
   * Retrieves myCurrentEnergy instance variable
   *
   * @return the current energy remaining for a shark, or -1
   */
  private int getEnergy() {
    return myCurrentEnergy;
  }

  /**
   * Retrieves myCurrentAge instance variable
   *
   * @return the number of timesteps since birth of the shark/fish, or -1
   */
  private int getAge() {
    return myCurrentAge;
  }

  /**
   * Updates myNextEnergy instance variable
   *
   * @param energy the energy remaining for a shark after this timestep, or -1
   */
  private void setNextEnergy(int energy) {
    myNextEnergy = energy;
  }

  /**
   * Updates myNextEnergy instance variable
   *
   * @param time the number of time steps since birth of the shark/fish after this timestep, or -1
   */
  private void setNextAge(int time) {
    myNextAge = time;
  }

  /**
   * Filters a list of WatorCell objects to retrieve all Cells of a certain state
   *
   * @param state target state in filtering operation
   * @return a (copy) of a filtered list of all cells in cellList with a currentState equal to state
   */
  private List<WatorCell> getNeighborsOfState(int state) {
    List<WatorCell> ret = new ArrayList<>();
    for (WatorCell cell : getNeighbors()) {
      if (cell.getCurrentState() == state) {
        ret.add(cell);
      }
    }
    return ret;
  }

  /**
   * Updates a cell to put it in an empty state
   */
  private void fillEmptyCell() {
    updateStateEnergyAge(WatorSimulation.EMPTY, -1, -1);
  }


  /**
   * Increases the age of a cell by 1, keeping other parameters constant
   */
  private void increaseFishAge() {
    updateStateEnergyAge(WatorSimulation.FISH, -1, getAge() + 1);
  }

  /**
   * Handles movement of shark from one cell to another. This assumes the cell the shark is
   * attempting to move to is already determined. Shark will reproduce if necessary.
   *
   * @param nextCell the cell the shark is attempting to move to
   */
  private void handleSharkMoveToEmptySpace(WatorCell nextCell) {
    if (nextCell.getNextState()
        == WatorSimulation.SHARK) { //another shark is already moving to nextCell
      updateStateEnergyAge(WatorSimulation.SHARK, getEnergy() - 1,
          getAge() + 1);
    } else {
      nextCell.updateStateEnergyAge(WatorSimulation.SHARK, getEnergy() - 1,
          getAge() + 1);
      if (getAge() >= sharkAgeOfReproduction) {
        updateStateEnergyAge(WatorSimulation.SHARK, initialEnergy, 0); //create shark
        nextCell.updateStateEnergyAge(WatorSimulation.SHARK, getEnergy() - 1,
            0); //reproducing Shark
      } else {
        fillEmptyCell();
      }
    }
  }

  /**
   * Handles shark's hunting of fish. Shark will move to cell with fish and eat the fish if no cell
   * is already planning to occupy that cell. Shark will reproduce if necessary, resetting their own
   * age too in the process.
   *
   * @param nextCell the cell where the prey (fish) is first located, and where the shark will move
   */
  private void handleSharkEatFish(WatorCell nextCell) {
    if (nextCell.getNextState() == WatorSimulation.SHARK
        || nextCell.getNextState() == WatorSimulation.FISH) {
      //another shark or fish is already moving to nextCell
      updateStateEnergyAge(WatorSimulation.SHARK, getEnergy() - 1,
          getAge() + 1);
    } else {
      if (getAge() >= sharkAgeOfReproduction) {
        updateStateEnergyAge(WatorSimulation.SHARK, initialEnergy, 0);
        nextCell.updateStateEnergyAge(WatorSimulation.SHARK, getEnergy() + energyBoost,
            0);

      } else {
        fillEmptyCell();
        nextCell.updateStateEnergyAge(WatorSimulation.SHARK, getEnergy() + energyBoost,
            getAge() + 1);

      }
    }
  }

  /**
   * Handles case where a shark is surrounded by sharks (cannot move). Shark ages and loses energy.
   */
  private void handleSharkCantMove() {
    if (getEnergy() > 1) {
      updateStateEnergyAge(WatorSimulation.SHARK, getEnergy() - 1,
          getAge() + 1);
    } else {
      fillEmptyCell();
    }
  }

  /**
   * Specific transition function for a fish cell in Wator Simulation. Calls the proper helper
   * function for transitioning based on the current state of neighbors.
   */
  private void updateFish() {
    List<WatorCell> emptyNeighbors = getNeighborsOfState(WatorSimulation.EMPTY);
    Collections.shuffle(emptyNeighbors);
    if (emptyNeighbors.isEmpty()) {
      increaseFishAge();
    } else {
      WatorCell nextCell = emptyNeighbors.get(0);
      if (nextCell.getNextState() == WatorSimulation.SHARK
          || nextCell.getNextState() == WatorSimulation.FISH) {
        increaseFishAge();
      } else {
        if (getAge() < fishAgeOfReproduction) {
          nextCell.updateStateEnergyAge(WatorSimulation.FISH, -1, getAge() + 1);
          fillEmptyCell();
        } else {
          updateStateEnergyAge(WatorSimulation.FISH, -1, 0);
          nextCell.updateStateEnergyAge(WatorSimulation.FISH, -1, 0);
        }
      }
    }
  }

  /**
   * Specific transition function for a shark cell in Wator Simulation. Calls the proper helper
   * function for transitioning based on the current state of neighbors.
   */
  private void updateShark() {
    List<WatorCell> emptyNeighbors = getNeighborsOfState(WatorSimulation.EMPTY);
    List<WatorCell> fishNeighbors = getNeighborsOfState(WatorSimulation.FISH);

    Collections.shuffle(emptyNeighbors);
    Collections.shuffle(fishNeighbors);

    if (fishNeighbors.isEmpty() && emptyNeighbors.isEmpty()) {
      handleSharkCantMove();
    } else if (!fishNeighbors.isEmpty()) {
      handleSharkEatFish(fishNeighbors.get(0));
    } else {
      if (getEnergy() <= 1) {
        fillEmptyCell();
      } else {
        handleSharkMoveToEmptySpace(emptyNeighbors.get(0));
      }
    }
  }

  /**
   * Handles transition of Wator Cell. Calls the proper helper function for transitioning based on
   * the state of the Wator Cell being updated.
   */
  @Override
  public void transition() {
    switch (getCurrentState()) {
      case WatorSimulation.EMPTY: {
        setNextState(WatorSimulation.EMPTY);
        break;
      }
      case WatorSimulation.FISH: {
        updateFish();
        break;
      }
      case WatorSimulation.SHARK: {
        updateShark();
        break;
      }
      default:
        break; //TODO: check exception
    }
  }

  public String getText() {
    return switch (getCurrentState()) {
      case WatorSimulation.SHARK -> "\uD83D\uDC20";
      case WatorSimulation.FISH -> "\uD83D\uDC1F";
      default -> "";
    };
  }

  public void setParams(Map<String, Double> params) {
    fishAgeOfReproduction = (int) Math.floor(params.get("fishAgeOfReproduction"));
    sharkAgeOfReproduction = (int) Math.floor(params.get("sharkAgeOfReproduction"));
    energyBoost = (int) Math.floor(params.get("energyBoost"));
    initialEnergy = (int) Math.floor(params.get("initialEnergy"));
  }

}


