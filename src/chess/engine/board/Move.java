package chess.engine.board;

import java.util.Objects;

public class Move {
    private final int startCol;
    private final int startRow;
    private final int endCol;
    private final int endRow;
    private final boolean isCapture;

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

    public int getStartCol() {
        return startCol;
    }

    public int getStartRow() {
        return startRow;
    }

    public int getEndCol() {
        return endCol;
    }

    public int getEndRow() {
        return endRow;
    }

    public boolean isCapture() {
        return isCapture;
    }
}
