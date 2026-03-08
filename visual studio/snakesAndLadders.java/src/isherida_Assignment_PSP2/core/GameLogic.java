package core;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class GameLogic{

    //initialize variables
    private int p1Position;
    private int p2Position;
    public int currentPlayer;
    Random random = new Random();

    //new maps for snakes and ladders
    private Map<Integer, Integer> ladders = new HashMap<>();
    private Map<Integer, Integer> snakes = new HashMap<>();

    //set starting values 
    public GameLogic(){
        p1Position = 0;
        p2Position = 0;
        currentPlayer = 1;

        //ladder positions (bottom -> top)
        ladders.put(3,24);
        ladders.put(27, 58);
        ladders.put(23,82);
        ladders.put(20,60);
        ladders.put(54,86);

        //snake positions (head -> tail)
        snakes.put(97,79);
        snakes.put(76,35);
        snakes.put(91,61);
        snakes.put(73,15);
        snakes.put(42,17);


    }

    //roll is a random number from 1-6
    public int roll(){
        return random.nextInt(6) + 1;
    }

    //move the current player by there current position plus roll 
    public void movePlayer(int r){
        if(currentPlayer == 1){
            p1Position += r;
            p1Position = checkSnakesLadders(p1Position); //check if the player is on a snake or a ladder
            if(p1Position > 100){ //if the players position is over 100 cancel out there roll 
                p1Position = p1Position - r;
            }
        }

        else if(currentPlayer == 2){
            p2Position += r;
            p2Position = checkSnakesLadders(p2Position);
             if(p2Position > 100){
                p2Position = p2Position - r;
            }
        }
    }

    private int checkSnakesLadders(int position){
        if(ladders.containsKey(position)){  //if the position is the bottom of a ladder
            return ladders.get(position); //return ladder top 
        }
        if(snakes.containsKey(position)){ //if the postion is the head 
            return snakes.get(position); //return snake tail
        }
        return position; //if neither return unchanged potion 
    }


    //return player location 
    public int getPlayerLocation(int player){
        if(player == 1){
            return p1Position;
        }
        else if(player == 2){
            return p2Position;
        }
        return 0;
    }

    //get current player
    public int getCurrentPlayer(){
        return currentPlayer;
    }

    //getter for ladders
    public Map<Integer, Integer> getLadders(){
        return ladders;
    }

    //getter for snakes
     public Map<Integer, Integer> getSnakes(){
        return snakes;
    }
    
    //if a player is at or over 100 they win 
    public boolean win(){
        if(p1Position == 100 || p2Position == 100){
            return true;
        }
        return false;
    }

    //switch player 
    public void switchPlayer(){
        if(currentPlayer == 1){
            currentPlayer = 2;
        }

        else if(currentPlayer == 2){
            currentPlayer = 1;
        }
    }
    
}