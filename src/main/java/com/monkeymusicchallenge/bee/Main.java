package main.java.com.monkeymusicchallenge.bee;

import java.util.HashMap;
import java.util.Map;

import org.json.JSONObject;

import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.Unirest;
import java.util.Scanner;

// Hi! Welcome to the Monkey Music Challenge Java starter kit!

public class Main {
	// You control your monkey by sending POST requests to the Monkey Music server
	private static final String GAME_URL = "http://competition.monkeymusicchallenge.com/game";



	public static void main(final String[] args) {

		System.out.print("Enter game ID: ");
		Scanner gameIDScanner = new Scanner(System.in);
		String gameID = gameIDScanner.nextLine();

		// You identify yourselves by your team name, your API key, and the current game ID
		final String teamName = "Bee";
		final String apiKey = "TKY5vloLtO9S21JBkes4+tsxFOI=";
		final String gameId = gameID;

		// We've put the AI-code in a separate class
		final AI ai = new AI();

		// Allright, time to get started!

		// When we POST a command to the server, it always replies with the current game state
		final Map<String, Object> joinGameCommand = new HashMap<String, Object>();
		joinGameCommand.put("command", "join game");
		joinGameCommand.put("team", teamName);
		joinGameCommand.put("apiKey", apiKey);
		joinGameCommand.put("gameId", gameId);

		JSONObject currentGameState = postToServer(joinGameCommand);

		ai.currentListBuilder.GridFiller(currentGameState);
		ai.currentListBuilder.createList(currentGameState);
		// The current game state tells you if the game is over
		while (!currentGameState.getBoolean("isGameOver")) {
			//Variables
			ListBuilder currentListBuilder = new ListBuilder();
			boolean firstTurn = true;

			//Constructor
			public Map<String, Object> move(final JSONObject gameState) {
				ai.currentListBuilder.updateListBuilder(currentGameState);
				// The game is not over, time to make a move!
				System.out.println("Remaining turns: " + currentGameState.getInt("remainingTurns"));

				// Use your AI to decide in which direction to move
				final Map<String, Object> nextCommand = ai.move(currentGameState);

				// Don't forget to include your credentials!
				nextCommand.put("team", teamName);
				nextCommand.put("apiKey", apiKey);
				nextCommand.put("gameId", gameId);

				// ...and send a new move command to the server
				currentGameState = postToServer(nextCommand);

				// After sending your command, you'll get the new game state back
				// and we go back up the loop to calculate our next move.
			}

			// If the game is over, our server will tell you how you did
			System.out.println("\nGame over!");
			System.exit(0);
		}
	}

	// Every time we POST a command to the server, we get the current game state back
	private static JSONObject postToServer(final Map<String, Object> command) {
		try {
			// In this starter kit, we use the Unirest library to send POST requests
			final HttpResponse<JsonNode> response = Unirest.post(GAME_URL)
					.header("Content-Type", "application/json")
					.body(new JSONObject(command).toString())
					.asJson();

			if (response.getCode() == 200) {
				return response.getBody().getObject();
			} else {
				if (response.getBody().getObject().has("message")) {
					System.out.println(response.getBody().getObject().getString("message"));
				}
				throw new RuntimeException("Server replied with status code " + response.getCode());
			}
		} catch (final Exception e) {
			// Hopefully, our server will always be able to handle your requests, but you never know...
			System.out.println(e.getMessage());
			System.exit(1);
			throw new AssertionError(); // unreachable
		}
	}
}