package main.java.com.monkeymusicchallenge.starterkit;

import java.util.ArrayList;
import java.util.List;

public class ListEntity {
	ArrayList<Integer> coordinates = new ArrayList<Integer>();
	List<ListEntity> connection = new ArrayList<ListEntity>();
	ListEntity right;
	String holding;


	public ListEntity (){
		
	}
	public ListEntity (int x, int y, String holding) {
		coordinates.add(x);
		coordinates.add(y);
		this.holding = holding;

	}
	
	public void AddConnection (ListEntity connection) {
		this.connection.add(connection);
	}
	public void SetHolding (String holding) {
		this.holding = holding;
	}
	public String GetHolding (){
		return this.holding;
	}
}
