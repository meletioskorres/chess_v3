package chess.engine.pieces;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.List;
import java.util.Objects;

import chess.engine.Alliance;
import chess.engine.board.Board;
import chess.engine.board.Move;
import chess.engine.gui.GuiBoard;

import javax.imageio.ImageIO;

public abstract class Piece {
    private final Alliance alliance;
    private PieceType pieceType;
    private boolean hasMoved;
    private int row;
    private int col;
    public int X, Y;
    private List<Move> legalMoves;
    Image sprite;

    public Piece(Alliance alliance, PieceType pieceType, int row, int col) {
        this.alliance = alliance;
        this.pieceType = pieceType;
        this.hasMoved = false;
        this.row = row;
        this.col = col;
        this.X = col * GuiBoard.TILE_SIZE;
        this.Y = row * GuiBoard.TILE_SIZE;
    }

    public Alliance getAlliance() {
        return alliance;
    }

    public PieceType getPieceType() {
        return pieceType;
    }

    public void setPieceType(PieceType pieceType) {
        this.pieceType = pieceType;
    }

    public boolean hasMoved() {
        return hasMoved;
    }

    public void setHasMoved(boolean hasMoved) {
        this.hasMoved = hasMoved;
    }

    public int getRow() {
        return row;
    }

    public void setRow(int row) {
        this.row = row;
    }

    public int getCol() {
        return col;
    }

    public void setCol(int col) {
        this.col = col;
    }

    public Image getSprite() {
        return sprite;
    }

    public int getX() {
        return X;
    }

    public void setX(int xPos) {
        this.X = xPos;
    }

    public int getY() {
        return Y;
    }

    public void setY(int yPos) {
        this.Y = yPos;
    }

    public List<Move> getLegalMoves() {
        return legalMoves;
    }

    public void setLegalMoves(List<Move> legalMoves) {
        this.legalMoves = legalMoves;
    }

    public abstract void calculateLegalMoves(Board board);

    BufferedImage sheet;
    {
        try{
            sheet = ImageIO.read(Objects.requireNonNull(ClassLoader.getSystemResourceAsStream("pieces.png")));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    protected int sheetScale = sheet.getWidth()/6;
    public enum PieceType{
        KING,
        QUEEN,
        BISHOP,
        KNIGHT,
        ROOK,
        PAWN
    }
}
