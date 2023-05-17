// Caleb Yosef: yosef006
// Adam Shabaneh: shaba070
public class Rook {
    private int row;
    private int col;
    private boolean isBlack;
    public Rook(int row, int col, boolean isBlack){
        this.row = row;
        this.col = col;
        this.isBlack = isBlack;
    }
    public boolean isMoveLegal(Board board, int endRow, int endCol){
        if (board.verifySourceAndDestination(row, col, endRow, endCol, isBlack))
            if (board.verifyHorizontal(row, col, endRow, endCol))
                return true;
            else if (board.verifyVertical(row, col, endRow, endCol))
                return true;
        return false;
    }
}
