package chess.pieces;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import chess.GameState;
import chess.Player;
import chess.Position;

/**
 * The 'Rook' class
 */
public class Rook extends Piece {

    public Rook(Player owner) {
        super(owner);
    }

    @Override
    protected char getIdentifyingCharacter() {
        return 'r';
    }

    @Override
    public Collection<Position> getMoves(GameState state, Position position) {
        List<Position> positions = new ArrayList<Position>();

        // collect forward moves
        positions.addAll(Util.collectMovesNorth(state, getOwner(), position, 8));
        // collect backward moves
        positions.addAll(Util.collectMovesSouth(state, getOwner(), position, 8));
        // collect moves to the right
        positions.addAll(Util.collectMovesEast(state, getOwner(), position, 8));
        // collect moves to the left
        positions.addAll(Util.collectMovesWest(state, getOwner(), position, 8));

        return positions;
    }

}
