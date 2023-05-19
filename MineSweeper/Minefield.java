import java.util.LinkedList;
import java.util.Queue;
import java.util.Stack;
import java.util.Random;

public class Minefield {
    private int numRows;
    private int numCols;
    private int numFlags;
    private Cell[][] field;
    /**
     * Global Section
     */
    public static final String ANSI_YELLOW = "\u001B[33m";
    public static final String ANSI_BLUE_BRIGHT = "\u001b[34;1m";
    public static final String ANSI_BLUE = "\u001b[34m";
    public static final String ANSI_RED_BRIGHT = "\u001b[31;1m";
    public static final String ANSI_RED = "\u001b[31m";
    public static final String ANSI_GREEN = "\u001b[32m";
    public static final String ANSI_GREY_BG = "\u001b[0m";

    /**
     * Constructor
     *
     * @param rows    Number of rows.
     * @param columns Number of columns.
     * @param flags   Number of flags, should be equal to mines
     */
    public Minefield(int rows, int columns, int flags) {
        this.numRows = rows;
        this.numCols = columns;
        this.numFlags = flags;
        field = new Cell[numRows][numCols];
        for (int i = 0; i < numRows; i++){ //initializes each coordinate with a cell
            for (int j = 0; j < numCols; j++){
                field[i][j] = new Cell(false, "0");
            }
        }
    }

    /**
     * evaluateField
     *
     * @function When a mine;'' is found in the field, calculate the surrounding 9x9 tiles values. If a mine is found, increase the count for the square.
     */
    public void evaluateField() {
        for (int i = 0; i < numRows; i++) {
            for (int j = 0; j < numCols; j++) {
                if (field[i][j].getStatus().equals("M")){ //checks for the 8 coordinates around the cell
                    if ((i - 1) >= 0 && (j - 1) >= 0 && (!field[i - 1][j - 1].getStatus().equals("M"))) {
                        field[i - 1][j - 1].setStatus(helperCoversion(field[i - 1][j - 1].getStatus()));
                    }
                    if ((i - 1) >= 0 && (!field[i - 1][j].getStatus().equals("M"))){
                        field[i - 1][j].setStatus(helperCoversion(field[i - 1][j].getStatus()));
                    }
                    if ((i - 1) >= 0 && (j + 1) < numCols && (!field[i - 1][j + 1].getStatus().equals("M"))){
                        field[i - 1][j + 1].setStatus(helperCoversion(field[i - 1][j + 1].getStatus()));
                    }
                    if ((j + 1) < numCols && (!field[i][j + 1].getStatus().equals("M"))){
                        field[i][j + 1].setStatus(helperCoversion(field[i][j + 1].getStatus()));
                    }
                    if ((i + 1) < numRows && (j + 1) < numCols && (!field[i + 1][j + 1].getStatus().equals("M"))){
                        field[i + 1][j + 1].setStatus(helperCoversion(field[i + 1][j + 1].getStatus()));
                    }
                    if ((i + 1) < numRows && (!field[i + 1][j].getStatus().equals("M"))){
                        field[i + 1][j].setStatus(helperCoversion(field[i + 1][j].getStatus()));
                    }
                    if ((i + 1) < numRows && (j - 1) >= 0 && (!field[i + 1][j - 1].getStatus().equals("M"))){
                        field[i + 1][j - 1].setStatus(helperCoversion(field[i + 1][j - 1].getStatus()));
                    }
                    if ((j - 1) >= 0 && (!field[i][j - 1].getStatus().equals("M"))){
                        field[i][j - 1].setStatus(helperCoversion(field[i][j - 1].getStatus()));
                    }
                }
            }
        }
    }
    public String helperCoversion(String s1){
        int x = Integer.parseInt(s1);
        x = x + 1;
        return Integer.toString(x);
    }


    /**
     * createMines
     *
     * @param x     Start x, avoid placing on this square.
     * @param y     Start y, avoid placing on this square.
     * @param mines Number of mines to place.
     */
    public void createMines(int x, int y, int mines) {
        for (int i = 0; i < mines; i++) {  //loop through cells to place mines
            Random randNum = new Random();
            int randRow = randNum.nextInt(numRows);  //create random ints for row and cols
            int randCol = randNum.nextInt(numCols);
            while (field[randRow][randCol].getStatus().equals("M") || (randRow == x && randCol == y)) {
                randRow = randNum.nextInt(numRows); //while there is already a mine in the random coordinates or the coordinates are the starting positions, create new random coordinates
                randCol = randNum.nextInt(numCols);
            }
            field[randRow][randCol].setStatus("M"); //set the cell to a mine
        }
    }

    /**
     * guess
     *
     * @param x    The x value the user entered.
     * @param y    The y value the user entered.
     * @param flag A boolean value that allows the user to place a flag on the corresponding square.
     * @return boolean Return false if guess did not hit mine or if flag was placed, true if mine found.
     */
    public boolean guess(int x, int y, boolean flag) {
        if (x >= numRows || x < 0 || y >= numCols || y < 0) { //check if guess is out of bounds and return false if so
            return false;
        }
        //placing flag
        if (flag && (numFlags > 0)) { //if user wants to place flag and there are flags left, return true if cell is a mine
            field[x][y].setStatus("F");
            if (field[x][y].getStatus().equals("M")) {
                field[x][y].setRevealed(true);
                numFlags--;
                return true;
            }
            field[x][y].setRevealed(true); //if guess is wrong place flag and return false
            numFlags--;
            return false;
        }
        //not placing flag
        else if (!flag && field[x][y].getStatus().equals("0")) { //if user doesn't want to place flag, call revealZeroes method
            revealZeroes(x, y);
            field[x][y].setRevealed(true);
        } else if (!flag && field[x][y].getStatus().equals("M")) {
            field[x][y].setRevealed(true);
            return true;
        } else {
            field[x][y].setRevealed(true);
        }
        return false;
    }

    /**
     * gameOver
     *
     * @return boolean Return false if game is not over and squares have yet to be revealed, otherwise return true.
     */
    public boolean gameOver() {
        for (int i = 0; i < numRows; i++) {
            for (int j = 0; j < numCols; j++) {
                if (!field[i][j].getRevealed()) { //if there are any fields that haven't been revealed game is not over
                    return false;
                }
            }
        }
        return true;
    }

    /**
     * revealField
     * <p>
     * This method should follow the psuedocode given.
     * Why might a stack be useful here rather than a queue?
     *
     * @param x The x value the user entered.
     * @param y The y value the user entered.
     */
    public void revealZeroes(int x, int y) {
        Stack<int[]> stk = new Stack<>();
        stk.push(new int[]{x, y});
        while (!stk.empty()) {
            int[] cords = stk.pop();
            int xCord = cords[0];
            int yCord = cords[1];
            if (field[xCord][yCord].getStatus().equals("0")) { //if cell is equal to 0, reveal it
                field[xCord][yCord].setRevealed(true);
            }
            if ((xCord - 1 >= 0 && xCord < numRows) && field[xCord - 1][yCord].getStatus().equals("0") && !field[xCord - 1][yCord].getRevealed()) { //check for coordinate above
                stk.push(new int[]{xCord - 1, yCord});
            }
            if ((yCord > 0 && yCord + 1 < numCols) && field[xCord][yCord + 1].getStatus().equals("0") && !field[xCord][yCord + 1].getRevealed()) { //check for coordinate to right
                stk.push(new int[]{xCord, yCord + 1});
            }
            if ((xCord > 0 && xCord + 1 < numRows) && field[xCord + 1][yCord].getStatus().equals("0") && !field[xCord + 1][yCord].getRevealed()) { //check for coordinate down
                stk.push(new int[]{xCord + 1, yCord});
            }
            if ((yCord - 1>= 0 && yCord < numCols) && field[xCord][yCord - 1].getStatus().equals("0") && !field[xCord][yCord - 1].getRevealed()) { //check for coordinate to left
                stk.push(new int[]{xCord, yCord - 1});
            }
        }
    }

    /**
     * revealMines
     * <p>
     * This method should follow the psuedocode given.
     * Why might a queue be useful for this function?
     *
     * @param x The x value the user entered.
     * @param y The y value the user entered.
     */
    public void revealMines(int x, int y) {
        Queue<int[]> q = new LinkedList<>(); //initialize queue
        q.add(new int[]{x, y});
        while (!q.isEmpty()) { //while the queue has items in it, continue loop
            int[] cord = q.poll();
            int xCord = cord[0];
            int yCord = cord[1];
            if (!field[xCord][yCord].getStatus().equals("M")){ //if cell isn't a mine, reveal it
                field[xCord][yCord].setRevealed(true);
            }
            if (field[xCord][yCord].getStatus().equals("M")) { //if cell is a mine, don't reveal and return out of loop
                return;
            }
            if ((xCord - 1 >= 0 && xCord < numRows) && !field[xCord - 1][yCord].getRevealed()) { //check for coordinate above
                q.add(new int[]{xCord - 1, yCord});
            }
            if ((yCord > 0 && yCord + 1 < numCols) && !field[xCord][yCord + 1].getRevealed()) { //check for coordinate to right
                q.add(new int[]{xCord, yCord + 1});
            }
            if ((xCord > 0 && xCord + 1 < numRows) && !field[xCord + 1][yCord].getRevealed()) { //check for coordinate down
                q.add(new int[]{xCord + 1, yCord});
            }
            if ((yCord - 1 >= 0 && yCord < numCols) && !field[xCord][yCord - 1].getRevealed()) { //check for coordinate to left
                q.add(new int[]{xCord, yCord - 1});
            }
        }
    }

    /**
     * revealStart
     *
     * @param x The x value the user entered.
     * @param y The y value the user entered.
     */
    public void revealStart(int x, int y) {

    }

    /**
     * printMinefield
     *
     * @fuctnion This method should print the entire minefield, regardless if the user has guessed a square.
     * *This method should print out when debug mode has been selected.
     */
    public void printMinefield() {
        int num = 0;
        System.out.print(" ");
        while (num < numCols){
            System.out.print(" "+ num + " ");
            num++;
        }
        System.out.println();
        for (int i = 0; i < numRows; i++) {
            System.out.print(i + " ");
            for (int j = 0; j < numCols; j++) {
                if (field[i][j].getStatus().equals("0")){
                    System.out.print(ANSI_YELLOW + field[i][j].getStatus() + ANSI_GREY_BG + "  ");
                }
                else if(field[i][j].getStatus().equals("1")){
                    System.out.print(ANSI_BLUE_BRIGHT + field[i][j].getStatus() + ANSI_GREY_BG + "  ");
                }
                else if(field[i][j].getStatus().equals("2")) {
                    System.out.print(ANSI_GREEN + field[i][j].getStatus() + ANSI_GREY_BG + "  ");
                }
                else if(field[i][j].getStatus().equals("3")){
                    System.out.print(ANSI_RED + field[i][j].getStatus() + ANSI_GREY_BG + "  ");
                }
                else {
                    System.out.print(ANSI_RED_BRIGHT + field[i][j].getStatus() + ANSI_GREY_BG + "  ");
                }
            }
            System.out.print("\n");
        }
    }

    /**
     * toString
     *
     * @return String The string that is returned only has the squares that has been revealed to the user or that the user has guessed.
     */

    public String toString() {
        String s = "  ";
        int num = 0;
        while (num < numCols){
            s += " "+ num + " ";
            num++;
        }
        s += "\n";

        for (int i = 0; i < numRows; i++) {
            s += i + " ";
            for (int j = 0; j < numCols; j++) {
                if (field[i][j].getRevealed()) {
                    if (field[i][j].getStatus().equals("0")){
                        s += " " + ANSI_YELLOW + field[i][j].getStatus() + ANSI_GREY_BG + " ";
                    }
                    else if(field[i][j].getStatus().equals("1")){
                        s += " " + ANSI_BLUE_BRIGHT + field[i][j].getStatus() + ANSI_GREY_BG + " ";
                    }
                    else if(field[i][j].getStatus().equals("2")) {
                        s += " " + ANSI_GREEN + field[i][j].getStatus() + ANSI_GREY_BG + " ";
                    }
                    else if(field[i][j].getStatus().equals("3")){
                        s += " " + ANSI_RED + field[i][j].getStatus() + ANSI_GREY_BG + " ";
                    } else {
                        s += " " + ANSI_RED_BRIGHT + field[i][j].getStatus() + ANSI_GREY_BG + " ";
                    }
                } else {
                    s += " - ";
                }
            }
            s += "\n";
        }
        return s;
    }
}
