package chess.engine.board;

import chess.engine.pieces.Piece;

public class Tile {
    private Piece piece;
    private boolean isOccupied;

    public Tile() {
    }

    public Piece getPiece() {
        return piece;
    }

    public void setPiece(Piece piece) {
        if (piece == null) {
            this.isOccupied = false;
            this.piece = null;
        } else {
            this.isOccupied = true;
            this.piece = piece;
        }
    }

    public boolean isOccupied() {
        return isOccupied;
    }

    @Override
    public String toString() {
        if (piece != null) {
            return piece.toString(); // If there's a piece, display its information
        } else {
            return "_"; // If no piece, display an underscore
        }
    }
}
