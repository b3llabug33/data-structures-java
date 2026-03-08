package ui;
//import the gameLogic so they can talk 
import core.GameLogic;
import java.util.Scanner; 

public class GameConsoleUI {

    //create the board out of a 2d 10 by 10 array
    private static int[][] board = new int[10][10];

    public static void main(String[] args) {
        //initialize the scanner and game logic 
        Scanner scanner = new Scanner(System.in);
        GameLogic game = new GameLogic();

        //create board
        createBoard();

        System.out.println("Begin :)");

        //keep looping while the game does not have a winner 
        while(!game.win()){
            //figure out what players turn it is
            int player = game.getCurrentPlayer();
                //ask player to roll then move the player by that number then print the updated board and
                //tell the player their roll and new position 
                System.out.println("player" + game.getCurrentPlayer() + " it's your turn to roll !!\npress enter to roll !!");
                scanner.nextLine();
                int roll = game.roll();
                game.movePlayer(roll);
                printBoard(game.getPlayerLocation(1),game.getPlayerLocation(2));
                System.out.println("you rolled a " + roll + " your position is " + game.getPlayerLocation(player));

                //if someone wins print the board tell the players and break
                if(game.win()){
                    printBoard(game.getPlayerLocation(1),game.getPlayerLocation(2));
                    System.out.println("player" + game.getCurrentPlayer() + "wins !!!");
                    break;
            
                }
                //switch player after every turn 
                game.switchPlayer(game.getCurrentPlayer());
            }

        }
    

    private static void createBoard(){
        //number to count from 
        int number = 1;

        //print 10 rows and 10 collums counting up from 1-100
        for(int row = 9; row >= 0; row--){
            for(int col = 0; col < 10; col++){
                board[row][col] = number++; //increase count every time
            }
        }

    }

    private static void printBoard(int p1, int p2){
        //loop through all of the squares 
        for(int row = 0; row < 10; row++){
            for(int col = 0; col < 10; col++){
                //create a string for each square 
                String text;
                int square = board[row][col];
                
                //check if player 1 and 2 are on the same square 
                if(square == p1 && square == p2){
                    text = "p1p2";
                }

                //check for p1
                else if(square == p1){
                    text = " p1";
                }

                //check for p2
                else if(square == p2){
                    text = " p2";

                }

                //make sure spacing is correct for all of the normal numbers 
                else if(square == 100){
                    text = "" + square;
                }

                else if(square < 10){
                    text = "  " + square;
                }

                else if(square > 9){
                    text = " " + square;
                }

                else {
                    text = " ";
                }

                System.out.print("|" + text);
            }
            System.out.println("|");
        }
        
    }


    
    
}
