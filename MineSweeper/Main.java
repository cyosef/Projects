// Caleb Yosef: yosef006
// Adam Shabaneh: shaba070
import java.util.Scanner;
public class Main{
    public static void main(String[] args) {
        boolean valid = false;
        Minefield mine = null;
        String gameType = null;
        Scanner input = null;
        int numRows = 0, numCols = 0, flags = 0, x = 0, y = 0, loser = 0, correctGuesses = 0, ogNumMines = 0;
        System.out.println("Welcome to MineSweeper!");

        while (!valid) { //get difficultly and create minefield of correct size
            System.out.println("Choose your difficulty: Easy, Medium, or Hard");
            input = new Scanner(System.in);
            String user = input.nextLine();
            if (user.equals("Easy") || user.equals("easy")) {
                mine = new Minefield(5, 5, 5);
                numRows = 5;
                numCols = 5;
                flags = 5;
                valid = true;
            } else if (user.equals("Medium") || user.equals("medium")) {
                mine = new Minefield(9, 9, 12);
                numRows = 9;
                numCols = 9;
                flags = 12;
                valid = true;
            } else if (user.equals("Hard") || user.equals("hard")) {
                mine = new Minefield(20, 20, 40);
                numRows = 20;
                numCols = 20;
                flags = 40;
                valid = true;
            } else {
                System.out.println("Invalid Difficulty Try Again");
            }
        }
        ogNumMines = flags;
        System.out.println("Choose Game Type: Debug, Regular");
        String choice = input.nextLine();
        if (choice.equals("Debug") || choice.equals("debug")) {
            gameType = "debug";
        } else if (choice.equals("Regular") || choice.equals("regular")) {
            gameType = "regular";
        } else {
            System.out.println("Invalid Input Default to Regular");
            gameType = "regular";
        }
        valid = false;

        while (!valid) { //get starting position & create mines with it
            System.out.println("Enter Starting Position: '{x} {y}'");
            Scanner scan = new Scanner(System.in);
            String[] inputMove = scan.nextLine().split(" ");
            try{
                int startRow = Integer.parseInt(inputMove[0]);
                int startCol = Integer.parseInt(inputMove[1]);
                if ((startRow >= 0 && startRow < numRows) && (startCol >= 0 && startCol < numCols)) { //if coordinates are valid set them as x and y and create mines
                    x = startRow;
                    y = startCol;
                    valid = true;
                    mine.createMines(startRow, startCol, flags);
                } else {
                    System.out.println("Invalid Coordinates Try Again");
                }
            } catch (NumberFormatException e){
                System.out.println("Invalid Coordinates Try Again");
            }
        }

        valid = false;
        mine.evaluateField();
        mine.revealMines(x, y); //reveal first mine around starting position
        mine.guess(x, y, false); //reveal starting position
        if (gameType.equals("debug")) { //print field based off debug or regular mode
            mine.printMinefield();
        } else {
            System.out.println(mine);
        }

        //game loop
        while (!mine.gameOver()) {
            System.out.println("Input Guess Coordinates: '{} {}', or Input 'F {x} {y}' to Place Flag (" + flags + " Left)");
            Scanner scan = new Scanner(System.in);
            String[] inputGuess = scan.nextLine().split(" ");
            if (inputGuess[0].equals("F")) { //if the user inputs 'F' in guess
                if (flags > 0) { //if there are flags left
                    x = Integer.parseInt(inputGuess[1]);
                    y = Integer.parseInt(inputGuess[2]);
                    if(mine.guess(x, y, true)){  //make a guess with flag
                        correctGuesses++;
                    }
                    flags--; //decrease flag amount
                } else { //if not enough flags
                    System.out.println("Not Enough Flags Try Again");
                }
            } else { //if field guess
                x = Integer.parseInt(inputGuess[0]);
                y = Integer.parseInt(inputGuess[1]);
                if (x < 0 || y < 0 || x >= numRows || y >= numCols) { //if inputted coordinates are out of bounds, return to top of loop
                    System.out.println("Invalid Coordinates Try Again");
                    continue;
                }
                if (mine.guess(x, y, false)) { //if guess == true, you hit a mine and break out of game loop
                    loser = 1; //set variable loser to one to signify to future statement that user lost game
                    if (gameType.equals("debug")) {
                        mine.printMinefield();
                    } else {
                        System.out.println(mine);
                    }
                    break;
                }
            }
            if (gameType.equals("debug")) {
                mine.printMinefield();
            } else {
                System.out.println(mine);
            }
        }
        if (loser == 1){
            System.out.println("You Lost! You Hit A Mine");
        }
        else {
            System.out.println("You Won! All Flags Correct");
        }
        System.out.println("Game Over!");
    }
}