public class Bishop {
    private int row;
    private int col;
    private boolean isBlack;
    public Bishop(int row, int col, boolean isBlack){ //Bishop constructor
        this.row = row;
        this.col = col;
        this.isBlack = isBlack;
    }
    public boolean isMoveLegal(Board board, int endRow, int endCol){ //checks if move with bishop is legal and returns boolean
        if (board.verifySourceAndDestination(row, col, endRow, endCol, isBlack))
            if (board.verifyDiagonal(row, col, endRow, endCol))
                return true;
        return false;
    }
}
