package chess;


import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import chess.pieces.Bishop;
import chess.pieces.King;
import chess.pieces.Knight;
import chess.pieces.Pawn;
import chess.pieces.Piece;
import chess.pieces.Queen;
import chess.pieces.Rook;

/**
 * Class that represents the current state of the game.  Basically, what pieces are in which positions on the
 * board.
 */
public class GameState {

    /**
     * The current player
     */
    private Player currentPlayer = Player.White;

    /**
     * A map of board positions to pieces at that position
     */
    private Map<Position, Piece> positionToPieceMap = new HashMap<Position, Piece>();

    public Player getCurrentPlayer() {
        return currentPlayer;
    }

    /**
     * Call to initialize the game state into the starting positions
     */
    public void reset() {
        // White Pieces
        placePiece(new Rook(Player.White), new Position("a1"));
        placePiece(new Knight(Player.White), new Position("b1"));
        placePiece(new Bishop(Player.White), new Position("c1"));
        placePiece(new Queen(Player.White), new Position("d1"));
        placePiece(new King(Player.White), new Position("e1"));
        placePiece(new Bishop(Player.White), new Position("f1"));
        placePiece(new Knight(Player.White), new Position("g1"));
        placePiece(new Rook(Player.White), new Position("h1"));
        placePiece(new Pawn(Player.White), new Position("a2"));
        placePiece(new Pawn(Player.White), new Position("b2"));
        placePiece(new Pawn(Player.White), new Position("c2"));
        placePiece(new Pawn(Player.White), new Position("d2"));
        placePiece(new Pawn(Player.White), new Position("e2"));
        placePiece(new Pawn(Player.White), new Position("f2"));
        placePiece(new Pawn(Player.White), new Position("g2"));
        placePiece(new Pawn(Player.White), new Position("h2"));

        // Black Pieces
        placePiece(new Rook(Player.Black), new Position("a8"));
        placePiece(new Knight(Player.Black), new Position("b8"));
        placePiece(new Bishop(Player.Black), new Position("c8"));
        placePiece(new Queen(Player.Black), new Position("d8"));
        placePiece(new King(Player.Black), new Position("e8"));
        placePiece(new Bishop(Player.Black), new Position("f8"));
        placePiece(new Knight(Player.Black), new Position("g8"));
        placePiece(new Rook(Player.Black), new Position("h8"));
        placePiece(new Pawn(Player.Black), new Position("a7"));
        placePiece(new Pawn(Player.Black), new Position("b7"));
        placePiece(new Pawn(Player.Black), new Position("c7"));
        placePiece(new Pawn(Player.Black), new Position("d7"));
        placePiece(new Pawn(Player.Black), new Position("e7"));
        placePiece(new Pawn(Player.Black), new Position("f7"));
        placePiece(new Pawn(Player.Black), new Position("g7"));
        placePiece(new Pawn(Player.Black), new Position("h7"));
    }

    /**
     * Get the piece at the position specified by the String
     * @param colrow The string indication of position; i.e. "d5"
     * @return The piece at that position, or null if it does not exist.
     */
    public Piece getPieceAt(String colrow) {
        Position position = new Position(colrow);
        return getPieceAt(position);
    }

    /**
     * Get the piece at a given position on the board
     * @param position The position to inquire about.
     * @return The piece at that position, or null if it does not exist.
     */
    public Piece getPieceAt(Position position) {
        return positionToPieceMap.get(position);
    }

    /**
     * Method to place a piece at a given position
     * @param piece The piece to place
     * @param position The position
     */
    private void placePiece(Piece piece, Position position) {
        positionToPieceMap.put(position, piece);
    }

    /**
     * Get the owner at a given position on the board
     * @param position The position to inquire about.
     * @return The owner of a piece at that position, or null if it does not exist.
     */
    public Player getOwnerAt(Position position) {
        Piece piece = getPieceAt(position);
        return (piece != null) ? piece.getOwner() : null;
    }

    /** Returns moves available for current player */
    public Collection<Move> getMoves() {
        // 1. get all possible moves for current player
        // 2. filter out moves which make current player king under attack
        Collection<Move> tmp = new ArrayList<Move>();
        for (Entry<Position, Piece> entry : positionToPieceMap.entrySet()) {
            if (entry.getValue().getOwner() != currentPlayer) {
                continue;
            }

            Collection<Position> moves = entry.getValue().getMoves(this, entry.getKey());
            for (Position move : moves) {
                tmp.add(new Move(entry.getKey(), move));
            }
        }

        Collection<Move> result = new ArrayList<Move>();
        for (Move move : tmp) {
            if (checkMove(move.getFrom(), move.getTo())) {
                result.add(move);
            }
        }
        return result;
    }

    /** Check if the move makes current player king to be under attack. */
    private boolean checkMove(Position from, Position to) {
        // 1 backup state
        // 2 make a move
        // 3 check if king is under attack
        // 4 restore state

        Piece piece = positionToPieceMap.remove(from);
        Piece anotherPiece = positionToPieceMap.remove(to);
        placePiece(piece, to);

        boolean result = !isKingUnderAttack(currentPlayer);

        positionToPieceMap.remove(to);
        if (anotherPiece != null) {
            placePiece(anotherPiece, to);
        }
        placePiece(piece, from);

        return result;
    }

    private boolean isKingUnderAttack(Player player) {
        Player anotherPlayer = (Player.White == player) ? Player.Black : Player.White;
        Position kingPosition = getKingPosition(player);
        Collection<Position> positionsUnderAttack = getPositionsUnderAttack(anotherPlayer);
        return positionsUnderAttack.contains(kingPosition);
    }

    /** Finds king position for specified player. */
    private Position getKingPosition(Player player) {
        for (Entry<Position, Piece> entry : positionToPieceMap.entrySet()) {
            Piece piece = entry.getValue();
            if (piece.getOwner() == player && piece instanceof King) {
                return entry.getKey();
            }
        }

        throw new IllegalArgumentException(String.format("no king found for %s player", player));
    }

    /** Returns state for the current player. */
    public State getPlayerState() {
        // check if king for current player is under check
        // if it is check if current player has legal moves
        State result = isKingUnderAttack(currentPlayer) ? State.Check : State.Normal;
        if (result == State.Check) {
            if (getMoves().isEmpty()) {
                result = State.Checkmate;
            }
        }
        return result;
    }

    /** Makes user move: moves piece from one location to another and changes current player. */
    public void move(Position from, Position to) {
        Piece piece = getPieceAt(from);
        if (piece == null || piece.getOwner() != currentPlayer) {
            throw new NoPieceFoundException(from);
        }

        Collection<Position> moves = piece.getMoves(this, from);
        if (!moves.contains(to)) {
            throw new IllegalMoveException(from, to);
        }

        if (!checkMove(from, to)) {
            throw new IllegalMoveException(from, to);
        }

        positionToPieceMap.remove(from);
        placePiece(piece, to);
        currentPlayer = (Player.White == currentPlayer) ? Player.Black : Player.White;
    }

    /** Returns positions under attack for specified player. */
    public Collection<Position> getPositionsUnderAttack(Player player) {
        Collection<Position> result = new ArrayList<Position>();
        for (Entry<Position, Piece> entry : positionToPieceMap.entrySet()) {
            if (entry.getValue().getOwner() != player) {
                continue;
            }

            Collection<Position> positions = entry.getValue().getPositionsUnderAtack(this, entry.getKey());
            result.addAll(positions);
        }

        return result;
    }


    @SuppressWarnings("serial")
    public static class NoPieceFoundException extends RuntimeException {

        public NoPieceFoundException(Position position) {
            super(String.format("No piece found at %s", position));
        }
    }

    @SuppressWarnings("serial")
    public static class IllegalMoveException extends RuntimeException {

        public IllegalMoveException(Position from, Position to) {
            super(String.format("Illegal move: %s %s", from, to));
        }
    }

    public static enum State {
        Normal, Check, Checkmate;
    }
}
