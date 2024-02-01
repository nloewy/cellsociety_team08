package cellsociety.model.core;

public class WatorCell extends Cell {

  /**
   * Represents an extension of the Cell, and serves as the atomic unit of the Wator simulation.
   * Contains extra instance variables that typical cells do not have, referencing energy and age.
   *
   * @author Noah Loewy
   */
  private int myCurrentAge;
  private int myCurrentEnergy;
  private int myNextAge;
  private int myNextEnergy;

  /**
   * Constructs a Wator cell object
   *
   * @param initialState is the original state of the cell, either randomly set or determined from a
   *                     configuration file
   * @param x            is the x-coordinate of the cell on the 2-dimensional grid
   * @param y            is the y-coordinate of the cell on the 2-dimensional grid
   * @param energy       is the initial energy level of the Wator Cell, if it is a shark, or -1
   * @param age          is the current age of the Wator Cell, if it is a shark/fish, or -1
   */

  public WatorCell(int initialState, int x, int y, int energy, int age) {
    super(initialState, x, y);
    myCurrentEnergy = energy;
    myCurrentAge = age;
    myNextEnergy = -1;
    myNextAge = -1;
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
  public void updateStateEnergyAge(int state, int energy, int age) {
    setNextState(state);
    setNextEnergy(energy);
    setNextAge(age);
  }

  /**
   * Retrieves myCurrentEnergy instance variable
   *
   * @return myCurrentEnergy, the current energy remaining for a shark, or -1
   */
  public int getEnergy() {
    return myCurrentEnergy;
  }

  /**
   * Retrieves myCurrentAge instance variable
   *
   * @return myCurrentAge, the number of timesteps since birth of the shark/fish, or -1
   */
  public int getAge() {
    return myCurrentAge;
  }

  /**
   * Updates myNextEnergy instance variable
   *
   * @param energy the energy remaining for a shark after this timestep, or -1
   */
  public void setNextEnergy(int energy) {
    myNextEnergy = energy;
  }

  /**
   * Updates myNextEnergy instance variable
   *
   * @param time the number of timesteps since birth of the shark/fish after this timestep, or -1
   */
  public void setNextAge(int time) {
    myNextAge = time;
  }

}
