package main.java.com.monkeymusicchallenge.bee;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONObject;

public class ListBuilder {
	String[][] oldStringLayout;
	String[][] currentStringLayout;
	ArrayList<ArrayList<ListEntity>> LayoutListEntity;
	int nrOfRows;
	int nrOfColumns;
	int[] currentPosition;

	public ListBuilder (){

	}

	public ArrayList<ArrayList<ListEntity>> createList(JSONObject gameState) {
		this.currentStringLayout = getStringRepresentation(gameState);

		//check row pairs
		for(int x = 0; x < nrOfRows-1; x++){
			for(int y = 0; y < nrOfColumns; y++){
				if (!currentStringLayout[x][y].equals("wall") && !(currentStringLayout[x][y+1]).equals("wall")){

					//Update Holding for both nodes
					LayoutListEntity.get(x).get(y).SetHolding(currentStringLayout[x][y]);
					LayoutListEntity.get(x).get(y+1).SetHolding(currentStringLayout[x][y+1]);

					//add connectivity for both nodes
					LayoutListEntity.get(x).get(y).AddConnection(LayoutListEntity.get(x).get(y+1));
					LayoutListEntity.get(x).get(y+1).AddConnection(LayoutListEntity.get(x).get(y));

				}
			}
		}

		//check column pairs
		for(int x = 0; x < nrOfRows-1; x++){
			for(int y = 0; y < nrOfColumns; y++){
				if (!currentStringLayout[x][y].equals("wall") && !(currentStringLayout[x][y+1]).equals("wall")){

					//Update Holding for both nodes
					LayoutListEntity.get(x).get(y).SetHolding(currentStringLayout[x][y]);
					LayoutListEntity.get(x).get(y+1).SetHolding(currentStringLayout[x][y+1]);

					//add connectivity for both nodes
					LayoutListEntity.get(x).get(y).AddConnection(LayoutListEntity.get(x).get(y+1));
					LayoutListEntity.get(x).get(y+1).AddConnection(LayoutListEntity.get(x).get(y));

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

	public void GridFiller(JSONObject gameState){
		JSONArray currentLayout = gameState.getJSONArray("layout");

		nrOfRows = currentLayout.length();
		nrOfColumns = ((JSONArray)currentLayout.get(0)).length();

		for (int x = 0; x < nrOfRows; x++){
			LayoutListEntity.add(x, new ArrayList<ListEntity>());
			for(int y = 0; y < nrOfColumns; y++){
				LayoutListEntity.get(x).add(y, new ListEntity(x, y, null) );
			}
		}

	}

	public void updateListBuilder(JSONObject gameState) {
		this.oldStringLayout = this.currentStringLayout;
		this.currentStringLayout = getStringRepresentation(gameState);

		JSONArray currentLayout = gameState.getJSONArray("layout");
		nrOfRows = currentLayout.length();
		nrOfColumns = ((JSONArray)currentLayout.get(0)).length();

		for (int x = 0; x < nrOfRows; x++) {
			for (int y = 0; y < nrOfColumns; y++) {
				if( !(currentStringLayout[x][y]==oldStringLayout[x][y]) ){
					LayoutListEntity.get(x).get(y).SetHolding(currentStringLayout[x][y]);
				}
			}
		}

	}

















}
