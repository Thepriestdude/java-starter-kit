package main.java.com.monkeymusicchallenge.bee;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ThreadLocalRandom;

import org.json.JSONObject;

public class AI {

  private List<String> inventory;
  private ListBuilder gameLayout;

  public AI(){
    gameLayout = new ListBuilder();

  }


  public String decision(){
    /*
    Make a decision

    - Pickup music

    - Pickup trap

    - Pickup banana

    - Go to coord

    - Use banana

    - if lever is within range and
     - Use lever

    - if monkey has wall on (right and left) or (up and down)
     - Use trap

    - if inventory is full
     - Use user

    */


  }

  public Map<String, Object> move(final JSONObject gameState) {
    // Go to http://github.com/monkey-music-challenge/core
    // for more info about the rules of Monkey Music Challenge!

    // TODO: You may want to do something smarter here
    final Map<String, Object> nextCommand = new HashMap<String, Object>();
    nextCommand.put("command", "move");
    nextCommand.put("direction", decision());
    return nextCommand;
  }

}
