package com.monkeymusicchallenge.bee;

import java.util.HashMap;
import java.util.Map;

import org.json.JSONObject;

import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.Unirest;
import java.util.Scanner;

// Kör med:
// mvn clean package && java -jar target/monkeybrain.jar Bee TKY5vloLtO9S21JBkes4+tsxFOI= 400

public class Main {
	private static final String GAME_URL = "http://competition.monkeymusicchallenge.com/game";

	public static void main(final String[] args) {

		if (args.length < 3) {
			System.out.println("Usage: java -jar target/monkey.jar <your-team-name> <your-api-key> <game-id>\n");
			if (args.length < 1) {
				System.out.println(" Missing argument: <your-team-name>");
			}
			if (args.length < 2) {
				System.out.println(" Missing argument: <your-api-key>");
			}
			if (args.length < 3) {
				System.out.println(" Missing argument: <game-id>");
			}
			System.exit(1);
		}
		final String teamName = args[0];
		final String apiKey = args[1];
		final String gameId = args[2];


		//System.out.print("Enter game ID: ");
		//Scanner gameIDScanner = new Scanner(System.in);
		//String gameID = gameIDScanner.nextLine();


		final Map<String, Object> joinGameCommand = new HashMap<String, Object>();
		joinGameCommand.put("command", "join game");
		joinGameCommand.put("team", teamName);
		joinGameCommand.put("apiKey", apiKey);
		joinGameCommand.put("gameId", gameId);

		JSONObject currentGameState = postToServer(joinGameCommand);

		// start the brain
		final AI ai = new AI();

		ai.currentListBuilder.GridFiller(currentGameState);
		ai.currentListBuilder.createList(currentGameState);

		// The current game state tells you if the game is over
		while (!currentGameState.getBoolean("isGameOver")) {
			System.out.println("Remaining turns: " + currentGameState.getInt("remainingTurns"));

			final Map<String, Object> nextCommand = ai.move(currentGameState);

			nextCommand.put("team", teamName);
			nextCommand.put("apiKey", apiKey);
			nextCommand.put("gameId", gameId);

			currentGameState = postToServer(nextCommand);
			ai.currentListBuilder.updateListBuilder(currentGameState);

		}

		// If the game is over, our server will tell you how you did
		System.out.println("\nGame over!");
		System.exit(0);
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
