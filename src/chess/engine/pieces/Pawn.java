package chess.engine.pieces;

import chess.engine.Alliance;
import chess.engine.board.Board;
import chess.engine.board.Move;
import chess.engine.board.Tile;
import chess.engine.gui.GuiBoard;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

import static chess.engine.Alliance.*;
import static chess.engine.board.Move.*;
import static chess.engine.pieces.Piece.PieceType.*;
import static chess.engine.pieces.PieceUtil.isValidMove;

public class Pawn extends Piece {

    public Pawn(Alliance alliance, int row, int col) {
        super(alliance, PAWN, row, col);
        this.sprite = sheet.getSubimage(5 * sheetScale, alliance == WHITE ? 0 : sheetScale, sheetScale, sheetScale)
                .getScaledInstance(GuiBoard.TILE_SIZE, GuiBoard.TILE_SIZE, Image.SCALE_SMOOTH);
    }

    @Override
    public void calculateLegalMoves(Board board) {

        List<Move> legalMoves = new ArrayList<>();
        Tile[][] tiles = board.getChessBoard();

        int dRow = this.getAlliance().isWhite() ? 1 : -1;
        int newRow = getRow() + dRow;
        int newCol = getCol();

        if (isValidMove(newRow, newCol) && !tiles[newRow][newCol].isOccupied()) {
            legalMoves.add(new SimpleMove(getCol(), getRow(), newCol, newRow));

            newRow += dRow;

            if (!this.hasMoved() && isValidMove(newRow, newCol) && !tiles[newRow][newCol].isOccupied()) {
                legalMoves.add(new PawnJump(getCol(), getRow(), newCol, newRow));
            }
        }

        int[] attackCols = {-1, 1};
        for (int attackCol : attackCols) {
            int attackRow = getRow() + dRow;
            int attackColIdx = getCol() + attackCol;
            if (isValidMove(attackRow, attackColIdx) && tiles[attackRow][attackColIdx].isOccupied()) {
                Piece piece = tiles[attackRow][attackColIdx].getPiece();
                if (piece.getAlliance() != this.getAlliance()) {
                    legalMoves.add(new AttackMove(getCol(), getRow(), attackColIdx, attackRow));
                }
            }

            if (board.getEnPassantPawn() != null
                    && board.getEnPassantPawn().getRow() == this.getRow()) {
                if (board.getEnPassantPawn().getCol() == attackColIdx) {
                    legalMoves.add(new EnPassantAttack(getCol(), getRow(), attackColIdx, attackRow));
                }
            }
        }



        setLegalMoves(legalMoves);
    }
}
