package com.monkeymusicchallenge.bee;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Comparator;
import java.util.PriorityQueue;
//import java.util.concurrent.ThreadLocalRandom;

import org.json.JSONObject;

public class AI {

	public ListBuilder currentListBuilder = new ListBuilder();
	private MonkeyAStar astar = new MonkeyAStar();

	private List<String> inventory;

	PriorityQueue<ListEntity> targetsQueue;

	public AI(){

		targetsQueue = new PriorityQueue<ListEntity>(100, new Comparator<ListEntity>() {
			public int compare(ListEntity start, ListEntity end) {
				astar.setGoal(end);
				return astar.compute(start).size();
			}
		});

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

		ArrayList<ListEntity> musics = currentListBuilder.getEntitiesByType("playlist");
		for (ListEntity music : musics) {
			targetsQueue.offer(music);
		}
		while(true) {
			ListEntity currentTarget = targetsQueue.poll();
			if(currentTarget == null) {
				System.out.println("No more targets!");
				break;
			}

			System.out.println("Current target "+currentTarget);
		}



		/*

		// Get all goals and insert into queue

		// Execute against first in queue



		 */

		return "up";

	}

	public Map<String, Object> move(final JSONObject gameState) {

		final Map<String, Object> nextCommand = new HashMap<String, Object>();

		nextCommand.put("command", "move");
		nextCommand.put("direction", decision());
		return nextCommand;

	}
}
