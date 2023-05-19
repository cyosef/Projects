import java.util.Scanner;
public class Game {
    public static void main(String[] args) {
        Board chessBoard = new Board();
        Fen.load("rnbqkbnr/pppppppp/8/8/8/8/PPPPPPPP/RNBQKBNR", chessBoard);
        String turn = "white"; // keeps track of whose turn it is
        Boolean valid = false; // used to check if move is valid
        Scanner scan = new Scanner(System.in);
        System.out.println("Welcome to Chess!");
        System.out.println(chessBoard);

        while (!chessBoard.isGameOver()) {  //checks to make sure game is not over
            if (turn.equals("white") && !chessBoard.isGameOver()) { //checks which turn it is and if game is over
                valid = false;
                while (valid == false) { //loops while valid is false, this way the loop continues if invalid move is inputted
                    System.out.println("It is currently white's turn to play.");
                    System.out.println("What is your move? (format: [start row] [start col] [end row] [end col])");
                    String[] inputMove = scan.nextLine().split(" ");
                    int srtRow = Integer.parseInt(inputMove[0]);
                    int srtCol = Integer.parseInt(inputMove[1]);
                    int endRow = Integer.parseInt(inputMove[2]);
                    int endCol = Integer.parseInt(inputMove[3]);

                    if (chessBoard.verifySourceAndDestination(srtRow, srtCol, endRow, endCol, false)) { //verifies that inputted locations are valid
                        if (chessBoard.getPiece(srtRow, srtCol).isMoveLegal(chessBoard, endRow, endCol)) {  //verifies that inputted move is valid
                            chessBoard.movePiece(srtRow, srtCol, endRow, endCol); //if all is true, move piece
                            valid = true; //set valid to true to end loop for this turn
                            turn = "black"; //set turn to black to allow next move to occur
                            System.out.println(chessBoard);
                        } else {
                            valid = false; // if move is not valid, valid continues to be false and loop goes again
                            turn = "white"; // move continues to be white
                            System.out.println("Invalid Move Try Again");
                        }
                    } else {System.out.println("Invalid Move Try Again");}
                }
            }
            if (turn.equals("black") && !chessBoard.isGameOver()) {
                valid = false;
                while (valid == false) {
                    System.out.println("It is currently black's turn to play.");
                    System.out.println("What is your move? (format: [start row] [start col] [end row] [end col])");
                    String[] inputMove = scan.nextLine().split(" ");
                    int srtRow = Integer.parseInt(inputMove[0]);
                    int srtCol = Integer.parseInt(inputMove[1]);
                    int endRow = Integer.parseInt(inputMove[2]);
                    int endCol = Integer.parseInt(inputMove[3]);

                    if (chessBoard.verifySourceAndDestination(srtRow, srtCol, endRow, endCol, true)) {
                        if (chessBoard.getPiece(srtRow, srtCol).isMoveLegal(chessBoard, endRow, endCol)) {
                            chessBoard.movePiece(srtRow, srtCol, endRow, endCol);
                            valid = true;
                            turn = "white";
                            System.out.println(chessBoard);
                        } else {
                            valid = false;
                            turn = "black";
                            System.out.println("Invalid Move Try Again");
                        }
                    } else {System.out.println("Invalid Move Try Again");}
                }
            }
        }
        scan.close();
        if (turn.equals("white")) //if it is white's move when game is over, black has won
            System.out.println("Black Wins");
        else
            System.out.println("White Wins");
    }
}
