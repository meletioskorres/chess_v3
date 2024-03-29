package chess.engine.gui;

import chess.engine.Alliance;
import chess.engine.board.Board;
import chess.engine.board.Move;
import chess.engine.pieces.Piece;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

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

        // Adjust the calculations based on the flipped state
        if (guiBoard.isFlipped()) {
            col = GuiBoard.NUMBER_OF_COLUMNS - col - 1;
            row = GuiBoard.NUMBER_OF_ROWS - row - 1;
        }

        Piece pieceXY = guiBoard.board.getPiece(row, col);

        if (pieceXY != null
                && pieceXY.getAlliance() == guiBoard.board.getCurrentPlayerTurn()) {
            guiBoard.selectedPiece = pieceXY;
        } else {
            guiBoard.selectedPiece = null;
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
        if (selectedPiece != null && board != null
                && selectedPiece.getAlliance() == board.getCurrentPlayerTurn()) {
            int currentCol = selectedPiece.getCol();
            int currentRow = selectedPiece.getRow();

            int newCol = e.getX() / GuiBoard.TILE_SIZE;
            int newRow = e.getY() / GuiBoard.TILE_SIZE;

            // Adjust the coordinates based on the flipped state
            if (guiBoard.isFlipped()) {
                newCol = GuiBoard.NUMBER_OF_COLUMNS - newCol - 1;
                newRow = GuiBoard.NUMBER_OF_ROWS - newRow - 1;
            }

            if (newCol < 0 || newCol >= NUMBER_OF_COLUMNS || newRow < 0 || newRow >= NUMBER_OF_ROWS) {
                // Move the piece back to its original position
                newCol = currentCol;
                newRow = currentRow;
            }
            board.movePiece(selectedPiece, newRow, newCol);
        }
        guiBoard.selectedPiece = null;
        guiBoard.repaint();
    }


}
