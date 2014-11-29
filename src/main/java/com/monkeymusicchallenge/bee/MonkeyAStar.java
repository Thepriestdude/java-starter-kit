package main.java.com.monkeymusicchallenge.bee;

public class MonkeyAStar extends AStar {

  ListEntry goal = null;


  /**
   * Default c'tor.
   */
  public MonkeyAStar(){
      super();
  }

  public void setGoal(ListEntry node){
    this.goal = node;
  }

  /**
   * Check if the current node is a goal for the problem.
   *
   * @param node The node to check.
   * @return <code>true</code> if it is a goal, <code>false</else> otherwise.
   */
  protected boolean isGoal(T node){
    if ( node.x  ) {

    }
    return false;
  }

  /**
   * Cost for the operation to go to <code>to</code> from
   * <code>from</from>.
   *
   * @param from The node we are leaving.
   * @param to The node we are reaching.
   * @return The cost of the operation.
   */
  protected Double g(T from, T to){
    /*
    Goal prio
    1. if inventory is full user
    2. if rabies banana
    3. if rabies trap
    4. playlist
    5. doublenote
    6. note
    */

    return 1.0;
  }

  /**
   * Estimated cost to reach a goal node.
   * An admissible heuristic never gives a cost bigger than the real
   * one.
   * <code>from</from>.
   *
   * @param from The node we are leaving.
   * @param to The node we are reaching.
   * @return The estimated cost to reach an object.
   */
  protected Double h(T from, T to){

    return 1.0;
  }


  /**
   * Generate the successors for a given node.
   *
   * @param node The node we want to expand.
   * @return A list of possible next steps.
   */
  protected List<T> generateSuccessors(T node){



  }

}
