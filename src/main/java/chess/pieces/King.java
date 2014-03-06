package chess.pieces;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import chess.GameState;
import chess.Player;
import chess.Position;

/**
 * The King class
 */
public class King extends Piece {
    public King(Player owner) {
        super(owner);
    }

    @Override
    protected char getIdentifyingCharacter() {
        return 'k';
    }

    /* Preventing stack overflow */
    @Override
    public Collection<Position> getPositionsUnderAtack(GameState state, Position position) {
        List<Position> positions = new ArrayList<Position>();

        positions.addAll(Util.collectMovesNorth(state, getOwner(), position, 1));
        positions.addAll(Util.collectMovesSouth(state, getOwner(), position, 1));
        positions.addAll(Util.collectMovesEast(state, getOwner(), position, 1));
        positions.addAll(Util.collectMovesWest(state, getOwner(), position, 1));

        positions.addAll(Util.collectMovesNorthEast(state, getOwner(), position, 1));
        positions.addAll(Util.collectMovesNorthWest(state, getOwner(), position, 1));
        positions.addAll(Util.collectMovesSouthEast(state, getOwner(), position, 1));
        positions.addAll(Util.collectMovesSouthWest(state, getOwner(), position, 1));

        return positions;
    }

    @Override
    public Collection<Position> getMoves(GameState state, Position position) {
        Collection<Position> positions = getPositionsUnderAtack(state, position);

        Player another = (getOwner() == Player.White) ? Player.Black : Player.White;
        Collection<Position> positionsUnderAttack = state.getPositionsUnderAttack(another);
        positions.removeAll(positionsUnderAttack);

        return positions;
    }
}
