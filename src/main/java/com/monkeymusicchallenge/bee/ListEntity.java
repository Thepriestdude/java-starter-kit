package com.monkeymusicchallenge.bee;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import com.mashape.unirest.request.GetRequest;


public class ListEntity {
	public int x, y;
	ArrayList<Integer> coordinates = new ArrayList<Integer>();
	ArrayList<ListEntity> connection = new ArrayList<ListEntity>();
	ListEntity right; // gammalt?
	String holding;


	public ListEntity (int xx, int yy, String holding) {
		this.x = xx;
		this.y = yy;
		coordinates.add(xx);
		coordinates.add(yy);
		this.holding = holding;

	}

	public void addConnection (ListEntity connection) {
		this.connection.add(connection);
	}
	public void setHolding (String holding) {
		this.holding = holding;
	}
	public String getHolding (){
		return this.holding;
	}
	public ArrayList<Integer> getCoordinates(){
		return coordinates;
	}
	public ArrayList<ListEntity> getConnections(){
		return connection;

	}

	public int getX(){
		return this.x;
	}

	public int getY(){
		return this.y;
	}

	@Override
	public boolean equals(Object other) {
		boolean result = false;
		if (other instanceof ListEntity) {
			ListEntity that = (ListEntity) other;
			result = (this.x == that.x && this.y == that.y && this.getClass().equals(that.getClass()));
		}
		return result;
	}

	public String toString() {
		return "ListEntity{" + x + ", " + y + ", "+holding+"}";
	}
}
