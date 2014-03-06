package chess.pieces;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import chess.GameState;
import chess.Player;
import chess.Position;

/**
 * The Pawn
 */
public class Pawn extends Piece {
    public Pawn(Player owner) {
        super(owner);
    }

    @Override
    protected char getIdentifyingCharacter() {
        return 'p';
    }

    @Override
    public Collection<Position> getPositionsUnderAtack(GameState state, Position position) {
        int nextRow = (Player.White == getOwner()) ? position.getRow() + 1 : position.getRow() - 1;
        Position takeLeft = new Position((char) (position.getColumn() - 1), nextRow); 
        Position takeRight = new Position((char) (position.getColumn() + 1), nextRow); 

        List<Position> positions = new ArrayList<Position>();

        if (takeLeft.isValid() && state.getOwnerAt(takeLeft) != null && state.getOwnerAt(takeLeft) != getOwner()) {
            positions.add(takeLeft);
        }

        if (takeRight.isValid() && state.getOwnerAt(takeRight) != null && state.getOwnerAt(takeRight) != getOwner()) {
            positions.add(takeRight);
        }

        return positions;
    }

    @Override
    public Collection<Position> getMoves(GameState state, Position position) {
        Position nextRow = (Player.White == getOwner())
                ? new Position(position.getColumn(), position.getRow() + 1)
                : new Position(position.getColumn(), position.getRow() - 1);
        Position firstMoveRow = (Player.White == getOwner())
                ? new Position(position.getColumn(), position.getRow() + 2)
                : new Position(position.getColumn(), position.getRow() -2);

        List<Position> positions = new ArrayList<Position>();

        if (nextRow.isValid() && state.getOwnerAt(nextRow) == null) {
            positions.add(nextRow);
        }

        // can make large move only from initial position and when next position is empty
        if (isInitialPosition(position)
                && firstMoveRow.isValid() && state.getOwnerAt(firstMoveRow) == null
                && !positions.isEmpty()) {
            positions.add(firstMoveRow);
        }

        positions.addAll(getPositionsUnderAtack(state, position));

        return positions;
    }

    private boolean isInitialPosition(Position position) {
        return (Player.White == getOwner()) ? position.getRow() == 2 : position.getRow() == 7;
    }
}
