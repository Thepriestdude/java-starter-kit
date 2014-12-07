package com.monkeymusicchallenge.bee;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Comparator;
import java.util.PriorityQueue;
import java.util.ArrayList;
import org.json.JSONObject;

public class AI {

	public ListBuilder currentListBuilder = ListBuilder.getInstance();

	private List<String> inventory;

	PriorityQueue<ListEntity> targetsQueue;

	public AI(){
		// Points are sorted by distance from our monkey.
		targetsQueue = new PriorityQueue<ListEntity>(100, new Comparator<ListEntity>() {
			public int compare(ListEntity a, ListEntity b) {
				ArrayList<ListEntity> pathToA = MonkeyAStar.getShortestPath(ListBuilder.getCurrentPosition(), a);
				ArrayList<ListEntity> pathToB = MonkeyAStar.getShortestPath(ListBuilder.getCurrentPosition(), b);
				if (pathToA == null || pathToB == null) {
					return 0;
				}
				System.out.println("Compared - "+a+" size:" + pathToA.size() + ", "+b+" size:"+pathToB.size());
				return (pathToA.size() > pathToB.size() ? 1 : -1);
			}
		});

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

		//System.out.println("Target list: "+ targetMusics);

		targetsQueue.clear();
		ListEntity nextNode;
		for (ListEntity music : targetMusics) {
			if (music != null) {
				ArrayList<ListEntity> path = MonkeyAStar.getShortestPath(ListBuilder.getCurrentPosition(), music);
				System.out.println("# There is a path("+path+") to "+ music +"");
				if ( path != null ) {
					targetsQueue.offer(music);
					//System.out.println("# There is a path("+path+") to "+ music+" added to queue.");
				}
			}
		}
		System.out.println("! Target queue: "+targetsQueue+"");

		final Map<String, Object> nextCommand = new HashMap<String, Object>();

		if (targetsQueue.size()>0) {
			// Execute against first in queue
			ListEntity currentTarget = targetsQueue.peek();

			if (currentTarget.equals(ListBuilder.getCurrentPosition())) {
				System.out.println("< We have reached the target, picking a new one");
				//ListEntity currentPos = targetsQueue.poll();
			}

			ArrayList<ListEntity> pathToTarget = MonkeyAStar.getShortestPath(ListBuilder.getCurrentPosition(), currentTarget);
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
