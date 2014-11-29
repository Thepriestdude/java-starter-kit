package main.java.com.monkeymusicchallenge.bee;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ThreadLocalRandom;

import org.json.JSONObject;

public class AI {

	private List<String> inventory;
	public ListBuilder currentListBuilder; //BE CARFUL WITH THIS ONE!!!
	private MonkeyAStar astar;


	public AI(){
		currentListBuilder = new ListBuilder();
		astar = new MonkeyAStar();
	}


	public String decision(){
		/*
    Make a decision
    - Go to nearest music
    - if ...
     - Go to trap
    - if ...
     - Go to banana
    - Go to coord
    - Use banana
    - if lever is within range and
     - Go to lever
    - if monkey has wall on (right and left) or (up and down)
     - Use trap
    - if inventory is full
     - Go to user

    */
    /*

    astar.setGoal(goal);
    List<T> path = astar.compute(start);

    path.length()

		 */

		return "up";

	}

	public Map<String, Object> move(final JSONObject gameState) {
		// Go to http://github.com/monkey-music-challenge/core
		// for more info about the rules of Monkey Music Challenge!

		// TODO: You may want to do something smarter here
		final Map<String, Object> nextCommand = new HashMap<String, Object>();
		nextCommand.put("command", "move");
		nextCommand.put("direction", decision());
		return nextCommand;
	}
}
