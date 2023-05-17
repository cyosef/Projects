// Caleb Yosef: yosef006
// Adam Shabaneh: shaba070
public class Queen {
    private int row;
    private int col;
    private boolean isBlack;
    public Queen(int row, int col, boolean isBlack){
        this.row = row;
        this.col = col;
        this.isBlack = isBlack;
    }
    public boolean isMoveLegal(Board board, int endRow, int endCol){
        if (board.verifySourceAndDestination(row, col, endRow, endCol, isBlack))
            if (board.verifyVertical(row, col, endRow, endCol))
                return true;
            else if (board.verifyDiagonal(row, col, endRow, endCol))
                return true;
            else return board.verifyHorizontal(row, col, endRow, endCol);
        return false;
    }
}
