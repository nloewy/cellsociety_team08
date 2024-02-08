package cellsociety.model.core;

public class State {

    public static final int PLACEHOLDER = -1;
    private int myCurrentStatus;
    private int myNextStatus;

    /**
     * Constructs a State object
     *
     * @param initialState is the original state of the cell, either randomly set or determined from a
     */
    public State(int initialState) {
      myCurrentStatus = initialState;
      myNextStatus = PLACEHOLDER;
    }

    /**
     * This function updates the state of the cell after calling the transition function. The new
     * currentState takes the value of the nextState placeholder, and nextState is set to placeholder
     * value.
     */

    public void updateStatus() {
      myCurrentStatus = myNextStatus;
      myNextStatus = PLACEHOLDER;
    }

    /**
     * Retrieves myCurrentState instance variable
     *
     * @return myCurrentState, the current state of the cell object.
     */
    public int getCurrentStatus() {
      return myCurrentStatus;
    }

    /**
     * Retrieves myNextState instance variable
     *
     * @return myNextState, the future state of the cell object.
     */
    public int getNextStatus() {
      return myNextStatus;
    }

    /**
     * Updates myNextState instance variable
     *
     * @param nextStatus, new value of myNextState, calculated by transition function
     */
    public void setNextStatus(int nextStatus) {
      myNextStatus = nextStatus;
    }


}
