public class Board {

    // Instance variables
    private Piece[][] board;

    public Board() { //board constructor and sets up 8 by 8 board for chess game
        board = new Piece[8][8];
    }

    public Piece getPiece(int row, int col) {
        return board[row][col];
    }

    public void setPiece(int row, int col, Piece piece) {
        board[row][col] = piece;
    }

    // Game functionality methods
    public boolean movePiece(int startRow, int startCol, int endRow, int endCol) { //moves chess piece at inputted location to another inputted location
        if (board[startRow][startCol].isMoveLegal(this, endRow, endCol) && board[startRow][startCol] != null) { // checks to make sure move is legal and end position is empty before moving piece
            board[endRow][endCol] = board[startRow][startCol];
            board[startRow][startCol].setPosition(endRow, endCol);
            board[startRow][startCol] = null;
            return true;
        }
        return false;
    }

    public boolean isGameOver() { //checks to see if game is over based on there being 2 kings still on the board
        int numKings = 0;
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                if (board[i][j] != null && (board[i][j].getCharacter() == '\u265a' || board[i][j].getCharacter() == '\u2654')) {
                    numKings++;
                }
            }
        }
        return numKings < 2;
    }

    public String toString() {
        String[] str = {"\uFF10", "\uFF11", "\uFF12", "\uFF13", "\uFF14", "\uFF15", "\uFF16", "\uFF17", "\u2001"}; // array of that holds first number of each row to be indexed and added to final board
//        String num2 =  " \u0020\uFF10\u0020\uFF11\u0020\uFF12\u0020\uFF13\u0020\uFF14\u0020\uFF15\u0020\uFF16\u0020\uFF17\n";
        String num2 = "\t\t";
        for (int i = 0; i < 8; i++){
            num2 += i + "\t\t";
        }
        num2 += "\n";
        String boardLines = "";
        for (int i = 0; i < 8; i++) {
            boardLines += str[i] + "\t"; //loops through board and adds row number to board
            for (int j = 0; j < 8; j++) {
                if (board[i][j] == null) // adds an empty board space if location on board is empty
                    boardLines += "|\t\t";
                else
                    boardLines += "|\t" + board[i][j].getCharacter() +"\t"; //adds a board space with piece that corresponds to board array
                if (j == 7)
                    boardLines += "|\n";
            }
        }
        return num2 + boardLines;
    }

    public void clear() { //clears entire board of all pieces
        for (int i = 0; i < 8; i++)
            for (int j = 0; j < 8; j++) {
                board[i][j] = null;
            }
    }

    // Movement helper functions
    public boolean verifySourceAndDestination(int startRow, int startCol, int endRow, int endCol, boolean isBlack) {
        if ((startRow > -1) && (startRow < 8) && (startCol > -1) && (startCol < 8) && (endRow > -1) && (endRow < 8) && (endCol > -1) && (endCol < 8)) { //checks if all start and end positions are within board
            if ((getPiece(startRow, startCol) != null) && (board[startRow][startCol].getIsBlack() == isBlack)) { //checks if start position is null and the piece in the start piece and input piece are the same piece
                return (getPiece(endRow, endCol) == null) || (board[endRow][endCol].getIsBlack() != isBlack);
            }
        }
        return false;
    }

    public boolean verifyAdjacent(int startRow, int startCol, int endRow, int endCol) {
        return (Math.abs(startRow - endRow) <= 1) && (Math.abs(startCol - endCol) <= 1); //checks if start/end positions are next to each other
    }

    public boolean verifyHorizontal(int startRow, int startCol, int endRow, int endCol) { //verifies that a horizontal move is legal
        if (startRow == endRow) { //checks to make sure start end rows for move are in the same row
            for (int i = (Math.min(startCol, endCol) + 1); i < Math.max(startCol, endCol); i++) {
                if (board[startRow][i] != null)
                    return false;
            }
            return true;
        }
        return false;
    }

    public boolean verifyVertical(int startRow, int startCol, int endRow, int endCol) { //verifies that a vertical move is legal
        if (startCol == endCol) { //checks to ensure start and end column for move are in same column
            for (int i = (Math.min(startRow, endRow) + 1); i < Math.max(startRow, endRow); i++) {
                if (board[startCol][i] != null)
                    return false;
            }
            return true;
        }
        return false;
    }

    public boolean verifyDiagonal(int startRow, int startCol, int endRow, int endCol) { //verifies that a diagonal move is legal
        int colChange = startCol - endCol;
        int rowChange = startRow - endRow;

        if (Math.abs(colChange) != Math.abs(rowChange)) {
            return false;
        }
        if (rowChange < 0 && colChange < 0) { // piece moving from left DOWN to right
            for (int i = 1; i < Math.abs(rowChange); i++) {
                if (getPiece(startRow + i, startCol + i) != null)
                    return false;
            }
        }
        if (rowChange > 0 && colChange < 0) { //piece moving from left UP to right
            for (int i = 1; i < Math.abs(rowChange); i++) {
                if (getPiece(startRow - i, startCol + i) != null)
                    return false;
            }
        }
        if (rowChange < 0 && colChange > 0) { //piece moving from right DOWN to left
            for (int i = 1; i < Math.abs(rowChange); i++) {
                if (getPiece(startRow + i, startCol - i) != null)
                    return false;
            }
        }
        if (rowChange > 0 && colChange > 0) { //piece moving from right UP to left
            for (int i = 1; i < Math.abs(rowChange); i++) {
                if (getPiece(startRow - i, startCol - i) != null)
                    return false;
            }
        }
        return true;
    }
}
