package chess.pieces;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import chess.GameState;
import chess.Player;
import chess.Position;

/**
 * The 'Bishop' class
 */
public class Bishop extends Piece {
    public Bishop(Player owner) {
        super(owner);
    }

    @Override
    protected char getIdentifyingCharacter() {
        return 'b';
    }

    @Override
    public Collection<Position> getMoves(GameState state, Position position) {
        List<Position> positions = new ArrayList<Position>();

        positions.addAll(Util.collectMovesNorthEast(state, getOwner(), position, 8));
        positions.addAll(Util.collectMovesNorthWest(state, getOwner(), position, 8));
        positions.addAll(Util.collectMovesSouthEast(state, getOwner(), position, 8));
        positions.addAll(Util.collectMovesSouthWest(state, getOwner(), position, 8));

        return positions;
    }
}
