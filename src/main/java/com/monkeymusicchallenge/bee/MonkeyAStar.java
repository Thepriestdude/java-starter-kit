package com.monkeymusicchallenge.bee;

import java.util.List;
import java.util.LinkedList;
import java.util.ArrayList;
import java.lang.Math;

public class MonkeyAStar extends AStar {

  ListEntity goal = null;


  private static MonkeyAStar instance = null;

  protected MonkeyAStar() {
    //super();
  }
  public static MonkeyAStar getInstance() {
    if(instance == null) {
      instance = new MonkeyAStar();
    }
    return instance;
  }

  public static ArrayList<ListEntity> getShortestPath(ListEntity start, ListEntity end){
    MonkeyAStar.getInstance().setGoal(end);
    ArrayList<ListEntity> result = new ArrayList<ListEntity>();
    LinkedList<ListEntity> path = (LinkedList<ListEntity>)MonkeyAStar.getInstance().compute(start);
    if (path != null) {
      result.addAll(path);
      return result;
    }
    return null;

  }

  public void setGoal(ListEntity node){
    this.goal = node;
  }

  /**
   * Check if the current node is a goal for the problem.
   *
   * @param node The node to check.
   * @return <code>true</code> if it is a goal, <code>false</else> otherwise.
   */
  @Override
  protected boolean isGoal(Object node){
    return this.goal.equals(node) ? true : false;
  }

  /**
   * Cost for the operation to go to <code>to</code> from
   * <code>from</from>.
   *
   * @param from The node we are leaving.
   * @param to The node we are reaching.
   * @return The cost of the operation.
   */
  @Override
  protected Double g(Object from, Object to){
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
   /*
   http://theory.stanford.edu/~amitp/GameProgramming/Heuristics.html
   */
  @Override
  protected Double h(Object ofrom, Object oto){
    /*if (ofrom == null || oto == null) {
      return 999999.0;
    }*/
    ListEntity from = (ListEntity) ofrom;
    ListEntity to = (ListEntity) oto;
    int dx = Math.abs(from.x - to.x);
    int dy = Math.abs(from.y - to.y);
    return (double)(2 * (dx + dy));
  }


  /**
   * Generate the successors for a given node.
   *
   * @param node The node we want to expand.
   * @return A list of possible next steps.
   */
  @Override
  protected List generateSuccessors(Object node) {
    /*ListEntity node = (ListEntity)onode;
    if (node == null) {
      return new ArrayList<ListEntity>();
    }*/
    //System.out.println("Connections from "+node+" are "+node.getConnections());
    return ((ListEntity)node).getConnections();
  }



}
