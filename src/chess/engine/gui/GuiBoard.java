package chess.engine.gui;

import chess.engine.board.Board;
import chess.engine.board.Move;
import chess.engine.pieces.Piece;

import javax.swing.*;
import java.awt.*;

import static chess.engine.Alliance.WHITE;

public class GuiBoard extends JPanel {
    Board board;
    public static final int TILE_SIZE = 85;
    public static final int NUMBER_OF_COLUMNS = 8;
    public static final int NUMBER_OF_ROWS = 8;
    public Piece selectedPiece;


    public GuiBoard(Board board) {
        this.board = board;
        this.setPreferredSize(new Dimension(NUMBER_OF_COLUMNS * TILE_SIZE, NUMBER_OF_ROWS * TILE_SIZE));
        Input input = new Input(this);
        addMouseListener(input);
        addMouseMotionListener(input);
    }

    public Board getBoard() {
        return board;
    }

    public void paintComponent(Graphics graphics) {
        Graphics2D graphics2D = (Graphics2D) graphics;

        paintBoard(graphics2D);

        paintLegalMoves(graphics2D);

        paintSelectedPiece(graphics2D);
    }

    private void paintBoard(Graphics2D graphics2D) {
        for (int row = 0; row < NUMBER_OF_COLUMNS; row++) {
            for (int col = 0; col < NUMBER_OF_ROWS; col++) {
                int x = col * TILE_SIZE;
                int y = row * TILE_SIZE;

                graphics2D.setColor((col + row) % 2 == 0 ? new Color(57, 100, 43)
                        : new Color(241, 195, 145));

                graphics2D.fillRect(x, y, TILE_SIZE, TILE_SIZE);

                Piece piece = board.getPiece(row, col);
                if (piece != null && piece != selectedPiece) {
                    int adjustedX = col * TILE_SIZE;
                    int adjustedY = row * TILE_SIZE;
                    graphics2D.drawImage(piece.getSprite(), adjustedX, adjustedY, this);
                }
            }
        }
    }

    private void paintLegalMoves(Graphics2D graphics2D) {
        if (selectedPiece != null) {
            for (Move move : selectedPiece.getLegalMoves()) {
                graphics2D.setColor(new Color(11, 225, 11, 128));
                graphics2D.fillRect(move.getEndCol() * TILE_SIZE, move.getEndRow() * TILE_SIZE, TILE_SIZE, TILE_SIZE);
            }
        }
    }

    private void paintSelectedPiece(Graphics2D graphics2D) {
        if (selectedPiece != null) {
            graphics2D.drawImage(selectedPiece.getSprite(), selectedPiece.getX(), selectedPiece.getY(), this);
        }
    }
}
