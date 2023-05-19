public class Knight {
    private int row;
    private int col;
    private boolean isBlack;

    public Knight(int row, int col, boolean isBlack) {
        this.row = row;
        this.col = col;
        this.isBlack = isBlack;
    }
    public boolean isMoveLegal(Board board, int endRow, int endCol){
        int rowDif = Math.abs(endRow - row);
        int colDif = Math.abs(endCol - col);

        if (board.verifySourceAndDestination(this.row, this.col, endRow, endCol, this.isBlack))
            if ((rowDif == 2 && colDif == 1) || (rowDif == 1 && colDif == 2))
                if(board.getPiece(endRow, endCol) == null || board.getPiece(endRow, endCol).getIsBlack() != isBlack)
                    return true;
    return false;
    }
}
