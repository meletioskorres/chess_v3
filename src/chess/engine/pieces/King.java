package chess.engine.pieces;

import chess.engine.Alliance;
import chess.engine.board.Board;
import chess.engine.board.Move;
import chess.engine.board.Tile;
import chess.engine.gui.GuiBoard;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

import static chess.engine.board.Move.NormalMove;
import static chess.engine.pieces.PieceUtil.isValidMove;

public class King extends Piece{
    Boolean isInCheck;
    public King(Alliance alliance, int row, int col) {
        super(alliance, PieceType.KING, row, col);
        this.sprite = sheet.getSubimage(0 * sheetScale, alliance == Alliance.WHITE ? 0 : sheetScale, sheetScale, sheetScale)
                .getScaledInstance(GuiBoard.TILE_SIZE, GuiBoard.TILE_SIZE, Image.SCALE_SMOOTH);
    }

    @Override
    public void calculateLegalMoves(Board board) {
        List<Move> legalMoves = new ArrayList<>();
        Tile[][] tiles = board.getChessBoard();
        int[][] directions = {{0, 1}, {0, -1}, {1, 0}, {-1, 0},{-1, -1}, {-1, 1}, {1, -1}, {1, 1}};

        for (int[] direction : directions) {
            int dRow = direction[0];
            int dCol = direction[1];
            int newRow = getRow() + dRow;
            int newCol = getCol() + dCol;

            if (isValidMove(newRow, newCol)) {
                Tile destinationTile = tiles[newRow][newCol];
                Piece pieceAtDestination = destinationTile.getPiece();
                if (destinationTile.isOccupied() && pieceAtDestination.getAlliance() != this.getAlliance()) {
                    legalMoves.add(new NormalMove(getCol(), getRow(), newCol, newRow));
                } else if (!destinationTile.isOccupied()){
                    legalMoves.add(new NormalMove(getCol(), getRow(), newCol, newRow));
                }
            }
        }
        setLegalMoves(legalMoves);
    }

    @Override
    public Piece copy() {
        return new King(this.getAlliance(), this.getRow(), this.getCol());
    }
}
