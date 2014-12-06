package com.monkeymusicchallenge.bee;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONObject;

public class ListBuilder {
	String[][] oldStringLayout;
	String[][] currentStringLayout;
	ArrayList<ArrayList<ListEntity>> LayoutListEntity = new ArrayList<ArrayList<ListEntity>>();
	int nrOfRows;
	int nrOfColumns;
	ListEntity currentPosition;

	private static ListBuilder instance = null;

	protected ListBuilder() {
		// Exists only to defeat instantiation.
	}
	public static ListBuilder getInstance() {
		if(instance == null) {
			instance = new ListBuilder();
		}
		return instance;
	}
	public static ListEntity getCurrentPosition(){
		//System.out.println("Monkey is now at "+ListBuilder.getInstance().currentPosition);
		return ListBuilder.getInstance().currentPosition;
	}

	public ArrayList<ArrayList<ListEntity>> createList(JSONObject gameState) {
		this.currentStringLayout = getStringRepresentation(gameState);

		JSONArray pos = gameState.getJSONArray("position");
		currentPosition = LayoutListEntity.get(pos.getInt(0)).get(pos.getInt(1));
		System.out.println("Monkey is now at "+currentPosition);

		System.out.println("Current rows: "+nrOfRows+"\nCurrent Columns: "+nrOfColumns);
		//check row pairs
		for(int x = 0; x < nrOfRows; x++){
			for(int y = 0; y < nrOfColumns-1; y++){
				if (!currentStringLayout[x][y].equals("wall") && !(currentStringLayout[x][y+1]).equals("wall")){

					//Update Holding for both nodes
					LayoutListEntity.get(x).get(y).setHolding(currentStringLayout[x][y]);
					LayoutListEntity.get(x).get(y+1).setHolding(currentStringLayout[x][y+1]);

					//add connectivity for both nodes
					LayoutListEntity.get(x).get(y).addConnection(LayoutListEntity.get(x).get(y+1));
					LayoutListEntity.get(x).get(y+1).addConnection(LayoutListEntity.get(x).get(y));

				}
			}
		}

		//check column pairs
		for(int x = 0; x < nrOfRows-1; x++){
			for(int y = 0; y < nrOfColumns; y++){
				if (!currentStringLayout[x][y].equals("wall") && !(currentStringLayout[x+1][y]).equals("wall")){

					//Update Holding for both nodes
					LayoutListEntity.get(x).get(y).setHolding(currentStringLayout[x][y]);
					LayoutListEntity.get(x+1).get(y).setHolding(currentStringLayout[x+1][y]);

					//add connectivity for both nodes
					LayoutListEntity.get(x).get(y).addConnection(LayoutListEntity.get(x+1).get(y));
					LayoutListEntity.get(x+1).get(y).addConnection(LayoutListEntity.get(x).get(y));

				}
			}
		}
		return LayoutListEntity;
	}

	private String[][] getStringRepresentation(JSONObject gameState) {
		String[][] returnStringLayout;
		JSONArray currentLayout = gameState.getJSONArray("layout");

		//check how large the gaming area are
		nrOfRows = currentLayout.length();
		nrOfColumns = ((JSONArray)currentLayout.get(0)).length();

		//fix the size of the currentStringLayout
		returnStringLayout = new String[nrOfRows][nrOfColumns];

		JSONArray row;	//use row to store the current row
		for (int rows = 0; rows < currentLayout.length(); rows++) {	//for all rows
			row = (JSONArray) currentLayout.get(rows);	//use the row
			for (int column = 0; column < row.length(); column++) {	//for all columns in the row
				returnStringLayout[rows][column] = (String)row.get(column);	//insert the string to our matrix
			}
		}

		return returnStringLayout;
	}

	public ArrayList<ListEntity> getEntitiesByType(String type){
		ArrayList<ListEntity> returnList = new ArrayList<ListEntity>();
		for (int x = 0; x < currentStringLayout.length; x++){
			for (int y = 0; y < currentStringLayout[0].length; y++){
				if (currentStringLayout[x][y].equals(type) ){
					returnList.add( LayoutListEntity.get(x).get(y) );
				}
			}
		}
		System.out.println("Entities of type "+type+": "+returnList);
		return returnList;
	}

	public void GridFiller(JSONObject gameState){
		JSONArray currentLayout = gameState.getJSONArray("layout");

		nrOfRows = currentLayout.length();
		nrOfColumns = ((JSONArray)currentLayout.get(0)).length();


		//ArrayList<ListEntity> empty = new ArrayList<ListEntity>
		for (int x = 0; x < nrOfRows; x++){
			LayoutListEntity.add(new ArrayList<ListEntity>());
		}
		for (int x = 0; x < nrOfRows; x++){
			for (int y = 0; y < nrOfColumns; y++){
				LayoutListEntity.get(y).add(new ListEntity(x, y, null) );
			}
		}
	}

	public void updateListBuilder(JSONObject gameState) {
		this.oldStringLayout = this.currentStringLayout;
		this.currentStringLayout = getStringRepresentation(gameState);

		JSONArray pos = gameState.getJSONArray("position");
		currentPosition = LayoutListEntity.get(pos.getInt(0)).get(pos.getInt(1));
		System.out.println("Monkey is now at "+currentPosition);

		JSONArray currentLayout = gameState.getJSONArray("layout");
		nrOfRows = currentLayout.length();
		nrOfColumns = ((JSONArray)currentLayout.get(0)).length();

		for (int x = 0; x < nrOfRows; x++) {
			for (int y = 0; y < nrOfColumns; y++) {
				if( !(currentStringLayout[x][y]==oldStringLayout[x][y]) ){
					LayoutListEntity.get(x).get(y).setHolding(currentStringLayout[x][y]);
				}
			}
		}
		/*
		for (int x = 0; x < LayoutListEntity.size(); x++){
			for(int y = 0; y < LayoutListEntity.get(x).size(); y++){
				System.out.print(LayoutListEntity.get(x).get(y).getConnections()+", ");
			}
			System.out.println("");
		}
		*/
	}

	public int getNrOfRows(){
		return this.nrOfRows;
	}

}
