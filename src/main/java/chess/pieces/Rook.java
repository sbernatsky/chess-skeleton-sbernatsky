package chess.pieces;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

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
    public Set<Position> getMoves(GameState state, Position position) {
        List<Position> positions = new ArrayList<Position>();

        // collect forward moves
        for (int i = position.getRow() + 1; i <= Position.MAX_ROW; i++) {
            Position p = new Position(position.getColumn(), i);
            boolean shouldBreak = collectPosition(state, positions, p);
            if(shouldBreak) {
                break;
            }
        }
        // collect backward moves
        for (int i = position.getRow() - 1; i >= Position.MIN_ROW; i--) {
            Position p = new Position(position.getColumn(), i);
            boolean shouldBreak = collectPosition(state, positions, p);
            if(shouldBreak) {
                break;
            }
        }
        // collect moves to the right
        for (char c = (char) (position.getColumn() + 1); c <= Position.MAX_COLUMN; c++) {
            Position p = new Position(c, position.getRow());
            boolean shouldBreak = collectPosition(state, positions, p);
            if(shouldBreak) {
                break;
            }
        }
        // collect moves to the left
        for (char c = (char) (position.getColumn() - 1); c >= Position.MIN_COLUMN; c--) {
            Position p = new Position(c, position.getRow());
            boolean shouldBreak = collectPosition(state, positions, p);
            if (shouldBreak) {
                break;
            }
        }

        return new HashSet<Position>(positions);
    }

    private boolean collectPosition(GameState state, List<Position> positions, Position p) {
        return Util.collectPosition(state, getOwner(), positions, p);
    }

}
