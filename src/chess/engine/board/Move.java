package chess.engine.board;

import chess.engine.pieces.Pawn;
import chess.engine.pieces.Piece;
import chess.engine.pieces.Queen;

import java.util.Objects;

import static chess.engine.board.MoveType.*;

public abstract class Move {
    private final int startCol;
    private final int startRow;
    private final int endCol;
    private final int endRow;
    private final MoveType moveType;

    public Move(int startCol, int startRow, int endCol, int endRow, MoveType moveType) {
        this.startRow = startRow;
        this.startCol = startCol;
        this.endRow = endRow;
        this.endCol = endCol;
        this.moveType = moveType;
    }

//    public boolean isLegal(Board board) {
//        if()
//    }

    public abstract void execute(Board board);

    @Override
    public boolean equals(Object object) {
        if (this == object) return true;
        if (object == null || getClass() != object.getClass() && !object.getClass().isAssignableFrom(getClass())) {
            return false;
        }
        Move move = (Move) object;
        return startRow == move.startRow && startCol == move.startCol && endRow == move.endRow && endCol == move.endCol;
    }

    @Override
    public int hashCode() {
        return Objects.hash(startRow, startCol, endRow, endCol);
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

    public MoveType getMoveType() {
        return moveType;
    }

    public static class NormalMove extends Move {
        public NormalMove(int startCol, int startRow, int endCol, int endRow) {
            super(startCol, startRow, endCol, endRow, Normal);
        }

        @Override
        public void execute(Board board) {
            Tile[][] chessBoard = board.getChessBoard();
            Piece pieceToMove = chessBoard[getStartRow()][getStartCol()].getPiece();
            chessBoard[getEndRow()][getEndCol()].setPiece(pieceToMove);
            chessBoard[getStartRow()][getStartCol()].setPiece(null);
            pieceToMove.setRow(getEndRow());
            pieceToMove.setCol(getEndCol());
            pieceToMove.setHasMoved(true);
        }
    }
    public static class KingSideCastle extends Move {
        public KingSideCastle(int startCol, int startRow, int endCol, int endRow) {
            super(startCol, startRow, endCol, endRow, KingSideCastle);
        }

        @Override
        public void execute(Board board) {

        }
    }
    public static class QueenSideCaste extends Move {
        public QueenSideCaste(int startCol, int startRow, int endCol, int endRow) {
            super(startCol, startRow, endCol, endRow, QueenSideCastle);
        }

        @Override
        public void execute(Board board) {

        }
    }
    public static class PawnJump extends Move {
        public PawnJump(int startCol, int startRow, int endCol, int endRow) {
            super(startCol, startRow, endCol, endRow, PawnJump);
        }

        @Override
        public void execute(Board board) {
            Tile[][] chessBoard = board.getChessBoard();
            Piece pieceToMove = chessBoard[getStartRow()][getStartCol()].getPiece();
            chessBoard[getEndRow()][getEndCol()].setPiece(pieceToMove);
            chessBoard[getStartRow()][getStartCol()].setPiece(null);
            pieceToMove.setRow(getEndRow());
            pieceToMove.setCol(getEndCol());
            pieceToMove.setHasMoved(true);
            board.setEnPassantPawn((Pawn) pieceToMove);
        }
    }
    public static class EnPassant extends Move {
        public EnPassant(int startCol, int startRow, int endCol, int endRow) {
            super(startCol, startRow, endCol, endRow, EnPassant);
        }

        @Override
        public void execute(Board board) {
            Tile[][] chessBoard = board.getChessBoard();
            Piece pieceToMove = chessBoard[getStartRow()][getStartCol()].getPiece();
            chessBoard[getEndRow()][getEndCol()].setPiece(pieceToMove);
            chessBoard[getStartRow()][getStartCol()].setPiece(null);
            pieceToMove.setRow(getEndRow());
            pieceToMove.setCol(getEndCol());
            pieceToMove.setHasMoved(true);

            int attackedPieceRow = getEndRow() + (pieceToMove.getAlliance().isWhite() ? -1 : 1);
            chessBoard[attackedPieceRow][getEndCol()].setPiece(null);
        }
    }
    public static class PawnPromotion extends Move {
        public PawnPromotion(int startCol, int startRow, int endCol, int endRow) {
            super(startCol, startRow, endCol, endRow, PawnPromotion);
        }

        @Override
        public void execute(Board board) {
            Tile[][] chessBoard = board.getChessBoard();
            Piece pieceToMove = chessBoard[getStartRow()][getStartCol()].getPiece();
            chessBoard[getEndRow()][getEndCol()].setPiece(pieceToMove);
            chessBoard[getStartRow()][getStartCol()].setPiece(null);
            pieceToMove.setRow(getEndRow());
            pieceToMove.setCol(getEndCol());
            pieceToMove.setHasMoved(true);

            board.setPiece(new Queen(pieceToMove.getAlliance(), pieceToMove.getRow(), pieceToMove.getCol()));
        }
    }
}
