package main.java.com.monkeymusicchallenge.bee;

import java.util.concurrent.ThreadLocalRandom;

public class Navigation {
	
	public static String getDirection(ListEntity listEntityToCheck) {
		ListBuilder listbuilder = new ListBuilder();
		ListEntity currentPosition = ListBuilder.getCurrentPosition();
		ListEntity move = listEntityToCheck;
		
		int monkeyPosX = currentPosition.getX();
		int monkeyPosY = currentPosition.getY();
		
		int movePosX = move.getX();
		int movePosY = move.getY();
		
		if ( (monkeyPosX == movePosX)&&(monkeyPosY == movePosY) ){
			System.out.println("Tried to move to the same spot, random direction returned");
			return randomDirection();
		}
		
		//coordinate on same row
		else if(monkeyPosX == movePosX) {
			
			//to the left
			if(monkeyPosY == movePosY-1){
				System.out.println("Moved to the left");
				return "left";
			}
			
			//to the left
			else if(monkeyPosY == movePosY+1){
				System.out.println("Moved to the right");
				return "right";
			}
			
			//out of bounds (coordinate to far to the right or left)
			else{
				System.out.println("Tried to move to far, random direction returned");
				return randomDirection();
			}
		}
		
		//coordinate in same Column
		else if (monkeyPosY == movePosY){
			
			//up
			if(monkeyPosX == movePosX-1){
				System.out.println("Moved up");
				return "up";
			}
			
			//down
			if(monkeyPosX == movePosY+1){
				System.out.println("Moved to the right");
				return "down";
			}
		}
		
		System.out.println("SOMETHING WENT REALLY WRONG, RANDOM DIRECTION RETURNED");
		return randomDirection();
		
	}

	private static String randomDirection() {
		return new String[] {"up", "down", "left", "right"}[ThreadLocalRandom.current().nextInt(4)];
	}
}
