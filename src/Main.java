import chess.engine.board.Board;
import chess.engine.gui.GuiBoard;

import javax.swing.*;
import java.awt.*;

public class Main {
    public static void main(String[] args) {
        Board chessBoard = new Board();

        SwingUtilities.invokeLater(() -> {
            GuiBoard guiBoard = new GuiBoard(chessBoard);

            JFrame frame = new JFrame();
            frame.getContentPane().setBackground(new Color(33, 24, 21));
            frame.setLayout(new GridBagLayout());
            frame.setMinimumSize(new Dimension(1000, 1000));
            frame.setLocationRelativeTo(null);
            frame.add(guiBoard);
            frame.setVisible(true);
        });
    }
}



