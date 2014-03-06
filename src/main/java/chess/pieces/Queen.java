package chess.pieces;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import chess.GameState;
import chess.Player;
import chess.Position;

/**
 * The Queen
 */
public class Queen extends Piece{
    public Queen(Player owner) {
        super(owner);
    }

    @Override
    protected char getIdentifyingCharacter() {
        return 'q';
    }

    @Override
    public Collection<Position> getMoves(GameState state, Position position) {
        List<Position> positions = new ArrayList<Position>();

        positions.addAll(Util.collectMovesNorth(state, getOwner(), position, 8));
        positions.addAll(Util.collectMovesSouth(state, getOwner(), position, 8));
        positions.addAll(Util.collectMovesEast(state, getOwner(), position, 8));
        positions.addAll(Util.collectMovesWest(state, getOwner(), position, 8));

        positions.addAll(Util.collectMovesNorthEast(state, getOwner(), position, 8));
        positions.addAll(Util.collectMovesNorthWest(state, getOwner(), position, 8));
        positions.addAll(Util.collectMovesSouthEast(state, getOwner(), position, 8));
        positions.addAll(Util.collectMovesSouthWest(state, getOwner(), position, 8));

        return positions;
    }
}
