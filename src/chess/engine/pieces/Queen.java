package chess.engine.pieces;

import chess.engine.Alliance;
import chess.engine.board.Board;
import chess.engine.board.Move;
import chess.engine.board.Tile;
import chess.engine.gui.GuiBoard;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

import static chess.engine.pieces.PieceUtil.isValidMove;

public class Queen extends Piece{
    public Queen(Alliance alliance, int row, int col) {
        super(alliance, PieceType.QUEEN, row, col);
        this.sprite = sheet.getSubimage(1 * sheetScale, alliance == Alliance.WHITE ? 0 : sheetScale, sheetScale, sheetScale)
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

            while (isValidMove(newRow, newCol)) {
                Tile destinationTile = tiles[newRow][newCol];

                if (destinationTile.isOccupied()) {
                    Piece pieceAtDestination = destinationTile.getPiece();
                    if (pieceAtDestination.getAlliance() != getAlliance()) {
                        legalMoves.add(new Move.NormalMove(getCol(), getRow(), newCol, newRow));
                    }
                    break;
                } else {
                    legalMoves.add(new Move.NormalMove(getCol(), getRow(), newCol, newRow));
                }
                newRow += dRow;
                newCol += dCol;
            }
        }
        setLegalMoves(legalMoves);
    }

    @Override
    public Piece copy() {
        return new Queen(this.getAlliance(), this.getRow(),this.getCol());
    }
}
