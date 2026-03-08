package ui;
import core.ComputerPlayer;
import core.GameLogic;
import java.util.HashMap;
import java.util.Map;
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

        boolean computerGame = false;
        ComputerPlayer cplayer = null;

        while(true){
            System.out.println("choose game mode !!!"); //ask the player if they want to play against player or bot
            System.out.println("1 - player vs player");
            System.out.println("2 - player vs bot");
            System.out.println("enter 1 or 2");

            String choice = scanner.nextLine().trim(); //choice should be 1 or 2
            if(choice.equals("1")){ //figure out what it is and set computerGame accordingly
                computerGame = false;
                break;
            } else if(choice.equals("2")){
                computerGame = true;
                cplayer = new ComputerPlayer(); //create cplayer
                break;
            } else {
                System.out.println("choice invalid :(");
            }
        }

        System.out.println("Begin :)");

        //keep looping while the game does not have a winner 
while(!game.win()){
    //figure out what players turn it is
    int player = game.getCurrentPlayer();
    int roll;

    //ask player to roll then move the player by that number
    //computer automatically rolls if its player 2 and vs bot mode
    if(computerGame && player == 2){
        System.out.println("computer is rolling...");
        roll = cplayer.cRoll(game);
    } 
    //otherwise ask the human player to roll
    else {
        System.out.println("player " + player + " its your turn to roll !!\npress enter!!");
        scanner.nextLine();
        roll = game.roll();
    }

    //move the player by that number then print the updated board
    game.movePlayer(roll);

    printBoard(
        game.getPlayerLocation(1),
        game.getPlayerLocation(2),
        game.getLadders(), 
        game.getSnakes()
    ); //now also calls getladders and getsnakes

    //tell the player their roll and new position 
    System.out.println("you rolled a " + roll + " your position is " + game.getPlayerLocation(player));

    //if someone wins print the board tell the players and break
    if(game.win()){
        printBoard(
            game.getPlayerLocation(1),
            game.getPlayerLocation(2), 
            game.getLadders(), 
            game.getSnakes()
        );
        System.out.println("player " + game.getCurrentPlayer() + " wins !!!");
        break;
    }

    //switch player after every turn 
    game.switchPlayer();
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

    private static void printBoard(int p1, int p2, Map<Integer,Integer> ladders, Map<Integer,Integer> snakes){
        //text labels for snakes and ladders 
        Map<Integer, String> labels = new HashMap<>();

        int i = 1; //ladder counter L1, L2
        for(int bottom : ladders.keySet()){ //loop through ladder bottoms 
            int top = ladders.get(bottom); //find ladder top 
            labels.put(bottom, "L" + i); //ladder bottom label
            labels.put(top, "H" + i); //ladder top label 
            i++; //increase
        }

        int j = 1;
        for(int head : snakes.keySet()){
            int tail = snakes.get(head);
            labels.put(head, "S" + j);
            labels.put(tail, "T" + j);
            j++;
        }

        //loop through all of the squares and print with fixed-width columns
        for(int row = 0; row < 10; row++){
            for(int col = 0; col < 10; col++){
                String text;
                int square = board[row][col];

                if(square == p1 && square == p2){ //players and when players are on the same square 
                    text = "p1p2";
                } else if(square == p1){
                    text = "p1";
                } else if(square == p2){
                    text = "p2"; 
                } else if(labels.containsKey(square)){
                    text = labels.get(square); //snakes and laddres
                } else {
                    text = Integer.toString(square); //normal numbers 
                }

                System.out.print("|" + String.format("%4s", text)); //keep everything lined up
            }
            System.out.println("|");

        }
        
    }


    
    
}
