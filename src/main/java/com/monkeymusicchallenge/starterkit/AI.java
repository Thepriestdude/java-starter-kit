package main.java.com.monkeymusicchallenge.starterkit;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ThreadLocalRandom;

import org.json.JSONObject;

public class AI {

  public Map<String, Object> move(final JSONObject gameState) {
    // Go to http://github.com/monkey-music-challenge/core
    // for more info about the rules of Monkey Music Challenge!

    // TODO: You may want to do something smarter here
    final Map<String, Object> nextCommand = new HashMap<String, Object>();
    nextCommand.put("command", "move");
    nextCommand.put("direction", randomDirection());
    return nextCommand;
  }

  private String randomDirection() {
    return new String[] {"up", "down", "left", "right"}[ThreadLocalRandom.current().nextInt(4)];
  }
}
