package main.java.com.monkeymusicchallenge.bee;

import java.util.List;

public class ListEntity {
	int [] coordinates;
	List<ListEntity> connection;
	ListEntity right;
	String holding;


	public ListEntity (){

	}
	public ListEntity (int x, int y, String holding) {
		coordinates[0] = x;
		coordinates[1] = y;
		this.coordinates = coordinates;
		this.holding = holding;

	}

	public void AddConnection (ListEntity connection) {
		this.connection.add(connection);
	}
	public void SetHolding (String holding) {
		this.holding = holding;
	}
}
