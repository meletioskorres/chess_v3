package Test.Pieces.Rook;

import chess.engine.board.Board;
import chess.engine.Alliance;
import chess.engine.pieces.Rook;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class RookTest {
    private Board board;
    private Rook rook;

    @BeforeEach
    public void setup() {
        board = new Board();
        rook = new Rook(Alliance.WHITE, 3, 3);
        board.getChessBoard()[3][3].setPiece(rook);
    }

    @Test
    public void testLegalMovesEmptyBoard() {
        assertEquals(14, rook.getLegalMoves().size(),
                "Rook should have 14 legal moves on an empty board from center position");

    }
}