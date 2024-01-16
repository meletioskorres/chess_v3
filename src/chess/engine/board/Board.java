package chess.engine.board;

import chess.engine.Alliance;
import chess.engine.board.Move.EnPassantAttack;
import chess.engine.board.Move.PawnJump;
import chess.engine.pieces.*;
import chess.engine.players.Player;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static chess.engine.gui.GuiBoard.NUMBER_OF_COLUMNS;
import static chess.engine.gui.GuiBoard.NUMBER_OF_ROWS;
import static chess.engine.Alliance.*;

public class Board {
    private final Tile[][] chessBoard;
    private final Player whitePlayer;
    private final Player blackPlayer;
    private List<Move> allMoves;
    private List<Move> whiteMoves = new ArrayList<>();
    private List<Move> blackMoves = new ArrayList<>();
    private Pawn enPassantPawn;
    private Alliance currentPlayerTurn;

    public Board() {
        whitePlayer = new Player();
        blackPlayer = new Player();

        this.currentPlayerTurn = WHITE;

        chessBoard = new Tile[8][8];
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                chessBoard[i][j] = new Tile();
            }
        }
        //White Pieces
        setPiece(new Rook(WHITE, 0, 0));
        setPiece(new Knight(WHITE, 0, 1));
        setPiece(new Bishop(WHITE, 0, 2));
        setPiece(new King(WHITE, 0, 3));
        setPiece(new Queen(WHITE, 0, 4));
        setPiece(new Bishop(WHITE, 0, 5));
        setPiece(new Knight(WHITE, 0, 6));
        setPiece(new Rook(WHITE, 0, 7));
        //White Pawns
        setPiece(new Pawn(WHITE,1,0));
        setPiece(new Pawn(WHITE,1,1));
        setPiece(new Pawn(WHITE,1,2));
        setPiece(new Pawn(WHITE,1,3));
        setPiece(new Pawn(WHITE,1,4));
        setPiece(new Pawn(WHITE,1,5));
        setPiece(new Pawn(WHITE,1,6));
        setPiece(new Pawn(WHITE,1,7));

        //Black Pieces
        setPiece(new Rook(BLACK, 7, 0));
        setPiece(new Knight(BLACK, 7, 1));
        setPiece(new Bishop(BLACK, 7, 2));
        setPiece(new King(BLACK, 7, 3));
        setPiece(new Queen(BLACK, 7, 4));
        setPiece(new Bishop(BLACK, 7, 5));
        setPiece(new Knight(BLACK, 7, 6));
        setPiece(new Rook(BLACK, 7, 7));
        //Black Pawns
        setPiece(new Pawn(BLACK,6,0));
        setPiece(new Pawn(BLACK,6,1));
        setPiece(new Pawn(BLACK,6,2));
        setPiece(new Pawn(BLACK,6,3));
        setPiece(new Pawn(BLACK,6,4));
        setPiece(new Pawn(BLACK,6,5));
        setPiece(new Pawn(BLACK,6,6));
        setPiece(new Pawn(BLACK,6,7));

        calculateAllLegalMoves();
    }

    private void setPiece(Piece piece) {
        chessBoard[piece.getRow()][piece.getCol()].setPiece(piece);
    }

    public Piece getPiece(int row, int col) {
        return getChessBoard()[row][col].getPiece();
    }

    public Tile[][] getChessBoard() {
        return chessBoard;
    }

    public Player getWhitePlayer() {
        return whitePlayer;
    }

    public Player getBlackPlayer() {
        return blackPlayer;
    }

    public void movePiece(Piece pieceToMove,Move move) {
        if (enPassantPawn != null && pieceToMove.getAlliance() == enPassantPawn.getAlliance()) {
            setEnPassantPawn(null);
        }

        if (pieceToMove.getPieceType() == Piece.PieceType.PAWN
        && move instanceof PawnJump) {
            setEnPassantPawn((Pawn) pieceToMove);
        }

        if (move instanceof EnPassantAttack) {
          int attackedPieceCol = move.getEndCol();
          int attackedPieceRow = move.getEndRow() + (pieceToMove.getAlliance().isWhite() ? -1 : 1);
          chessBoard[attackedPieceRow][attackedPieceCol].setPiece(null);

        }
        Tile[][] tiles = getChessBoard();

        // Move the piece to the new position
        tiles[move.getEndRow()][move.getEndCol()].setPiece(pieceToMove);
        // Clear the starting position
        tiles[move.getStartRow()][move.getStartCol()].setPiece(null);

        // Update the piece's internal position
        pieceToMove.setCol(move.getEndCol());
        pieceToMove.setRow(move.getEndRow());

        pieceToMove.setHasMoved(true);
        calculateAllLegalMoves();
        printBoard();
    }

    private void calculateAllLegalMoves() {
        blackMoves = new ArrayList<>();
        whiteMoves = new ArrayList<>();
        for (int row = 0; row < NUMBER_OF_ROWS; row++) {
            for (int col = 0; col < NUMBER_OF_COLUMNS; col++) {
                Piece pieceAtRowCol = chessBoard[row][col].getPiece();
                if (pieceAtRowCol != null) {
                    pieceAtRowCol.calculateLegalMoves(this);
                    if (pieceAtRowCol.getAlliance() == WHITE) {
                        whiteMoves.addAll(pieceAtRowCol.getLegalMoves());
                    } else {
                        blackMoves.addAll(pieceAtRowCol.getLegalMoves());
                    }
                }
            }
        }
    }

    public void printBoard() {
        Tile[][] tiles = getChessBoard();
        for (int row = 0; row < NUMBER_OF_ROWS; row++) {
            for (int col = 0; col < NUMBER_OF_COLUMNS; col++) {
                Piece piece = tiles[row][col].getPiece();
                if (piece != null) {
                    System.out.print(piece.getPieceType().toString().charAt(0) + " "); // Or print piece details
                } else {
                    System.out.print("_" + " "); // Print empty space if no piece
                }
            }
            System.out.println(); // Move to the next line after each row
        }
    }

    public Alliance getCurrentPlayerTurn() {
        return currentPlayerTurn;
    }

    public void setCurrentPlayerTurn(Alliance currentPlayerTurn) {
        this.currentPlayerTurn = currentPlayerTurn;
    }

    public Pawn getEnPassantPawn() {
        return enPassantPawn;
    }

    public void setEnPassantPawn(Pawn enPassantPawn) {
        this.enPassantPawn = enPassantPawn;
    }
}
