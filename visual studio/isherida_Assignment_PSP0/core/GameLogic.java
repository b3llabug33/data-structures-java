package core;

import java.util.Random;

public class GameLogic{

    //initialize variables
    private int p1Position;
    private int p2Position;
    public int currentPlayer;
    Random random = new Random();

    //set starting values 
    public GameLogic(){
        p1Position = 0;
        p2Position = 0;
        currentPlayer = 1;
    }

    //roll is a random number from 1-6
    public int roll(){
        return random.nextInt(6) + 1;
    }

    //move the current player by there current position plus roll 
    public void movePlayer(int r){
        if(currentPlayer == 1){
            p1Position += r;
        }

        else if(currentPlayer == 2){
            p2Position += r;
        }
    }

    //return player location 
    public int getPlayerLocation(int player){
        if(player == 1){
            return p1Position;
        }
        else if(player == 2){
            return p2Position;
        }
        return 1;
    }

    //get current player
    public int getCurrentPlayer(){
        return currentPlayer;
    }

    //if a player is at or over 100 they win 
    public boolean win(){
        if(p1Position >= 100 || p2Position >= 100){
            return true;
        }
        return false;
    }

    //switch player 
    public void switchPlayer(int player){
        if(currentPlayer == 1){
            currentPlayer = 2;
        }

        else if(currentPlayer == 2){
            currentPlayer = 1;
        }
    }
    
}