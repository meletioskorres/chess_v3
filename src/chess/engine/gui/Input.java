package chess.engine.gui;

import chess.engine.board.Board;
import chess.engine.board.Move;
import chess.engine.pieces.Piece;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import static chess.engine.board.Move.*;
import static chess.engine.gui.GuiBoard.NUMBER_OF_COLUMNS;
import static chess.engine.gui.GuiBoard.NUMBER_OF_ROWS;

public class Input extends MouseAdapter {
    GuiBoard guiBoard;
    public Input(GuiBoard guiBoard) {
        this.guiBoard = guiBoard;
    }

    @Override
    public void mousePressed(MouseEvent e) {
        int col = e.getX() / GuiBoard.TILE_SIZE;
        int row = e.getY() / GuiBoard.TILE_SIZE;
        Piece pieceXY = guiBoard.board.getPiece(row, col);
        if (pieceXY != null) {
            guiBoard.selectedPiece = pieceXY;
        }
    }

    @Override
    public void mouseDragged(MouseEvent e) {
        if (guiBoard.selectedPiece != null) {
            guiBoard.selectedPiece.setX(e.getX() - GuiBoard.TILE_SIZE / 2);
            guiBoard.selectedPiece.setY(e.getY() - GuiBoard.TILE_SIZE / 2);

            guiBoard.repaint();
        }
    }
    @Override
    public void mouseReleased(MouseEvent e) {
        Piece selectedPiece = guiBoard.selectedPiece;
        Board board = guiBoard.getBoard();
        if (selectedPiece != null && board != null) {
            int currentCol = selectedPiece.getCol();
            int currentRow = selectedPiece.getRow();

            int newCol = e.getX() / GuiBoard.TILE_SIZE;
            int newRow = e.getY() / GuiBoard.TILE_SIZE;

            if (newCol < 0 || newCol >= NUMBER_OF_COLUMNS || newRow < 0 || newRow >= NUMBER_OF_ROWS) {
                // Move the piece back to its original position
                newCol = currentCol;
                newRow = currentRow;
            }

            Piece pieceAtNewPosition = board.getPiece(newRow, newCol);
            Move move;
            if (pieceAtNewPosition != null &&
                    pieceAtNewPosition.getAlliance() != selectedPiece.getAlliance()) {
                move = new AttackMove(selectedPiece.getCol(), selectedPiece.getRow(), newCol, newRow);
            } else {
                move = new SimpleMove(selectedPiece.getCol(), selectedPiece.getRow(), newCol, newRow);
            }

            System.out.println(isValidMove(selectedPiece, move, board));
            if (isValidMove(selectedPiece, move, board)) {
                board.movePiece(selectedPiece, move);
            }
        }
        guiBoard.selectedPiece = null;
        guiBoard.repaint();
    }

    private boolean isValidMove(Piece selectedPiece, Move move, Board board) {
        for (Move legalMove : selectedPiece.getLegalMoves()) {
            if (legalMove.equals(move)) {
                return true;
            }
        }

        return false;
    }
}
