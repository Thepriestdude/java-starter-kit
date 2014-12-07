package com.monkeymusicchallenge.bee;

import java.util.*;
import org.json.JSONObject;

public class AI {

	public ListBuilder currentListBuilder = ListBuilder.getInstance();

	private List<String> inventory;

	TreeMap<Integer, ListEntity> targets = new TreeMap<Integer, ListEntity>();

	public AI(){

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

		final Map<String, Object> nextCommand = new HashMap<String, Object>();
		ArrayList<ListEntity> pathToTarget = null;
		ListEntity nextNodeInPath = null;
		ListEntity currentTarget = null;

		if (inventory.size()<3) {
			// Get all goals and insert into queue
			ArrayList<ListEntity> targetMusics = currentListBuilder.getEntitiesByType("song");
			targetMusics.addAll(currentListBuilder.getEntitiesByType("album"));
			targetMusics.addAll(currentListBuilder.getEntitiesByType("playlist"));

			System.out.println("! Possible targets list: "+ targetMusics);

			targets.clear();
			ListEntity nextNode;
			for (ListEntity music : targetMusics) {
				if (music != null) {
					ArrayList<ListEntity> path = ListBuilder.DFS(music);
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
		}else{

		}

		currentTarget = targets.firstEntry().getValue();

		// Inventory is full, goto user
		pathToTarget = ListBuilder.DFS(nearestUser);
		if (pathToTarget != null) {
			System.out.println("- Moving to user "+currentTarget+" on path:"+pathToTarget);
			nextNodeInPath = pathToTarget.values().toArray()[1];

			String move = Navigation.getDirection(nextNodeInPath);
			nextCommand.put("command", "move");
			nextCommand.put("direction",  move);
			return nextCommand;
		}

		// Just grab it
		ListEntity pickup = Navigation.somethingNearBy(ListBuilder.getCurrentPosition());
		if (pickup!=null) {
			String move = Navigation.getDirection(pickup);
			nextCommand.put("command", "move");
			nextCommand.put("direction",  move);
			System.out.println("- Picking up " + pickup + " to the " + move);
			return nextCommand;
		}

		// We need to move along the path to music
		pathToTarget = ListBuilder.DFS(currentTarget);
		if(pathToTarget != null) {
			System.out.println("- Moving to user "+currentTarget+" on path:"+pathToTarget);

			nextNodeInPath = pathToTarget.values().toArray()[1];

			String move = Navigation.getDirection(nextNodeInPath);
			nextCommand.put("command", "move");
			nextCommand.put("direction",  move);
			return nextCommand;
		}

		// There is nothing we can do, just idle

		nextCommand.put("command", "idle");
		System.out.println("! No path to target, idle");

		return nextCommand;
	}
}
