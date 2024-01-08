package chess.engine.pieces;

public class PieceUtil {

    public static boolean isValidMove(int row, int col) {
        return row >= 0 && row < 8 && col >= 0 && col < 8;
    }
}
