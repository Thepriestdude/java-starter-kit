package com.monkeymusicchallenge.bee;

import java.util.*;
import org.json.JSONObject;

public class AI {

	public ListBuilder currentListBuilder = ListBuilder.getInstance();

	private List<String> inventory;

	TreeMap<Integer, ListEntity> targets = new TreeMap<Integer, ListEntity>();

	//ArrayList<ListEntity> targets;

	public AI(){
		// Points are sorted by distance from our monkey.
		/*targets = new PriorityQueue<ListEntity>(100, new Comparator<ListEntity>() {
			public int compare(ListEntity a, ListEntity b) {
				System.out.println("% Compared "+a+" size:" + pathToA.size() + ", "+b+" size:"+pathToB.size());
				ArrayList<ListEntity> pathToA = MonkeyAStar.getShortestPath(ListBuilder.getCurrentPosition(), a);
				ArrayList<ListEntity> pathToB = MonkeyAStar.getShortestPath(ListBuilder.getCurrentPosition(), b);
				if (pathToA == null || pathToB == null) {
					return 0;
				}
				return (pathToA.size() > pathToB.size() ? 1 : -1);
			}
		});*/

	}


	public Map<String, Object> decision(final JSONObject gameState){
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

		// Get all goals and insert into queue
		ArrayList<ListEntity> targetMusics = currentListBuilder.getEntitiesByType("song");
		targetMusics.addAll(currentListBuilder.getEntitiesByType("album"));
		targetMusics.addAll(currentListBuilder.getEntitiesByType("playlist"));

		System.out.println("! Possible targets list: "+ targetMusics);

		targets.clear();
		ListEntity nextNode;
		for (ListEntity music : targetMusics) {
			if (music != null) {
				ArrayList<ListEntity> path = MonkeyAStar.getShortestPath(music, ListBuilder.getCurrentPosition());
				//System.out.println("# From "+ListBuilder.getCurrentPosition()+" to "+music+" path("+path+")");
				if ( path != null ) {
					targets.put(path.size(), music);
					//System.out.println("# There is a path("+path+") to "+ music+" added to queue.");
				}
			}else{
				System.out.println("!!! Panic - Music was null");
			}
		}
		System.out.println("! Targets: "+targets+"");

		final Map<String, Object> nextCommand = new HashMap<String, Object>();

		if (targets.size()>0) {
			// Execute against first in queue
			targets.remove(targets.firstKey());
			Integer lk = targets.firstKey();
			ListEntity currentTarget = targets.get(lk);

			if (currentTarget.equals(ListBuilder.getCurrentPosition())) {
				System.out.println("< We have reached the target, picking a new one");
				targets.remove(lk);
			}

			ArrayList<ListEntity> pathToTarget = MonkeyAStar.getShortestPath(currentTarget, ListBuilder.getCurrentPosition());
			System.out.println("- Current path to target: ["+pathToTarget+"]");

			if (pathToTarget != null && pathToTarget.size() > 0 && (nextNode = pathToTarget.get(0)) != null ) {
				// Decide how to move based on direction of the target
				String move = Navigation.getDirection(nextNode);
				nextCommand.put("command", "move");
				nextCommand.put("direction",  move);
				System.out.println("- Moving " + move + " to " + nextNode);
			}else{
				nextCommand.put("command", "idle");
				System.out.println("- No path to target, idle");
			}
		}else{
			nextCommand.put("command", "idle");
			System.out.println("- No path to target, idle");
		}

		return nextCommand;
	}
}
