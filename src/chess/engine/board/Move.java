package chess.engine.board;

import java.util.Objects;

public class Move {
    private int startCol;
    private int startRow;
    private int endCol;
    private int endRow;
    private boolean isCapture;

    public Move(int startCol, int startRow, int endCol, int endRow, boolean isCapture) {
        this.startRow = startRow;
        this.startCol = startCol;
        this.endRow = endRow;
        this.endCol = endCol;
        this.isCapture = isCapture;
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) return true;
        if (object == null || getClass() != object.getClass()) return false;
        Move move = (Move) object;
        return startRow == move.startRow && startCol == move.startCol && endRow == move.endRow && endCol == move.endCol;
    }

    @Override
    public int hashCode() {
        return Objects.hash(startRow, startCol, endRow, endCol, isCapture);
    }

    @Override
    public String toString() {
        return "Move{" +
                "startRow=" + startRow +
                ", startCol=" + startCol +
                ", endRow=" + endRow +
                ", endCol=" + endCol +
                '}';
    }

    public int getEndCol() {
        return endCol;
    }

    public int getEndRow() {
        return endRow;
    }
}
